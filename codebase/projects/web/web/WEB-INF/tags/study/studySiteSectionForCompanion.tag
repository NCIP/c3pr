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
<c:set var="keepOpen" value="${(!empty maximized && maximized) || fn:contains(openSections, site.healthcareSite.primaryIdentifier)}"/>
<c:set var="isActionSuccess" value="${empty errorMessage?true:false}" />
<c:set var="isLocalSiteCoordinating" value="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.ctepCode}"/>
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
<chrome:division  title="(${site.healthcareSite.primaryIdentifier}) ${site.healthcareSite.name} : ${site.siteStudyStatus.code}" id="divison-${site.healthcareSite.primaryIdentifier}" cssClass="divisonClass">
	<div class="row">
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
					${empty site.targetAccrualNumber?'NA':site.targetAccrualNumber}
				</div>
			</div>
		</div>
		<div class="rightpanel">
			<div class="row">
				<div class="label"><fmt:message key="site.IRBApprovalDate" />:</div>
				<div class="value">
						${empty site.currentStudySiteStudyVersion.irbApprovalDateStr?'NA':site.currentStudySiteStudyVersion.irbApprovalDateStr }
				</div>
			</div>
			<div class="row">
				<c:if test="${isMultisite}">
					<div class="label"><fmt:message key="site.hostedMode" />:</div>
					<div class="value">
						${empty site.hostedMode?'No':(site.hostedMode?'Yes':'No') }
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
<div id="site_status_history-${site.healthcareSite.primaryIdentifier}" style="display:none">
<chrome:division title="Site Status History" >
	<table id="siteStatusHistoryTable-${site.healthcareSite.primaryIdentifier}" class="tablecontent" border="0" cellspacing="0" cellpadding="0" width="95%" align="center">
        <tr>
            <th align="center"><b><tags:requiredIndicator /><fmt:message key="study.site.status"/></b>
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
    	<tags:button type="button" color="red" value="Close" id="close" onclick="closePopup();"  />
    </div>
</chrome:division>
 </div>
</chrome:division>