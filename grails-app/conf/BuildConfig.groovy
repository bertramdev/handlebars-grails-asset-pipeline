grails.project.work.dir = 'target'

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		grailsPlugins()
		mavenCentral()
	}

	dependencies {
		compile "com.bertramlabs.plugins:handlebars-asset-pipeline:2.1.3"
	}

	plugins {

		runtime ":asset-pipeline:2.2.0"


		build ':release:3.0.1', ':rest-client-builder:2.0.1', {
			export = false
		}
	}
}
