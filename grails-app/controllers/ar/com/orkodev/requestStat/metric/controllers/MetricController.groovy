package ar.com.orkodev.requestStat.metric.controllers

class MetricController {

    def appMetric
    def sessionFactory

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

    def hibernateStats(){
        render view: 'hibernateStats', model: [statistitcs: sessionFactory.getStatistics()]
    }
}
