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
        .labelR { width: 12em; text-align: right; padding: 4px; }
</style>
<style type="text/css">
        .label { width: 12em; text-align: left; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function updateTargetPage(target){
	document.studyDesignForm._target0.value=s;
	document.studyDesignForm.submit();
}

</script>
</head>
<body>
<form:form method="post">
<input type="hidden" name="_finish" value="true"/>
<div><tabs:division id="study-studysites">
<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<hr>
			<strong>Study Details </strong>
			<br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="table1">

				<tr>
					<td width="20%" class="labelR"><b>Short Title:<b></td>
					<td>${command.shortTitleText}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Target Accrual Number:</b></td>
					<td>${command.targetAccrualNumber}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Status:</b></td>
					<td>${command.status}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Disease Code:</b></td>
					<td>${command.diseaseCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Disease Type:</b></td>
					<td>${command.type}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Monitor Code:</b></td>
					<td>${command.monitorCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Phase Code:<b></td>
					<td>${command.phaseCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Multi Institution:</b></td>
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

			<table width="60%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width ="33%" align="left"><b>Source</b></td>
							<td width ="33%" align="left"><b>Type</b></td>
							<td width ="33%" align="left" class="label"><b>Identifier</b></td>
						</tr>
						<c:forEach items="${command.identifiers}" var="identifier">
						<tr class="results">
							<td width ="33%" align="left">${identifier.source}</td>
							<td width ="33%" align="left">${identifier.type}</td>
							<td width ="33%" align="left">${identifier.value}</td>
						</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				<tr>
					<td><br><br>
						<input type="image" name="_target1" src="<tags:imageUrl name="b-edit.gif"/>" border="0"
							alt="edit this page">
					</td>
				</tr>
			</table>
			</div>
			<br>
			<br>
			<hr>
			<strong>Study Sites</strong>
			<br>
			<br>
			<div class="review">
			<table width="70%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width="40% align="left"><b>Study Site</b></td>
							<td width="15%" align="left"><b>Status</b></td>
							<td width="15%" align="left"><b>Role</b></td>
							<td width="15%" align="left"><b>Start Date</b></td>
							<td width="15%" align="left"><b>IRB Approval Date</b></td>
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
			<br>
			<hr>
			<strong>Study Investigators</strong>
			<br>
			<br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width="33% align="left"><b>Investigator</b></td>
							<td width="33%" align="left"><b>Role</b></td>
							<td width="33%" align="left"><b>Status</td>
						</tr>
						<c:forEach items="${command.studySites}" var="studySite" varStatus="status">
							<c:forEach items="${studySite.studyInvestigators}" var="studyInvestigator" varStatus="status">
							<tr class="results">
								<td>${studyInvestigator.healthcareSiteInvestigator.investigator.fullName}</td>
								<td>${studyInvestigator.roleCode}</td>
								<td>${studyInvestigator.statusCode}</td>
							</tr>
							</c:forEach>
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
			<br>
			<hr>
			<strong>Study Personnel</strong>
			<br>
			<br>
			<div class="review">

			<table width="50%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width="20% align="left"><b>Study Personnel</b></td>
							<td width="20%" align="left"><b>Role</b></td>
							<td width="20%" align="left"><b>Status</b></td>
						</tr>
						<c:forEach items="${command.studySites}" var="studySite" varStatus="status">
							<c:forEach items="${studySite.studyPersonnels}" var="studyPersonnel" varStatus="status">
							<tr class="results">
								<td>${studyPersonnel.researchStaff.fullName}</td>
								<td>${studyPersonnel.roleCode}</td>
								<td>${studyPersonnel.statusCode}</td>
							</tr>
							</c:forEach>
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
				<br>
				<hr>
				<strong>Eligibility Criteria</strong>
				<br>
				<br>
				<div class="review">

				<table width="70%" border="0" cellspacing="0" cellpadding="0"
					id="table1">
					<tr>
						<td valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<tr><strong>Inclusion Eligibility Criteria</strong></tr>
							<tr>
								<td width="33%" align="left"><b>No</b></td>
								<td width="33%" align="left"><b>Question</td>
								<td width="33%" align="left"><b>N/A</b></td>
							</tr>
							<c:forEach items="${command.incCriterias}" var="inc">
							<tr class="results">
								<td width="33% align="left">${inc.questionNumber}</td>
								<td width="33% align="left">${inc.questionText}</td>
								<td width="33% align="left">${inc.notApplicableIndicator}</td>
							</tr>
							</c:forEach>
						</table>
						</td>
					</tr>
					<tr>
						<td valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<tr><strong>Exclusion Eligibility Criteria</strong></tr>
							<tr>
								<td width="33%" align="left"><b>No</b></td>
								<td width="33%" align="left"><b>Question</b></td>
								<td width="33%" align="left"><b>N/A</b></td>
							</tr>
							<c:forEach items="${command.excCriterias}" var="exc">
							<tr class="results">
								<td>${exc.questionNumber}</td>
								<td>${exc.questionText}</td>
								<td>${exc.notApplicableIndicator}</td>
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
					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width="20% align="left"><b>Epochs</b></td>
							<td width="80%" align="left"><b>Arms</b>
							<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Target Accrual No</b></td>

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
											<td align="left" width="50%">${arm.name}</td>
											<td align="left" width="50%">${arm.targetAccrualNumber}</td>
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
		</td>
	</tr>
</table>
</div>
<!-- MAIN CONTENT ENDS HERE -->
</tabs:division>
</form:form>
</table>
</div>
</body>
</html>