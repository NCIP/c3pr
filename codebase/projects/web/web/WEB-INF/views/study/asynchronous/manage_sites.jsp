<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
function takeEndpointAction(action){
	parent.takeAction(action,'${site.healthcareSite.nciInstituteCode }','true');
	Dialog.closeInfo();
}
</script>
<c:choose>
<c:when test="${fn:length(site.possibleEndpoints)>0}">
	<div align="center" style="font-size: 1.7em; color: red;">Errors</div>
	<tags:displayErrors id="endpoint-errors" errors="${site.recentErrors}"></tags:displayErrors>
</c:when>
<c:otherwise>
	<div align="center" style="font-size: 1.7em; color: red;">Sync Study</div>
	The study is out of sync with this site. The following action(s) are available.
</c:otherwise>
</c:choose>
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
	<c:if test="${apiName=='ACTIVATE_STUDY_SITE'}">
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