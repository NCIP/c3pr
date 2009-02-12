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

function changeCompanionStudySiteStatus(nciCode){
	action=$("companionSiteAction-"+nciCode).value;
	<tags:tabMethod method="changeStatus" formName="'tabMethodForm'" onFailure='failedStatusChange' viewName="/study/asynchronous/companionSites_row" divElement="'dummy-div'" javaScriptParam="'action=' + action+ '&nciCode='+nciCode+ '&studySiteType=companionSite&DO_NOT_SAVE=true'" />
	Element.show('companionSendingMessage-'+nciCode);
}

function showEndpointError(nciCode, localNciCode){
	Dialog.alert({url: $('command').action, options: {method: 'post', parameters:"decorator=nullDecorator&_asynchronous=true&_asyncMethodName=showEndpointMessage&_asyncViewName=/study/asynchronous/manage_sites&_target${tab.number}=${tab.number}&_page=${tab.number}&nciCode="+nciCode+"&localNciCode="+localNciCode, asynchronous:true, evalScripts:true}},              
					{className: "alphacube", width:540, okLabel: "Done"});
}
function showLocalActionError(errorDiv){
	Dialog.alert($(errorDiv).innerHTML,{className: "alphacube", width:540, okLabel: "Done"});
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
	        <th><b><tags:requiredIndicator /><fmt:message key="c3pr.common.organization"/></b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
	        <th><b><fmt:message key="site.status"/></b><tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
	        <th><b><fmt:message key="site.IRBApprovalDate"/></b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
	        <th><b><fmt:message key="site.messages"/></b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
	        <th><b><fmt:message key="site.actions"/></b></th>
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
	            <td>
	            <%-- ${fn:length(site.possibleTransitions)}
	            	[${site.possibleTransitions}]--%>
	            <div id="actions-${site.healthcareSite.nciInstituteCode }">
	            	<%-- %>1.[${siteEndpoint.healthcareSite.nciInstituteCode}]<br>
	            	2.${site.hostedMode || (!site.hostedMode && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode)}<br>
	            	3.${site.hostedMode}<br>
	            	4.${(!site.hostedMode && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode)}<br>
	            	5.${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}<br>
	            	6.${empty siteEndpoint.lastAttemptedEndpoint || (siteEndpoint.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED' && fn:length(siteEndpoint.possibleEndpoints)==0)}<br>
	            	7.${empty siteEndpoint.lastAttemptedEndpoint}<br>
	            	8.${siteEndpoint.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED'}<br>
	            	9.${fn:length(siteEndpoint.possibleEndpoints)==0}<br>--%>
	            	<c:set var="noAction" value="true"/>
	            	<c:if test="${fn:length(site.possibleTransitions)>0 && (site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode || localNCICode==site.healthcareSite.nciInstituteCode)}">
	            	<select id="siteAction-${site.healthcareSite.nciInstituteCode }">
	            		<c:forEach items="${site.possibleTransitions}" var="possibleAction">
	            		<c:choose>
	   					<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE'}">
	   						<c:if test="${site.hostedMode || (localNCICode==site.healthcareSite.nciInstituteCode && (site.siteStudyStatus=='APPROVED_FOR_ACTIVTION' || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode))}">
	   						<option value="${possibleAction}">${possibleAction.displayName }</option>
	   						<c:set var="noAction" value="false"/>
	   						</c:if>
	   					</c:when>
	   					<c:when test="${possibleAction=='APPROVE_STUDY_SITE_FOR_ACTIVATION'}">
	   						<c:if test="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}">
	   						<option value="${possibleAction}">${possibleAction.displayName }</option>
	   						<c:set var="noAction" value="false"/>
	   						</c:if>
	   					</c:when>
	   					<c:otherwise>
	   						<option value="${possibleAction}">${possibleAction.displayName }</option>
	   						<c:set var="noAction" value="false"/>
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
					<c:if test="${noAction}">
					<script>
						Element.hide("actions-${site.healthcareSite.nciInstituteCode }");
					</script>
					</c:if>
	            </td>
	        </tr>
	    </c:forEach>
	</table>
	<br>
	<br>
	<div id="companionStudyAssocition-studySite" <c:if test="${!command.study.companionIndicator}">style="display:none;"</c:if>>
 	<c:forEach items="${command.study.parentStudyAssociations}" var="parentStudyAssociation" varStatus="parentStudySiteStatus">
 		<div id="parentStudySiteDiv-${parentStudySiteStatus.index}">
			<chrome:division title="${parentStudyAssociation.parentStudy.shortTitleText}">
				<table id="siteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
			     <tr id="h-multiSite" <c:if test="${fn:length(command.study.studySites) == 0}">style="display:none;"</c:if>>
			        <th><b><tags:requiredIndicator /><fmt:message key="c3pr.common.organization"/></b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
			        <th><b><fmt:message key="site.status"/></b><tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
			        <th><b><fmt:message key="site.IRBApprovalDate"/></b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
			        <th><b><fmt:message key="site.messages"/></b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
			        <th><b><fmt:message key="site.actions"/></b></th>
			    </tr>
			    <c:forEach items="${parentStudyAssociation.studySites}" varStatus="status" var="site">
				    <c:set var="companionSiteEndpoint" value="${site}"/>
					<c:if test="${localNCICode==site.healthcareSite.nciInstituteCode}"><c:set var="companionSiteEndpoint" value="${site.study.studyCoordinatingCenters[0]}"/></c:if>
			        <tr id="siteTable-${site.healthcareSite.nciInstituteCode }">
			        	<td>${site.healthcareSite.name }</td>
			        	<td><div id="companionSiteStatus-${site.healthcareSite.nciInstituteCode }">${site.siteStudyStatus.code}</div></td>
			            <td><div id="companionSiteIRB-${site.healthcareSite.nciInstituteCode }"><tags:formatDate value="${site.irbApprovalDate}"></tags:formatDate></div></td>
						<td>
							<div id="companionMessages-${site.healthcareSite.nciInstituteCode }">
							<c:choose>
								<c:when test="${!site.hostedMode && !site.isCoordinatingCenter && fn:length(companionSiteEndpoint.endpoints)>0}">
									<c:choose>
										<c:when test="${companionSiteEndpoint.lastAttemptedEndpoint.status=='MESSAGE_SEND_FAILED'}">
											<font color="red">${companionSiteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
											Click <a href="javascript:showEndpointError('${companionSiteEndpoint.healthcareSite.nciInstituteCode }','${site.healthcareSite.nciInstituteCode }');">here</a> to see the error messages
										</c:when>
										<c:otherwise>
											<font color="green">${companionSiteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
											Click <a href="javascript:showEndpointError('${companionSiteEndpoint.healthcareSite.nciInstituteCode }','${site.healthcareSite.nciInstituteCode }');">here</a> to see the messages
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
			            <div id="companionActions-${site.healthcareSite.nciInstituteCode }">
			            	<%-- 1.[${siteEndpoint.healthcareSite.nciInstituteCode}]<br>
			            	2.${site.hostedMode || (!site.hostedMode && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode)}<br>
			            	3.${site.hostedMode}<br>
			            	4.${(!site.hostedMode && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode)}<br>
			            	5.${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}<br>
			            	6.${empty siteEndpoint.lastAttemptedEndpoint || (siteEndpoint.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED' && fn:length(siteEndpoint.possibleEndpoints)==0)}<br>
			            	7.${empty siteEndpoint.lastAttemptedEndpoint}<br>
			            	8.${siteEndpoint.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED'}<br>
			            	9.${fn:length(siteEndpoint.possibleEndpoints)==0}<br>--%>
			            	<c:set var="noAction" value="true"/>
			            	<c:if test="${fn:length(site.possibleTransitions)>0 && (site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode || localNCICode==site.healthcareSite.nciInstituteCode)}">
			            	<select id="companionSiteAction-${site.healthcareSite.nciInstituteCode }">
			            		<c:forEach items="${site.possibleTransitions}" var="possibleAction">
			            		<c:choose>
			   					<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE'}">
			   						<c:if test="${site.hostedMode || (localNCICode==site.healthcareSite.nciInstituteCode && site.siteStudyStatus=='APPROVED_FOR_ACTIVTION')}">
			   						<option value="${possibleAction}">${possibleAction.displayName }</option>
			   						<c:set var="noAction" value="false"/>
			   						</c:if>
			   					</c:when>
			   					<c:when test="${possibleAction=='APPROVE_STUDY_SITE_FOR_ACTIVATION'}">
			   						<c:if test="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}">
			   						<option value="${possibleAction}">${possibleAction.displayName }</option>
			   						<c:set var="noAction" value="false"/>
			   						</c:if>
			   					</c:when>
			   					<c:otherwise>
			   						<option value="${possibleAction}">${possibleAction.displayName }</option>
			   						<c:set var="noAction" value="false"/>
			   					</c:otherwise>
								</c:choose>
			            		</c:forEach>
			            	</select>
			            	<input type="button" value="Go" onclick="changeCompanionStudySiteStatus('${site.healthcareSite.nciInstituteCode}');"/>
							</c:if>
							<div id="companionSendingMessage-${site.healthcareSite.nciInstituteCode }" class="working" style="display: none">
								Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
							</div>
							</div>
							<c:if test="${noAction}">
							<script>
								Element.hide("companionActions-${site.healthcareSite.nciInstituteCode }");
							</script>
							</c:if>
			            </td>
			        </tr>
			    </c:forEach>
			</table>
			<br>
			<br>
			</chrome:division>
			<br>
		</div>
	</c:forEach>
</div>

	
	
	
</form:form>
</tags:panelBox>
</body>
</html>