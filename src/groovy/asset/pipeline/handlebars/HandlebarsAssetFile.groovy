package asset.pipeline.handlebars

class HandlebarsAssetFile {
	static final String contentType = 'application/javascript'
	static extensions = ['handlebars', 'hbs']
	static final String compiledExtension = 'js'
	static processors = [HandlebarsProcessor]

	File file
	def baseFile

	HandlebarsAssetFile(file, baseFile=null) {
		this.file = file
		this.baseFile = baseFile
	}

	def processedStream() {
		println "Processing handlebars"
		def fileText = file?.text
		for(processor in processors) {
			def processInstance = processor.newInstance()
			fileText = processInstance.process(fileText, this)
		}
		return fileText
		// Return File Stream
	}

	def directiveForLine(line) {
		return null
		// line.find(/#=(.*)/) { fullMatch, directive -> return directive }
	}
}
