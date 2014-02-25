eventAssetPrecompileStart = { assetConfig ->
	assetConfig.specs << 'asset.pipeline.handlebars.HandlebarsAssetFile'
	if(!config.grails.assets.plugin."handlebars-asset-pipeline".excludes || config.grails.assets.plugin."handlebars-asset-pipeline".excludes.size() == 0) {
		config.grails.assets.plugin."handlebars-asset-pipeline".excludes = ["handlebars/**/*"]
	}
}
