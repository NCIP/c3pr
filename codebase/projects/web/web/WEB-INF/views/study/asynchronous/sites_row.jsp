<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="siteEndpoint" value="${site}"/>
<c:if test="${localNCICode==site.healthcareSite.nciInstituteCode}"><c:set var="siteEndpoint" value="${site.study.studyCoordinatingCenters[0]}"/></c:if>
<div id="ajax-IRB">
	<tags:formatDate value="${site.irbApprovalDate}"></tags:formatDate>
</div>
<div id="ajax-message">
<c:choose>
	<c:when test="${!site.hostedMode && !site.isCoordinatingCenter}">
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
<div id="actions"/>
<c:if test="${empty siteEndpoint.lastAttemptedEndpoint || (siteEndpoint.lastAttemptedEndpoint.status!='MESSAGE_SEND_FAILED' && fn:length(siteEndpoint.possibleEndpoints)==0)}">
<c:forEach items="${site.possibleStatusTransitions}" var="siteStatus">
	<c:choose>
		<c:when test="${siteStatus=='ACTIVE' && (site.hostedMode || localNCICode==site.healthcareSite.nciInstituteCode)}">
			<input type="button" value="Activate" onclick="takeAction('ACTIVATE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','false');"/>
		</c:when>
		<c:when test="${siteStatus=='APPROVED_FOR_ACTIVTION' && localNCICode!=site.healthcareSite.nciInstituteCode}">
			<input type="button" value="Approve" onclick="takeAction('APPROVE_STUDY_SITE_FOR_ACTIVATION','${site.healthcareSite.nciInstituteCode }','false');"/>
		</c:when>
		<c:when test="${siteStatus=='CLOSED_TO_ACCRUAL'}">
			<c:set value="true" var="isClose" />
		</c:when>
		<c:when test="${siteStatus=='TEMPORARILY_CLOSED_TO_ACCRUAL'}">
			<c:set value="true" var="isClose" />
		</c:when>
	</c:choose>
</c:forEach>
<c:if test="${!empty isClose}">
	<input type="button" value="Close" onclick="takeAction('CLOSE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','false');"/>
</c:if>
</c:if>
</div>
<script>
$('siteIRB-${site.healthcareSite.nciInstituteCode }').innerHTML=$('ajax-IRB').innerHTML;
$('Messages-${site.healthcareSite.nciInstituteCode }').innerHTML=$('ajax-message').innerHTML;
$('actions-${site.healthcareSite.nciInstituteCode }').innerHTML=$('actions').innerHTML;
new Effect.Highlight($('siteIRB-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
new Effect.Highlight($('Messages-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
$('siteStatus-${site.healthcareSite.nciInstituteCode }').innerHTML='${site.siteStudyStatus.code}';
new Effect.Highlight($('siteStatus-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
Element.hide('sendingMessage-${site.healthcareSite.nciInstituteCode }');
</script>