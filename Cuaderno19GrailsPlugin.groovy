class Cuaderno19GrailsPlugin {
    def version = "1.0"
    def grailsVersion = "2.0 > *"
    def dependsOn = [:]
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Cuaderno19 Plugin" 
    def author = "Jesús J. Ballano"
    def authorEmail = "jjballano@gmail.com"
    def organization = [ name: "BeCodeMyFriend", url: "http://becodemyfriend.com" ]
    def issueManagement = [ system: "GITHUB", url: "https://github.com/jjballano/Cuaderno19/issues" ]

    def description = '''\
Cuaderno19 is a file needed to order a payment to a bank in Spain. \
It can be done very easy with this plugin.\
It just makes a simple Cuaderno19, but it can be completed so easy\
\
Grails version is 2.0 or later. It is not tested on previous versions\

The source code is available in https://github.com/jjballano/Cuaderno19. Contributions are welcome
'''

    def documentation = "https://github.com/jjballano/Cuaderno19/blob/master/README"

    def license = "APACHE"

}
