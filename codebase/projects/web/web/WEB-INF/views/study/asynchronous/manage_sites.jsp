<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<script>
function takeEndpointAction(action){
	parent.takeAction(action,'${localSite.healthcareSite.nciInstituteCode }','true');
	Dialog.closeInfo();
}
</script>
<chrome:box title="Multisite messages for ${localSite.healthcareSite.name}">
<c:forEach items="${site.endpoints}" var="endpoint" varStatus="status">
	<chrome:division title="${endpoint.apiName.code }" style="text-align: left;">
	<div align="left" style="font-size: 1.4em; ${endpoint.status=='MESSAGE_SEND_FAILED'?'color: red;':'color: green;' }">${endpoint.status.code }</div>
	<c:if test="${endpoint.status=='MESSAGE_SEND_FAILED'}"><tags:displayErrors id="endpoint-errors" errors="${endpoint.errors}"></tags:displayErrors></c:if>
	</chrome:division>
</c:forEach>
<c:if test="${fn:length(site.endpoints)==0 && fn:length(site.possibleEndpoints)==0}">There are no messages.</c:if>
<div class="content buttons autoclear" align="right">
<c:forEach items="${site.possibleEndpoints}" var="apiName">
	<c:if test="${apiName=='CREATE_STUDY'}">
		<input type="button" value="Create Study" onclick="takeEndpointAction('CREATE_STUDY');"/>
	</c:if>
	<c:if test="${apiName=='OPEN_STUDY'}">
		<input type="button" value="Open Study" onclick="takeEndpointAction('OPEN_STUDY');"/>
	</c:if>
	<c:if test="${apiName=='APPROVE_STUDY_SITE_FOR_ACTIVATION'}">
		<input type="button" value="Approve Site" onclick="takeEndpointAction('APPROVE_STUDY_SITE_FOR_ACTIVATION');"/>
	</c:if>
	<c:if test="${apiName=='ACTIVATE_STUDY_SITE' && (localSite.siteStudyStatus=='APPROVED_FOR_ACTIVTION' || localSite.siteStudyStatus=='ACTIVE')}">
		<input type="button" value="Activate Site" onclick="takeEndpointAction('ACTIVATE_STUDY_SITE');"/>
	</c:if>
	<c:if test="${apiName=='AMEND_STUDY'}">
		<input type="button" value="Amend Study" onclick="takeEndpointAction('AMEND_STUDY');"/>
	</c:if>
	<c:if test="${apiName=='CLOSE_STUDY'}">
		<input type="button" value="Close Study" onclick="takeEndpointAction('CLOSE_STUDY');"/>
	</c:if>
	<c:if test="${apiName=='CLOSE_STUDY_SITE'}">
		<input type="button" value="Close Site" onclick="takeEndpointAction('CLOSE_STUDY_SITE');"/>
	</c:if>
</c:forEach>
</div>
</chrome:box>