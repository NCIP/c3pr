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
					{onClose: function() {$('refreshPage').submit();},title: "Registration", top:35, scrollbar: false, left:100, zIndex:100, width:700, height:325 , url: "<c:url value='/pages/registration/createCompanionRegistration?decorator=noheaderDecorator&customButton=true&create_companion=true&participant='/>" + participant  +"&parentRegistrationId=" + parentRegistrationId +"&study=" + companionStudy, 
					  showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter(true)
		}

		function closePopup() {
			win.close();
			//reloadCompanion();
		}

		function reloadCompanion(){
			$('refreshPage').submit();
   		}

   		function manageCompanionRegistration(url){
   			document.location="../registration/manageRegistration?"+url;
   	   	}
	</script>
</head>
<body>

<c:choose>
	<c:when test="${fn:length(companions)>0}">
		<tags:formPanelBox tab="${tab}" flow="${flow}">
			<div id="CompanionRegistration">
			<table class="tablecontent" width="100%"
				title="Companion Registration">
				<tr>
					<th width="40%" scope="col" align="center"><b>Short
					Title-(Identifier)</b></th>
					<th width="9%" scope="col" align="center"><b>Mandatory</b></th>
					<th width="20%" scope="col" align="center"><b>Registration
					Status</b></th>
					<th />
				</tr>
				<c:forEach items="${companions}" var="companion">
					<tr>
						<td class="alt">${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})</td>
						<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>
						<td class="alt">${companion.registrationId == 0?"Not Started":companion.registrationStatus}</td>
						<td class="alt"><c:choose>
							<c:when test="${companion.registrationId != 0}">
								<csmauthz:accesscontrol domainObject="${command.studySubject}"
									hasPrivileges="UPDATE"
									authorizationCheckName="domainObjectAuthorizationCheck">
									<input type="button" value="Manage"
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
			</div>
		</tags:formPanelBox>
	</c:when>
	<c:otherwise>
		<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
			<div align="center"><fmt:message key="REGISTRATION.NO_COMAPNION_REGISTRATION_INVOLVED"/></div><br>
		</tags:formPanelBox>	</c:otherwise>
</c:choose>
</body>
</html>
