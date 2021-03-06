<div>
    <h3>${titleStats} Stats</h3>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Name</th>
            <th>Proccessor Time</th>
            <th>Last Proccessor Time</th>
            <th>Render Time</th>
            <th>Last Render Time</th>
            <th>Total Time</th>
            <th>Last Total Time</th>
            <th>Total Access</th>
            <th>Avg ms</th>
            <th>Render Avg ms</th>
            <th>Total Avg ms</th>
            <th>Total Exception</th>
            <th>Exception Percentage</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${metrics}" var="metric">
            <tr ${metric.isOverBound() ? "class=danger" : ""}>
                <td><g:render template="${templateName}" model="[metric: metric]"/></td>
                <td>${metric.timeProcessor} ms</td>
                <td>${metric.lastTimeProcessor} ms</td>
                <td>${metric.renderTimeProcessor} ms</td>
                <td>${metric.lastRenderTimeProcessor} ms</td>
                <td>${metric.totalTimeProcessor} ms</td>
                <td>${metric.lastTotalTimeProcessor} ms</td>
                <td>${metric.totalAccess} u</td>
                <td>${metric.avg} ms</td>
                <td>${metric.renderAvg} ms</td>
                <td>${metric.totalAvg} ms</td>
                <td>${metric.totalException} u</td>
                <td>${metric.exceptionPercentage} %</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>