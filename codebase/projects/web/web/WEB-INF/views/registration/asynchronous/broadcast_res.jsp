<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="../taglibs.jsp" %>
<script>
new Element.hide('broadcastWait');
new Element.hide('broadcastResponseCheckWait');
</script>
<div style="padding-top: 5px" align="left">
<c:choose>
	<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND'}">
     	<div style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
			<fmt:message key="REGISTRATION.BROADCAST.SENT_NO_RESPONSE"/>
		</div>
		<div align="center" style="padding-top: 20px">
			<tags:button type="button "color="blue" value="Check response" onclick="javascript:getBroadcastStatus();"/>
			<tags:button type="button" color="red" icon="x" value="Later" onclick="window.location.reload();" />
		</div>
     </c:when>
     <c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND_CONFIRMED'}">
		<div style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
			<strong><font color="green">
				<fmt:message key="REGISTRATION.BROADCAST.SENT_SUCCESSFULLY"/>
			</font><br></strong>
		</div>
		<div align="center" style="padding-top: 20px">
		<tags:button type="button" color="red" icon="x" value="Close" onclick="window.location.reload();" />
		</div>
	</c:when>
	<c:when test="${!empty codedError}">
		<div style="font-size: 10pt; padding-top: 5px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
			<div style="float: left; padding: 5px;">
				<img src="<tags:imageUrl name='error.png'/>" alt="Calendar" border="0" align="middle"/>
			</div>
			<strong><font color="red">
				${codedError.codedExceptionMesssage}
			</font><br></strong>
			<fmt:message key="REGISTRATION.BROADCAST.ERROR"/>
     		<fmt:message key="BROADCAST.RESEND"/>
		</div>
		<div align="center" style="padding-top: 20px">
		<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
		</div>
	</c:when>
	<c:when test="${!empty generalError}">
		<div style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
			<div style="float: left; padding: 5px;">
				<img src="<tags:imageUrl name='error.png'/>" alt="Calendar" border="0" align="middle"/>
			</div>
			<fmt:message key="REGISTRATION.BROADCAST.ERROR"/>
	     	<fmt:message key="BROADCAST.RESEND"/>
		</div>
		<div align="center" style="padding-top: 20px">
		<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
		</div>
	</c:when>
	<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_ACK_FAILED'}">
		<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
			<div style="float: left; padding: 5px;">
				<img src="<tags:imageUrl name='error.png'/>" alt="Calendar" border="0" align="middle"/>
			</div>
			<fmt:message key="REGISTRATION.BROADCAST.SENT_NO_ACK"/>
			<fmt:message key="BROADCAST.RESEND"/>
		</div>
		<div align="center" style="padding-top: 20px">
		<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
		</div>
	</c:when>
	<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND_FAILED'}">
    	<div style="font-size: 10pt; padding-top: 10px; padding-bottom: 5px; padding-left: 5px; padding-right: 5px">
    		<div style="float: left; padding: 5px;">
				<img src="<tags:imageUrl name='error.png'/>" alt="Calendar" border="0" align="middle"/>
			</div>
			<fmt:message key="REGISTRATION.BROADCAST.SEND_FAILED"/>
			<div id="ccts-error-message"></div><script>$("ccts-error-message").update(C3PR.buildCCTSErrorHtml("${command.studySubject.cctsErrorString}"))</script>
	     	<fmt:message key="BROADCAST.RESEND"/>
		</div>
		<div align="center">
	   	<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
		</div>
    </c:when>
</c:choose>
</div>
