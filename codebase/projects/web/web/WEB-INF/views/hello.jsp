<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head><title><fmt:message key="title"/></title></head>
<body>
<h1><fmt:message key="heading"/></h1>
<p><fmt:message key="greeting"/> <c:out value="${model.now}"/>
</p>
<h3>Protocols</h3>
<c:forEach items="${model.protocols}" var="prod">
  <c:out value="${prod.id}"/> <i>$<c:out value="${prod.modificationBy}"/></i>
  <i>$<c:out value="${prod.createdBy}"/></i><br><br>
</c:forEach>
<br>
<br>
</body>
</html>