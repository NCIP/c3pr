<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
		var win;
		function openPopup(companionStudy, participant, parentRegistrationId){
			win = new Window(
					{onClose: function() {reloadSection();},
						title: "Companion Registration", 
						top:35, scrollbar: false, left:100, 
						zIndex:100, width:850, height:500 , 
						minimizable:false, maximizable:false,
						url: "<c:url value='/pages/registration/createEmbeddedCompanionRegistration?decorator=noheaderRegistrationflow&participant='/>" + participant  +"&parentRegistrationId=" + parentRegistrationId +"&study=" + companionStudy, 
					  showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter(true)
		}

		function closePopup() {
			win.close();
		}

		function editCompanionRegistration(url){
			win = new Window(
					{onClose: function() {reloadSection();},
						title: "Companion Registration", 
						top:35, scrollbar: false, left:100, 
						zIndex:100, width:850, height:500 , 
						minimizable:false, maximizable:false,
						url: "<c:url value='/pages/registration/editCompanionRegistration?decorator=noheaderRegistrationflow&companionRegistration=true&'/>" + url , 
					 	 showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter(true);
   	   	}

								
   		function removeChildStudySubject(childRegId){
   			<tags:tabMethod method="removeChildStudySubject" divElement="'CompanionRegistration'" formName="'tabMethodForm'"  viewName="/registration/asynchronous/reg_companion_section" javaScriptParam="'childStudySubjectId='+childRegId" />
   			Element.hide('flash-message-companion-create');
   			Element.show('flash-message-companion-delete');
   			new Effect.Pulsate('flash-message-companion-delete')
   			
   	   	}

   	   	function reloadSection(){
   	   		<tags:tabMethod method="refreshCompanionSection" divElement="'CompanionRegistration'" formName="'tabMethodForm'"  viewName="/registration/asynchronous/reg_companion_section" />
   	   		Element.show('flash-message-companion-create');
			Element.hide('flash-message-companion-delete');
			new Effect.Pulsate('flash-message-companion-create')
   	   	}
	</script>
</head>
<body>
<div id="flash-message-companion-delete" style="display:none;">
	<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /><fmt:message key="registration.companion.companionDeleted"/></div>
</div>
<div id="flash-message-companion-create" style="display:none;">
	<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /><fmt:message key="registration.companion.dataSaved"/></div>
</div>
<div id="CompanionRegistration">
<c:choose>
	<c:when test="${fn:length(companions)>0 && command.studySubject.currentScheduledEpoch.epoch.enrollmentIndicator}">
		<tags:formPanelBox tab="${tab}" flow="${flow}">
			<table class="tablecontent" width="100%"
				title="Companions">
				<tr>
					<th width="40%" scope="col" align="center"><b><fmt:message key="c3pr.common.study"/></b></th>
					<th width="10%" scope="col" align="center"><b><fmt:message key="c3pr.common.mandatory"/></b></th>
					<th width="18%" scope="col" align="center"><b><fmt:message key="c3pr.common.status"/></b></th>
					<th width="18%" scope="col" align="center"><b><fmt:message key="c3pr.common.actions"/></b></th>
				</tr>
				<c:forEach items="${companions}" var="companion">
					<tr>
						<td class="alt">
							<c:if test="${companion.mandatoryIndicator}"><tags:requiredIndicator /></c:if>
							${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})
						</td>
						<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>
						<td class="alt">${empty companion.companionRegistrationUrl ?"Not Started": (companion.registrationStatus == 'Registered but not enrolled')?'Pending':companion.registrationStatus}</td>
						<td class="alt">
						<c:if test="${companion.registrationStatus != 'Enrolled'}">
							<c:choose>
								<c:when test="${not empty companion.companionRegistrationUrl}">
									<csmauthz:accesscontrol domainObject="${command.studySubject}"
										hasPrivileges="UPDATE"
										authorizationCheckName="domainObjectAuthorizationCheck">
										<tags:button type="button" size="small" color="green" value="Edit" onclick="editCompanionRegistration('${companion.companionRegistrationUrl}');" />
										<c:if test="${!companion.mandatoryIndicator}">
											<tags:button type="button" size="small" color="red" value="Remove" onclick="removeChildStudySubject('${companion.registrationId}');" />
										</c:if>
									</csmauthz:accesscontrol>
								</c:when>
								<c:otherwise>
									<csmauthz:accesscontrol domainObject="${command.studySubject}"
										hasPrivileges="UPDATE"
										authorizationCheckName="domainObjectAuthorizationCheck">
										<tags:button type="button" id="registerCompanionStudy" size="small" color="green" value="Register" onclick="openPopup('${ companion.companionStudyId}','${command.studySubject.participant.id}','${command.studySubject.id}');" />
									</csmauthz:accesscontrol>
								</c:otherwise>
							</c:choose>
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</tags:formPanelBox>
	</c:when>
	<c:when test="${fn:length(companions)>0 && ! command.studySubject.currentScheduledEpoch.epoch.enrollmentIndicator}">
		<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
			<div><fmt:message key="REGISTRATION.NO_COMAPNION_REGISTRATION_INVOLVED_FOR_EPOCH"/></div><br>
		</tags:formPanelBox>	
	</c:when>
	<c:otherwise>
		<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
			<div><fmt:message key="REGISTRATION.NO_COMAPNION_REGISTRATION_INVOLVED"/></div><br>
		</tags:formPanelBox>	
	</c:otherwise>
</c:choose>
</div>
</body>
</html>
