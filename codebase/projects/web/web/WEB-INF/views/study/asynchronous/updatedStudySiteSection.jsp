<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<studyTags:studySiteSection index="${siteIndex}" site="${site}" isMultisite="${multisiteEnv}" localNCICode="${localNCICode}" action="${apiName }" errorMessage="${errorMessage }" maximized="true"/>
<script>
new Effect.Highlight($('siteSection_${site.healthcareSite.ctepCode }'), { startcolor: '#ffff99',
endcolor: '#ffffff' });
</script>