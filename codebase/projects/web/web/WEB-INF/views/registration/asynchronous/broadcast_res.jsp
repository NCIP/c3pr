<%@ include file="../taglibs.jsp" %>
<script>
new Element.hide('broadcastWait');
</script>
<div align="center" style="padding-top: 20px">
<c:choose>
    <c:when test="${empty command.registration.cctsErrorString}">
    	<div style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
			<strong><spring:message code="esb.broadcast.error" text="System encounted some error."/><fmt:message key="BROADCAST.RESEND"/>
    			Click here to see details.</strong>
		</div>
		<div align="center" style="padding-top: 20px">
    	<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
		</div>
    </c:when>
     <c:when test="${command.registration.cctsWorkflowStatus=='MESSAGE_SEND_FAILED'}">
     	<div style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
			<strong><fmt:message key="REGISTRATION.BROADCAST.SEND_FAILED"/>
	     	${command.registration.cctsErrorString}
	     	<fmt:message key="BROADCAST.RESEND"/></strong>
		</div>
		<div align="center" style="padding-top: 20px">
    	<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
		</div>
     </c:when>
     <c:when test="${command.registration.cctsWorkflowStatus=='MESSAGE_SEND'}">
     	<div style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
			<strong><fmt:message key="REGISTRATION.BROADCAST.SENT_NO_RESPONSE"/></strong>
		</div>
		<div align="center" style="padding-top: 20px">
			<tags:button type="button "color="blue" value="Check response" onclick="javascript:getBroadcastStatus();"/>
			<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
		</div>
     </c:when>
</c:choose>
</div>