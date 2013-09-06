// includeTargets << new File(assetPipelinePluginDir, "scripts/_AssetCompile.groovy")

eventAssetPrecompileStart = { assetSpecs ->
	def handlebarsAssetFile = classLoader.loadClass('asset.pipeline.handlebars.HandlebarsAssetFile')
	assetSpecs.specs << handlebarsAssetFile
}
