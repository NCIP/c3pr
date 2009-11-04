<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<c:choose>
	<c:when test="${notRegistrable}">
		<td class="no-selection">
			<input class="epochCheck" type="radio" value="${epoch.id}" disabled="disabled" id="${transferToStatus}"/>
		</td>
		<td align="left" class="no-selection">${epoch.name}</td>
		<td align="left" class="no-selection">${epoch.descriptionText}</td>
		<td align="left" class="no-selection">${epoch.treatmentIndicator?'Treatment':'Non Treatment'}</td>
		<td align="left" class="no-selection">${additionalInformation}</td>
	</c:when>
	<c:otherwise>
		<td>
			<input class="epochCheck" type="radio" id="${transferToStatus}" value="${epoch.id}" onclick="manageEpochSelection(this);" <c:if test="${isCurrentScheduledEpoch}"> checked </c:if>/>
		</td>
		<td align="left">${epoch.name}</td>
		<td align="left">${epoch.descriptionText}</td>
		<td align="left">${epoch.treatmentIndicator?'Treatment':'Non Treatment'}</td>
		<td align="left">${additionalInformation} 
			
		</td>
	</c:otherwise>
</c:choose>
