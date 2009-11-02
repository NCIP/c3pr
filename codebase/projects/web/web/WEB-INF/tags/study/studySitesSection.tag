<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@attribute name="commandObj" required="true" type="edu.duke.cabig.c3pr.web.study.StudyWrapper"%>
<c:forEach items="${commandObj.study.studySites}" varStatus="status" var="site">
	<div id="siteSection_${site.healthcareSite.primaryIdentifier }">
		<studyTags:studySiteSection index="${status.index}" site="${site}" isMultisite="${multisiteEnv}" localNCICode="${localNCICode}" />
		<br>
	</div>
</c:forEach>