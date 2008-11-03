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
          			<tags:displayErrors id="endpoint-errors-${status.index}" errors="${site.endpoints[fn:length(site.endpoints)-1].errors}"></tags:displayErrors>
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
<script>
$('Messages-${site.healthcareSite.nciInstituteCode }').innerHTML=$('ajax-message').innerHTML;
new Effect.Highlight($('Messages-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
$('siteStatus-${site.healthcareSite.nciInstituteCode }').innerHTML='${site.siteStudyStatus.code}';
new Effect.Highlight($('siteStatus-${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
</script>