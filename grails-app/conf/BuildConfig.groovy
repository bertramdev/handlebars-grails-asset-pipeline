grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		grailsPlugins()
		mavenCentral()
	}

	dependencies {
		compile "com.bertramlabs.plugins:handlebars-asset-pipeline:2.1.0"
	}

	plugins {

		runtime ":asset-pipeline:2.1.0"


		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
