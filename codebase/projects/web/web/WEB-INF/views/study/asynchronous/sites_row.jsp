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
	<c:when test="${not empty actionError}">
		<font color="red">Error</font><br>
				Click <a href="javascript:showLocalActionError('errorDiv-${site.healthcareSite.nciInstituteCode }');">here</a> to see the error messages
				<div id="errorDiv-${site.healthcareSite.nciInstituteCode }" style="display: none;">
					${actionError.message }
				</div>
	</c:when>
	<c:otherwise>
		None
	</c:otherwise>
</c:choose>
</div>
<div id="actions"/>
	<c:set var="action" value="false"/>
    <c:if test="${fn:length(site.possibleTransitions)>0 && (site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode || localNCICode==site.healthcareSite.nciInstituteCode)}">
      	<select id="siteAction-${site.healthcareSite.nciInstituteCode }">
   		<c:forEach items="${site.possibleTransitions}" var="possibleAction">
	   		<c:choose>
			<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE'}">
				<c:if test="${site.hostedMode || (localNCICode==site.healthcareSite.nciInstituteCode && (site.siteStudyStatus=='APPROVED_FOR_ACTIVTION' || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode))}">
				<option value="${possibleAction}">${possibleAction.displayName }</option>
				<c:set var="action" value="true"/>
				</c:if>
			</c:when>
			<c:when test="${possibleAction=='APPROVED_FOR_ACTIVTION'}">
				<c:if test="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}">
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
       	<input type="button" value="Go" onclick="takeAction('${site.healthcareSite.nciInstituteCode }');"/>
	</c:if>
	<div id="sendingMessage-${site.healthcareSite.nciInstituteCode }" class="working" style="display: none">
		Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
	</div>
	</div>
</div>
<script>
$('siteIRB-${site.healthcareSite.nciInstituteCode }').innerHTML=$('ajax-IRB').innerHTML;
$('Messages-${site.healthcareSite.nciInstituteCode }').innerHTML=$('ajax-message').innerHTML;
<c:choose>
<c:when test="${action}">
$('actions-${site.healthcareSite.nciInstituteCode }').innerHTML=$('actions').innerHTML;
</c:when>
<c:otherwise>
$('actions-${site.healthcareSite.nciInstituteCode }').innerHTML='';
</c:otherwise>
</c:choose>

$('siteStatus-${site.healthcareSite.nciInstituteCode }').innerHTML='${site.siteStudyStatus.code}';
new Effect.Highlight($('siteIRB-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
new Effect.Highlight($('Messages-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
new Effect.Highlight($('siteStatus-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
new Effect.Highlight($('actions-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
Element.hide('sendingMessage-${site.healthcareSite.nciInstituteCode }');
</script>