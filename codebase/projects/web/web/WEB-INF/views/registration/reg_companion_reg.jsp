<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">

<script>
		var win;
		function openPopup(companionStudy, participant, parentRegistrationId, index){
			$('searchCompanionInd-'+index).style.display = "";
			win = new Window(
					{onClose: function() {reloadSection();},
						title: "Companion Registration", 
						top:35, scrollbar: false, left:100, 
						zIndex:100, width:850, height:500 , 
						minimizable:false, maximizable:false,
						closable: true, destroyOnClose:true,
						url: "<c:url value='/pages/registration/createEmbeddedCompanionRegistration?decorator=noheaderRegistrationflow'/>" +"&parentRegistrationId=" + parentRegistrationId +"&study=" + companionStudy, 
					  showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter(true)
		}

		function closePopup() {
			win.close();
		}

		function editCompanionRegistration(url, index){
			$('searchCompanionInd-'+index).style.display = "";
			win = new Window(
					{onClose: function() {reloadSection();},
						title: "Companion Registration", 
						top:35, scrollbar: false, left:100, 
						zIndex:100, width:850, height:500 , 
						minimizable:false, maximizable:false,
						closable: true, destroyOnClose:true,
						url: "<c:url value='/pages/registration/editCompanionRegistration?decorator=noheaderRegistrationflow&companionRegistration=true&'/>" + url , 
					 	 showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter(true);
   	   	}

								
   		function removeChildStudySubject(childRegId, index){
   			$('searchCompanionInd-'+index).style.display = "";
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
	<c:when test="${fn:length(companions)>0 && command.studySubject.scheduledEpoch.epoch.enrollmentIndicator}">
		<tags:formPanelBox tab="${tab}" flow="${flow}">
			<table class="tablecontent" width="100%"
				title="Companions">
				<tr>
					<th width="35%" scope="col" align="center"><b><fmt:message key="c3pr.common.study"/></b><tags:hoverHint keyProp="study.shortTitleText" /></th>
					<th width="11%" scope="col" align="center"><b><fmt:message key="c3pr.common.mandatory"/></b><tags:hoverHint keyProp="study.companionstudy.mandatory" /></th>
					<th width="19%" scope="col" align="center"><b><fmt:message key="c3pr.common.status"/></b><tags:hoverHint keyProp="registration.status" /></th>
					<th width="21%" scope="col" align="center"><b><fmt:message key="c3pr.common.actions"/></b><tags:hoverHint keyProp="registration.action" /></th>
				</tr>
				<c:forEach items="${companions}" var="companion" varStatus="status">
					<tr>
						<td class="alt">
							<c:if test="${companion.mandatoryIndicator}"><tags:requiredIndicator /></c:if>
							${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})
						</td>
						<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>
						<td class="alt">${empty companion.companionRegistrationUrl ?"Not Started": (companion.registrationStatus == 'PENDING_ON_STUDY')?'Pending':companion.registrationStatus}</td>
						<td class="alt">
						<c:if test="${companion.registrationStatus != 'ON_STUDY'}">
							<c:choose>
								<c:when test="${not empty companion.companionRegistrationUrl}">
									<csmauthz:accesscontrol domainObject="${command.studySubject}"
										hasPrivileges="UI_STUDYSUBJECT_UPDATE"
										authorizationCheckName="studySubjectAuthorizationCheck">
										<a href="javascript:editCompanionRegistration('${companion.companionRegistrationUrl}', '${status.index}');"><img src="<tags:imageUrl name="../templates/mocha/images/controlPanel/controlPanel_pencil.png" />" alt="" /> Edit</a>
										&nbsp;
										<c:if test="${!companion.mandatoryIndicator}">
											<a href="javascript:removeChildStudySubject('${companion.registrationId}', '${status.index}');"><img src="<tags:imageUrl name="icons/button_icons/small/x_icon_small.png" />" alt="" /> Remove</a>
										</c:if>
									</csmauthz:accesscontrol>
								</c:when>
								<c:when test="${companion.companionStudyStatus == 'OPEN'}">
									<csmauthz:accesscontrol domainObject="${command.studySubject}"
										hasPrivileges="STUDYSUBJECT_UPDATE"
										authorizationCheckName="studySubjectAuthorizationCheck">
										<a id="registerCompanionStudy" href="javascript:openPopup('${ companion.companionStudyId}','${command.participant.id}','${command.studySubject.id}', '${status.index}');"><img src="<tags:imageUrl name="icons/button_icons/small/add_icon_small.png" />" alt="" /> Register</a>
									</csmauthz:accesscontrol>
								</c:when>
							</c:choose>
							<img id="searchCompanionInd-${status.index}" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">  
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</tags:formPanelBox>
	</c:when>
	<c:when test="${fn:length(companions)>0 && ! command.studySubject.scheduledEpoch.epoch.enrollmentIndicator}">
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
