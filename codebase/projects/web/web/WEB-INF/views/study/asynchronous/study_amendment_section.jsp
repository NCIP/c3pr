<%@ include file="../taglibs.jsp"%>
<div id="study-amendment-${amendmentIndex}">
<tr>
	<td>${amendment.name}</td>
	<td>${amendment.versionDateStr}</td>
	<td>${amendment.mandatoryIndicator ? 'Yes' : 'No'}</td>
	<td>${amendment.versionStatus.code}</td>
	<td>
		<tags:button color="blue" icon="search" onclick="viewStudy();" size="small" value="View"></tags:button>
	</td>
</tr>
</div>