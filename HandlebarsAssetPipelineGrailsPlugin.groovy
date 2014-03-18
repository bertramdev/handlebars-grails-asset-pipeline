import asset.pipeline.AssetHelper
import asset.pipeline.handlebars.HandlebarsAssetFile

class HandlebarsAssetPipelineGrailsPlugin {

    def version = "1.3.0.3"

    def grailsVersion = "2.0 > *"
    def title = "Handlebars Asset-Pipeline Plugin"
    def author = "David Estes"
    def authorEmail = "destes@bcap.com"
    def description = "Provides Handlebars precompiler support for the asset-pipeline static asset management plugin."
    def documentation = "http://github.com/bertramdev/handlebars-grails-asset-pipeline"

    def license = "APACHE"
    def organization = [ name: "Bertram Capital", url: "http://www.bertramcapital.com/" ]
    def issueManagement = [ system: "GITHUB", url: "http://github.com/bertramdev/handlebars-grails-asset-pipeline/issues" ]
    def scm = [ url: "http://github.com/bertramdev/handlebars-grails-asset-pipeline" ]

    def doWithDynamicMethods = { ctx ->
        AssetHelper.assetSpecs << HandlebarsAssetFile
    }
}
