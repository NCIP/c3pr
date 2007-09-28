<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz"%>


<html>
<head>
<script language="JavaScript" type="text/JavaScript">
        function doExportAction() {
            document.viewDetails._action.value = "export";
            document.viewDetails.submit();
            document.viewDetails._action.value = "";
        }
    </script>
</head>

<body>
<form:form name="viewDetails">
	<tags:tabFields tab="${tab}" />
	<chrome:box title="Study Summary">
		<div><input type="hidden" name="_finish" value="true" /> <input
			type="hidden" name="_action" value=""></div>

		<chrome:division id="study-details" title="Study Details">
			<table class="tablecontent">
				<tr>
					<td class="alt" align="left"><b>Short Title:</b></td>
					<td class="alt" align="left">${command.shortTitleText}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Sponsor Study Identifier:</b></td>
					<td class="alt" align="left">${command.organizationAssignedIdentifiers[0].value}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Target Accrual Number:</b></td>
					<td class="alt" align="left">${command.targetAccrualNumber}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Phase:</b></td>
					<td class="alt" align="left">${command.phaseCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Data Entry Status:</b></td>
					<td class="alt" align="left">${command.dataEntryStatus.code}</td>
				</tr>
				<tr>
					<td class="alt" align="left" rows="2"><b>Coordinating Center Status:</b></td>
					<c:set var="commanSepOptVal"
						value="[['Active','Active'],['Pending','Pending'],['Amendment Pending','Amendment Pending'],
						['Closed To Accrual And Treatment','Closed To Accrual And Treatment'],['Closed To Accrual','Closed To Accrual'],
						['Temporarily Closed To Accrual And Treatment','Temporarily Closed To Accrual And Treatment'],
						['Temporarily Closed To Accrual','Temporarily Closed To Accrual']]"></c:set>
					<td><tags:inPlaceSelect
						value="${command.coordinatingCenterStudyStatus.code}"
						path="changedCoordinatingCenterStudyStatus"
						commanSepOptVal="${commanSepOptVal}" />&nbsp;</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Type:</b></td>
					<td class="alt" align="left">${command.type}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Phase:</b></td>
					<td class="alt" align="left">${command.phaseCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Multi Institution:</b></td>
					<td class="alt" align="left">${command.multiInstitutionIndicator}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Blinded:</b></td>
					<td class="alt" align="left">${command.blindedIndicator}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Randomized:</b></td>
					<td class="alt" align="left">${command.randomizedIndicator}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Randomization Type:</b></td>
					<td class="alt" align="left">${command.randomizationType.displayName}</td>
				</tr>

			</table>
		</chrome:division>

		<chrome:division title="Identifiers">
			<h4>Organization Assigned Identifiers</h4>
			<br>

			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">Assigning Authority</th>
					<th scope="col" align="left">Identifier Type</th>
					<th scope="col" align="left">Identifier</th>
				</tr>
				<c:forEach items="${command.organizationAssignedIdentifiers}"
					var="orgIdentifier">
					<tr class="results">
						<td class="alt" align="left">${orgIdentifier.healthcareSite.name}</td>
						<td class="alt" align="left">${orgIdentifier.type}</td>
						<td class="alt" align="left">${orgIdentifier.value}</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<br>
			<h4>System Assigned Identifiers</h4>

			<br>

			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">System Name</th>
					<th scope="col" align="left">Identifier Type</th>
					<th scope="col" align="left">Identifier</th>
				</tr>
				<c:forEach items="${command.systemAssignedIdentifiers}"
					var="identifier">
					<tr class="results">
						<td class="alt" align="left">${identifier.systemName}</td>
						<td class="alt" align="left">${identifier.type}</td>
						<td class="alt" align="left">${identifier.value}</td>
					</tr>
				</c:forEach>
			</table>
		</chrome:division>

		<chrome:division title="Sites">
			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">Study Site</th>
					<th scope="col" align="left">Status</th>
					<th scope="col" align="left">Role</th>
					<th scope="col" align="left">Start Date</th>
					<th scope="col" align="left">IRB Approval Date</th>
				</tr>
				<c:forEach items="${command.studySites}" var="studySite"
					varStatus="status">
					<tr class="results">
						<td class="alt" align="left">${studySite.healthcareSite.name}</td>
						<td class="alt" align="left">${studySite.siteStudyStatus.code}</td>
						<td class="alt" align="left">${studySite.roleCode}</td>
						<td class="alt" align="left">${studySite.startDateStr}</td>
						<td class="alt" align="left">${studySite.irbApprovalDateStr}</td>
					</tr>
				</c:forEach>
			</table>
		</chrome:division>

		<chrome:division title="Investigators">
			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">Investigator</th>
					<th scope="col" align="left">Role</th>
					<th scope="col" align="left">Status</th>
					<th scope="col" align="left">Organization</th>

				</tr>
				<c:forEach items="${command.studyOrganizations}"
					var="studyOrganization" varStatus="status">
					<c:forEach items="${studyOrganization.studyInvestigators}"
						var="studyInvestigator" varStatus="status">
						<tr class="results">
							<td class="alt" align="left">${studyInvestigator.healthcareSiteInvestigator.investigator.fullName}</td>
							<td class="alt" align="left">${studyInvestigator.roleCode}</td>
							<td class="alt" align="left">${studyInvestigator.statusCode}</td>
							<td class="alt">${studyInvestigator.studyOrganization.healthcareSite.name}</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
		</chrome:division>

		<chrome:division title="Personnel">
			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">Name</th>
					<th scope="col" align="left">Role</th>
					<th scope="col" align="left">Status</th>
					<th scope="col" align="left">Organization</th>
				</tr>
				<c:forEach items="${command.studyOrganizations}"
					var="studyOrganization" varStatus="status">
					<c:forEach items="${studyOrganization.studyPersonnel}"
						var="studyPersonnel" varStatus="status">
						<tr class="results">
							<td class="alt">${studyPersonnel.researchStaff.fullName}</td>
							<td class="alt">${studyPersonnel.roleCode}</td>
							<td class="alt">${studyPersonnel.statusCode}</td>
							<td class="alt">${studyPersonnel.studyOrganization.healthcareSite.name}</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
		</chrome:division>

		<chrome:division title="Stratification Factors">
			<table class="tablecontent">
				<tr>
					<th scope="col" align="left"><b>Strata</b></th>
					<th scope="col" align="left"><b>Permissible Answers</b></th>
				</tr>
				<c:forEach items="${command.epochs}" var="epoch">
					<c:if
						test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch'}">
						<c:forEach items="${epoch.stratificationCriteria}" var="strat">
							<tr>
								<td class="alt">${strat.questionText}</td>
								<td class="alt">
								<table border="0" cellspacing="0" cellpadding="0"
									class="tablecontent">
									<c:forEach items="${strat.permissibleAnswers}" var="ans">
										<tr>
											<td>${ans.permissibleAnswer}</td>
										</tr>
									</c:forEach>
								</table>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</c:forEach>
			</table>
		</chrome:division>

		<chrome:division title="Stratum Groups">
			<table class="tablecontent">
				<tr>
					<th scope="col" align="left"><b>Stratum Group Number</b></th>
					<th scope="col" align="left"><b>Answer Combination</b></th>

				</tr>
				<c:forEach items="${command.epochs}" var="epoch">
					<c:if
						test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch'}">
						<c:forEach items="${epoch.stratumGroups}" var="stratGrp">
							<tr>
								<td class="alt">${stratGrp.stratumGroupNumber}</td>
								<td class="alt">${stratGrp.answerCombinations}</td>
							</tr>
						</c:forEach>
					</c:if>
				</c:forEach>
			</table>
		</chrome:division>

		<chrome:division title="Diseases">
			<table class="tablecontent">
				<tr>
					<th scope="col" align="left"><b>Disease Term</b></th>
					<th scope="col" align="left"><b>Primary</b></th>
				</tr>
				<c:forEach items="${command.studyDiseases}" var="studyDisease"
					varStatus="status">
					<tr class="results">
						<td class="alt">${studyDisease.diseaseTerm.ctepTerm}</td>
						<td class="alt">${studyDiseases[status.index].leadDisease}</td>
					</tr>
				</c:forEach>
			</table>
		</chrome:division>

		<chrome:division title="Epochs & Arms">
			<table class="tablecontent">
				<tr>
					<th><b>Epochs</b></th>
					<th><b>Arms</b>
				</tr>
				<c:forEach items="${command.epochs}" var="epoch">
					<tr>
						<td class="alt">${epoch.name}</td>
						<td><c:if
							test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch'}">
							<table border="0" cellspacing="0" cellpadding="0"
								class="tablecontent">
								<tr>
									<th><b>Name</b></th>
									<th><b>Target Accrual No</b>
								</tr>
								<tr>
									<c:forEach items="${epoch.arms}" var="arm">
										<tr>
											<td>${arm.name}</td>
											<td>${arm.targetAccrualNumber}</td>
										</tr>
									</c:forEach>
							</table>
						</c:if></td>

					</tr>
				</c:forEach>
			</table>
		</chrome:division>

		<chrome:division title="Amendments">
			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">Version #</th>
					<th scope="col" align="left">Date</th>
					<th scope="col" align="left">IRB Approval Date</th>
					<th scope="col" align="left">Comments</th>
				</tr>
				<c:forEach items="${command.studyAmendments}" var="amendment">
					<tr class="results">
						<td class="alt" align="left">${amendment.amendmentVersion}</td>
						<td class="alt" align="left">${amendment.amendmentDateStr}</td>
						<td class="alt" align="left">${amendment.irbApprovalDateStr}</td>
						<td class="alt" align="left">${amendment.comments}</td>
					</tr>
				</c:forEach>
			</table>
		</chrome:division>

		<%--Optionally display edit mode buttons--%>
		<c:if test="${not empty editAuthorizationTask}">
			<div class="content buttons autoclear">
			<div class="flow-buttons"><span class="next"> <input type="button"
				value="Export Study" onclick="doExportAction();" /> <csmauthz:accesscontrol
				domainObject="${editAuthorizationTask}"
				authorizationCheckName="taskAuthorizationCheck">
				<c:choose>
					<c:when
						test="${command.coordinatingCenterStudyStatus == 'PENDING'}">
						<input type="submit" value="Edit Study" />
					</c:when>
					<c:when
						test="${command.coordinatingCenterStudyStatus == 'ACTIVE' || 
    					command.coordinatingCenterStudyStatus == 'AMENDMENT_PENDING'}">
						<input type="button" value="Amend Study"
							onclick="document.location='../study/amendStudy?studyId=${command.id}'" />
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<%--	<input type="button" value="Amend Study" onclick="document.location='../study/amendStudy?studyId=${command.id}'"/> --%>
			</csmauthz:accesscontrol> </span></div>
			</div>

		</c:if>

	</chrome:box>
</form:form>
</body>
</html>
