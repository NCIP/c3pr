<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="site" required="true" type="edu.duke.cabig.c3pr.domain.StudySite"%>
<%@attribute name="index" required="true"%>
<%@attribute name="localNCICode" required="true"%>
<%@attribute name="isMultisite" required="true"%>
<%@attribute name="maximized"%>
<%@attribute name="action" type="edu.duke.cabig.c3pr.constants.APIName"%>
<%@attribute name="errorMessage"%>
<c:set var="keepOpen" value="${(!empty maximized && maximized) || fn:contains(openSections, site.healthcareSite.nciInstituteCode)}"/>
<c:set var="isActionSuccess" value="${empty errorMessage?true:false}" />
<c:set var="isLocalSiteCoordinating" value="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}"/>
<c:set var="isSiteLocal" value="${localNCICode==site.healthcareSite.nciInstituteCode}"></c:set>
<c:set var="isSiteManageable" value="${site.hostedMode || isLocalSiteCoordinating || isSiteLocal}"/>
<chrome:deletableDivision divTitle="studySite-${site.healthcareSite.nciInstituteCode}" onclick="deleteStudySite('${site.healthcareSite.nciInstituteCode}');" title="(${site.healthcareSite.nciInstituteCode}) ${site.healthcareSite.name} : ${site.siteStudyStatus.code}" minimize="${keepOpen ? 'false':'true'}" divIdToBeMinimized="site-${site.healthcareSite.nciInstituteCode}" id="divison-${site.healthcareSite.nciInstituteCode}" cssClass="divisonClass">
<div id="site-${site.healthcareSite.nciInstituteCode}" style="${keepOpen ? '':'display:none'}" class="hiddenDiv">
	<div class="row">
		<div class="leftpanel">
			<div class="row">
				<div class="label"><fmt:message key="site.IRBApprovalDate" /></div>
				<div class="value">
					<c:choose>
						<c:when test="${isSiteManageable}">
							<input type="text" name="study.studySites[${index}].irbApprovalDate" id="irbApprovalDate-${site.healthcareSite.nciInstituteCode}" 
							class="date validate-DATE" value="${site.irbApprovalDateStr}"/>
			            	<a href="#" id="irbApprovalDate-${site.healthcareSite.nciInstituteCode}-calbutton">
			           	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
			           		</a>
						</c:when>
						<c:otherwise>
							${empty site.irbApprovalDateStr?'NA':site.irbApprovalDateStr }
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.targetAccrual" /></div>
				<div class="value">
					<c:choose>
						<c:when test="${isSiteManageable}">
							<input type="test" id="targetAccrual-${site.healthcareSite.nciInstituteCode}" name="study.studySites[${index}].targetAccrualNumber" class="validate-NUMERIC" size="6" value="${site.targetAccrualNumber}"/>
						</c:when>
						<c:otherwise>
							${empty site.targetAccrualNumber?'NA':site.targetAccrualNumber}
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="rightpanel">
			<div class="row">
				<div class="label"><fmt:message key="site.activationDate" /></div>
				<div class="value">
					<c:choose>
						<c:when test="${isSiteManageable}">
							<input type="text" name="study.studySites[${index}].startDate" id="startDate-${site.healthcareSite.nciInstituteCode}" 
							class="date validate-DATE" value="${site.startDateStr}"/>
			            	<a href="#" id="startDate-${site.healthcareSite.nciInstituteCode}-calbutton">
			           	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
			           		</a>
						</c:when>
						<c:otherwise>
							${empty site.startDateStr?'NA':site.startDateStr }
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row">
				<c:if test="${isMultisite}">
					<div class="label"><fmt:message key="site.hostedMode" /></div>
					<div class="value">
					<c:choose>
						<c:when test="${isLocalSiteCoordinating}">
							<input type="checkbox" id="hostedMode-${site.healthcareSite.nciInstituteCode}" name="study.studySites[${index}].hostedMode" ${site.hostedMode?'checked':'' } />
	            			<input type="hidden" id="_hostedMode-${site.healthcareSite.nciInstituteCode}" value="1" name="_study.studySites[${index}].hostedMode"/>
       						<input type="hidden" id="" name="hostedMode-wasHosted-${site.healthcareSite.nciInstituteCode}" value="${site.hostedMode}"/>
						</c:when>
						<c:otherwise>
							${empty site.hostedMode?'No':(site.hostedMode?'Yes':'No') }
						</c:otherwise>
					</c:choose>
					</div>
		        </c:if>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.status" /></div>
				<div class="value">${site.siteStudyStatus.code}</div>
			</div>
		</div>
	</div>
	<c:if test="${fn:length(site.possibleTransitions)>0 && isSiteManageable}">
	<div class="row">
		<c:set var="close" value="false"/>
		<c:set var="temporary" value="false"/>
		<c:forEach items="${site.possibleTransitions}" var="possibleAction">
		<c:choose>
			<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE'}">
				<c:if test="${site.hostedMode || isSiteLocal}">
					<tags:button type="button" color="blue" value="${possibleAction.displayName }" id="${possibleAction}" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', '${possibleAction}');" size="small"/>
				</c:if>
			</c:when>
			<c:when test="${possibleAction=='CLOSE_STUDY_SITE_TO_ACCRUAL' || possibleAction=='CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT'}">
				<c:if test="${site.hostedMode || isLocalSiteCoordinating}">
					<c:set var="close" value="true"/>
				</c:if>
			</c:when>
			<c:when test="${possibleAction=='TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL' || possibleAction=='TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT'}">
				<c:if test="${isSiteManageable}">
					<c:set var="temporary" value="true"/>
				</c:if>
			</c:when>
			<c:otherwise>
				<tags:button type="button" color="blue" value="${possibleAction.displayName }" id="${possibleAction}" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', '${possibleAction}');" size="small"/>
			</c:otherwise>
		</c:choose>
		</c:forEach>
		<c:if test="${close}">
			<tags:button type="button" color="blue" value="Close Study Site" id="closeStudy"
			onclick="Effect.SlideDown('close-choices-${site.healthcareSite.nciInstituteCode }')" size="small"/>
			<div id="close-choices-${site.healthcareSite.nciInstituteCode }" class="autocomplete" style="display: none">
				<ul>
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', 'CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT');">Closed To Accrual And Treatment</li>
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', 'CLOSE_STUDY_SITE_TO_ACCRUAL');">Closed To Accrual</li>
					<c:if test="${temporary}">
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', 'TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT');">Temporarily Closed To Accrual And Treatment</li>
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', 'TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL');">Temporarily Closed To Accrual</li>
					</c:if>
				</ul>
				<div align="right"><tags:button type="button" color="red" value="Cancel" icon="x"
					onclick="Effect.SlideUp('close-choices')" size="small"/></div>
			</div>
		</c:if>
		<div id="sendingMessage-${site.healthcareSite.nciInstituteCode }" class="working" style="display: none">
			Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
		</div>
	</div>
	</c:if>
	<c:if test="${!empty action}">
	<div class="row">
		<c:choose>
			<c:when test="${isActionSuccess}">
				<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /><fmt:message key="site.action.success.${action}" /></div>
			</c:when>
			<c:otherwise>
				<div style="float:left" width="5%"><img src="<tags:imageUrl name='error.gif'/>" width="16" height="16"></div>
				<div align="left"><font color="red"><fmt:message key="site.action.error.${action}" /> ${errorMessage}</font></div>
				
			</c:otherwise>
		</c:choose>
	</div>
	</c:if>
</div>
</chrome:deletableDivision>