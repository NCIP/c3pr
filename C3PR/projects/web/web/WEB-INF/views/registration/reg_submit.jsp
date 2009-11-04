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
</style>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
ValidationManager.submitPostProcess= function(formElement, continueSubmission){
	if(formElement.id=='command' && ${registerable && !empty command.studySubject.studySite.targetAccrualNumber && command.studySubject.studySite.targetAccrualNumber <= command.studySubject.studySite.currentAccrualCount}){
		return confirm("This registration will exceed the accrual ceiling at ${command.studySubject.studySite.healthcareSite.name}. Do you want to continue?");
	}
	return continueSubmission;
}

function redirectToTab(tabNumber){
	if(tabNumber != ''){
		document.getElementById('flowredirect-target').name='_target'+tabNumber;
		document.getElementById('flowredirect').submit()
	}
}
</script>
</head>
<body>
	<c:forEach items="${flow.tabs}" var="tabLink" varStatus="status">
		<c:if test="${tabLink.shortTitle == 'Enrollment Details'}">
			<c:set var="enrollmentTab" value="${status.index}"/>
		</c:if>
		<c:if test="${tabLink.shortTitle == 'Eligibility'}">
			<c:set var="eligibilityTab" value="${status.index}"/>
		</c:if>
		<c:if test="${tabLink.shortTitle == 'Stratification'}">
			<c:set var="stratificationTab" value="${status.index}"/>
		</c:if>
		<c:if test="${tabLink.shortTitle == 'Companion Registrations'}">
			<c:set var="companionTab" value="${status.index}"/>
		</c:if>
	</c:forEach>
	<tags:instructions code="reg_submit" />
	<c:if test="${requiresRandomization && command.studySubject.dataEntryStatusString == 'Complete' && ( command.studySubject.parentStudySubject == null || command.studySubject.parentStudySubject.regWorkflowStatus=='ENROLLED')}">
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
	<div id="summary">
	<chrome:division id="Study Information" title="Study">
			<div class="leftpanel">
				<div class="row">
					<div class="label"><b><fmt:message key="study.version.name"/></b>:</div>
					<div class="value">${command.studySubject.studySiteVersion.studyVersion.name}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.primaryIdentifier"/>:</div>
					<div class="value">${command.studySubject.studySite.study.primaryIdentifier}</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="study.shortTitle"/>:</div>
					<div class="value">${command.studySubject.studySiteVersion.studyVersion.shortTitleText}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="study.randomized"/>:</div>
					<div class="value">${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="study.stratified"/>:</div>
					<div class="value">${command.studySubject.studySite.study.stratificationIndicator?'Yes':'No'}</div>
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
					<div class="value">${command.studySubject.studySite.healthcareSite.ctepCode}</div>
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
			</div>
	</chrome:division>
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
					<div class="value">${command.studySubject.participant.MRN.value }</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.assigningAuthority"/>:</div>
					<div class="value">${command.studySubject.participant.MRN.healthcareSite.name }</div>
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
	            		<c:choose>
	            			<c:when test="${fn:length(command.studySubject.scheduledEpoch.epoch.arms) > 0}">
	            				<font color="red">&nbsp;	Arm not assigned</font> 
	            				
	            			</c:when>
	            			<c:otherwise>
	            				<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notApplicable"/></span></div>
	            			</c:otherwise>
	            		</c:choose>
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
	<chrome:division id="enrollment" title="Enrollment Details"  link="javascript:redirectToTab('${enrollmentTab}');">
			<div class="leftpanel">
				<c:if test="${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator == 'true'}">
				
					<div class="row">
						<div class="label"><fmt:message key="registration.startDate"/>:</div>
						<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.startDateStr }' workflow='registration'/></div>
					</div>
					
				</c:if>
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
			</div>
	</chrome:division>
	
	<chrome:division title="Informed Consents">
		<c:choose>
			<c:when test="${fn:length(command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions)> 0}">
				<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
				<div class="row">
					<div class="label"><b>${studySubjectConsentVersion.consent.name}</b>:</div>
					<div class="value">
						<c:choose>
							<c:when test="${studySubjectConsentVersion.informedConsentSignedDateStr != null 
							&& studySubjectConsentVersion.informedConsentSignedDateStr != ''}">
								${studySubjectConsentVersion.informedConsentSignedDateStr}
							</c:when>
							<c:otherwise>
								<font color="red"><i>not signed</i></font>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
			</c:when>
			<c:otherwise>
			 The subject has not signed any consent forms.
			</c:otherwise>
		</c:choose>
	</chrome:division>
	<chrome:division id="Eligibility" title="Eligibility" link="javascript:redirectToTab('${eligibilityTab}');">
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
	<chrome:division id="stratification" title="Stratification" link="javascript:redirectToTab('${stratificationTab}')">
		<c:choose>
		<c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers) == 0}">
			<div align="left"><span><fmt:message key="registartion.stratificationNotAvailable"/></span></div>
		</c:when>
		<c:otherwise>
			<c:if test="${command.studySubject.scheduledEpoch.epoch.stratificationIndicator && !empty command.studySubject.scheduledEpoch.stratumGroupNumber}">
					<div class="row">
						<div class="label"><fmt:message key="registration.stratumGroupNumber"/>:</div>
						<div class="value"> ${command.studySubject.scheduledEpoch.stratumGroupNumber}</div>
					</div>
			</c:if>
			<table border="0" cellspacing="0" cellpadding="0" class="tablecontent"  width="80%">
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
	<div id="companionAssociationsDiv" <c:if test="${fn:length(companions) == 0 || !command.studySubject.scheduledEpoch.epoch.enrollmentIndicator || not empty command.studySubject.parentStudySubject}">style="display:none;"</c:if>>
	<chrome:division id="companionRegistration" title="Companion Registration" link="javascript:redirectToTab('${companionTab}')">
			<table border="0" cellspacing="0" cellpadding="0" class="tablecontent"  >
				<tr>
					<th width="68%" scope="col" align="left"><b><fmt:message key="c3pr.common.study"/></b></th>
					<th scope="12%" align="left"><b><fmt:message key="c3pr.common.mandatory"/></b></th>
					<th scope="20%" align="left"><b><fmt:message key="c3pr.common.status"/></b></th>
				</tr>
				<c:forEach items="${companions}" var="companion">
					<tr class="results">
						<td class="alt"><c:if test="${companion.mandatoryIndicator}"><tags:requiredIndicator /></c:if>${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})</td>
						<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>
						<td class="alt">
							<c:choose>
								<c:when test="${companion.mandatoryIndicator}">
									<c:choose>
										<c:when test="${empty companion.registrationStatus}">
											<font color="Red"><i><fmt:message key="c3pr.common.notStarted" /></i></font>
										</c:when>
										<c:when test="${!(companion.registrationStatus == 'Registered but not enrolled'|| companion.registrationStatus == 'Enrolled') }">
											<font color="Red"><i>${(companion.registrationStatus == 'Registered but not enrolled')?'Pending':companion.registrationStatus}</i></font>
										</c:when>
										<c:otherwise>
											${(companion.registrationStatus == 'Registered but not enrolled')?'Pending':companion.registrationStatus}
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									${empty companion.registrationStatus? 'Not Started': (companion.registrationStatus == 'Registered but not enrolled')?'Pending':companion.registrationStatus}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
	</chrome:division>
	</div>
	</div>
	<tags:formPanelWithoutBox tab="${tab}" flow="${flow}" title="${tabTitle}" continueLabel="${(not empty command.studySubject.parentStudySubject && command.studySubject.parentStudySubject.regWorkflowStatus != 'ENROLLED')?'Save & Done': (empty actionLabel? '' : actionLabel)}"  isSummaryPage="true">
		<input type="hidden" name="_finish" value="true"/>
		<c:if test="${command.studySubject.dataEntryStatusString == 'Complete' && ( command.studySubject.parentStudySubject == null || command.studySubject.parentStudySubject.regWorkflowStatus=='ENROLLED')}">
			<a name="randomize"></a>
			<registrationTags:randomization registration="${command.studySubject}"></registrationTags:randomization>
		</c:if> 
	</tags:formPanelWithoutBox>
	
</body>
</html>