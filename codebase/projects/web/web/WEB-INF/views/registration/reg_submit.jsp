<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>

<style type="text/css">
		.instructions .summaryvalue {width:85%;}
		div.row div.label {
			width:15em;
		}
		div.row div.value {
			margin-left:16em;
		}
		#main {
			top:35px;
		}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
ValidationManager.submitPostProcess= function(formElement, continueSubmission){
	if(formElement.id=='command' && ${registerable && !empty command.studySubject.studySite.targetAccrualNumber && command.studySubject.studySite.targetAccrualNumber <= command.studySubject.studySite.currentAccrualCount}){
		return confirm("This registration will exceed the accrual ceiling at ${command.studySubject.studySite.healthcareSite.name}. Do you want to continue?");
	}
	return continueSubmission;
}
</script>
</head>
<body>
	<tags:instructions code="reg_submit" />
	<c:if test="${command.studySubject.scheduledEpoch.requiresRandomization && command.studySubject.dataEntryStatusString == 'Complete'}">
	<div style="border:1px solid #f00; height:100px; padding:9px; margin-bottom:10px;">
		<img src="<tags:imageUrl name="stop_sign.png" />" alt="Stop!" style="float:left; margin-right:30px; margin-left:80px;" />
		<div style="font-size:20px; margin-bottom:5px;">Almost done...</div>
		<div>
			You still need to <a href="#randomize">randomize</a>!<br/>
			<ul style="padding-left:230px;">
			<li>Please review the information below then randomize at the bottom of the page.</li>
			<li>When you're done, click the Randomize & Enroll button.</li>
			</ul>
		</div>
	</div>
	</c:if>
	<div id="registrationSummary">
	<chrome:division id="Subject Information" title="Subject">
			<div class="leftpanel">
				<div class="row">
						<div class="label"><fmt:message key="c3pr.common.firstName"/>:</div>
						<div class="value">${command.studySubject.participant.firstName}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.lastName"/>:</div>
					<div class="value">${command.studySubject.participant.lastName}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.gender"/>:</div>
					<div class="value">${command.studySubject.participant.administrativeGenderCode}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.MRN"/>:</div>
					<div class="value">${command.studySubject.participant.medicalRecordNumber.value }</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.assigningAuthority"/>:</div>
					<div class="value">${command.studySubject.participant.medicalRecordNumber.healthcareSite.name }</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.birthDate"/>:</div>
					<div class="value">${command.studySubject.participant.birthDateStr}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.ethnicity"/>:</div>
					<div class="value">${command.studySubject.participant.ethnicGroupCode}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.race"/>:</div>
					
						<c:forEach items="${command.studySubject.participant.raceCodes}" var="raceCode">
				            <div class="value">
				                ${raceCode.displayName}
				            </div>
				        </c:forEach>
					
				</div>
			</div>
	</chrome:division>
	<chrome:division id="Study Information" title="Study">
			<div class="leftpanel">
				<div class="row">
					<div class="label"><fmt:message key="study.shortTitle"/>:</div>
					<div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="study.longTitle"/>:</div>
					<div class="value">${command.studySubject.studySite.study.longTitleText}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="study.randomized"/>:</div>
					<div class="value">${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="study.multiInstitution"/>:</div>
					<div class="value">${command.studySubject.studySite.study.multiInstitutionIndicator?'Yes':'No'}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.primaryIdentifier"/>:</div>
					<div class="value">${command.studySubject.studySite.study.primaryIdentifier}</div>
				</div>
			</div>
	</chrome:division>
	<chrome:division id="Study Site Information" title="Study Site">
			<div class="leftpanel">
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.name"/>:</div>
					<div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="site.NCIInstitutionCode"/>:</div>
					<div class="value">${command.studySubject.studySite.healthcareSite.nciInstituteCode}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="site.address"/>:</div>
					<div class="value">${command.studySubject.studySite.healthcareSite.address.addressString}</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="site.IRBApprovalDate"/>:</div>
					<div class="value">${command.studySubject.studySite.irbApprovalDateStr}
					</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="site.activationDate"/>:</div>
					<div class="value">${command.studySubject.studySite.startDateStr}
					</div>
				</div>
			</div>
	</chrome:division>
	<chrome:division id="Current Epoch Information" title="Epoch & Arm">
		<div class="leftpanel">
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.epoch"/>:</div>
				<div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
			</div>
			<c:if test="${!command.studySubject.scheduledEpoch.requiresRandomization}">
			<div class="row">
	            <div class="label"><fmt:message key="study.epoch.arm"/>:</div>
	            <c:choose>
	            	<c:when test="${empty armAssignedLabel}">
	            		<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notApplicable"/></span></div>
	            	</c:when>
	            	<c:otherwise>
	            		<div class="value">${armAssigned}</div>
	            	</c:otherwise>
	            </c:choose>
        	</div>
        	</c:if>
		</div>
		<div class="rightpanel">
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.enrolling"/>:</div>
				<div class="value">${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</div>
			</div>
		</div>
	</chrome:division>
	<chrome:division id="enrollment" title="Enrollment Details" link="javascript:document.getElementById('flowredirect-target').name='_target1';document.getElementById('flowredirect').submit();">
			<div class="leftpanel">
				<div class="row">
					<div class="label"><fmt:message key="registration.startDate"/>:</div>
					<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.startDateStr }' workflow='registration'/></div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="registration.consentSignedDate"/>:</div>
					<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.informedConsentSignedDateStr}' workflow='registration'/></div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="registration.consentVersion"/>:</div>
					<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.informedConsentVersion}' workflow='registration'/></div>
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
	<chrome:division id="Eligibility" title="Eligibility" link="javascript:document.getElementById('flowredirect-target').name='_target2';document.getElementById('flowredirect').submit();">
		<div class="leftpanel">
		<div class="row">
			<div class="label"><fmt:message key="registration.eligible"/>:</div>
			<c:choose>
				<c:when test="${command.studySubject.scheduledEpoch.eligibilityIndicator}">
					<div class="value"><fmt:message key="c3pr.common.yes"/></div>
				</c:when>
				<c:otherwise>
					<div class="value"><fmt:message key="c3pr.common.no"/></div>
					<div style="margin-left: 144px;"><span class="red"><fmt:message key="registartion.eligibiltyRequired"/></span></div>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
	</chrome:division>
	<chrome:division id="stratification" title="Stratification" link="javascript:document.getElementById('flowredirect-target').name='_target3';document.getElementById('flowredirect').submit();">
		<c:choose>
		<c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers) == 0}">
			<div align="left"><span><fmt:message key="registartion.stratificationNotAvailable"/></span></div>
		</c:when>
		<c:otherwise>
			<div class="leftpanel">
			<c:if test="${command.studySubject.scheduledEpoch.epoch.stratificationIndicator && !empty command.studySubject.scheduledEpoch.stratumGroupNumber}">
					<div class="row">
						<div class="label"><fmt:message key="registration.stratumGroupNumber"/>:</div>
						<div class="value"> ${command.studySubject.scheduledEpoch.stratumGroupNumber}</div>
				</div>
			</c:if>
			</div>
			<div class="rightpanel">&nbsp;</div>
			<table border="0" cellspacing="0" cellpadding="0" class="tablecontent"  >
				<tr>
					<th width="35%" scope="col" align="left"><fmt:message key="study.criterion"/></th>
					<th scope="20%" align="left"><b><fmt:message key="study.answers"/></th>
				</tr>
				<c:forEach items="${command.studySubject.scheduledEpoch.subjectStratificationAnswers}" var="criteria">
					<tr class="results">
						<td class="alt" align="left">${criteria.stratificationCriterion.questionText}</td>
						<td class="alt" align="left">
						<tags:requiredFieldEmptyIndicator value='${criteria.stratificationCriterionAnswer.permissibleAnswer}' workflow='registration'/></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
		</c:choose>
	</chrome:division>
	</div>
	<tags:formPanelWithoutBox tab="${tab}" flow="${flow}" title="${tabTitle}" continueLabel="${empty actionLabel? '' : actionLabel}"  isSummaryPage="true">
		<input type="hidden" name="_finish" value="true"/>
		<c:if test="${command.studySubject.dataEntryStatusString == 'Complete'}">
			<a name="randomize"></a>
			<registrationTags:randomization registration="${command.studySubject}"></registrationTags:randomization>
		</c:if> 
	</tags:formPanelWithoutBox>
	
</body>
</html>
