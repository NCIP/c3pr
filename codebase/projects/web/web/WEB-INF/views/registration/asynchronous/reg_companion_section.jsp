<%@ include file="../taglibs.jsp"%>
<c:choose>
	<c:when test="${fn:length(companions)>0}">
		<tags:formPanelBox tab="${tab}" flow="${flow}">
			<table class="tablecontent" width="100%"
				title="Companions">
				<tr>
					<th width="40%" scope="col" align="center"><b><fmt:message key="c3pr.common.study"/></b></th>
					<th width="10%" scope="col" align="center"><b><fmt:message key="c3pr.common.mandatory"/></b></th>
					<th width="18%" scope="col" align="center"><b><fmt:message key="c3pr.common.status"/></b></th>
					<th />
				</tr>
				<c:forEach items="${companions}" var="companion">
					<tr>
						<td class="alt">
							<c:if test="${companion.mandatoryIndicator}"><tags:requiredIndicator /></c:if>
							${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})
						</td>
						<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>
						<td class="alt">${companion.registrationId == 0?"Not Started":companion.registrationStatus}</td>
						<td class="alt">
						<c:choose>
							<c:when test="${companion.registrationId != 0}">
								<csmauthz:accesscontrol domainObject="${command.studySubject}"
									hasPrivileges="UPDATE"
									authorizationCheckName="domainObjectAuthorizationCheck">
									<c:if test="${companion.registrationStatus != 'Registered but not enrolled'}">
										<input type="button" value="Edit" onclick='editCompanionRegistration("${companion.companionRegistrationUrl}");' />
									</c:if>
									<c:if test="${!companion.mandatoryIndicator}">
										<input type="button" value="Remove" onclick='manageCompanionRegistration("${companion.companionRegistrationUrl}");' />
									</c:if>
								</csmauthz:accesscontrol>
							</c:when>
							<c:otherwise>
								<csmauthz:accesscontrol domainObject="${command.studySubject}"
									hasPrivileges="UPDATE"
									authorizationCheckName="domainObjectAuthorizationCheck">
									<input type="button" id="registerCompanionStudy" value="Register"
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
