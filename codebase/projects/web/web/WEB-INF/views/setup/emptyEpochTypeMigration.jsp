<%@ include file="taglibs.jsp"%>
<html>
<head>
<title>Release Migration</title>
<link rel="stylesheet" type="text/css" href="/c3pr/public/css/tables1" />
</head>
<body>
<tags:instructions code="release_migration" />
<form:form method="post">
<c:set var="epochIndex" value="0"/>
<c:forEach items="${studies}" var="study">
	<chrome:box title="${study.shortTitleText} (${study.primaryIdentifier})">
		<c:forEach var="studyVersion" items="${study.studyVersions}">
			<chrome:division title="${studyVersion.name}">
				<table class="tablecontent">
					<th>Epoch Name</th>
					<th>Select Type</th>
					<c:forEach var="epoch" items="${studyVersion.epochs}">
						<c:if test="${empty epoch.type}">
						<tr>
						<td>${epoch.name }<input type="hidden" name="epochs[${epochIndex }].id" value="${epoch.id }"/></td>
						<td>
							<select name="epochs[${epochIndex }].type">
								<option value="" selected>--Please Select--</option>
								<option value="TREATMENT">Treatment</option>
								<option value="SCREENING">Screening</option>
								<option value="FOLLOWUP">Follow-up</option>
								<option value="RESERVING">Reserving</option>
							</select>
						</td>
						</tr>
						<c:set var="epochIndex" value="${epochIndex+1}"/>
						</c:if>
					</c:forEach>
				</table>			
			</chrome:division>
		</c:forEach>
	</chrome:box>
</c:forEach>
<table width="100%">	
	<tr>
		<td align="right">
			<tags:button color="green" type="submit" value="Save & Finish"/>
		</td>
	<tr>	
</table>
</form:form>
</body>
</html>
