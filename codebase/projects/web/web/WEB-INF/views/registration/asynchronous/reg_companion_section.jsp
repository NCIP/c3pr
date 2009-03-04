<%@ include file="../taglibs.jsp"%>
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
								<c:when test="${not empty companion.companionRegistrationUrl }">
									<csmauthz:accesscontrol domainObject="${command.studySubject}"
										hasPrivileges="UPDATE"
										authorizationCheckName="domainObjectAuthorizationCheck">
										<a href="javascript:editCompanionRegistration('${companion.companionRegistrationUrl}');"><img src="<tags:imageUrl name="../templates/mocha/images/controlPanel/controlPanel_pencil.png" />" alt="" /> Edit</a>
										<c:if test="${!companion.mandatoryIndicator}">
											<a href="javascript:removeChildStudySubject('${companion.registrationId}');"><img src="<tags:imageUrl name="icons/button_icons/small/x_icon_small.png" />" alt="" /> Remove</a>
										</c:if>
									</csmauthz:accesscontrol>
								</c:when>
								<c:otherwise>
									<csmauthz:accesscontrol domainObject="${command.studySubject}"
										hasPrivileges="UPDATE"
										authorizationCheckName="domainObjectAuthorizationCheck">
										<a id="registerCompanionStudy" href="javascript:openPopup('${ companion.companionStudyId}','${command.studySubject.participant.id}','${command.studySubject.id}');"><img src="<tags:imageUrl name="icons/button_icons/small/add_icon_small.png" />" alt="" /> Register</a>
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