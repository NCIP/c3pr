<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <script>
function saveStudy() {
    document.getElementById("command").submit();
}
function takeAction(nciCode){
	action=$("siteAction-"+nciCode).value;
	<tags:tabMethod method="changeStatus" formName="'tabMethodForm'" onFailure='failedStatusChange' viewName="/study/asynchronous/sites_row" divElement="'dummy-div'" javaScriptParam="'action=' + action+ '&nciCode='+nciCode+'&DO_NOT_SAVE=true'" />
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
	        <th><b>Actions</b></th>
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
						<c:when test="${!site.hostedMode && !site.isCoordinatingCenter && fn:length(siteEndpoint.endpoints)>0}">
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
	            	<c:if test="${fn:length(site.possibleActions)>0 && (site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode || localNCICode==site.healthcareSite.nciInstituteCode)}">
	            	<select id="siteAction-${site.healthcareSite.nciInstituteCode }">
	            		<c:forEach items="${site.possibleActions}" var="possibleAction">
	            		<c:choose>
	   					<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE' && (site.hostedMode || localNCICode==site.healthcareSite.nciInstituteCode)}">
	   						<option value="${possibleAction}">${possibleAction.displayName }</option>
	   					</c:when>
	   					<c:when test="${possibleAction=='APPROVED_FOR_ACTIVTION' && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}">
	   						<option value="${possibleAction}">${possibleAction.displayName }</option>
	   					</c:when>
	   					<c:otherwise>
	   						<option value="${possibleAction}">${possibleAction.displayName }</option>
	   					</c:otherwise>
						</c:choose>
	            		</c:forEach>
	            	</select>
	            	<input type="button" value="Go" onclick="takeAction('${site.healthcareSite.nciInstituteCode }');"/>
					</c:if>
					<div id="sendingMessage-${site.healthcareSite.nciInstituteCode }" class="working" style="display: none">
						Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
					</div>
					</div>
	            </td>
	        </tr>
	    </c:forEach>
	</table>
</form:form>
</tags:panelBox>
</body>
</html>