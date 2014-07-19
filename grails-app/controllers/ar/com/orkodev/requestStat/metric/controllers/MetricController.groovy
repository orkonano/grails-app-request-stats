package ar.com.orkodev.requestStat.metric.controllers

import static org.springframework.http.HttpStatus.NOT_FOUND

class MetricController {

    def appMetric

    def index() {
        render view: 'index', model: [appMetric: appMetric]
    }

    def metric(String controllerName){
        if (controllerName){
            render view: 'metric', model: [controllerMetric: appMetric.controllersMetrics.get(controllerName)]
        }else{
            response.status = 404
            render view: '/error404'
        }
    }
}
