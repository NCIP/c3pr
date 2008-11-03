<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <script>
function saveStudy() {
    document.getElementById("command").submit();
}
function takeAction(action,nciCode,isRetry){
	<tags:tabMethod method="changeStatus" formName="'tabMethodForm'" viewName="/study/asynchronous/manage_sites" divElement="'dummy-div'" javaScriptParam="'action=' + action+ '&nciCode='+nciCode+'&isRetry='+isRetry" />
	$('Messages-'+nciCode).update($('sendingMessage').innerHTML);
	
}
</script>


</head>

<body>
<div id="dummy-div" style="display: none;"></div>
<div id="sendingMessage" style="display: none">
	Sending...<img src="<tags:imageUrl name='2_computers.gif'/>" width="50" height="15" border="0" alt="sending.."/>
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
	        <tr id="siteTable-${status.index}">
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
	              			<tags:displayErrors id="endpoint-errors-${status.index}" errors="${site.lastAttemptedEndpoint.errors}"></tags:displayErrors>
								</c:when>
								<c:otherwise>
									<font color="green">${site.lastAttemptedEndpoint.status.code}</font><br>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							None
						</c:otherwise>
					</c:choose>
					</div>
				</td>
	            <td>
					 <c:choose>
						<c:when test="${fn:length(site.endpoints)>0 && site.lastAttemptedEndpoint.status=='MESSAGE_SEND_FAILED'}">
							<c:forEach items="${site.endpoints}" var="endpoint">
							<c:if test="${endpoint.status=='MESSAGE_SEND_FAILED'}">
							<c:choose>
								<c:when test="${endpoint.apiName=='CREATE_STUDY'}">
									<input type="button" value="Create Study" onclick="takeAction('CREATE_STUDY','${site.healthcareSite.nciInstituteCode }','true');"/>
								</c:when>
								<c:when test="${endpoint.apiName=='OPEN_STUDY'}">
									<input type="button" value="Open Study" onclick="takeAction('OPEN_STUDY','${site.healthcareSite.nciInstituteCode }','true');"/>
								</c:when>
								<c:when test="${endpoint.apiName=='APPROVE_STUDY_SITE_FOR_ACTIVATION'}">
									<input type="button" value="Approve" onclick="takeAction('APPROVE_STUDY_SITE_FOR_ACTIVATION','${site.healthcareSite.nciInstituteCode }','true');"/>
								</c:when>
								<c:when test="${endpoint.apiName=='ACTIVATE_STUDY_SITE'}">
									<input type="button" value="Activate" onclick="takeAction('ACTIVATE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','true');"/>
								</c:when>
								<c:when test="${endpoint.apiName=='AMEND_STUDY'}">
									<input type="button" value="Amend Study" onclick="takeAction('AMEND_STUDY','${site.healthcareSite.nciInstituteCode }','true');"/>
								</c:when>
								<c:when test="${endpoint.apiName=='CLOSE_STUDY'}">
									<input type="button" value="Close Study" onclick="takeAction('CLOSE_STUDY','${site.healthcareSite.nciInstituteCode }','true');"/>
								</c:when>
								<c:when test="${endpoint.apiName=='CLOSE_STUDY_SITE'}">
									<input type="button" value="Close" onclick="takeAction('CLOSE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','true');"/>
								</c:when>
							</c:choose>
							</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${site.possibleStatusTransitions}" var="siteStatus">
								<c:choose>
			   					<c:when test="${siteStatus=='ACTIVE' && (!canMultisiteBroadcast || localNCICode==site.healthcareSite.nciInstituteCode)}">
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
						</c:otherwise>
					</c:choose>
	            </td>
	        </tr>
	    </c:forEach>
	</table>
</form:form>
</tags:panelBox>
</body>
</html>