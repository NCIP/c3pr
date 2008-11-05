<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <script>
function saveStudy() {
    document.getElementById("command").submit();
}
function takeAction(action,nciCode,isRetry){
	<tags:tabMethod method="changeStatus" formName="'tabMethodForm'" viewName="/study/asynchronous/sites_row" divElement="'dummy-div'" javaScriptParam="'action=' + action+ '&nciCode='+nciCode+'&isRetry='+isRetry+'&DO_NOT_SAVE=true'" />
	Element.descendants('actions-'+nciCode).each(function(value){
							value.disable();
							});
	$('Messages-'+nciCode).update($('sendingMessage').innerHTML);
	
}
function showEndpointError(nciCode){
	Dialog.alert({url: $('command').action, options: {method: 'post', parameters:"decorator=nullDecorator&_asynchronous=true&_asyncMethodName=showEndpointMessage&_asyncViewName=/study/asynchronous/manage_sites&_target${tab.number}=${tab.number}&_page=${tab.number}&nciCode="+nciCode, asynchronous:true, evalScripts:true}},              
					{className: "alphacube", width:540, okLabel: "Done"});
}
</script>


</head>

<body>
<div id="dummy-div" style="display: none;"></div>
<div id="sendingMessage" style="display: none">
	Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
</div>
<tags:panelBox>
<form:form>
	<br>
	<table id="siteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
	     <tr id="h-multiSite" <c:if test="${fn:length(command.study.studySites) == 0}">style="display:none;"</c:if>>
	        <th><b><span class="required-indicator">Organization</span></b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
	        <th><b>Status</b><tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
	        <th><b>IRB Approval Date</b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
	        <th><b>Messages</b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
	        <th></th>
	    </tr>
	        
	    <c:forEach items="${command.study.studySites}" varStatus="status" var="site">
	        <tr id="siteTable-${site.healthcareSite.nciInstituteCode }">
	        	<td>${site.healthcareSite.name }</td>
	        	<td><div id="siteStatus-${site.healthcareSite.nciInstituteCode }">${site.siteStudyStatus.code}</div></td>
	            <td><tags:formatDate value="${site.irbApprovalDate}"></tags:formatDate></td>
				<td>
					<div id="Messages-${site.healthcareSite.nciInstituteCode }">
					<c:choose>
						<c:when test="${fn:length(site.endpoints)>0}">
							<c:choose>
								<c:when test="${site.lastAttemptedEndpoint.status=='MESSAGE_SEND_FAILED'}">
									<font color="red">${site.lastAttemptedEndpoint.status.code}</font><br>
									Click <a href="javascript:showEndpointError('${site.healthcareSite.nciInstituteCode }');">here</a> to see the error messages
								</c:when>
								<c:otherwise>
									<font color="green">${site.lastAttemptedEndpoint.status.code}</font><br>
									<c:if test="${fn:length(site.possibleEndpoints)>0}">
									Click <a href="javascript:showEndpointError('${site.healthcareSite.nciInstituteCode }');">here</a> to see the messages
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							None
						</c:otherwise>
					</c:choose>
					</div>
				</td>
	            <td><div id="actions-${site.healthcareSite.nciInstituteCode }">
	            	<c:if test="${site.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED'}">
					<c:forEach items="${site.possibleStatusTransitions}" var="siteStatus">
						<c:choose>
	   					<c:when test="${siteStatus=='ACTIVE' && (site.hostedMode || localNCICode==site.healthcareSite.nciInstituteCode)}">
	   						<input type="button" value="Activate" onclick="takeAction('ACTIVATE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','false');"/>
	   					</c:when>
	   					<c:when test="${siteStatus=='APPROVED_FOR_ACTIVTION' && localNCICode!=site.healthcareSite.nciInstituteCode}">
	   						<input type="button" value="Approve" onclick="takeAction('APPROVE_STUDY_SITE_FOR_ACTIVATION','${site.healthcareSite.nciInstituteCode }','false');"/>
	   					</c:when>
	   					<c:when test="${siteStatus=='CLOSED_TO_ACCRUAL'}">
	   						<c:set value="true" var="isClose" />
	   					</c:when>
	   					<c:when test="${siteStatus=='TEMPORARILY_CLOSED_TO_ACCRUAL'}">
	   						<c:set value="true" var="isClose" />
	   					</c:when>
						</c:choose>
						<c:if test="${!empty isClose}">
	  						<input type="button" value="Close" onclick="takeAction('CLOSE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','false');"/>
	  					</c:if>
					</c:forEach>
					</c:if>
					</div>
	            </td>
	        </tr>
	    </c:forEach>
	</table>
</form:form>
</tags:panelBox>
</body>
</html>