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
						zIndex:100, width:800, height:550 , 
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
						zIndex:100, width:800, height:550 , 
						minimizable:false, maximizable:false,
						url: "<c:url value='/pages/registration/editCompanionRegistration?decorator=noheaderRegistrationflow&'/>" + url , 
					 	 showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter(true);
   		//	document.location="../registration/editCompanionRegistration?"+url;
   	   	}

								
   		function manageCompanionRegistration(url){
   			document.location="../registration/manageRegistration?"+url;
   	   	}

   	   	function reloadSection(){
   	   		<tags:tabMethod method="refreshCompanionSection" divElement="'CompanionRegistration'" formName="'companionRegForm'"  viewName="/registration/asynchronous/reg_companion_section" />
   	   	}
	</script>
</head>
<body>
<div id="CompanionRegistration">
<c:choose>
	<c:when test="${fn:length(companions)>0}">
		<tags:formPanelBox tab="${tab}" flow="${flow}">
			<table class="tablecontent" width="100%"
				title="Companions">
				<tr>
					<th width="40%" scope="col" align="center"><b><fmt:message key="study.studyShortTitle"/>(<fmt:message key="c3pr.common.identifier"/>)</b></th>
					<th width="9%" scope="col" align="center"><b><fmt:message key="c3pr.common.mandatory"/></b></th>
					<th width="18%" scope="col" align="center"><b><fmt:message key="registration.registrationStatus"/></b></th>
					<th />
				</tr>
				<c:forEach items="${companions}" var="companion">
					<tr>
						<td class="alt">${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})</td>
						<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>
						<td class="alt">${companion.registrationId == 0?"Not Started":companion.registrationStatus}</td>
						<td class="alt">
						<c:choose>
							<c:when test="${companion.registrationId != 0 && companion.registrationDataEntryStatus == 'Complete'}">
								<csmauthz:accesscontrol domainObject="${command.studySubject}"
									hasPrivileges="UPDATE"
									authorizationCheckName="domainObjectAuthorizationCheck">
									<input type="button" value="Edit"
										onclick='editCompanionRegistration("${companion.companionRegistrationUrl}");' />
									<input type="button" value="Remove"
										onclick='manageCompanionRegistration("${companion.companionRegistrationUrl}");' />
							
								</csmauthz:accesscontrol>
							</c:when>
							<c:otherwise>
								<csmauthz:accesscontrol domainObject="${command.studySubject}"
									hasPrivileges="UPDATE"
									authorizationCheckName="domainObjectAuthorizationCheck">
									<input type="button" id="registerCompanionStudy"
										value="Register"
										onclick="openPopup('${ companion.companionStudyId}','${command.studySubject.participant.id}','${command.studySubject.id}');" />
								</csmauthz:accesscontrol>
							</c:otherwise>
						</c:choose></td>
					</tr>
				</c:forEach>
			</table>
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
