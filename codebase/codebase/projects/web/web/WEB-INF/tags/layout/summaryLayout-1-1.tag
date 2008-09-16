<%@taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@attribute name="tab" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab" required="true"%>
<%@attribute name="flow" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" required="true" %>

<table width="100%" border="0" cellspacing="0" cellpadding="3" class="body">
	<tr>
	<c:choose>
		<c:when test="${tab.showSummary=='false' }">
			<td valign="top">
				<jsp:doBody/>
			</td>
		</c:when>
		<c:otherwise>
	        <td width="30%" valign="top">
		        <tags:panelBox title="Summary" boxId="RegSummary">
			        <registrationTags:registrationSummary />
		        </tags:panelBox>
	        </td>
	        <td width="70%" valign="top">
				<jsp:doBody/>
	        </td>
		</c:otherwise>
	</c:choose>
	</tr>
</table>