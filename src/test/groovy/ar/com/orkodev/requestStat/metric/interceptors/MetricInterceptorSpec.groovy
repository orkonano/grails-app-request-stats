package ar.com.orkodev.requestStat.metric.interceptors


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MetricInterceptor)
class MetricInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test metric interceptor not matching"() {
        when:"A request matches the interceptor"
            withRequest(controller: "metric")

        then:"The interceptor does match"
            !interceptor.doesMatch()
    }

    void "Test metric interceptor matching"() {
        when:"A request matches the interceptor"
        withRequest(controller: "test")

        then:"The interceptor does match"
        interceptor.doesMatch()
    }
}
