package grails.app.request.stats

import ar.com.orkodev.requestStat.metric.AppMetric
import grails.util.Holders

class GrailsAppRequestStatsGrailsPlugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.1 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "grails-app/views/index.gsp",
        "grails-app/views/error404.gsp",
        "**/demo/**",
    ]

    def profiles = ['web']

    def title = "Grails App Request Stats Plugin" // Headline display name of the plugin
    def author = "Mariano Kfuri"
    def authorEmail = "marianoekfuri@gmail.com"
    def description = '''\
Grails plugin to show request stats
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/orkonano/grails-app-request-stats/wiki"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
    def developers = [ [ name: "Mariano Kfuri", email: "marianoekfuri@gmail.com" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "GitHub", url: "https://github.com/orkonano/grails-app-request-stats/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/orkonano/grails-app-request-stats.wiki.git" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        Float boundValue
        if (Holders.grailsApplication.config?.requestStats?.metric?.lowerBound instanceof Number) {
            boundValue = Holders.grailsApplication.config.requestStats.metric.lowerBound as Float
        }

        if (!boundValue){
            GroovyClassLoader classLoader = new GroovyClassLoader(getClass().getClassLoader())
            ConfigObject defaultConfig
            try {
                defaultConfig = new ConfigSlurper().parse(classLoader.loadClass('DefaultAppRequestStatsConfig'))
                boundValue = defaultConfig.requestStats.metric.lowerBound as Float
            } catch (Exception e) {
                throw RuntimeException(e)
            }
        }

        appMetric(AppMetric){
            grailsApplication = ref('grailsApplication')
            lowerBound = boundValue
        }

    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
