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
					<td class="tableHeader">Name</td>
					<td class="tableHeader">Description</td>
					<td class="tableHeader">Enrolling</td>
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
				onClick="postProcessEpochSelection('${epoch.id}','${epoch.name}', '${epoch.enrollmentIndicator?'Enrolling':'Non-Enrolling'}')">					
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
