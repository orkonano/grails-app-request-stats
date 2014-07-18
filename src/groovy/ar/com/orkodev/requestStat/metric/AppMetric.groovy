package ar.com.orkodev.requestStat.metric

/**
 * Created by orko on 14/07/14.
 */
class AppMetric extends Metric{

    static final String DEFAULT_NAME = "sin_nombre"

    Map<String, ControllerMetric> controllersMetrics
    Metric appMetric
    def grailsApplication

    def synchronized inicializarMapas(){
        if (appMetric == null){
            //contabilizo uno mas para cuando no se registra accón ni controlador
            int totalControllers = grailsApplication.controllerClasses.size() + 1
            this.controllersMetrics = new HashMap<String, ControllerMetric>((int) Math.ceil(totalControllers / 0.75))
            for (controllerArtefact in grailsApplication.controllerClasses){
                String nameController = controllerArtefact.getName().toLowerCase()
                this.controllersMetrics.put(nameController, new ControllerMetric(name: nameController))
            }
            this.controllersMetrics.put(DEFAULT_NAME, new ControllerMetric(name: DEFAULT_NAME))
            appMetric = new Metric(lowerBound: this.lowerBound)
        }
    }

    def incrementAccess(String controllerName, String actionName){
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.incrementAccess()
        ControllerMetric controllerMetric = getControllerMetricByName(controllerName)
        controllerMetric.incrementAccess(actionName)
    }

    def addTimeProcesor(String controllerName, String actionName, long time){
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.addTimeProcessor(time)
        ControllerMetric controllerMetric = getControllerMetricByName(controllerName)
        controllerMetric.addTimeProcesor(actionName, time)
    }

    def getAllControllersMetrics(){
        controllersMetrics.values()
    }

    public static String procesarControllerName(controllerName){
        (controllerName ?:DEFAULT_NAME).toLowerCase()
    }

    def addRenderTimeProcesor(String controllerName, String actionName, long time) {
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.addRenderTimeProcessor(time)
        ControllerMetric controllerMetric = getControllerMetricByName(controllerName)
        controllerMetric.addRenderTimeProcessor(actionName, time)
    }

    private ControllerMetric getControllerMetricByName(controllerName){
        controllerName = procesarControllerName(controllerName)
        controllersMetrics.get(controllerName)
    }

    def addException(controllerName, actionName){
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.addException()
        ControllerMetric controllerMetric = getControllerMetricByName(controllerName)
        controllerMetric.addException(actionName)
    }
}
