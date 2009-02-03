<%@taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="tab" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab" required="true" %>
<%@attribute name="flow" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" required="true" %>
<style>
#registrationSummary div.row div.label {
	float:left;
	font-weight:bold;
	margin-left:0em;
	text-align:right;
	width:8em;
}
#registrationSummary div.row div.value {
	font-weight:normal;
	margin-left:8.3em;
}
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="3" class="body">
    <tr>
        <c:choose>
            <c:when test="${tab.showSummary=='false' }">
                <td valign="top">
                    <jsp:doBody/>
                </td>
            </c:when>
            <c:otherwise>
                <tr>
                    <td>
                        <div id="registrationSummary">
                            <registrationTags:registrationSummary/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <jsp:doBody/>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tr>
</table>