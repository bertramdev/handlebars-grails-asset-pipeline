package asset.pipeline.handlebars

import asset.pipeline.CacheManager
import asset.pipeline.AssetHelper


class HandlebarsAssetFile {
	static final String contentType = 'application/javascript'
	static extensions = ['handlebars', 'hbs']
	static final String compiledExtension = 'js'
	static processors = [HandlebarsProcessor]


	// Cache For MD5
	static cache = [:]

	File file
	def baseFile
	def encoding

	HandlebarsAssetFile(file, baseFile=null) {
		this.file = file
		this.baseFile = baseFile
	}

	def processedStream(precompiler=false) {
		def fileText
		if(baseFile?.encoding || encoding) {
			fileText = file?.text(baseFile?.encoding ? baseFile.encoding : encoding)
		} else {
			fileText = file?.text
		}

		def md5 = AssetHelper.getByteDigest(fileText.bytes)
		if(!precompiler) {
			def cache = CacheManager.findCache(file.canonicalPath, md5)
			if(cache) {
				return cache
			}
		}

		for(processor in processors) {
			def processInstance = processor.newInstance(precompiler)
			fileText = processInstance.process(fileText, this)
		}


		if(!precompiler) {
			CacheManager.createCache(file.canonicalPath,md5,fileText)
		}

		return fileText
		// Return File Stream
	}


	def directiveForLine(line) {
		return null
		// line.find(/#=(.*)/) { fullMatch, directive -> return directive }
	}
}
