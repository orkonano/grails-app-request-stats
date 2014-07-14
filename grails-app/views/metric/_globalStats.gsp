<div>
    <h3> ${tituloStat.capitalize()} stats: </h3>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Type</th>
            <th>Value</th>
        </tr>
        </thead>
        <tbody>
        <tbody>
        <tr>
            <td>Proccessor time</td>
            <td>${metric.timeProcessor} ms</td>
        </tr>
        <tr>
            <td>Last Proccessor time</td>
            <td>${metric.lastTimeProcessor} ms</td>
        </tr>
        <tr>
            <td>Render Proccessor time</td>
            <td>${metric.renderTimeProcessor} ms</td>
        </tr>
        <tr>
            <td>Last render Proccessor time</td>
            <td>${metric.lastRenderTimeProcessor} ms</td>
        </tr>
        <tr>
            <td>Total time</td>
            <td>${metric.totalTimeProcessor} ms</td>
        </tr>
        <tr>
            <td>Last Total time</td>
            <td>${metric.lastTotalTimeProcessor} ms</td>
        </tr>
        <tr>
            <td>Total access</td>
            <td>${metric.totalAccess}</td>
        </tr>
        <tr>
            <td>Avg</td>
            <td>${metric.avg} ms</td>
        </tr>
        <tr>
            <td>Render Avg</td>
            <td>${metric.renderAvg} ms</td>
        </tr>
        <tr>
            <td>Total Avg</td>
            <td>${metric.totalAvg} ms</td>
        </tr>
        <tr>
            <td>Exception </td>
            <td>${metric.totalException} u</td>
        </tr>
        <tr ${metric.exceptionPercentage > grailsApplication.config.metric.lowerBound ? "class=danger" : ""}>
            <td>Exception %</td>
            <td>${metric.exceptionPercentage} %</td>
        </tr>
        </tbody>
    </table>
</div>