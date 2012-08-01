class Cuaderno19GrailsPlugin {
    def version = "1.0"
    def grailsVersion = "1.3.6 > *"
    def dependsOn = [:]
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Cuaderno19 Plugin" 
    def author = "Jesús J. Ballano"
    def authorEmail = "jjballano@gmail.com"
    def description = '''\
Cuaderno19 is a file needed to order a payment to a bank in Spain. \
It can be done very easy with this plugin.\
It just makes a simple Cuaderno19, but it can be completed so easy
'''

    def documentation = "https://github.com/jjballano/Cuaderno19/blob/master/README"

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
