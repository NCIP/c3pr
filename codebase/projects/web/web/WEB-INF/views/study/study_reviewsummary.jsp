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
<div><tabs:division id="study-summary">
<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<strong>Study Details </strong>
			<br>
			<div class="review">
			<table width="50%" border="2" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<td width="20%" align="right"><b>Short Title:<b></td>
					<td>${command.shortTitleText}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Sponsor Study Identifier:<b></td>
					<td>${command.primaryIdentifier}</td>
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
					<td width="20%" class="labelR"><b>Phase:<b></td>
					<td>${command.phaseCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Type:<b></td>
					<td>${command.type}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Phase:<b></td>
					<td>${command.phaseCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Multi Institution:</b></td>
					<td>${command.multiInstitutionIndicator}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Blinded:</b></td>
					<td> ${command.randomizedIndicator}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Randomized:</b></td>
					<td>${command.blindedIndicator}</td>
				</tr>
			</table>
			</div>
			<br>

			<hr>
			<strong>Study Identifiers</strong>
			<br>
			<br>
			<div class="review">

			<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left">Source</td>
					<th scope="col" align="left">Type</td>
					<th scope="col" align="left">Identifier</td>
				</tr>
				<c:forEach items="${command.identifiers}" var="identifier">
				<tr class="results">
					<td class="alt" align="left">${identifier.source}</td>
					<td class="alt" align="left">${identifier.type}</td>
					<td class="alt" align="left">${identifier.value}</td>
				</tr>
			</c:forEach>
			</table>
			</div>
			<br>
			<br>
			<hr>
			<strong>Study Sites</strong>
			<br>
			<br>
			<div class="review">
			<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left">Study Site</td>
					<th scope="col" align="left">Status</td>
					<th scope="col" align="left">Role</td>
					<th scope="col" align="left">Start Date</td>
					<th scope="col" align="left">IRB Approval Date</td>
				</tr>
				<c:forEach items="${command.studySites}" var="studySite">
				<tr class="results">
					<td class="alt" align="left">${studySite.site.name}</td>
					<td class="alt" align="left">${studySite.statusCode}</td>
					<td class="alt" align="left">${studySite.roleCode}</td>
					<td class="alt" align="left">${studySite.startDateStr}</td>
					<td class="alt" align="left">${studySite.irbApprovalDateStr}</td>
				</tr>
				</c:forEach>
			</table>		
			</div>
			<br>
			<br>
			<hr>
			<strong>Study Investigators</strong>
			<br>
			<br>
			<div class="review">
			<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left">Investigator</td>
					<th scope="col" align="left">Role</td>
					<th scope="col" align="left">Status</td>
				</tr>
				<c:forEach items="${command.studySites}" var="studySite" varStatus="status">
					<c:forEach items="${studySite.studyInvestigators}" var="studyInvestigator" varStatus="status">
					<tr class="results">
						<td class="alt" align="left">${studyInvestigator.healthcareSiteInvestigator.investigator.fullName}</td>
						<td class="alt" align="left">${studyInvestigator.roleCode}</td>
						<td class="alt" align="left">${studyInvestigator.statusCode}</td>
					</tr>
					</c:forEach>
				</c:forEach>
			</table>
			</div>
			<br>
			
			<br>
			<hr>
			<strong>Study Personnel</strong>
			<br>
			<br>
			<div class="review">

			<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left">Name</td>
					<th scope="col" align="left">Role</td>
					<th scope="col" align="left">Status</td>
					</tr>
					<c:forEach items="${command.studySites}" var="studySite" varStatus="status">
						<c:forEach items="${studySite.studyPersonnels}" var="studyPersonnel" varStatus="status">
						<tr class="results">
							<td class="alt">${studyPersonnel.researchStaff.fullName}</td>
							<td class="alt">${studyPersonnel.roleCode}</td>
							<td class="alt">${studyPersonnel.statusCode}</td>
						</tr>
						</c:forEach>
					</c:forEach>								
			</table>
			</div>
			<br>
			
			<br>
			<hr>
			<strong>Stratifications</strong>
			<br>
			<br>
			<div class="review">

			<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left"><b>Strata</b></td>
					<b></td>
				</tr>
				<c:forEach items="${command.stratificationCriteria}" var="strat">
				<tr>
					<td class="alt">${strat.questionText}</td>
					<td class="alt">
						<table border="0" cellspacing="0" cellpadding="0" id="mytable">
						<c:forEach items="${strat.permissibleAnswers}" var="ans">
						<tr>
							<td class="alt" align="left">${ans.permissibleAnswer}</td>
						</tr>
						</c:forEach>
					 	</table>
					</td>
				</tr>
				</c:forEach>
			</table>
			</div>
			
			<br>
			<hr>
			<strong>Study Diseases</strong>
			<br>
			<br>
			<div class="review">

			<table order="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left"><b>Disease Term</b></td>
					<th scope="col" align="left"><b>Primary</b></td>
				</tr>
				<c:forEach items="${command.studyDiseases}" var="studyDisease" varStatus="status">
					<tr class="results">
						<td class="alt">${studyDisease.diseaseTerm.ctepTerm}</td>
						<td class="alt">${studyDiseases[status.index].leadDisease}</td>
					</tr>
				</c:forEach>
			</table>
			</div>

			<br>
			<hr>
			<strong>Study Design</strong>
			<br>
			<br>
			<div class="review">

			<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left"><b>Epochs</b></td>
					<th scope="col" align="left"><b>Arms</b>
					<b>&nbsp;&nbsp;&nbsp;&nbsp;Target Accrual No</b></td>
				</tr>
				<c:forEach items="${command.epochs}" var="epoch">
				<tr>
					<td class="alt">${epoch.name}</td>
					<td>
						<table border="0" cellspacing="0" cellpadding="0" id="mytable">
						<tr>
							<c:forEach items="${epoch.arms}" var="arm">
								<tr>
									<td class="alt" align="left">${arm.name}</td>
									<td class="alt" align="left">${arm.targetAccrualNumber}</td>
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
</div>
<!-- MAIN CONTENT ENDS HERE -->
</tabs:division>
</form:form>
</table>
</div>
</body>
</html>