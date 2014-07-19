package ar.com.orkodev.requestStat.metric

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class AppMetricSpec extends Specification {

    AppMetric appMetric
    def grailsApplicationTest

    public class GrailsApplicationTest{
        def controllerClasses = [new ControllerArtefact(name: "myFirstController"),
                                 new ControllerArtefact(name: "mySecondController")]

        public class ControllerArtefact{
            String name
        }
    }

    def setup() {
        grailsApplicationTest = new GrailsApplicationTest()
        appMetric = new AppMetric(lowerBound: 1, grailsApplication: grailsApplicationTest)

    }

    def cleanup() {
    }

    void "test inicializarMapas"() {
        when:"inicializarMapas is called"
        appMetric.inicializarMapas()
        then:"todos los atributos se inicializan"
        appMetric.controllersMetrics
        appMetric.controllersMetrics.size() == grailsApplicationTest.controllerClasses.size() + 1
        appMetric.allControllersMetrics.size() == grailsApplicationTest.controllerClasses.size() + 1
        appMetric.appMetric
    }

    void "test all total properties"() {
        given:
        Random rand = new Random()
        int max = 100
        def timesProcessor = []
        def renderTimes = []
        def exceptionTimes = [4, 30]
        def actionsName = ["first action", "second action"]
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
        grailsApplicationTest.controllerClasses.each{ controllerArtefact ->
            String controllerName = controllerArtefact.name
            actionsName.each { actionName ->
                (1..maxAccess).each { actionTime ->
                    appMetric.incrementAccess(controllerName, actionName)
                    appMetric.addTimeProcessor(controllerName, actionName, timesProcessor[actionTime.intValue() - 1])
                    appMetric.addRenderTimeProcessor(controllerName, actionName, renderTimes[actionTime.intValue() - 1])
                    if (exceptionTimes.contains(actionTime.intValue())){
                        appMetric.addException(controllerName, actionName)
                    }
                }
            }
        }

        //El primero es el sin-nombre que no contabilizo nada
        Metric controllerMetric = appMetric.allControllersMetrics[1]
        Metric metric = appMetric.appMetric
        then: "Verify the totalTimeProcessor properties"
        Long totalActionTimeProcessor = totalProccesor + totalRender
        Long totalControllerProcessor = totalActionTimeProcessor * actionsName.size()
        Long totalAppProcessor = totalControllerProcessor * grailsApplicationTest.controllerClasses.size()
        Long totalRenderController = totalRender * actionsName.size()
        Long totalRenderApp = totalRenderController * grailsApplicationTest.controllerClasses.size()
        Integer maxControllerAccess = maxAccess * actionsName.size()
        Integer maxAppAccess = maxControllerAccess * grailsApplicationTest.controllerClasses.size()
        Integer controllersException = exceptionTimes.size() * actionsName.size()
        Integer appControllersException = controllersException  * grailsApplicationTest.controllerClasses.size()
        metric.totalTimeProcessor == totalAppProcessor
        metric.lastTotalTimeProcessor == timesProcessor.last() + renderTimes.last()
        metric.totalAvg == (Double)(totalAppProcessor / maxAppAccess)
        metric.renderAvg == (Double)(totalRenderApp / maxAppAccess)
        metric.totalException == appControllersException
        metric.exceptionPercentage == (Double)((appControllersException / maxAppAccess) * 100)
        !metric.isOverBound()

        controllerMetric.totalTimeProcessor == totalControllerProcessor
        controllerMetric.lastTotalTimeProcessor == timesProcessor.last() + renderTimes.last()
        controllerMetric.totalAvg == (Double)(totalControllerProcessor / maxControllerAccess)
        controllerMetric.renderAvg == (Double)(totalRenderController / maxControllerAccess)
        controllerMetric.totalException == controllersException
        controllerMetric.exceptionPercentage == (Double)((controllersException / maxControllerAccess) * 100)
        !controllerMetric.isOverBound()

        when: "When the lowerBound is to low"
        metric.lowerBound = 0.0001F
        then: "the lower bound is false"
        metric.isOverBound()
    }

}
