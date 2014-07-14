import ar.com.orkodev.requestStat.metric.AppMetric

/**
 * Created by orko on 14/07/14.
 */
beans = {


    xmlns context:"http://www.springframework.org/schema/context"
    context.'component-scan'('base-package': "ar.com.orkodev.requestStat")

    appMetric(AppMetric){
        grailsApplication = ref('grailsApplication')
    }

}