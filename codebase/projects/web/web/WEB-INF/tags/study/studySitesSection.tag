<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>

<%@attribute name="commandObj" required="true" type="edu.duke.cabig.c3pr.web.study.StudyWrapper"%>

<c:forEach items="${commandObj.study.studySites}" varStatus="status" var="site">
	<csmauthz:accesscontrol domainObject="${site}" hasPrivileges="STUDYSITE_READ"  
				            authorizationCheckName="studySiteAuthorizationCheck">
		<div id="siteSection_${site.healthcareSite.primaryIdentifier }">
			<studyTags:studySiteSection index="${status.index}" site="${site}" isMultisite="${multisiteEnv}" localNCICode="${localNCICode}" site_index="${status.index}" />
			<br>
		</div>
	</csmauthz:accesscontrol>
</c:forEach>

<script>
	var irbApprovalDateRenewalArray = new Array();
    <c:forEach items="${commandObj.study.studySites}" varStatus="status" var="site">
    	irbApprovalDateRenewalArray.push(editor_irbApprovalDateRenewal_${site.healthcareSite.primaryIdentifier});	
    </c:forEach>
    <%
		java.util.Calendar yearOld=java.util.Calendar.getInstance();
		yearOld.add(java.util.Calendar.YEAR, -1);
		session.setAttribute("yearOld",yearOld);
	%>
</script>