class Cuaderno19GrailsPlugin {
    def version = "0.1"
    def grailsVersion = "1.3.6 > *"
    def dependsOn = [:]
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Cuaderno19 Plugin" 
    def author = "Jesús J. Ballano"
    def authorEmail = "jjballano@gmail.com"
    def description = '''\
Ayuda a la generación del Cuaderno19 necesario para la domiciliación de recibos en España.
'''

    def documentation = "http://grails.org/plugin/cuaderno19"

    def license = "APACHE"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

    def onShutdown = { event ->
    }
}
