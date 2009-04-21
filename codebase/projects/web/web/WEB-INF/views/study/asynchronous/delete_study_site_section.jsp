<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach items="${command.study.studySites}" varStatus="status" var="site">
	<chrome:deletableDivision divTitle="studySite-${site.healthcareSite.nciInstituteCode}" onclick="deleteStudySite('${site.healthcareSite.nciInstituteCode}');" title="(${site.healthcareSite.nciInstituteCode}) ${site.healthcareSite.name} : ${site.siteStudyStatus.code}" minimize="true" divIdToBeMinimized="site-${status.index}" id="divison-${site.healthcareSite.nciInstituteCode}" cssClass="divisonClass">
		<div id="site-${status.index}" style="display:none;">
			<div class="row">
				<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="site.IRBApprovalDate" /></div>
						<div class="value" id="siteIRB-${site.healthcareSite.nciInstituteCode }">
							<input type="text" name="study.studySites[${status.index}].irbApprovalDate" id="irbApprovalDate-${site.healthcareSite.nciInstituteCode}" class="date validate-DATE" />
				            <a href="#" id="irbApprovalDate-${site.healthcareSite.nciInstituteCode}-calbutton">
				           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
				           	</a>
						</div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.targetAccrual" /></div>
						<div class="value">
							<input name="study.studySites[${status.index}].targetAccrualNumber" maxlength="6" class="validate-NUMERIC" size="6"/>
						</div>
					</div>
					<div class="row" id="actions-${status.index}">
						<div class="label"><fmt:message key="site.actions" /></div>
						<div class="value" id="actions-${site.healthcareSite.nciInstituteCode }">
         							<c:set var="noAction" value="true"/>
         							<c:if test="${fn:length(site.possibleTransitions)>0 && (site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode || localNCICode==site.healthcareSite.nciInstituteCode)}">
         								<select id="siteAction-${site.healthcareSite.nciInstituteCode}">
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
         								<tags:button type="button" color="blue" value="Go" id="go" onclick="takeAction('${site.healthcareSite.nciInstituteCode}');" size="small"/>
							</c:if>
							<div id="sendingMessage-${site.healthcareSite.nciInstituteCode }" class="working" style="display: none">
								Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
							</div>
						</div>
						<c:if test="${noAction}">
							<script>
								Element.hide('actions-'+${status.index});
							</script>
						</c:if>
					</div>
					<div class="row" id="message-${status.index}">
						<div class="label"><fmt:message key="site.messages" /></div>
						<div class="value" id="Messages-${site.healthcareSite.nciInstituteCode }">
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
					</div>
				</div>
				<div class="rightpanel">
					<div class="row">
						<div class="label"><fmt:message key="site.activationDate" /></div>
						<div class="value">
							<input type="text" name="study.studySites[${status.index}].startDate" id="startDate-${site.healthcareSite.nciInstituteCode}" class="date validate-DATE" />
				            <a href="#" id="startDate-${site.healthcareSite.nciInstituteCode}-calbutton">
				           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
				           	</a>
						</div>
					</div>
					<div class="row">
						<c:if test="${multisiteEnv}">
							<div class="label"><fmt:message key="site.hostedMode" /></div>
							<div class="value">
		            			<input  type="checkbox" name="study.studySites[${status.index}].hostedMode"/>
         						<input type="hidden" name="${command.study.studySites[status.index].healthcareSite.nciInstituteCode}-wasHosted" value="${command.study.studySites[status.index].hostedMode}"/>
							</div>
 			        			</c:if>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.status" /></div>
						<div class="value" id="siteStatus-${site.healthcareSite.nciInstituteCode }">${site.siteStudyStatus.code}</div>
					</div>
				</div>
			</div>
		</div>
	</chrome:deletableDivision>
	<div class="division"></div>	
</c:forEach>
			