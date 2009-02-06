<%@ include file="taglibs.jsp"%>
<chrome:box title="Change Epoch"> 
<table border="0" cellspacing="0" cellpadding="5px" class="tablecontent"  width="100%">
	<tr>
		<th></th>
		<th scope="col"><fmt:message key="registration.epochName"/></th>
		<th scope="col" ><b><fmt:message key="c3pr.common.description"/></th>
		<th scope="col" ><b><fmt:message key="c3pr.common.type"/></th>
		<th scope="col" ><b><fmt:message key="c3pr.common.additionalInfo"/></th>
		
	</tr>
	<c:forEach items="${command.studySubject.studySite.study.epochs}" var="epoch" varStatus="epochStatus">
		<tr>
			<td>
				<input type="radio" value="" style="margin:0 5px;"/> 
			</td>
			<td align="left">${epoch.name}</td>
			<td align="left">${epoch.descriptionText}</td>
			<td align="left">${epoch.treatmentIndicator?'Treatment':'Non Treatment'}</td>
			<td align="left">${needAdditionalInfo?'Required':'Not required'}</td>
		</tr>
	</c:forEach>
</table>
</chrome:box>