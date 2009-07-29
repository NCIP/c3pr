<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<jwr:script src="/js/tabbedflow.js" />
<style>
#main {
	top: 33px;
}
</style>
<script>
function viewStudy(){

}
</script>
</head>
<body>
<chrome:box title="Amendments">
<c:choose>
	<c:when test="${fn:length(command.study.studyAmendments) > 0}">
	<table id="studyAmendmets" class="tablecontent">
		<tr>
			<th width="25%">
				<fmt:message key="study.version.name" />
				<tags:hoverHint keyProp="study.version.name" />
			</th>
			<th width="17%">
				<fmt:message key="study.version.date" />
				<tags:hoverHint keyProp="study.version.date" />
			</th>
			<th width="13%">
				<fmt:message key="study.version.required" />
				<tags:hoverHint keyProp="study.version.required" />
			</th>
			<th width="17%">
				<fmt:message key="study.version.status" />
				<tags:hoverHint keyProp="study.version.status" />
			</th>
			<th width="25%">
			</th>
		</tr>
		<c:forEach items="${command.study.studyAmendments}" var="amendment" varStatus="status">
			<tr>
				<td>${amendment.name}</td>
				<td>${amendment.versionDateStr}</td>
				<td>${amendment.mandatoryIndicator ? 'Yes' : 'No'}</td>
				<td>${amendment.versionStatus.code}</td>
				<td>
					<tags:button color="blue" icon="search" onclick="viewStudy();" size="small" value="View"></tags:button>
					<c:if test="${amendment.versionStatus == 'IN'}">
						<tags:button color="blue" icon="amend" onclick="resumeAmendment();" size="small" value="Resume amendment"></tags:button>
					</c:if>
				</td>
			</tr>
		</c:forEach>

	</table>
	</c:when>
	<c:otherwise>
		<fmt:message key="study.version.noAmendment"></fmt:message>
	</c:otherwise>
</c:choose>
</chrome:box>
</body>
</html>
