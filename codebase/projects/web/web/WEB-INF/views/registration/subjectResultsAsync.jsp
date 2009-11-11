<%@ include file="taglibs.jsp"%>

<!-- SUBJECT SEARCH RESULTS START HERE -->
<div class="eXtremeTable" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table class="tableRegion" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
			<c:if test="${participants!=null}">
				<tr>
					<td class="tableHeader"><fmt:message key="c3pr.common.lastName"/>, <fmt:message key="c3pr.common.firstName"/></td>
					<td class="tableHeader"><fmt:message key="participant.medicalRecordNumber"/></td>
					<td class="tableHeader"><fmt:message key="c3pr.common.assigningAuthority"/></td>
					<td class="tableHeader"><fmt:message key="participant.gender"/></td>
					<td class="tableHeader"><fmt:message key="participant.race"/></td>
					<td class="tableHeader"><fmt:message key="participant.birthDate"/></td>
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

            <tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'" style="cursor:pointer"
				onMouseOut="this.className='<%= currClass %>'" 
				onClick="postProcessSubjectSelection('${participant.id}','${participant.lastName} ${participant.firstName}','${participant.identifiers[0].type.code}'+' - '+ '${participant.identifiers[0].value}')">					
					<td>${participant.lastName},${participant.firstName}</td>
					<td>${participant.MRN.value}</a></td>
					<td><c:if test="${! empty participant.MRN}">${participant.MRN.healthcareSite.name}</c:if></a></td>
					<td>${participant.administrativeGenderCode}</td>
					<td>
						<c:forEach items="${participant.raceCodes}" var="raceCode">
				            <div class="row">
				                <div class="left">${raceCode.displayName}</div>
				            </div>
			        	</c:forEach>
		        	</td>
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
