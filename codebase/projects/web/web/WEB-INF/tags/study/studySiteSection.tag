<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="studysiteTags" tagdir="/WEB-INF/tags/studysite" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="site" required="true" type="edu.duke.cabig.c3pr.domain.StudySite"%>
<%@attribute name="index" required="true"%>
<%@attribute name="localNCICode" required="true"%>
<%@attribute name="isMultisite" required="true"%>
<%@attribute name="maximized"%>
<%@attribute name="action" type="edu.duke.cabig.c3pr.constants.APIName"%>
<%@attribute name="errorMessage"%>
<%@attribute name="isNewStudySite"%>
<%@attribute name="site_index"%>
<c:set var="keepOpen" value="${(!empty maximized && maximized) || fn:contains(openSections, site.healthcareSite.primaryIdentifier)}"/>
<c:set var="isActionSuccess" value="${empty errorMessage?true:false}" />
<c:set var="isLocalSiteCoordinating" value="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.primaryIdentifier}"/>
<c:set var="isSiteLocal" value="${localNCICode==site.healthcareSite.primaryIdentifier}"></c:set>
<c:set var="isSiteManageable" value="${site.hostedMode || isLocalSiteCoordinating || isSiteLocal}"/>
<c:set var="showActionButtons" value="${empty isNewStudySite || !isNewStudySite}"/>
<style>
	.rightpanel {
		float:right;
		width:60%;
	}
	.leftpanel {
		float:left;
		width:38%;
	}
</style>
<jsp:useBean id="now" class="java.util.Date"/>
<div id="statusChangeConfirmation" style="display : none;padding: 15px;">
	<img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="STUDY.SITE.STATUS_CHANGE.WARNING"/>
	<div class="flow-buttons">
   	<span class="next">
   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopupOnly();" size="small"/>
		<tags:button type="button" color="green" icon="save" onclick="takeAction(${site.healthcareSite.primaryIdentifier});" value="Continue" size="small"/>
	</span>
	</div>
</div>
<chrome:deletableDivision divTitle="studySite-${site.healthcareSite.primaryIdentifier}" onclick="deleteStudySite('${site.healthcareSite.primaryIdentifier}');" title="(${site.healthcareSite.primaryIdentifier}) ${site.healthcareSite.name} : ${site.siteStudyStatus.code}" minimize="${keepOpen ? 'false':'true'}" divIdToBeMinimized="site-${site.healthcareSite.primaryIdentifier}" id="divison-${site.healthcareSite.primaryIdentifier}" cssClass="divisonClass" disableDelete="${fn:length(site.siteStatusHistory) > 1 || (fn:length(site.siteStatusHistory) == 1 && site.siteStudyStatus.name != 'PENDING')}">
<div id="site-${site.healthcareSite.primaryIdentifier}" style="${keepOpen ? '':'display:none'}" class="hiddenDiv">
	<div class="row">
		<c:if test='${not empty studyVersionAssociationMap[site.healthcareSite.primaryIdentifier]}'>
			<div id="flash-message" class="${studyVersionAssociationMap[site.healthcareSite.primaryIdentifier][0]}">
				<img src="<tags:imageUrl name='error-${studyVersionAssociationMap[site.healthcareSite.primaryIdentifier][0]}.png'/>" style="vertical-align:bottom;">&nbsp;
				${studyVersionAssociationMap[site.healthcareSite.primaryIdentifier][1]}
			</div>
		</c:if>
		<c:if test="${!empty action}">
		<div class="row">
			<c:choose>
				<c:when test="${isActionSuccess}">
					<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /><fmt:message key="site.action.success.${action}" /></div>
				</c:when>
				<c:otherwise>
				<div id="flash-message" class="error"><img src="<tags:imageUrl name='error-red.png'/>" style="vertical-align:bottom;">&nbsp;<fmt:message key="site.action.error.${action}" />&nbsp;${errorMessage}</div>
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>
		<div class="leftpanel">
			<div class="row">
				<div class="label"><fmt:message key="site.studyVersion" />:</div>
				<div class="value">
					${site.currentStudySiteStudyVersion.studyVersion.name}
				</div>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.targetAccrual" />:</div>
				<div class="value">
					<c:choose>
						<c:when test="${isSiteManageable}">
							<input type="text" id="targetAccrual-${site.healthcareSite.primaryIdentifier}" name="study.studySites[${index}].targetAccrualNumber" class="validate-NUMERIC" size="6" value="${site.targetAccrualNumber}"/>
						</c:when>
						<c:otherwise>
							${empty site.targetAccrualNumber?'Not specified':site.targetAccrualNumber}
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="rightpanel">
			<div class="row">
				<div class="label"><fmt:message key="site.IRBApprovalDate" />:</div>
				<div class="value">
					<c:choose>
						<c:when test="${isSiteManageable && fn:length(site.siteStatusHistory)  == 1}">
							<input type="text" <c:if test="${site.study.studyVersion.versionDate gt now}"> disabled="true" </c:if> name="study.studySites[${index}].irbApprovalDate" id="irbApprovalDate-${site.healthcareSite.primaryIdentifier}"
							class="date validate-DATE" value="${site.currentStudySiteStudyVersion.irbApprovalDateStr}"/>
							 <c:if test="${site.study.studyVersion.versionDate <= now}">
				            	<a href="#" id="irbApprovalDate-${site.healthcareSite.primaryIdentifier}-calbutton">
				           	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
				           		</a>
				           		<script type="text/javascript">
									Calendar.setup(
							            {
							                inputField  : "irbApprovalDate-${site.healthcareSite.primaryIdentifier}",
							                button      : "irbApprovalDate-${site.healthcareSite.primaryIdentifier}-calbutton",
							                ifFormat    : "%m/%d/%Y", 
							                weekNumbers : false
							            }
							        );
								</script>
							 </c:if>
						</c:when>
						<c:otherwise>
								<c:choose>
									<c:when test="${empty site.currentStudySiteStudyVersion.irbApprovalDateStr}">'Not specified'</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${site.currentStudySiteStudyVersion.irbApprovalDate.time le yearOld.timeInMillis}">
												<tags:inPlaceEdit value="${site.currentStudySiteStudyVersion.irbApprovalDateStr}" path="study.studySites[${site_index}].studySiteStudyVersions[${site.currentStudySiteStudyVersionIndex}].irbApprovalDate" 
												id="irbApprovalDateRenewal_${site.healthcareSite.primaryIdentifier}" validations="validate-notEmpty"/>
											</c:when>
											<c:otherwise>
												${site.currentStudySiteStudyVersion.irbApprovalDateStr}
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row">
				<c:if test="${isMultisite}">
					<div class="label"><fmt:message key="site.hostedMode" />:</div>
					<div class="value">
					<c:choose>
						<c:when test="${isLocalSiteCoordinating}">
							<input type="checkbox" id="hostedMode-${site.healthcareSite.primaryIdentifier}" name="study.studySites[${index}].hostedMode" ${site.hostedMode?'checked':'' } />
	            			<input type="hidden" id="_hostedMode-${site.healthcareSite.primaryIdentifier}" value="1" name="_study.studySites[${index}].hostedMode"/>
       						<input type="hidden" id="hostedMode-wasHosted-${site.healthcareSite.primaryIdentifier}" name="${site.healthcareSite.primaryIdentifier}-wasHosted" value="${site.hostedMode}"/>
						</c:when>
						<c:otherwise>
							${empty site.hostedMode?'No':(site.hostedMode?'Yes':'No') }
							<input type="hidden" id="hostedMode-wasHosted-${site.healthcareSite.primaryIdentifier}" name="${site.healthcareSite.primaryIdentifier}-wasHosted" value="${site.hostedMode}"/>
						</c:otherwise>
					</c:choose>
					</div>
		        </c:if>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.status" />:</div>
				<div class="value">${site.siteStudyStatus.code} <c:if test="${fn:length(site.siteStatusHistory) > 1}"> &nbsp;&nbsp;<a href="#" onclick="showSiteStatusHistory('${site.healthcareSite.primaryIdentifier}');">Status history</a></c:if></div>
			</div>
			<c:if test="${site.nextPossibleSiteStatusHistory != null}">
			<div class="row">
				<div class="green">Study site status will change to ${site.nextPossibleSiteStatusHistory.siteStudyStatus.displayName} on  ${site.nextPossibleSiteStatusHistory.startDateStr}</div>
			</div>
			</c:if>
		</div>
	</div>
	<c:if test="${fn:length(site.possibleTransitions)>0 && isSiteManageable}">
	<div class="row">
		<c:set var="closeToAccrual" value="false"/>
		<c:set var="closeToAccrualAndTreatment" value="false"/>
		<c:set var="temporaryCloseToAccrual" value="false"/>
		<c:set var="temporaryCloseToAccrualAndTreatment" value="false"/>
			<c:forEach items="${site.possibleTransitions}" var="possibleAction">
			<c:choose>
				<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE'}">
					<c:if test="${site.hostedMode || isSiteLocal}">
						<c:choose>
							<c:when test="${site.study.studyVersion.versionDate gt now}">
								<tags:button type="button" color="blue" value="${possibleAction.displayName }"  disabled="true"  id="${possibleAction}" onclick="$('siteIndicator-${site.healthcareSite.primaryIdentifier}-${possibleAction}').style.display='';chooseEffectiveDate('${site.healthcareSite.primaryIdentifier}', '${possibleAction}');" size="small"/>
								<img id="siteIndicator-${site.healthcareSite.primaryIdentifier}-${possibleAction}" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
								<div><span style="color:orange">Study site IRB approval date cannot be specified and site be activated until the study becomes 
									'Open' on ${site.study.studyVersion.versionDateStr} </span>
								</div>
							</c:when>
							<c:otherwise>
								<tags:button type="button" color="blue" value="${possibleAction.displayName }"  id="${possibleAction}" onclick="$('siteIndicator-${site.healthcareSite.primaryIdentifier}-${possibleAction}').style.display='';chooseEffectiveDate('${site.healthcareSite.primaryIdentifier}', '${possibleAction}');" size="small"/>
								<img id="siteIndicator-${site.healthcareSite.primaryIdentifier}-${possibleAction}" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:when>
				<c:when test="${possibleAction=='CLOSE_STUDY_SITE_TO_ACCRUAL'}">
					<c:if test="${site.hostedMode || isLocalSiteCoordinating}">
						<c:set var="closeToAccrual" value="true"/>
					</c:if>
				</c:when>
				<c:when test="${possibleAction=='CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT'}">
					<c:if test="${site.hostedMode || isLocalSiteCoordinating}">
						<c:set var="closeToAccrualAndTreatment" value="true"/>
					</c:if>
				</c:when>
				<c:when test="${possibleAction=='TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL'}">
					<c:if test="${site.hostedMode || isLocalSiteCoordinating}">
						<c:set var="temporaryCloseToAccrual" value="true"/>
					</c:if>
				</c:when>
				<c:when test="${possibleAction=='TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT'}">
					<c:if test="${isSiteManageable}">
						<c:set var="temporaryCloseToAccrualAndTreatment" value="true"/>
					</c:if>
				</c:when>
				<c:otherwise>
					<tags:button type="button" color="blue" value="${possibleAction.displayName }" id="${possibleAction}" onclick="$('siteIndicator-${site.healthcareSite.primaryIdentifier}-${possibleAction}').style.display='';chooseEffectiveDate('${site.healthcareSite.primaryIdentifier}', '${possibleAction}');" size="small"/>
					<img id="siteIndicator-${site.healthcareSite.primaryIdentifier}-${possibleAction}" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
				</c:otherwise>
			</c:choose>
			</c:forEach>
		<c:if test="${closeToAccrual || closeToAccrualAndTreatment || temporaryCloseToAccrual || temporaryCloseToAccrualAndTreatment}">
			<tags:button type="button" color="blue" value="Close Study Site" id="closeStudy"
			onclick="Effect.SlideDown('close-choices-${site.healthcareSite.primaryIdentifier }')" size="small"/>
			<img id="siteIndicator-${site.healthcareSite.primaryIdentifier}-closeStudySite" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
				<div id="close-choices-${site.healthcareSite.primaryIdentifier }" class="autocomplete" style="display: none">
				<ul>
				<c:if test="${closeToAccrualAndTreatment}">
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="$('siteIndicator-${site.healthcareSite.primaryIdentifier}-closeStudySite').style.display='';chooseEffectiveDate('${site.healthcareSite.primaryIdentifier}', 'CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT');">Closed To Accrual And Treatment</li>
				</c:if>
				<c:if test="${closeToAccrual}">
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="$('siteIndicator-${site.healthcareSite.primaryIdentifier}-closeStudySite').style.display='';chooseEffectiveDate('${site.healthcareSite.primaryIdentifier}', 'CLOSE_STUDY_SITE_TO_ACCRUAL');">Closed To Accrual</li>
				</c:if>
				<c:if test="${temporaryCloseToAccrualAndTreatment}">
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="$('siteIndicator-${site.healthcareSite.primaryIdentifier}-closeStudySite').style.display='';chooseEffectiveDate('${site.healthcareSite.primaryIdentifier}', 'TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT');">Temporarily Closed To Accrual And Treatment</li>
					</c:if>
				<c:if test="${temporaryCloseToAccrual}">
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="$('siteIndicator-${site.healthcareSite.primaryIdentifier}-closeStudySite').style.display='';chooseEffectiveDate('${site.healthcareSite.primaryIdentifier}', 'TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL');">Temporarily Closed To Accrual</li>
				</c:if>
				</ul>
				<div align="right"><tags:button type="button" color="red" value="Cancel" icon="x"
					onclick="Effect.SlideUp('close-choices-${site.healthcareSite.primaryIdentifier }')" size="small"/></div>
			</div>
		</c:if>
		<div id="sendingMessage-${site.healthcareSite.primaryIdentifier }" class="working" style="display: none">
			Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
		</div>
	</div>
	</c:if>
	<div id="site_study_version-${site.healthcareSite.primaryIdentifier}">
	<div>
		<table id="siteStudyVersionsTable-${site.healthcareSite.primaryIdentifier}" class="tablecontent" border="0" cellspacing="0" cellpadding="0" width="100%">
	        <tr>
	            <th align="center"><b><tags:requiredIndicator /><fmt:message key="study.site.version"/></b>
	            <tags:hoverHint keyProp="study.site.version" /></th>
	            <th align="center"><b><fmt:message key="study.site.siteversion.startDate"/></b>
	            <tags:hoverHint keyProp="study.site.siteversion.startdate" /></th>
	            <th align="center"><b><fmt:message key="study.site.siteversion.endDate"/></b>
	            <tags:hoverHint keyProp="study.site.siteversion.enddate" /></th>
	            <th align="center"><b><fmt:message key="study.site.siteversion.irbApprovalDate"/></b>
	            <tags:hoverHint keyProp="study.site.siteversion.irbApprovalDate" /></th>
	            <th/>
	        </tr>
	        <c:choose>
	        	<c:when test="${fn:length(site.study.reverseSortedStudyVersions) == 0 || site.study.reverseSortedStudyVersions[0].versionStatus.name != 'AC'}">
	        		<tr>
	        			<td colspan="4"><fmt:message key="study.studysiteversion.noVersion" /></td>
	        		</tr>
	        	</c:when>
	        	<c:otherwise>
			        <c:forEach items="${site.study.reverseSortedStudyVersions}" var="sortedStudyVersion" varStatus="status">
						<c:if test="${sortedStudyVersion.versionStatus.name == 'AC'}">
					        <c:set var="studySiteStudyVersionPresent" value="false"></c:set>
					        <tr>
					            <td>${sortedStudyVersion.name }</td>
				            	<c:forEach items="${sortedStudyVersion.studySiteStudyVersions}" var="studySiteStudyVersion">
						            <c:if test="${studySiteStudyVersion.studySite.id == site.id}">
						            	<td>${studySiteStudyVersion.startDateStr }</td>
							            <td>${studySiteStudyVersion.endDateStr }</td>
							            <td>${studySiteStudyVersion.irbApprovalDateStr }</td>
							            <td>
							            <c:if test="${fn:length(site.study.reverseSortedStudyVersions) != status.index + 1 }">
							            	<tags:button type="button" color="blue" value="View summary" id="viewSummary-${sortedStudyVersion.id}" onclick="viewAmendmentSummary('${sortedStudyVersion.id}');" size="small"/>
							            </c:if>
							            </td>
							            <c:set var="studySiteStudyVersionPresent" value="true"></c:set>
							            <c:set var="newerVersionApplied" value="true"></c:set>
						            </c:if>
					            </c:forEach>
					            <c:if test="${studySiteStudyVersionPresent != true}">
					            	<td></td>
						            <td></td>
						            <td>
						            	<c:if test="${newerVersionApplied != true}">
							            	<input type="text" name="irbApproval-${site.healthcareSite.primaryIdentifier}-${status.index}" id="irbApproval-${site.healthcareSite.primaryIdentifier}-${status.index}"/>
								           	<a href="#" id="irbApproval-${site.healthcareSite.primaryIdentifier}-${status.index}-calbutton">
								      	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
								        	</a>
								        	<script type="text/javascript">
												Calendar.setup(
										            {
										                inputField  : "irbApproval-${site.healthcareSite.primaryIdentifier}-${status.index}",
										                button      : "irbApproval-${site.healthcareSite.primaryIdentifier}-${status.index}-calbutton",
										                ifFormat    : "%m/%d/%Y", 
										                weekNumbers : false
										            }
										        );
											</script>
										</c:if>
						            </td>
						            <td>
						            	<c:if test="${newerVersionApplied != true}">
					            		<tags:button type="button" color="blue" value="Apply amendment" id="applyAmendment-${site.healthcareSite.primaryIdentifier}-${status.index}" onclick="$('applyIndicator-${site.healthcareSite.primaryIdentifier}-${status.index}').style.display='';applyAmendment('${site.healthcareSite.primaryIdentifier}', ${status.index}, '${index}', '${localNCICode}', '${isMultisite}', 'APPLY_AMENDMENT',  '${sortedStudyVersion.name }');" size="small"/>
						            		<img id="applyIndicator-${site.healthcareSite.primaryIdentifier}-${status.index}" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
						            	</c:if>
						            	<tags:button type="button" color="blue" value="View summary" id="viewSummary-${sortedStudyVersion.id}" onclick="viewAmendmentSummary('${sortedStudyVersion.id}');" size="small"/>
						            </td>
					            </c:if>
					        </tr>
					        <div id="versionSummary-${sortedStudyVersion.id}" style="display:none;">
								<studyTags:study_amendment_summary studyVersion="${sortedStudyVersion}"></studyTags:study_amendment_summary>
							</div>
						</c:if>
			        </c:forEach>
	        	</c:otherwise>
	        </c:choose>
	    </table>
	   <div>
	</div>
</div>
</div>
<div id="site_status_history-${site.healthcareSite.primaryIdentifier}" style="display:none">
<chrome:division title="Site Status History" >
	<table id="siteStatusHistoryTable-${site.healthcareSite.primaryIdentifier}" class="tablecontent" border="0" cellspacing="0" cellpadding="0" width="95%" align="center">
        <tr>
            <th align="center"><b><fmt:message key="study.site.status"/></b>
            <tags:hoverHint keyProp="study.site.status" /></th>
            <th align="center"><b><fmt:message key="study.site.startdate"/></b>
            <tags:hoverHint keyProp="study.site.startdate" /></th>
            <th align="center"><b><fmt:message key="study.site.enddate"/></b>
            <tags:hoverHint keyProp="study.site.enddate" /></th>
        </tr>
        <c:forEach items="${site.sortedSiteStatusHistory}" var="siteStatusHistory">
        <tr>
            <td>${siteStatusHistory.siteStudyStatus.displayName }</td>
            <td>${siteStatusHistory.siteStudyStatus == 'PENDING'? 'NA ' : siteStatusHistory.startDateStr }</td>
            <td>${siteStatusHistory.endDateStr }</td>
        </tr>
        </c:forEach>
    </table>
    <br>
    <div align="center">
    	<tags:button type="button" color="red" value="Close" id="close" onclick="closePopupOnly();"  />
    </div>
</chrome:division>
 </div>
</div>
</chrome:deletableDivision>
<div style="display:none">
<div id="effectiveDate-${site.healthcareSite.primaryIdentifier}">
<input type="hidden" name="_actionToTake" id="_actionToTake" />

	<chrome:division title="Choose Effective Date">
		<div class="row">
			<div class="label">
				<fmt:message key="site.effectiveDate" />
			</div>
			<div class="value">
				<input type="text" name="effectiveDate" id="effectiveDateField-${site.healthcareSite.primaryIdentifier}" class="validate-DATE" value="${currentDate }"/>
	           	<a href="#" id="effectiveDateField-${site.healthcareSite.primaryIdentifier}-calbutton">
	      	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
	        	</a>
	        	<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "effectiveDateField-${site.healthcareSite.primaryIdentifier}",
			                button      : "effectiveDateField-${site.healthcareSite.primaryIdentifier}-calbutton",
			                ifFormat    : "%m/%d/%Y", 
			                weekNumbers : false
			            }
			        );
				</script>
				<br>
				<div class="row">
					<tags:button type="button" color="blue" value="OK" id="changeSiteStatus-${site.healthcareSite.primaryIdentifier}" onclick="takeAction('${site.healthcareSite.primaryIdentifier}' );" size="small"/>
					<tags:button type="button" color="red" value="Cancel" id="cancel" onclick="closePopup('${site.healthcareSite.primaryIdentifier}', '_actionToTake');" size="small"/>
				</div>
			</div>
		</div>
		</chrome:division>
		<div id="effectiveDateDiv-${site.healthcareSite.primaryIdentifier}" style="display: none;"></div>
	<div id="effectiveDateError-${site.healthcareSite.primaryIdentifier}"></div>
</div>
</div>