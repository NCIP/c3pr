<%@tag%>
<%@attribute name="url" required="true"%>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!-- SUBJECT SEARCH RESULTS START HERE -->
<div class="eXtremeTable" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<c:if test="${participants!=null}">
			<table class="tableRegion" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
					<tr>
						<td class="tableHeader"><fmt:message key="c3pr.common.lastName"/>, <fmt:message key="c3pr.common.middleName"/>, <fmt:message key="c3pr.common.firstName"/></td>
						<td class="tableHeader"><fmt:message key="c3pr.common.primaryIdentifier"/></td>
						<td class="tableHeader"><fmt:message key="participant.gender"/></td>
						<td class="tableHeader"><fmt:message key="participant.race"/></td>
						<td class="tableHeader"><fmt:message key="participant.birthDate"/></td>
	    			</tr>
				</thead>
				<tbody class="tableBody">
				<c:if test="${participants!=null && fn:length(participants)==0}">
					<tr>
						Sorry, no matches were found
					</tr>
				</c:if>
				<%int i=0; %>
				<c:forEach items="${participants}" var="participant">
					  <% String currClass=i%2==0? "odd":"even"; %>
	
	            <tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'" style="cursor:pointer"
					onMouseOut="this.className='<%= currClass %>'"
						onClick="
							<c:choose>
								<c:when test="${!empty studySiteId}">
									document.location='createRegistration?studySite=${studySiteId}&participant=${participant.id}&resumeFlow=true&_page=2&_target3=3'
								</c:when>
								<c:when test="${!empty inRegistration}">
									document.location='searchStudy?inRegistration=true&subjectId=${participant.id}'
								</c:when>
								<c:otherwise>
									document.location='viewParticipant?<tags:identifierParameterString identifier="${participant.systemAssignedIdentifiers[0] }"/>'
								</c:otherwise>
							</c:choose>
						">
						<c:choose>
						<c:when test="${not empty participant.middleName }">
						<td>${participant.lastName},${participant.middleName}, ${participant.firstName}</td>
						</c:when>
						<c:otherwise>
						<td>${participant.lastName},${participant.firstName}</td>
						</c:otherwise>
						</c:choose>
						<td>${participant.primaryIdentifier}</a></td>
						<td>${participant.administrativeGenderCode}</td>
						<td>
							<c:forEach items="${participant.raceCodes}" var="raceCode">
					            <div class="row">
					                <div class="left">
					                        ${raceCode.displayName}
					                </div>
					            </div>
					        </c:forEach>
					     </td>
						<td>${participant.birthDateStr}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:if>
		</td>
	</tr>
</table>
</div>
<!-- SUBJECT SEARCH RESULTS END HERE -->
