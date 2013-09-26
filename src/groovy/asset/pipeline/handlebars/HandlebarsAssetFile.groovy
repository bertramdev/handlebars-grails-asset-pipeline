package asset.pipeline.handlebars
<<<<<<< HEAD
import asset.pipeline.CacheManager
import asset.pipeline.AssetHelper
=======
import java.security.MessageDigest
>>>>>>> version bumping handlebars

class HandlebarsAssetFile {
	static final String contentType = 'application/javascript'
	static extensions = ['handlebars', 'hbs']
	static final String compiledExtension = 'js'
	static processors = [HandlebarsProcessor]


	// Cache For MD5
	static cache = [:]

	File file
	def baseFile

	HandlebarsAssetFile(file, baseFile=null) {
		this.file = file
		this.baseFile = baseFile
	}

	def processedStream(precompiler=false) {
		def fileText = file?.text
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


	def md5Sum(file) {
		MessageDigest md = MessageDigest.getInstance("MD5")
		md.update(file.bytes)
		def checksum = md.digest()
		return checksum.encodeHex().toString()
	}

	def directiveForLine(line) {
		return null
		// line.find(/#=(.*)/) { fullMatch, directive -> return directive }
	}
}
