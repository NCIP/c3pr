<%@ include file="../taglibs.jsp"%>
${irbApprovalError}
<c:choose>
	<c:when test="${not empty irbApprovalError}">
		<script>
		alert("I came here");
			$('irbError-${site.healthcareSite.primaryIdentifier}').innerHTML="${irbApprovalError}";
		</script>
	</c:when>
	<c:otherwise>
		<div id="tempSiteSection-${site.healthcareSite.primaryIdentifier}">
		<studyTags:studySiteSection index="${index}" site="${site}" isMultisite="${isMultisite}" localNCICode="${localNCICode}" maximized="true" isNewStudySite="false"/>
		</div>
		<script>
		alert("I came here too");
		Element.update('siteSection_${site.healthcareSite.ctepCode }',$('tempSiteSection-${site.healthcareSite.primaryIdentifier}').innerHTML);
		new Effect.Highlight($('siteSection_${site.healthcareSite.ctepCode }'), { startcolor: '#ffff99',
		endcolor: '#ffffff' });
		closePopup();
		</script>
	</c:otherwise>
</c:choose>

