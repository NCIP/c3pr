<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="../taglibs.jsp"%>
<c:choose>
	<c:when test="${fn:length(companions)>0 && command.studySubject.scheduledEpoch.epoch.enrollmentIndicator}">
		<tags:formPanelBox tab="${tab}" flow="${flow}">
			<table class="tablecontent" width="100%"
				title="Companions">
				<tr>
					<th width="40%" scope="col" align="center"><b><fmt:message key="c3pr.common.study"/></b><tags:hoverHint keyProp="study.shortTitleText" /></th>
					<th width="10%" scope="col" align="center"><b><fmt:message key="c3pr.common.mandatory"/></b><tags:hoverHint keyProp="study.companionstudy.mandatory" /></th>
					<th width="18%" scope="col" align="center"><b><fmt:message key="c3pr.common.status"/></b><tags:hoverHint keyProp="registration.status" /></th>
					<th width="18%" scope="col" align="center"><b><fmt:message key="c3pr.common.actions"/></b><tags:hoverHint keyProp="registration.action" /></th>
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
