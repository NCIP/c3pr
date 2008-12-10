<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
		var win;
		function openPopup(studySite, participant, parentRegistrationId){
			win = new Window(
					{onClose: function() {$('manageCompanion').submit()},title: "Registration", top:35, scrollbar: false, left:100, zIndex:100, width:700, height:325 , url: "<c:url value='/pages/registration/testPage?decorator=noheaderDecorator&customButton=true&create_companion=true&participant='/>" + participant  +"&parentRegistrationId=" + parentRegistrationId +"&studySite=" + studySite, 
					  showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter()
		}

		function closePopup() {
			win.close();
			reloadCompanion();
		}
		function reloadCompanion(){
			$('manageCompanion').submit();
   		}
	</script>
</head>
<body>
<form action="../registration/createEmbeddedCompanionRegistration?registrationId=${command.studySubject.systemAssignedIdentifiers[0] }" method="post" id="manageCompanion">
	<input type="hidden" name="_page0" id="_page0" value="0" /> 
	<input type="hidden" name="_target2" id="_target2" value="2" /> 
</form>
<c:choose>
	<c:when test="${fn:length(companions)>0}">
		<tags:panelBox>
			<div id="CompanionRegistration">
			<table class="tablecontent" width="100%"
				title="Companion Registration">
				<tr>
					<th width="40%" scope="col" align="center"><b>Short
					Title-(Identifier)</b></th>
					<th width="9%" scope="col" align="center"><b>Mandatory</b></th>
					<th width="20%" scope="col" align="center"><b>Registartion
					Status</b></th>
					<th />
				</tr>
				<c:forEach items="${companions}" var="companion">
					<tr>
						<td class="alt">${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})</td>
						<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>
						<td class="alt">${companion.registrationId == 0?"Not
						Started":companion.registrationStatus}</td>
						<td class="alt"><c:choose>
							<c:when test="${companion.registrationId != 0}">
								<csmauthz:accesscontrol domainObject="${command.studySubject}"
									hasPrivileges="UPDATE"
									authorizationCheckName="domainObjectAuthorizationCheck">
									<input type="button" value="Manage"
										onclick="javascript:document.location='<c:url value='/pages/registration/manageRegistration?registrationId=${ companion.registrationId}' />'" />
								</csmauthz:accesscontrol>
							</c:when>
							<c:otherwise>
								<csmauthz:accesscontrol domainObject="${command.studySubject}"
									hasPrivileges="UPDATE"
									authorizationCheckName="domainObjectAuthorizationCheck">
									<input type="button" id="registerCompanionStudy"
										value="Register"
										onclick="openPopup('${ companion.studySiteId}','${command.studySubject.participant.id}','${command.studySubject.id}');" />
								</csmauthz:accesscontrol>
							</c:otherwise>
						</c:choose></td>
					</tr>
				</c:forEach>
			</table>
			</div>
		</tags:panelBox>
		
		<c:if test="${registerableWithCompanions &&(actionRequired || hasCompanions) && command.studySubject.dataEntryStatusString=='Complete' && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Unapproved'}">
			<tags:panelBox title="Parent Registration"><br>
				<font color="GREEN"> <strong>Data entry is complete
				for all mandatory companion registrations, Click on '${actionLabel}'
				button to register subject on parent and companion studies.</strong></font><br><br>
				<registrationTags:register registration="${command.studySubject}"
					newReg="${newRegistration}" actionButtonLabel="${actionLabel}"
					requiresMultiSite="${requiresMultiSite}" />
			</tags:panelBox>
		</c:if>
	</c:when>
	<c:otherwise>
		<tags:panelBox>
			<center><b>No Companion Associated with this
			Registration.</b></center>
		</tags:panelBox>
	</c:otherwise>
</c:choose>
</body>
</html>
