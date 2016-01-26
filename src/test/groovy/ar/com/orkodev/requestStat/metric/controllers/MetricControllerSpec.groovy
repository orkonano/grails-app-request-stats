package ar.com.orkodev.requestStat.metric.controllers

import ar.com.orkodev.requestStat.metric.AppMetric
import ar.com.orkodev.requestStat.metric.ControllerMetric
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MetricController)
class MetricControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test index action"() {
        given:
        AppMetric appMetric = new AppMetric(lowerBound: 1, grailsApplication: grailsApplication)
        controller.appMetric = appMetric

        when: "When tha action was called"
        controller.index()
        then: "La action retorna al view index y con model 1 de tamaño"
        view == '/metric/index'
        model.size() == 1
        model.appMetric

    }

    void "test metric action"() {
        given:
        AppMetric appMetric = new AppMetric(lowerBound: 1, grailsApplication: grailsApplication)
        String controllerNameTest = "controllerTest"
        appMetric.controllersMetrics = [:]
        appMetric.controllersMetrics[controllerNameTest] = new ControllerMetric()
        controller.appMetric = appMetric

        when: "When tha action was called with a controllerName that exists"
        controller.metric(controllerNameTest)
        then: "The results is the view and a model with size 1 and controllerMetric not empty"
        view == '/metric/metric'
        model.size() == 1
        model.controllerMetric

        when: "When tha action was called with a controllerName that doesn't exists"
        response.reset()
        controller.metric("pepitoController")
        then: "The results is the view and a model with size 1 and controllerMetric  empty"
        view == '/metric/metric'
        model.size() == 1
        !model.controllerMetric

        when: "When tha action was called without a controllerName"
        response.reset()
        controller.metric()
        then: "Response 404"
        response.status == 404
        view == '/error404'
    }
}
