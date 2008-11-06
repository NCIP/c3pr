<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <script>
function saveStudy() {
    document.getElementById("command").submit();
}
function takeAction(action,nciCode,isRetry){
	<tags:tabMethod method="changeStatus" formName="'tabMethodForm'" onFailure='failedStatusChange' viewName="/study/asynchronous/sites_row" divElement="'dummy-div'" javaScriptParam="'action=' + action+ '&nciCode='+nciCode+'&isRetry='+isRetry+'&DO_NOT_SAVE=true'" />
	//Element.descendants('actions-'+nciCode).each(function(value){
	//						value.disable();
	//						});
	Element.show('sendingMessage-'+nciCode);
	
}
function showEndpointError(nciCode, localNciCode){
	Dialog.alert({url: $('command').action, options: {method: 'post', parameters:"decorator=nullDecorator&_asynchronous=true&_asyncMethodName=showEndpointMessage&_asyncViewName=/study/asynchronous/manage_sites&_target${tab.number}=${tab.number}&_page=${tab.number}&nciCode="+nciCode+"&localNciCode="+localNciCode, asynchronous:true, evalScripts:true}},              
					{className: "alphacube", width:540, okLabel: "Done"});
}
failedStatusChange= function (responseXML){
									Dialog.alert(responseXML.responseText, 
						             {width:600, height:600, okLabel: "Close", 
						              ok:function(win) {$('reload').submit(); return true;}});
								}

</script>
</head>

<body>
<form:form id="reload">
<input type="hidden" name="_target${tab.number}" id="_target"/>
<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
</form:form>
<div id="dummy-div" style="display: none;"></div>
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
		    <c:set var="siteEndpoint" value="${site}"/>
			<c:if test="${localNCICode==site.healthcareSite.nciInstituteCode}"><c:set var="siteEndpoint" value="${site.study.studyCoordinatingCenters[0]}"/></c:if>
	        <tr id="siteTable-${site.healthcareSite.nciInstituteCode }">
	        	<td>${site.healthcareSite.name }</td>
	        	<td><div id="siteStatus-${site.healthcareSite.nciInstituteCode }">${site.siteStudyStatus.code}</div></td>
	            <td><div id="siteIRB-${site.healthcareSite.nciInstituteCode }"><tags:formatDate value="${site.irbApprovalDate}"></tags:formatDate></div></td>
				<td>
					<div id="Messages-${site.healthcareSite.nciInstituteCode }">
					<c:choose>
						<c:when test="${!site.hostedMode && !site.isCoordinatingCenter}">
							<c:choose>
								<c:when test="${siteEndpoint.lastAttemptedEndpoint.status=='MESSAGE_SEND_FAILED'}">
									<font color="red">${siteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
									Click <a href="javascript:showEndpointError('${siteEndpoint.healthcareSite.nciInstituteCode }','${site.healthcareSite.nciInstituteCode }');">here</a> to see the error messages
								</c:when>
								<c:otherwise>
									<font color="green">${siteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
									Click <a href="javascript:showEndpointError('${siteEndpoint.healthcareSite.nciInstituteCode }','${site.healthcareSite.nciInstituteCode }');">here</a> to see the messages
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
	            	<%-- %>1.[${siteEndpoint.healthcareSite.nciInstituteCode}]<br>
	            	2.${site.hostedMode || (!site.hostedMode && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode)}<br>
	            	3.${site.hostedMode}<br>
	            	4.${(!site.hostedMode && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode)}<br>
	            	5.${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}<br>
	            	6.${empty siteEndpoint.lastAttemptedEndpoint || (siteEndpoint.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED' && fn:length(siteEndpoint.possibleEndpoints)==0)}<br>
	            	7.${empty siteEndpoint.lastAttemptedEndpoint}<br>
	            	8.${siteEndpoint.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED'}<br>
	            	9.${fn:length(siteEndpoint.possibleEndpoints)==0}<br>--%>
	            	<c:if test="${site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode || localNCICode==site.healthcareSite.nciInstituteCode}">
	            	<c:if test="${empty siteEndpoint.lastAttemptedEndpoint || (siteEndpoint.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED' && fn:length(siteEndpoint.possibleEndpoints)==0)}">
					<c:forEach items="${site.possibleStatusTransitions}" var="siteStatus">
						<c:choose>
	   					<c:when test="${siteStatus=='ACTIVE' && (site.hostedMode || localNCICode==site.healthcareSite.nciInstituteCode)}">
	   						<input type="button" value="Activate" onclick="takeAction('ACTIVATE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','false');"/>
	   					</c:when>
	   					<c:when test="${siteStatus=='APPROVED_FOR_ACTIVTION' && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}">
	   						<input type="button" value="Approve" onclick="takeAction('APPROVE_STUDY_SITE_FOR_ACTIVATION','${site.healthcareSite.nciInstituteCode }','false');"/>
	   					</c:when>
	   					<c:when test="${siteStatus=='CLOSED_TO_ACCRUAL'}">
	   						<c:set value="true" var="isClose" />
	   					</c:when>
	   					<c:when test="${siteStatus=='TEMPORARILY_CLOSED_TO_ACCRUAL'}">
	   						<c:set value="true" var="isClose" />
	   					</c:when>
						</c:choose>
					</c:forEach>
					<c:if test="${!empty isClose}">
  						<input type="button" value="Close" onclick="takeAction('CLOSE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','false');"/>
  					</c:if>
					</c:if>
					</c:if>
					</div>
					<div id="sendingMessage-${site.healthcareSite.nciInstituteCode }" class="working" style="display: none">
						Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
					</div>
	            </td>
	        </tr>
	    </c:forEach>
	</table>
</form:form>
</tags:panelBox>
</body>
</html>