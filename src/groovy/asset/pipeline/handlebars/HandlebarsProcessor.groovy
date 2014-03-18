package asset.pipeline.handlebars
import asset.pipeline.AssetHelper
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable
import org.springframework.core.io.ClassPathResource
import grails.util.Holders

class HandlebarsProcessor {

  Scriptable globalScope
  ClassLoader classLoader
  def precompilerMode
  HandlebarsProcessor(precompiler=false){
    try {
      this.precompilerMode = precompiler ? true : false
      classLoader = getClass().getClassLoader()

      def handlebarsJsResource = new ClassPathResource('asset/pipeline/handlebars/handlebars.js', classLoader)


      def handlebarsJsStream = handlebarsJsResource.inputStream

      Context cx = Context.enter()
      cx.setOptimizationLevel(-1)
      globalScope = cx.initStandardObjects()
      cx.evaluateReader(globalScope, new InputStreamReader(handlebarsJsStream, 'UTF-8'), handlebarsJsResource.filename, 0, null)
    } catch (Exception e) {
      throw new Exception("Handlebars Engine initialization failed.", e)
    } finally {
      try {
        Context.exit()
      } catch (IllegalStateException e) {}
    }
  }

  def process(input, assetFile) {
    try {
      def cx = Context.enter()
      def compileScope = cx.newObject(globalScope)
      compileScope.setParentScope(globalScope)
      compileScope.put("handlebarsSrc", compileScope, input)
      def result = cx.evaluateString(compileScope, "Handlebars.precompile(handlebarsSrc)", "Handlebars compile command", 0, null)
      return wrapTemplate(templateNameForFile(assetFile), result)
    } catch (Exception e) {
      throw new Exception("""
        Handlebars Engine compilation of handlebars to javascript failed.
        $e
        """)
    } finally {
      Context.exit()
    }
  }

  def templateNameForFile(assetFile) {
    def grailsApplication  = Holders.grailsApplication
    def templateRoot       = grailsApplication.config.grails.assets.handlebars.templateRoot ?: 'templates'
    def templateSeperator  = grailsApplication.config.grails.assets.handlebars.templatePathSeperator ?: '/'
    def relativePath       = relativePath(assetFile.file, templateRoot, templateSeperator)
    def templateName           = AssetHelper.nameWithoutExtension(assetFile.file.getName())
    if(relativePath) {
      templateName = [relativePath,templateName].join(templateSeperator)
    }
    return templateName
  }

  def relativePath(file, templateRoot, templateSeperator) {
    def path          = file.getParent().split(AssetHelper.QUOTED_FILE_SEPARATOR)
    def startPosition = path.findLastIndexOf{ it == templateRoot }

    if(startPosition+1 >= path.length) {
      return ""
    }

    path = path[(startPosition+1)..-1]
    return path.join(templateSeperator)
  }

  def wrapTemplate = { String templateName, String compiledTemplate ->
    """
    (function(){
        var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
        templates['$templateName'] = template($compiledTemplate);
    }());
    """
  }
}
