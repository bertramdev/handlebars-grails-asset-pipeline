grails.project.work.dir = 'target'

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		grailsPlugins()
		mavenCentral()
		jcenter()
	}

	dependencies {
		compile "com.bertramlabs.plugins:handlebars-asset-pipeline:2.6.1"
		compile 'org.mozilla:rhino:1.7R4'
	}

	plugins {

		runtime ":asset-pipeline:2.6.1"
		

		build ':release:3.1.1', ':rest-client-builder:2.0.1', {
			export = false
		}
	}
}
