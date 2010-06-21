<%@ include file="taglibs.jsp"%>
<html>
<head>
<title>Release Migration</title>
<link rel="stylesheet" type="text/css" href="/c3pr/public/css/tables1" />
</head>
<body>
<tags:instructions code="release_migration" />
<form:form method="post">
<c:set var="offOffEpochReasonIndex" value="0"/>
<c:forEach items="${studySubjects}" var="registration">
	<chrome:box title="Registration: ${registration.participant.fullName} (${registration.participant.primaryIdentifierValue }) -${registration.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.shortTitleText} (${registration.studySite.study.primaryIdentifier})">
		<c:set var="currentScheduledEpoch" value="${registration.scheduledEpoch}"/>
		<c:if test="${registration.regWorkflowStatus=='OFF_STUDY'}">
			<chrome:division title="Off Study Details">
				<b>Off study date</b> : ${ registration.offStudyDateStr}<br>
				<tags:requiredIndicator /><b>Off study reason</b>: 
				<form:select path="offEpochReasons[${offOffEpochReasonIndex }].reason.id" cssClass="required validate-notEmpty">
						<option value="" selected>--Please Select--</option>
						<form:options items="${offStudyReasons}" itemLabel="description" itemValue="id"/>
					</form:select>
					<input type="hidden" name="offEpochReasons[${offOffEpochReasonIndex }].id" value="${currentScheduledEpoch.offEpochReasons[0].id }"/>
				<br>
				<b>Off study description</b>:${ registration.offStudyReasons[0].description}<br>
			</chrome:division>
			<c:set var="offOffEpochReasonIndex" value="${offOffEpochReasonIndex+1}"/>
		</c:if>
		<chrome:division title="Off Epoch Details">
		<table class="tablecontent">
			<th>Epoch Name</th>
			<th>Epoch Type</th>
			<th>Select Off Epoch Reason</th>
			<th>Off Epoch Description</th>
			<c:forEach var="scheduledEpoch" items="${registration.studySubjectStudyVersions[0].scheduledEpochs}">
				<c:if test="${scheduledEpoch.id!=currentScheduledEpoch.id && scheduledEpoch.scEpochWorkflowStatus=='OFF_EPOCH'}">
				<tr>
				<td>${scheduledEpoch.epoch.name }</td>
				<td>${scheduledEpoch.epoch.type.code }</td>
				<td>
					<form:select path="offEpochReasons[${offOffEpochReasonIndex }].reason.id" cssClass="required validate-notEmpty">
						<option value="" selected>--Please Select--</option>
						<c:choose>
							<c:when test="${scheduledEpoch.epoch.type == 'TREATMENT'}">
								<form:options items="${offTreatmentReasons}" itemLabel="description" itemValue="id"/>
							</c:when>
							<c:when test="${scheduledEpoch.epoch.type == 'SCREENING'}">
								<form:options items="${offScreeningReasons}" itemLabel="description" itemValue="id"/>
							</c:when>
							<c:when test="${scheduledEpoch.epoch.type == 'RESERVING'}">
								<form:options items="${offReservingReasons}" itemLabel="description" itemValue="id"/>
							</c:when>
							<c:when test="${scheduledEpoch.epoch.type == 'FOLLOWUP'}">
								<form:options items="${offFollowupReasons}" itemLabel="description" itemValue="id"/>
							</c:when>
						</c:choose>
					</form:select>
					<input type="hidden" name="offEpochReasons[${offOffEpochReasonIndex }].id" value="${scheduledEpoch.offEpochReasons[0].id }"/>
				</td>
				<td>${scheduledEpoch.offEpochReasons[0].description }</td>
				</tr>
				<c:set var="offOffEpochReasonIndex" value="${offOffEpochReasonIndex+1}"/>
				</c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${registration.regWorkflowStatus!='OFF_STUDY' && currentScheduledEpoch.scEpochWorkflowStatus=='OFF_EPOCH'}">
					<tr>
						<td>${currentScheduledEpoch.epoch.name }</td>
						<td>${currentScheduledEpoch.epoch.type.code }</td>
						<td>
							<form:select path="offEpochReasons[${offOffEpochReasonIndex }].reason.id" cssClass="required validate-notEmpty">
								<option value="" selected>--Please Select--</option>
								<c:choose>
									<c:when test="${currentScheduledEpoch.epoch.type == 'TREATMENT'}">
										<form:options items="${offTreatmentReasons}" itemLabel="description" itemValue="id"/>
									</c:when>
									<c:when test="${currentScheduledEpoch.epoch.type == 'SCREENING'}">
										<form:options items="${offScreeningReasons}" itemLabel="description" itemValue="id"/>
									</c:when>
									<c:when test="${currentScheduledEpoch.epoch.type == 'RESERVING'}">
										<form:options items="${offReservingReasons}" itemLabel="description" itemValue="id"/>
									</c:when>
									<c:when test="${currentScheduledEpoch.epoch.type == 'FOLLOWUP'}">
										<form:options items="${offFollowupReasons}" itemLabel="description" itemValue="id"/>
									</c:when>
								</c:choose>
							</form:select>
							<input type="hidden" name="offEpochReasons[${offOffEpochReasonIndex }].id" value="${currentScheduledEpoch.offEpochReasons[0].id }"/>
						</td>
						<td>${currentScheduledEpoch.offEpochReasons[0].description }</td>
					</tr>
					<c:set var="offOffEpochReasonIndex" value="${offOffEpochReasonIndex+1}"/>
				</c:when>
			</c:choose>
		</table>
		</chrome:division>
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
