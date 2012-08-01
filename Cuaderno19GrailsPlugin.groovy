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
    Cuaderno 19" is a standard electronic document format used by Spanish banks. It is normally used by bank customers to request a recursive payment (domiciliación) from a final customer or client.\
    This plugin can be used to generate a basic Cuaderno 19 request, but it is very easy to customize it to suit your purposes.\
    The plugin is compatible with Grails version 2.0 or later and it has not been tested on previous versions.\

    The source code and documentation is available in https://github.com/jjballano/Cuaderno19. Contributions are welcome
    '''

    def documentation = "https://github.com/jjballano/Cuaderno19/blob/master/README.md"

    def license = "Beerware"

}
