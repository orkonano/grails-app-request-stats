<%--
  Created by IntelliJ IDEA.
  User: orko
  Date: 4/19/14
  Time: 4:16 PM
--%>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_stat">
    <title><g:message code="default.appStats.label"  default="App Metric Stats" /></title>
    <g:javascript library="application"/>
</head>
<body>
    <h1>App Stats</h1>
    <g:set var="globalMetric" value="${appMetric.appMetric}"/>
    <g:render template="globalStats" model="[tituloStat: 'app stats',
            metric: globalMetric]"/>
<g:render template="specificStats" model="[tituloStat: 'Controllers',
            metrics: appMetric.getAllControllersMetrics(),
            templateName: 'nameController']"/>



</body>
</html>