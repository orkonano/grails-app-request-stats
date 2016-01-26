<%--
  Created by IntelliJ IDEA.
  User: orko
  Date: 4/14/14
  Time: 8:00 PM
--%>

<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main_stat">
  <title><g:message code="default.hibernateStats.label"  default="Hibernate Stats" /></title>
</head>
<body>
<h1>Hibernate Stats</h1>
<div>
  <table class="table table-hover">
    <thead>
    <tr>
      <th colspan="100%">General Stats</th>
    </tr>
    <tr>
      <th>Type</th>
      <th>Count</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>Start Time</td>
      <td>${new Date(statistitcs.startTime)}</td>
    </tr>
    <tr>
      <td>Close Statement count</td>
      <td>${statistitcs.getCloseStatementCount()}</td>
    </tr>
    <tr>
      <td>Connect count</td>
      <td>${statistitcs.getConnectCount()}</td>
    </tr>
    <tr>
      <td>Prepared Statement Count</td>
      <td>${statistitcs.getPrepareStatementCount()}</td>
    </tr>
    <tr>
      <td>Session open count</td>
      <td>${statistitcs.sessionOpenCount}</td>
    </tr>
    <tr>
      <td>Session close count</td>
      <td>${statistitcs.sessionCloseCount}</td>
    </tr>
    <tr>
      <td>Transaction count</td>
      <td>${statistitcs.transactionCount}</td>
    </tr>
    <tr>
      <td>Successful Transaction count</td>
      <td>${statistitcs.successfulTransactionCount}</td>
    </tr>
    </tbody>
  </table>
</div>
<br/>
<div>
  <table class="table table-hover">
    <thead>
    <tr>
      <th colspan="100%">Entity Stats</th>
    </tr>
    <tr>
      <th>Type</th>
      <th>Count</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>Entity Fetch count</td>
      <td>${statistitcs.getEntityFetchCount()}</td>
    </tr>
    <tr>
      <td>Entity Load count</td>
      <td>${statistitcs.getEntityLoadCount()}</td>
    </tr>
    <tr>
      <td>Entity Insert count</td>
      <td>${statistitcs.getEntityInsertCount()}</td>
    </tr>
    <tr>
      <td>Entity Update count</td>
      <td>${statistitcs.getEntityUpdateCount()}</td>
    </tr>
    <tr>
      <td>Entity Delete Count</td>
      <td>${statistitcs.getEntityDeleteCount()}</td>
    </tr>
    <tr>
      <td>Flush Count</td>
      <td>${statistitcs.getFlushCount()}</td>
    </tr>
    <tr>
      <td>Optimistic Failure Count</td>
      <td>${statistitcs.getOptimisticFailureCount()}</td>
    </tr>
    </tbody>
  </table>
  <table class="table table-hover">
    <thead>
    <tr>
      <th>Clase</th>
      <th>Insert</th>
      <th>Update</th>
      <th>Delete</th>
      <th>Fetch</th>
      <th>Load</th>
      <th>Optimistic Failure</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${statistitcs.getEntityNames()}" var="entity">
      <tr>
        <td>${entity}</td>
        <td>${statistitcs.getEntityStatistics(entity).getInsertCount()}</td>
        <td>${statistitcs.getEntityStatistics(entity).getUpdateCount()}</td>
        <td>${statistitcs.getEntityStatistics(entity).getDeleteCount()}</td>
        <td>${statistitcs.getEntityStatistics(entity).getFetchCount()}</td>
        <td>${statistitcs.getEntityStatistics(entity).getLoadCount()}</td>
        <td>${statistitcs.getEntityStatistics(entity).getOptimisticFailureCount()}</td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
<br/>
<div>
  <table class="table table-hover">
    <thead>
    <tr>
      <th colspan="100%">Query Stats</th>
    </tr>
    <tr>
      <th>Type</th>
      <th>Count</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>Query Cache Hit count</td>
      <td>${statistitcs.getQueryCacheHitCount()}</td>
    </tr>
    <tr>
      <td>Query Cache Miss count</td>
      <td>${statistitcs.getQueryCacheMissCount()}</td>
    </tr>
    <tr>
      <td>Query Cache Put count</td>
      <td>${statistitcs.getQueryCachePutCount()}</td>
    </tr>
    <tr>
      <td>Query Excecution count</td>
      <td>${statistitcs.getQueryExecutionCount()}</td>
    </tr>
    <tr>
      <td>Query Excecution max time</td>
      <td>${statistitcs.getQueryExecutionMaxTime()}</td>
    </tr>
    <tr>
      <td>Query Excecution max time query string</td>
      <td>${statistitcs.getQueryExecutionMaxTimeQueryString()}</td>
    </tr>
    </tbody>
  </table>
  <table class="table table-hover">
    <thead>
    <tr>
      <th>Query</th>
      <th>Cache hit</th>
      <th>Cache miss</th>
      <th>Hit Ratio</th>
      <th>Cache put</th>
      <th>Execution avg time</th>
      <th>Execution Count</th>
      <th>Execution Max time</th>
      <th>Execution Min time</th>
      <th>Execution Row count</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${statistitcs.getQueries()}" var="query">
      <tr>
        <td>${query}</td>
        <td>${statistitcs.getQueryStatistics(query).cacheHitCount}</td>
        <td>${statistitcs.getQueryStatistics(query).cacheMissCount}</td>
        <td>
          <g:set var="dividendoQueryRatio" value="${(statistitcs.getQueryStatistics(query).cacheHitCount + statistitcs.getQueryStatistics(query).cacheMissCount)}"/>
          <g:if test="${dividendoQueryRatio > 0}">
            ${statistitcs.getQueryStatistics(query).cacheHitCount / dividendoQueryRatio}
          </g:if>
          <g:else>
            0
          </g:else>
        </td>
        <td>${statistitcs.getQueryStatistics(query).cachePutCount}</td>
        <td>${statistitcs.getQueryStatistics(query).executionAvgTime}</td>
        <td>${statistitcs.getQueryStatistics(query).executionMaxTime}</td>
        <td>${statistitcs.getQueryStatistics(query).executionMinTime}</td>
        <td>${statistitcs.getQueryStatistics(query).executionRowCount}</td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
<br/>
<div>
  <table class="table table-hover">
    <thead>
    <tr>
      <th colspan="100%">Second Level Cache Stats</th>
    </tr>
    <tr>
      <th>Type</th>
      <th>Count</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>Cache Hit count</td>
      <td>${statistitcs.secondLevelCacheHitCount}</td>
    </tr>
    <tr>
      <td>Cache miss count</td>
      <td>${statistitcs.secondLevelCacheMissCount}</td>
    </tr>
    <tr>
      <td>Cache put count</td>
      <td>${statistitcs.secondLevelCachePutCount}</td>
    </tr>
    </tbody>
  </table>
  <table class="table table-hover">
    <thead>
    <tr>
      <th>Region Name</th>
      <th>Element count in memory</th>
      <th>Element count on disk</th>
      <th>Hit count</th>
      <th>Miss Count</th>
      <th>Hit Ratio</th>
      <th>Put count</th>
      <th>Size in memory</th>
      <th>Entries</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${statistitcs.getSecondLevelCacheRegionNames()}" var="region">
      <tr>
        <td>${region}</td>
        <td>${statistitcs.getSecondLevelCacheStatistics(region)?.elementCountInMemory}</td>
        <td>${statistitcs.getSecondLevelCacheStatistics(region)?.elementCountOnDisk}</td>
        <td>${statistitcs.getSecondLevelCacheStatistics(region)?.hitCount}</td>
        <td>${statistitcs.getSecondLevelCacheStatistics(region)?.missCount}</td>
        <td>
          <g:set var="dividendoSecondCacheRatio" value="${statistitcs.getSecondLevelCacheStatistics(region)?.hitCount + statistitcs.getSecondLevelCacheStatistics(region)?.missCount}"/>
          <g:if test="${dividendoSecondCacheRatio > 0}">
            ${statistitcs.getSecondLevelCacheStatistics(region)?.hitCount / dividendoSecondCacheRatio}
          </g:if>
          <g:else>
            0
          </g:else>
        <td>${statistitcs.getSecondLevelCacheStatistics(region)?.putCount}</td>
        <td>${statistitcs.getSecondLevelCacheStatistics(region)?.sizeInMemory}</td>
        <td>${statistitcs.getSecondLevelCacheStatistics(region)?.entries}</td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
<br/>
<div>
  <p title="Global number of collections fetched">
    Collection Fetch count: ${statistitcs.getCollectionFetchCount()}
  </p>
  <p title="Global number of collections loaded">
    Collection Load count: ${statistitcs.getCollectionLoadCount()}
  </p>
  <p title="Global number of collections recreated">
    Collection Recreate count: ${statistitcs.getCollectionRecreateCount()}
  </p>
  <p title="Global number of collections removed">
    Collection Remove count: ${statistitcs.getCollectionRemoveCount()}
  </p>
  <p title="Global number of collections updated">
    Collection Update count: ${statistitcs.getCollectionUpdateCount()}
  </p>
</div>
</body>
</html>