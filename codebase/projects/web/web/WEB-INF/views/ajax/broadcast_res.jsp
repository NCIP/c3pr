<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test='${responseMessage=="error"}'>
        <spring:message code="esb.status.error" text="Unable to confirm status from caXchange"/>
    </c:when>
    <c:otherwise>
        ${responseMessage}
    </c:otherwise>
</c:choose>

<script>
    new Effect.Highlight('broadcastResponse');
</script>