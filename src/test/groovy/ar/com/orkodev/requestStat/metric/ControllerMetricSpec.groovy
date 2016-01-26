package ar.com.orkodev.requestStat.metric

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class ControllerMetricSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test contructor"() {
        when: "the contructor is called"
        ControllerMetric metric = new ControllerMetric()
        then: "Verify the default value of the attribute"
        !metric.name
        metric.totalAccess == 0
        metric.timeProcessor == 0
        metric.renderTimeProcessor == 0
        metric.lastTimeProcessor == 0
        metric.lastRenderTimeProcessor == 0
        metric.totalException == 0
        !metric.actionMetrics
        metric.actionMetrics != null
        !metric.lowerBound
    }

    void "test all total properties"() {
        given:
        ControllerMetric metric = new ControllerMetric(lowerBound: 1, name: "Pepe")
        Random rand = new Random()
        int max = 100
        def timesProcessor = []
        def renderTimes = []
        def exceptionTimes = [4, 6, 8, 16, 31]
        def actionsName = ["first action", "second action", "third action"]
        long maxAccess = 32
        (1..maxAccess).each {
            timesProcessor << rand.nextInt(max + 1)
            renderTimes << rand.nextInt(max + 1)
        }

        Long totalProccesor = timesProcessor.inject(){ acc, number ->
            acc += number
        }

        Long totalRender = renderTimes.inject(){ acc, number ->
            acc += number
        }

        when: "Call to all method to view the complete scene"
        actionsName.each { actionName ->
            (1..maxAccess).each { actionTime ->
                metric.addTimeProcessor(actionName, timesProcessor[actionTime.intValue() - 1])
                metric.addRenderTimeProcessor(actionName, renderTimes[actionTime.intValue() - 1])
                metric.incrementAccess(actionName)
                if (exceptionTimes.contains(actionTime.intValue())){
                    metric.addException(actionName)
                }
            }
        }

        Metric actionMetric = metric.allActionMetric[0]

        then: "Verify the totalTimeProcessor properties"
        Long totalActionTimeProcessor = totalProccesor + totalRender
        Long totalControllerProcessor = totalActionTimeProcessor * actionsName.size()
        Long totalRenderController = totalRender * actionsName.size()
        Integer maxControllerAccess = maxAccess * actionsName.size()
        Integer controllersException = exceptionTimes.size() * actionsName.size()
        metric.totalTimeProcessor == totalControllerProcessor
        metric.lastTotalTimeProcessor == timesProcessor.last() + renderTimes.last()
        metric.totalAvg == (Double)(totalControllerProcessor / maxControllerAccess)
        metric.renderAvg == (Double)(totalRenderController / maxControllerAccess)
        metric.totalException == controllersException
        metric.exceptionPercentage == (Double)((controllersException / maxControllerAccess) * 100)
        !metric.isOverBound()
        metric.allActionMetric.size() == actionsName.size()

        actionMetric.totalTimeProcessor == totalActionTimeProcessor
        actionMetric.lastTotalTimeProcessor == timesProcessor.last() + renderTimes.last()
        actionMetric.totalAvg == (Double)(totalActionTimeProcessor / maxAccess)
        actionMetric.renderAvg == (Double)(totalRender / maxAccess)
        actionMetric.totalException == exceptionTimes.size()
        actionMetric.exceptionPercentage == (Double)((exceptionTimes.size() / maxAccess) * 100)
        !actionMetric.isOverBound()

        when: "When the lowerBound is to low"
        metric.lowerBound = 0.0001F
        then: "the lower bound is false"
        metric.isOverBound()
    }
}
