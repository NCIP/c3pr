<%@taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="tab" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" %>
<%@attribute name="flow" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" required="true" %>
<table width="100%" border="0" cellspacing="0" cellpadding="3" class="body">
	<tr>
	<c:choose>
		<c:when test="${tab.showSummary=='false' }">
			<td valign="top">
				<jsp:doBody/>
			    <c:if test="${tab.subFlow=='false'}">
				    <tabs:tabControls tabNumber="${tab.number}" isLast="${tab.number < rFlow.tabCount - 1}"/>
			    </c:if>
			</td>
		</c:when>
		<c:otherwise>
	        <td width="30%" valign="top"><registrationTags:registrationSummary /></td>
	        <td width="70%" valign="top">
				<jsp:doBody/>
			    <c:if test="${tab.subFlow=='false'}">
				    <tabs:tabControls tabNumber="${tab.number}" isLast="${tab.number < flow.tabCount - 1}"/>
			    </c:if>
	        </td>
		</c:otherwise>
	</c:choose>
	</tr>
</table>