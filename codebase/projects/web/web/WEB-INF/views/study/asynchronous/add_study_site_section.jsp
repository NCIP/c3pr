<%@ include file="../taglibs.jsp"%>
<div id="siteSection_${site.healthcareSite.primaryIdentifier}">
<studyTags:studySiteSection index="${siteIndex}" site="${site}" isMultisite="${multisiteEnv}" localNCICode="${localNCICode}" maximized="true" isNewStudySite="true"/>
<script>
new Effect.Highlight($('siteSection_${site.healthcareSite.primaryIdentifier }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
</script>
</div>