<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
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
        <c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
				<div class="row">
					<div class="label"><b>Informed Consent ${status.index+1}</b>:</div>
					<div class="value">${studySubjectConsentVersion.informedConsentSignedDateStr} (${studySubjectConsentVersion.consent.name})</div>
				</div>
			</c:forEach>
	</div>
	<div class="rightpanel">
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
