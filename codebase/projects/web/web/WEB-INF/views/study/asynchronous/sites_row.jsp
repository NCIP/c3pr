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
			<font color="red">Error</font>
			<br>
			Click <a href="javascript:showLocalActionError('errorDiv-${site.healthcareSite.nciInstituteCode }');">here</a> to see the error messages
					<div id="errorDiv-${site.healthcareSite.nciInstituteCode }" style="display: none;">
						${actionError.message }
					</div>
		</c:when>
	</c:choose>
</div>
<div id="actions"/>
	<c:set var="noAction" value="true"/>
	<c:if test="${fn:length(site.possibleTransitions)>0 && (site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode || localNCICode==site.healthcareSite.nciInstituteCode)}">
		<c:forEach items="${site.possibleTransitions}" var="possibleAction">
			<c:choose>
				<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE'}">
					<c:if test="${site.hostedMode || (localNCICode==site.healthcareSite.nciInstituteCode && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode)}">
					<%--<c:if test="${site.hostedMode || (localNCICode==site.healthcareSite.nciInstituteCode && (site.siteStudyStatus=='APPROVED_FOR_ACTIVTION' || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode))}">--%>
						<tags:button type="button" color="blue" value="${possibleAction.displayName }" id="${possibleAction}" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', '${possibleAction}');" size="small"/>
						<c:set var="noAction" value="false"/>
					</c:if>
				</c:when>
				<c:when test="${possibleAction=='CLOSE_STUDY_SITE_TO_ACCRUAL' || possibleAction=='CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT'}">
					<c:set var="noAction" value="false"/>
					<c:set var="close" value="true"/>
				</c:when>
				<c:when test="${possibleAction=='TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL' || possibleAction=='TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT'}">
					<c:set var="noAction" value="false"/>
					<c:set var="close" value="temp"/>
				</c:when>
				<%--<c:when test="${possibleAction=='APPROVE_STUDY_SITE_FOR_ACTIVATION'}">
					<c:if test="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.nciInstituteCode}">
						<option value="${possibleAction}">${possibleAction.displayName }</option>
						<c:set var="noAction" value="false"/>
					</c:if>
				</c:when>--%>
				<c:otherwise>
					<tags:button type="button" color="blue" value="${possibleAction.displayName }" id="${possibleAction}" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', '${possibleAction}');" size="small"/>
					<c:set var="noAction" value="false"/>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${!empty close}">
			<tags:button type="button" color="blue" value="Close Study Site" id="closeStudy"
				onclick="Effect.SlideDown('close-choices')" size="small"/>
			<div id="close-choices" class="autocomplete" style="display: none">
				<ul>
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', 'CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT');">Closed To Accrual And Treatment</li>
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', 'CLOSE_STUDY_SITE_TO_ACCRUAL');">Closed To Accrual</li>
					<c:if test="${close == 'temp'}">
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', 'TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT');">Temporarily Closed To Accrual And Treatment</li>
					<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="takeAction('${site.healthcareSite.nciInstituteCode}', 'TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL');">Temporarily Closed To Accrual</li>
					</c:if>
				</ul>
				<div align="right"><tags:button type="button" color="red" value="Cancel" icon="x"
					onclick="Effect.SlideUp('close-choices')" size="small"/></div>
			</div>
		</c:if>
	</c:if>
	<div id="sendingMessage-${site.healthcareSite.nciInstituteCode }" class="working" style="display: none">
		Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
	</div>
<script>
$('siteIRB-${site.healthcareSite.nciInstituteCode }').innerHTML=$('ajax-IRB').innerHTML;
$('Messages-${site.healthcareSite.nciInstituteCode }').innerHTML=$('ajax-message').innerHTML;
<c:choose>
<c:when test="${!noAction}">
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
new Element.update('dummy-div','');
</script>