package asset.pipeline.handlebars

import asset.pipeline.CacheManager
import asset.pipeline.AssetHelper
import asset.pipeline.AbstractAssetFile


class HandlebarsAssetFile extends AbstractAssetFile {
	static final String contentType = 'application/javascript'
	static extensions = ['handlebars', 'hbs']
	static final String compiledExtension = 'js'
	static processors = [HandlebarsProcessor]

	String directiveForLine(String line) {
		return null
	}
}
