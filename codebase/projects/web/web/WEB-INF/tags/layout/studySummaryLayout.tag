<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="tab" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab" required="true" %>
<%@attribute name="flow" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" required="true" %>
<style>
#summary div.row div.label {
	float:left;
	font-weight:bold;
	margin-left:0em;
	text-align:right;
	width:12em;
}
#summary div.row div.value {
	font-weight:normal;
	margin-left:12.2em;
}
</style>
<c:choose>
    <c:when test="${tab.showSummary=='false' }">
            <jsp:doBody/>
    </c:when>
    <c:otherwise>
                <div id="summary">
                    <studyTags:studySummary/>
                </div>
                <jsp:doBody/>

    </c:otherwise>
</c:choose>