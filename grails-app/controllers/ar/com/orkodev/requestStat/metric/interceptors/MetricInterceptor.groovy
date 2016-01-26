package ar.com.orkodev.requestStat.metric.interceptors

import ar.com.orkodev.requestStat.metric.AppMetric
import ar.com.orkodev.requestStat.metric.ControllerMetric
import ar.com.orkodev.requestStat.metric.Metric


class MetricInterceptor {

    def appMetric
    private static final String START_TIME_ATTRIBUTE = 'Controller__START_TIME__'
    private static final String AFTER_TIME_ATTRIBUTE = 'Controller__AFTER_TIME__'

    MetricInterceptor() {
        matchAll().excludes(controller: "metric")
                .excludes(controller: "favicon")
                .excludes(controller: "images")
    }

    boolean before() {
        long start = System.currentTimeMillis()
        request[START_TIME_ATTRIBUTE] = start
        appMetric.incrementAccess(controllerName, actionName)
        true
    }

    boolean after() {
        long start = request[START_TIME_ATTRIBUTE]
        long end = System.currentTimeMillis()
        def timeProcess = end - start
        request[AFTER_TIME_ATTRIBUTE] = end
        appMetric.addTimeProcessor(controllerName, actionName, timeProcess)
        def controller = AppMetric.procesarControllerName(controllerName)
        def action = ControllerMetric.procesarActionName(actionName)
        Metric actualMetric = appMetric.controllersMetrics.get(controller)?.actionMetrics?.get(action)
        if (model == null){
            model = new HashMap()
        }
        if (actualMetric){
            model.put('metric', actualMetric)
        }
        model.put('metricTime', timeProcess)
        true
    }

    void afterView() {
        if (throwable != null) {
            appMetric.addException(controllerName, actionName)
        }else{
            long start = request[AFTER_TIME_ATTRIBUTE]
            long end = System.currentTimeMillis()
            def timeProcess = end - start
            appMetric.addRenderTimeProcessor(controllerName, actionName, timeProcess)
        }
    }
}
