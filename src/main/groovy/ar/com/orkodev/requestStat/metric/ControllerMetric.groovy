package ar.com.orkodev.requestStat.metric

import groovy.transform.CompileStatic

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Created by orko on 14/07/14.
 */
@CompileStatic
class ControllerMetric extends Metric{
    static final String DEFAULT_NAME = "sin_nombre"
    static final int DEFAULT_ACTION_CANT = 10

    ConcurrentMap<String, Metric> actionMetrics

    public ControllerMetric(){
        actionMetrics = new ConcurrentHashMap((int)Math.ceil((double) DEFAULT_ACTION_CANT / 0.75))
    }

    void addTimeProcessor(String actionName, long time){
        super.addTimeProcessor(time)
        Metric actionMetric = getActionMetricByName(actionName)
        actionMetric.addTimeProcessor(time)
    }

    private Metric getActionMetricByName(String actionName){
        actionName = procesarActionName(actionName)
        Metric actionMetric = new Metric(name: actionName, lowerBound: this.lowerBound)
        actionMetrics.putIfAbsent(actionName, actionMetric) ?: actionMetric
    }

    Collection<Metric> getAllActionMetric(){
        actionMetrics.values()
    }

    public static String procesarActionName(String actionName){
        (actionName ?: DEFAULT_NAME).toLowerCase()
    }

    void addRenderTimeProcessor(String actionName, long time) {
        super.addRenderTimeProcessor(time)
        Metric actionMetric = getActionMetricByName(actionName)
        actionMetric.addRenderTimeProcessor(time)
    }

    void addException(String actionName){
        super.addException()
        Metric actionMetric = getActionMetricByName(actionName)
        actionMetric.addException()
    }

    void incrementAccess(String actionName){
        super.incrementAccess()
        Metric actionMetric = getActionMetricByName(actionName)
        actionMetric.incrementAccess()
    }

}
