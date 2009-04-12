<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<chrome:division title="${site.healthcareSite.name} (${site.healthcareSite.nciInstituteCode})" minimize="true" divIdToBeMinimized="site-${index}">
	<div id="site-${index}">
		<div class="row">
			<div class="leftpanel">
				<div class="row">
					<div class="label"><fmt:message key="site.IRBApprovalDate" /></div>
					<div class="value" id="siteIRB-${site.healthcareSite.nciInstituteCode }">
						<input type="text" name="study.studySites[${index}].irbApprovalDate" id="irbApprovalDate-${site.healthcareSite.nciInstituteCode}" class="date validate-DATE" />
			            <a href="#" id="irbApprovalDate-${site.healthcareSite.nciInstituteCode}-calbutton">
			           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
			           	</a>
					</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.targetAccrual" /></div>
					<div class="value">
						<input type="text" name="study.studySites[${index}].targetAccrualNumber" maxlength="6" class="validate-NUMERIC" size="6" />
					</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="site.activationDate" /></div>
					<div class="value">
						<input type="text" name="study.studySites[${index}].startDate" id="startDate-${site.healthcareSite.nciInstituteCode}" class="date validate-DATE" />
			            <a href="#" id="startDate-${site.healthcareSite.nciInstituteCode}-calbutton">
			           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
			           	</a>
					</div>
				</div>
				<div class="row">
					<c:if test="${command.study.multiInstitutionIndicator && multisiteEnv}">
						<div class="label"><fmt:message key="site.hostedMode" /></div>
						<div class="value">
	            			<input type="checkbox" name="study.studySites[${index}].hostedMode" />
        					<input type="hidden" name="${command.study.studySites[index].healthcareSite.nciInstituteCode}-wasHosted" value="${command.study.studySites[index].hostedMode}"/>
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
</chrome:division>
