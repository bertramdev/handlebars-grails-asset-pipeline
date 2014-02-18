import org.codehaus.groovy.grails.commons.ApplicationHolder


eventAssetPrecompileStart = { assetConfig ->
	assetConfig.specs << 'asset.pipeline.handlebars.HandlebarsAssetFile'
	def grailsApplication = ApplicationHolder.getApplication()
	if(!grailsApplication.config.grails.assets.plugin."handlebars-asset-pipeline".excludes || grailsApplication.config.grails.assets.plugin."handlebars-asset-pipeline".excludes.size() == 0) {
		grailsApplication.config.grails.assets.plugin."handlebars-asset-pipeline".excludes = ["handlebars/**/*"]
	}
}
