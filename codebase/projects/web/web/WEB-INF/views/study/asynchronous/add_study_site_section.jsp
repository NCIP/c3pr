<%@ include file="../taglibs.jsp"%>
<div id="siteSection_${site.healthcareSite.nciInstituteCode }">
<studyTags:studySiteSection index="${siteIndex}" site="${site}" isMultisite="${multisiteEnv}" localNCICode="${localNCICode}" maximized="true" isNewStudySite="true"/>
<script>
new Effect.Highlight($('siteSection_${site.healthcareSite.nciInstituteCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
</script>
<div id="siteSection_${site.healthcareSite.nciInstituteCode }">