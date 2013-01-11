<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="../taglibs.jsp"%>
<div id="siteSection_${site.healthcareSite.primaryIdentifier}">
<studyTags:studySiteSection index="${siteIndex}" site="${site}" isMultisite="${multisiteEnv}" localNCICode="${localNCICode}" maximized="true" isNewStudySite="true"/>
<script>
new Effect.Highlight($('siteSection_${site.healthcareSite.primaryIdentifier }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
</script>
</div>
