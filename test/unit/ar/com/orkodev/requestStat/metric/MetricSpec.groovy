package ar.com.orkodev.requestStat.metric

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class MetricSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test contructor"() {
        when: "the contructor is called"
        Metric metric = new Metric()
        then: "Verify the default value of the attribute"
        !metric.name
        metric.totalAccess == 0
        metric.timeProcessor == 0
        metric.renderTimeProcessor == 0
        metric.lastTimeProcessor == 0
        metric.lastRenderTimeProcessor == 0
        metric.totalException == 0
        !metric.lowerBound
    }

    void "test addProcesorTime"() {
        given:
        Metric metric = new Metric()

        when: "the addProcessorTime is called"
        Long time1 = 5
        metric.addTimeProcessor(time1)
        then: "Verify the timeProcessor atribute and the lastTimeProcessor"
        metric.timeProcessor == time1
        metric.lastTimeProcessor == time1

        when: "the addProcessorTime is called again"
        Long time2 = 32
        metric.addTimeProcessor(time2)
        then: "Verify the timeProcessor atribute and the lastTimeProcessor"
        metric.timeProcessor == time1 + time2
        metric.lastTimeProcessor == time2

    }

    void "test incrementAccess"() {
        given:
        Metric metric = new Metric()

       when: "the incrementAccess is called so many time"
        (1..32).each {
            metric.incrementAccess()
        }
        then: "Verify the totalAccess atribute"
        metric.totalAccess == 32
    }

    void "test addRenderTimeProcessor"() {
        given:
        Metric metric = new Metric()

        when: "the addProcessorTime is called"
        Long time1 = 5
        metric.addRenderTimeProcessor(time1)
        then: "Verify the timeProcessor atribute and the lastTimeProcessor"
        metric.renderTimeProcessor == time1
        metric.lastRenderTimeProcessor == time1

        when: "the addProcessorTime is called again"
        Long time2 = 32
        metric.addRenderTimeProcessor(time2)
        then: "Verify the timeProcessor atribute and the lastTimeProcessor"
        metric.renderTimeProcessor == time1 + time2
        metric.lastRenderTimeProcessor == time2

    }

    void "test addException"() {
        given:
        Metric metric = new Metric()

        when: "the incrementAccess is called so many time"
        (1..32).each {
            metric.addException()
        }
        then: "Verify the totalAccess atribute"
        metric.totalException == 32
    }

    void "test getAvg"() {
        given:
        Metric metric = new Metric()
        Random rand = new Random()
        int max = 100
        def randomIntegerList = []
        long maxAccess = 32
        (1..maxAccess).each {
            randomIntegerList << rand.nextInt(max + 1)
        }

        Long totalProccesor = randomIntegerList.inject(){ acc, number ->
            acc += number
        }

        when: "call any times to incrementAccess and addTimeProcessor"
        (1..maxAccess).each {
            metric.incrementAccess()
            metric.addTimeProcessor(randomIntegerList[it.intValue() - 1])
        }
        then: "Verify the avg properties"
        metric.avg == (Double)(totalProccesor / maxAccess)
    }

    void "test all total properties"() {
        given:
        Metric metric = new Metric(lowerBound: 1)
        Random rand = new Random()
        int max = 100
        def timesProcessor = []
        def renderTimes = []
        def exceptionTimes = [4, 6, 8, 16, 31]
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

        when: "call any times to addTimeProcessor and addRenderTimeProcessor"
        (1..maxAccess).each {
            metric.incrementAccess()
            metric.addTimeProcessor(timesProcessor[it.intValue() - 1])
            metric.addRenderTimeProcessor(renderTimes[it.intValue() - 1])
            if (exceptionTimes.contains(it.intValue())){
                metric.addException()
            }
        }
        then: "Verify the totalTimeProcessor properties"
        Long totalTimeProcessor = totalProccesor + totalRender
        metric.totalTimeProcessor == totalTimeProcessor
        metric.lastTotalTimeProcessor == timesProcessor.last() + renderTimes.last()
        metric.totalAvg == (Double)(totalTimeProcessor / maxAccess)
        metric.renderAvg == (Double)(totalRender / maxAccess)
        metric.totalException == exceptionTimes.size()
        metric.exceptionPercentage == (Double)((exceptionTimes.size() / maxAccess) * 100)
        !metric.isOverBound()

        when: "When the lowerBound is to low"
        metric.lowerBound = 0.0001F
        then: "the lower bound is false"
        metric.isOverBound()
    }
}
