<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- SUBJECT SEARCH RESULTS START HERE -->
<div class="eXtremeTable" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table class="tableRegion" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
			<c:if test="${participants!=null}">
				<tr>
					<td class="tableHeader">Last Name, First Name</td>
					<td class="tableHeader">Primary Identifier</td>
					<td class="tableHeader">Gender</td>
					<td class="tableHeader">Race</td>
					<td class="tableHeader">Birth Date</td>
    			</tr>
			</c:if>
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

            <tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
				onMouseOut="this.className='<%= currClass %>'"
				onClick="postProcessSubjectSelection('${participant.id}','${participant.lastName} ${participant.firstName}')">					
					<td>${participant.lastName},${participant.firstName}</td>
					<td>${participant.primaryIdentifier}</a></td>
					<td>${participant.administrativeGenderCode}</td>
					<td>${participant.raceCode}</td>
					<td>${participant.birthDateStr}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- SUBJECT SEARCH RESULTS END HERE -->
