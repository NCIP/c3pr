<%@ include file="taglibs.jsp"%>
<chrome:division id="Current Epoch Information" title="Epoch & Arm">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.epoch"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
        </div>
       	<div class="row">
       		<c:choose>
            	<c:when test="${empty armAssignedLabel}">
            		<div class="label"><fmt:message key="study.epoch.arm"/>:</div>
            	</c:when>
            	<c:otherwise>
            		<div class="label">${armAssignedLabel}:</div>
            	</c:otherwise>
            </c:choose>
            <c:choose>
            	<c:when test="${empty armAssignedLabel}">
            		<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notApplicable"/></span></div>
            	</c:when>
            	<c:otherwise>
            		<div class="value">${armAssigned}</div>
            	</c:otherwise>
            </c:choose>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.enrolling"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.epochStatus"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code}</div>
        </div>
    </div>
</chrome:division>

<div id="enrollmentSection">
<chrome:division id="enrollment" title="Enrollment Details">
<div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.startDate"/>:</div>
            <div class="value">${command.studySubject.startDateStr}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.registrationStatus"/>:</div>
            <div class="value">${command.studySubject.regWorkflowStatus.code}
            </div>
        </div>
        <c:if test="${command.studySubject.regWorkflowStatus=='OFF_STUDY'}">
            <div class="row">
                <div class="label"><fmt:message key="registration.offStudyReason"/>:</div>
                <div class="value">${command.studySubject.offStudyReasonText }</div>
            </div>
            <div class="row">
                <div class="label"><fmt:message key="registration.offStudyDate"/>:</div>
                <div class="value">${command.studySubject.offStudyDateStr }</div>
            </div>
        </c:if>
        <div class="row">
					<div class="label"><b>Consents </b>:</div>
					<div class="value">
						<c:set var="size" value="${fn:length(command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions)}"></c:set>
						<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
							${studySubjectConsentVersion.informedConsentSignedDateStr} (${studySubjectConsentVersion.consent.name})
							<c:if test="${size - status.index > 1}"><br></c:if>
						</c:forEach>
						
					</div>
			</div>
        <div class="row">
            <div class="label"><fmt:message key="registration.enrollingPhysician"/>:</div>
            	<c:choose>
					<c:when test="${!empty command.studySubject.treatingPhysicianFullName}">
						<div class="value">${command.studySubject.treatingPhysicianFullName}</div>
					</c:when>
					<c:otherwise>
						<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
					</c:otherwise>
				</c:choose>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.registrationIdentifier"/>:</div>
            	<c:choose>
					<c:when test="${!empty command.studySubject.coOrdinatingCenterIdentifier.value}">
						<div class="value">${command.studySubject.coOrdinatingCenterIdentifier.value}</div>
					</c:when>
					<c:otherwise>
						<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notGenerated"/></span></div>
					</c:otherwise>
				</c:choose>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.primaryDisease"/>:</div>
            	<c:choose>
						<c:when test="${!empty command.studySubject.diseaseHistory.primaryDiseaseStr}">
							<div class="value">${command.studySubject.diseaseHistory.primaryDiseaseStr}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.primaryDiseaseSite"/>:</div>
            <c:choose>
						<c:when test="${!empty command.studySubject.diseaseHistory.primaryDiseaseSiteStr }">
							<div class="value">${command.studySubject.diseaseHistory.primaryDiseaseSiteStr }</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
        <div class="row">
        <div class="label"><fmt:message key="registration.paymentMethod"/>:</div>
            <c:choose>
						<c:when test="${!empty command.studySubject.paymentMethod}">
							<div class="value">${command.studySubject.paymentMethod}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
</div>
</chrome:division>
</div>
