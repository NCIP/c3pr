<%--
Copyright Duke Comprehensive Cancer Center and SemanticBits
 
  Distributed under the OSI-approved BSD 3-Clause License.
  See  https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="../taglibs.jsp"%>
${irbApprovalError}
<c:choose>
	<c:when test="${not empty irbApprovalError}">
		<script>
			$('irbError-${site.healthcareSite.primaryIdentifier}').innerHTML="${irbApprovalError}";
		</script>
	</c:when>
	<c:otherwise>
		<div id="tempSiteSection-${site.healthcareSite.primaryIdentifier}">
		<studyTags:studySiteSection index="${index}" site="${site}" isMultisite="${isMultisite}" localNCICode="${localNCICode}" maximized="true" isNewStudySite="false"/>
		</div>
		<script>
		Element.update('siteSection_${site.healthcareSite.primaryIdentifier }',$('tempSiteSection-${site.healthcareSite.primaryIdentifier}').innerHTML);
		new Effect.Highlight($('siteSection_${site.healthcareSite.primaryIdentifier }'), { startcolor: '#ffff99',
		endcolor: '#ffffff' });
		closePopup();
		</script>
	</c:otherwise>
</c:choose>

