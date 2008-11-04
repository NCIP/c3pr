<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="ajax-message">
<c:choose>
	<c:when test="${fn:length(site.endpoints)>0}">
		<c:choose>
			<c:when test="${site.endpoints[fn:length(site.endpoints)-1].status=='MESSAGE_SEND_FAILED'}">
				<font color="red">${site.endpoints[fn:length(site.endpoints)-1].status.code}</font><br>
          			Click <a href="javascript:showEndpointError('${site.healthcareSite.nciInstituteCode }');">here</a> to see the error message
			</c:when>
			<c:otherwise>
				<font color="green">${site.endpoints[fn:length(site.endpoints)-1].status.code}</font><br>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		None
	</c:otherwise>
</c:choose>
</div>
<div id="actions"/>
<c:forEach items="${site.possibleStatusTransitions}" var="siteStatus">
	<c:choose>
				<c:when test="${siteStatus=='ACTIVE' && (!site.hostedMode || localNCICode==site.healthcareSite.nciInstituteCode)}">
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
	<c:if test="${!empty isClose}">
				<input type="button" value="Close" onclick="takeAction('CLOSE_STUDY_SITE','${site.healthcareSite.nciInstituteCode }','false');"/>
			</c:if>
</c:forEach>
</div>
<script>
$('Messages-${site.healthcareSite.nciInstituteCode }').innerHTML=$('ajax-message').innerHTML;
$('actions-${site.healthcareSite.nciInstituteCode }').innerHTML=$('actions').innerHTML;
new Effect.Highlight($('Messages-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
$('siteStatus-${site.healthcareSite.nciInstituteCode }').innerHTML='${site.siteStudyStatus.code}';
new Effect.Highlight($('siteStatus-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
</script>