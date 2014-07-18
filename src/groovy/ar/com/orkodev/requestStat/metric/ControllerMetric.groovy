package ar.com.orkodev.requestStat.metric

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Created by orko on 14/07/14.
 */
class ControllerMetric extends Metric{
    static final String DEFAULT_NAME = "sin_nombre"
    static final int DEFAULT_ACTION_CANT = 10

    ConcurrentMap<String, Metric> actionMetrics

    public ControllerMetric(){
        actionMetrics = new ConcurrentHashMap((int)Math.ceil((double) DEFAULT_ACTION_CANT / 0.75))
    }

    def addTimeProcesor(String actionName, long time){
        super.addTimeProcessor(time)
        Metric actionMetric = getActionMetricByName(actionName)
        actionMetric.addTimeProcessor(time)
    }

    private Metric getActionMetricByName(actionName){
        actionName = procesarActionName(actionName)
        Metric actionMetric = new Metric(name: actionName, lowerBound: this.lowerBound)
        actionMetrics.putIfAbsent(actionName, actionMetric) ?: actionMetric
    }

    def getAllActionMetric(){
        actionMetrics.values()
    }

    public static String procesarActionName(actionName){
        (actionName ?: DEFAULT_NAME).toLowerCase()
    }

    def addRenderTimeProcessor(String actionName, long time) {
        super.addRenderTimeProcessor(time)
        Metric actionMetric = getActionMetricByName(actionName)
        actionMetric.addRenderTimeProcessor(time)
    }

    def addException(actionName){
        super.addException()
        Metric actionMetric = getActionMetricByName(actionName)
        actionMetric.addException()
    }

    def incrementAccess(actionName){
        super.incrementAccess()
        Metric actionMetric = getActionMetricByName(actionName)
        actionMetric.incrementAccess()
    }

}
