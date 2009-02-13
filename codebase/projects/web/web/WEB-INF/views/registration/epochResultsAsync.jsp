<%@ include file="taglibs.jsp"%>

<!-- EPOCH SEARCH RESULTS START HERE -->
<div class="eXtremeTable" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table class="tableRegion" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
			<c:if test="${epochResults!=null}">
				<tr>
					<td class="tableHeader"><fmt:message key="c3pr.common.name"/></td>
					<td class="tableHeader"><fmt:message key="c3pr.common.description"/></td>
					<td class="tableHeader"><fmt:message key="c3pr.common.enrolling"/></td>
    			</tr>
			</c:if>
			</thead>
			<tbody class="tableBody">
			<c:if test="${epochResults!=null && fn:length(epochResults)==0}">
				<tr>
					Sorry, no matches were found
				</tr>
			</c:if>
			<%int i=0; %>
			<c:forEach items="${epochResults}" var="epoch">
			<% String currClass=i%2==0? "odd":"even"; %>

            <tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
				onMouseOut="this.className='<%= currClass %>'" style="cursor:pointer"
				onClick="postProcessEpochSelection('${epoch.id}','${epoch.name}', '${epoch.enrollmentIndicator?'Enrolling':'Non-Enrolling'}',${epoch.reservationIndicator?'true':'false'})">					
					<td>${epoch.name}</td>
					<td>${epoch.descriptionText}</a></td>
					<td>${epoch.enrollmentIndicator?'Yes':'No'}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- EPOCH SEARCH RESULTS END HERE -->
