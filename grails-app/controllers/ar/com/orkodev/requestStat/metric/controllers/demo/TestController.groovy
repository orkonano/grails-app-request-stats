package ar.com.orkodev.requestStat.metric.controllers.demo

class TestController {

    def index() {
        render view: "index", model: [pepe: 1]
    }

    def indexException() {
        throw  new Exception()
    }
}
