<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="companionSiteEndpoint" value="${site}"/>
<c:if test="${localNCICode==site.healthcareSite.ctepCode}">
	<c:set var="companionSiteEndpoint" value="${site.study.studyCoordinatingCenters[0]}"/>
</c:if>
<div id="ajax-IRB">
	<tags:formatDate value="${site.irbApprovalDate}"></tags:formatDate>
</div>
<div id="ajax-message">
<c:choose>
	<c:when test="${!site.hostedMode && !site.isCoordinatingCenter && fn:length(companionSiteEndpoint.endpoints)>0}">
		<c:choose>
			<c:when test="${companionSiteEndpoint.lastAttemptedEndpoint.status=='MESSAGE_SEND_FAILED'}">
				<font color="red">${companionSiteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
				Click <a href="javascript:showEndpointError('${companionSiteEndpoint.healthcareSite.ctepCode}','${site.healthcareSite.ctepCode}');">here</a> to see the error messages
			</c:when>
			<c:otherwise>
				<font color="green">${companionSiteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
				Click <a href="javascript:showEndpointError('${companionSiteEndpoint.healthcareSite.ctepCode }','${site.healthcareSite.ctepCode}');">here</a> to see the messages
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		None
	</c:otherwise>
</c:choose>
</div>
<div id="companionActions"/>
	<c:set var="action" value="false"/>
    <c:if test="${fn:length(site.possibleTransitions)>0 && (site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.ctepCode || localNCICode==site.healthcareSite.ctepCode)}">
      	<select id="companionSiteAction-${site.healthcareSite.ctepCode}">
   		<c:forEach items="${site.possibleTransitions}" var="possibleAction">
	   		<c:choose>
			<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE'}">
				<c:if test="${site.hostedMode || localNCICode==site.healthcareSite.ctepCode}">
				<option value="${possibleAction}">${possibleAction.displayName }</option>
				<c:set var="action" value="true"/>
				</c:if>
			</c:when>
			<c:when test="${possibleAction=='APPROVED_FOR_ACTIVTION'}">
				<c:if test="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.ctepCode}">
				<option value="${possibleAction}">${possibleAction.displayName }</option>
				<c:set var="action" value="true"/>
				</c:if>
			</c:when>
			<c:otherwise>
				<option value="${possibleAction}">${possibleAction.displayName }</option>
				<c:set var="action" value="true"/>
			</c:otherwise>
			</c:choose>
       	</c:forEach>
       	</select>
       	<input type="button" value="Go" onclick="changeCompanionStudySiteStatus('${site.healthcareSite.ctepCode}');"/>
	</c:if>
	<div id="companionSendingMessage-${site.healthcareSite.ctepCode }" class="working" style="display: none">
		Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
	</div>
	</div>
</div>
<script>
$('companionSiteIRB-${site.healthcareSite.ctepCode }').innerHTML=$('ajax-IRB').innerHTML;
$('companionMessages-${site.healthcareSite.ctepCode }').innerHTML=$('ajax-message').innerHTML;
<c:choose>
<c:when test="${action}">
$('companionActions-${site.healthcareSite.ctepCode }').innerHTML=$('companionActions').innerHTML;
</c:when>
<c:otherwise>
$('companionActions-${site.healthcareSite.ctepCode }').innerHTML='';
</c:otherwise>
</c:choose>

$('companionSiteStatus-${site.healthcareSite.ctepCode }').innerHTML='${site.siteStudyStatus.code}';
new Effect.Highlight($('companionSiteIRB-${site.healthcareSite.ctepCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
new Effect.Highlight($('companionMessages-${site.healthcareSite.ctepCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
new Effect.Highlight($('companionSiteStatus-${site.healthcareSite.ctepCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
new Effect.Highlight($('companionActions-${site.healthcareSite.ctepCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
Element.hide('companionSendingMessage-${site.healthcareSite.ctepCode }');
</script>