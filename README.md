grails-app-request-stats
========================

El plugin está destinado a llevar las estadísticas de uso de la app.
Mide los accesos a los controladores y a sus acciones.
Mide los tiempos de procesamiento y tiempos de renderización.
Lleva un control de los porcentajes de errores.

#Instalación

Agregar el plugin al proyecto
```groovy
runtime ":grails-app-request-stats:0.1.0"
```

#Build

Para compilar el proyecto e intalarlo localmente se debe ejecutar

 ```grails
grails maven-install
```

Para publicarlo se deje ejecutar

```grails
grails publish-plugin --protocol=webdav
```

El repositorio default para la publicación es https://repository-orkoapp.forge.cloudbees.com/snapshot/

###**Atención**
Tener en cuenta que se tiene que tener configurado en .grails/setting.groovy
```groovy
grails.project.repos.cloudbees.url = "dav:https://repository-orkoapp.forge.cloudbees.com/snapshot/"
grails.project.repos.cloudbees.username = yourUsername
grails.project.repos.cloudbees.password = yourPass
```


#Test

El proyecto usa travis-ci como entorno de integración continua. https://travis-ci.org/orkonano/grails-app-request-stats
Se ejecutan test unitarios

#Cómo usarlo

Al instalar el plugin, automáticamente el proyecto empieza a loggear los tiempos de procesamiento.

Para poder visualizar las estadísticas, es necesarios acceder a través de urlProject/metric

##Explicación de clases

A través del filter *MetricFilters* la aplicación empieza a logear los tiempos de procesamiento.
Todos los datos son guardados en clases ThreadSafe
