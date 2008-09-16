<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test='${responseMessage=="error"}'>
        <spring:message code="esb.status.error" text="Unable to confirm status from caXchange"/>
    </c:when>
    <c:otherwise>
    	${command.cctsWorkflowStatus.displayName}
        <c:if test="${command.cctsWorkflowStatus=='MESSAGE_SEND_FAILED'}">
                        	<a href="javascript:C3PR.showCCTSError();">Click here to see the error message</a>
                        	<div id="cctsErrorMessage" style="display: none;">${ !empty command.cctsErrorString?command.cctsErrorString:'There are no error messages'}</div>
        </c:if>
    </c:otherwise>
</c:choose>

<script>
    new Effect.Highlight('broadcastResponse');
</script>