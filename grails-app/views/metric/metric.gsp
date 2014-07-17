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
<h1>Controller Stats</h1>
<g:set var="globalMetric" value="${controllerMetric}"/>
<g:render template="globalStats" model="[tituloStat: globalMetric.name,
        metric: globalMetric]"/>
<g:render template="specificStats" model="[tituloStat: 'Action',
        metrics: controllerMetric.getAllActionMetric(),
        templateName: 'nameAction']"/>
</body>
</html>