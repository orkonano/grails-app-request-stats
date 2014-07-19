package ar.com.orkodev.requestStat.metric
/**
 * Created by orko on 14/07/14.
 */
class AppMetric{

    static final String DEFAULT_NAME = "sin_nombre"

    Map<String, ControllerMetric> controllersMetrics
    Metric appMetric
    Float lowerBound
    def grailsApplication

    synchronized void  inicializarMapas(){
        if (appMetric == null){
            //contabilizo uno mas para cuando no se registra accón ni controlador
            int totalControllers = grailsApplication.controllerClasses.size() + 1
            this.controllersMetrics = new HashMap<String, ControllerMetric>((int) Math.ceil(totalControllers / 0.75))
            for (controllerArtefact in grailsApplication.controllerClasses){
                String nameController = controllerArtefact.getName().toLowerCase()
                this.controllersMetrics.put(nameController, new ControllerMetric(name: nameController,
                        lowerBound: this.lowerBound))
            }
            this.controllersMetrics.put(DEFAULT_NAME, new ControllerMetric(name: DEFAULT_NAME,
                    lowerBound: this.lowerBound))

            appMetric = new Metric(lowerBound: this.lowerBound)
        }
    }

    void incrementAccess(String controllerName, String actionName){
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.incrementAccess()
        ControllerMetric controllerMetric = getControllerMetricByName(controllerName)
        controllerMetric.incrementAccess(actionName)
    }

    void addTimeProcessor(String controllerName, String actionName, long time){
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.addTimeProcessor(time)
        ControllerMetric controllerMetric = getControllerMetricByName(controllerName)
        controllerMetric.addTimeProcessor(actionName, time)
    }

    Collection<ControllerMetric> getAllControllersMetrics(){
        controllersMetrics.values()
    }

    public static String procesarControllerName(String controllerName){
        (controllerName ?:DEFAULT_NAME).toLowerCase()
    }

    void addRenderTimeProcessor(String controllerName, String actionName, long time) {
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.addRenderTimeProcessor(time)
        ControllerMetric controllerMetric = getControllerMetricByName(controllerName)
        controllerMetric.addRenderTimeProcessor(actionName, time)
    }

    private ControllerMetric getControllerMetricByName(String controllerName){
        controllerName = procesarControllerName(controllerName)
        controllersMetrics.get(controllerName)
    }

    def addException(String controllerName, String actionName){
        if (appMetric == null){
            inicializarMapas()
        }
        appMetric.addException()
        ControllerMetric controllerMetric = getControllerMetricByName(controllerName)
        controllerMetric.addException(actionName)
    }
}
