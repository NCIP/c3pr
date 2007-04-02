<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function updateTargetPage(target){
	document.studyDesignForm._target0.value=s;
	document.studyDesignForm.submit();
}

</script>
</head>
<body>
<tabs:body title="${flow.name}: ${tab.longTitle} - Short Title: ${command.trimmedShortTitleText}">
<form:form method="post">
<input type="hidden" name="_finish" value="true"/>
<div><tabs:division id="study-studysites">
<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top" class="additionals2">
			<hr>
			<strong>Study Details </strong>
			<br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0"
				id="table1">

				<tr>
					<td width="20%" class="label">Short Title:</td>
					<td>${command.shortTitleText}</td>
				</tr>
				<tr>
					<td width="20%" class="label">Target Accrual Number:</td>
					<td>${command.targetAccrualNumber}</td>
				</tr>
				<tr>
					<td width="20%" class="label">Status:</td>
					<td>${command.status}</td>
				</tr>
				<tr>
					<td width="20%" class="label">Disease Code:</td>
					<td>${command.diseaseCode}</td>
				</tr>
				<tr>
					<td width="20%" class="label">Disease Type:</td>
					<td>${command.type}</td>
				</tr>
				<tr>
					<td width="20%" class="label">Monitor Code:</td>
					<td>${command.monitorCode}</td>
				</tr>
				<tr>
					<td width="20%" class="label">Phase Code:</td>
					<td>${command.phaseCode}</td>
				</tr>
				<tr>
					<td width="20%" class="label">Multi Institution:</td>
					<td><form:checkbox path="multiInstitutionIndicator" disabled="true"/>
					Blinded: <form:checkbox path="blindedIndicator" disabled="true"/>
					Randomized: <form:checkbox path="randomizedIndicator" disabled="true"/></td>
				</tr>
				<tr>
			<td><br><br>
				<input type="image" name="_target0" src="<tags:imageUrl name="b-edit.gif"/>" border="0"
				alt="edit this page">
				</tr>
			</table>
			</div>
			<br>

			<hr>
			<strong>Study Identifiers</strong>
			<br>
			<br>
			<div class="review">

			<table width="50%" border="0" cellspacing="0" cellpadding="0"
				id="table1">

				<tr>
					<td valign="top">
					<table width="100%" border="1" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width="20% align="left" class="label">Source</td>
							<td width="20%" align="left" class="label">Type</td>
							<td width="20%" align="left" class="label">Identifier</td>
						</tr>
						<c:forEach items="${command.identifiers}" var="identifier">
						<tr class="results">
							<td>${identifier.source}</td>
							<td>${identifier.type}</td>
							<td>${identifier.value}</td>
						</tr>
						</c:forEach>

					</table>
					</td>
				</tr>
				<tr>
					<td><br><br>
						<input type="image" name="_target1" src="<tags:imageUrl name="b-edit.gif"/>" border="0"
							alt="edit this page"></td>
				</tr>
			</table>
			</div>
			<br>
			<br>
			<hr>
			<strong>Study Site</strong>
			<br>
			<br>
			<div class="review">

			<table width="50%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td valign="top">
					<table width="100%" border="1" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width="20% align="left" class="label">Study Site</td>
							<td width="20%" align="left" class="label">Status</td>
							<td width="20%" align="left" class="label">Role</td>
							<td width="20%" align="left" class="label">Start Date</td>
							<td width="20%" align="left" class="label">IRB Approval Date</td>
						</tr>
						<c:forEach items="${command.studySites}" var="studySite">
						<tr class="results">
							<td>${studySite.site.name}</td>
							<td>${studySite.statusCode}</td>
							<td>${studySite.roleCode}</td>
							<td>${studySite.startDateStr}</td>
							<td>${studySite.irbApprovalDateStr}</td>
						</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				<tr>
				<td><br><br>
					<input type="image" name="_target2" src="<tags:imageUrl name="b-edit.gif"/>" border="0"
						alt="edit this page"></td>
				</tr>
			</table>
			</div>
			<br>
			<hr>
			<strong>Study Design</strong>
			<br>
			<br>
			<div class="review">

			<table width="50%" border="0" cellspacing="10" cellpadding="0"
				id="table1">
				<tr>
					<td valign="top">
					<table width="100%" border="1" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width="20% align="left" class="label">Epochs</td>
							<td width="80%" align="left" class="label">Arms</td>
						</tr>
						<c:forEach items="${command.epochs}" var="epoch">
						<tr class="results">
							<td lclass="label">${epoch.name}</td>
							<td >
								<table width="100%" border="0" cellspacing="2" cellpadding="2"
									id="table1">
								<tr>
									<c:forEach items="${epoch.arms}" var="arm">
										<tr>
											<td width="50%">${arm.name}</td>
											<td width="50%">${arm.targetAccrualNumber}</td>
										</tr>
									</c:forEach>
								</tr>
								</table>
							</td>
						</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
			</table>
			<br>
		</td>
	</tr>
</table>
</div>
<!-- MAIN CONTENT ENDS HERE -->
</tabs:division>
</form:form>
</tabs:body>
</table>
</div>
</body>
</html>