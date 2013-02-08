<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>


function closePopup(){
	Effect.CloseDown('studyVersionDiv');
}

function changeStudyVersion(){
	Effect.CloseDown('studyVersionDiv');
	$('updateStudyVersion').value="true";
	$('consentSignedDate').value=$('studySubject.allConsents[0].informedConsentSignedDate').value;
	$('dontSave').remove();
	$('studyVersionForm').submit();
}

</script>
<style>
	#single-fields-interior div.row div.label {
		width:22em;
	}
	#single-fields-interior div.row div.value {
		margin-left:23em;
	}
</style>
</head>
<body>
<c:choose>
<c:when test="${alreadyRegistered!=null}">
	<tags:panelBox>
	<form id="manage" name="manage" action="../registration/manageRegistration" method="get">
	<input type="hidden" name="registrationId" id="manage_registrationId" value=""/>
	</form>
	<font color="red">The participant is already registered on this epoch. If you want to move this subject to another epoch of this study,
	please use Manage Registration module. You can navigate to Manage Registration by searching the registration and then clicking on the registration record.
	</font>
	</tags:panelBox>
</c:when>
<c:otherwise>
<div id="checkInformedConsentSignedDateDiv" style="display: none;">
</div>
<form:form id="studyVersionForm">
	<form:errors path="*">
		<div id="checkInformedConsentSignedDateDivInline">
		</div>
	</form:errors>
	<input type="hidden" id="updateStudyVersion" name="updateStudyVersion" value="false"/>
	<input type="hidden" id="consentSignedDate" name="consentSignedDate"/>
	<input type="hidden" id="dontSave" name="dontSave" value="true"/>
</form:form>
<tags:formPanelBox tab="${tab}" flow="${flow}">
	<input type="hidden" name="_validateForm" id="_validateForm"/>
	<div id="errorMsg1" style="display:none">
	</div>
	<div id="studyVersionDiv">
	<c:if test="${not empty canEnroll && !canEnroll}">
		<c:choose>
		<c:when test="${empty studyVersion}">
				<img src="<tags:imageUrl name="stop_sign.png" />" alt="Stop!" style="float:left; margin-right:30px; margin-left:80px;" />
				<div style="font-size:20px; margin-bottom:5px;">Invalid</div>
				<div>
					Cannot register subject. Site is/was not accruing on the given consent signed date because the site is/was not having the valid IRB approval for the study version.
				</div>
			<div align="right" style="padding-top: 10px">
			<tags:button type="button" color="blue" icon="Close" value="Close" onclick="closePopup();" />
		</div>
		</c:when>
		<c:otherwise>
			<div style="padding-top: 20px">
				<img src="<tags:imageUrl name="error.png" />" alt="Alert!" style="float:left; margin-right:30px; margin-left:30px;" />
				<fmt:message key="REGISTRATION.STUDYVERSION.ERROR.FOUND.VALID" />
				<ul style="padding-left:150px;">
					<li><fmt:message key="study.versionNameNumber" /> : ${studyVersion.name}</li>
				</ul>
			</div>
			<div align="right" style="padding-top: 10px">
			<tags:button type="button" color="red" icon="Cancel" value="Cancel" onclick="closePopup();" /> &nbsp;&nbsp;&nbsp;
			<tags:button type="button" color="green" icon="Save &amp; Continue" value="Continue" onclick="changeStudyVersion()" />
		</div>
		</c:otherwise>
		</c:choose>
		<hr>
	</c:if>
	</div>

<%--<tags:instructions code="enrollment_details" />--%>
	<c:choose>
		<c:when test="${command.studySubject.scheduledEpoch.epoch.type == 'RESERVING'}">
			<c:set var="reservingEpoch" value="true"/>
		</c:when>
		<c:otherwise>
			<c:set var="reservingEpoch" value="z"/>
		</c:otherwise>
	</c:choose>
	
<c:if test="${fn:length(command.studySubject.allConsents) > 0}">
<c:forEach items="${command.studySubject.allConsents}" var="subjectConsentVersion" varStatus="status">
<chrome:division title="Consent: ${subjectConsentVersion.consent.name}">
	<table width="100%" cellpadding="2" cellspacing="4">
		<tr>
			<td width="50%">
				<table>
					<tr>
					  <td align="right"><c:if test="${subjectConsentVersion.consent.mandatoryIndicator == 'true'}"><tags:requiredIndicator/></c:if>
			          	<b><fmt:message key="registration.consentSignedDate"/></b>
			          	<tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate" />
			          </td>
			          
			          <c:choose>
			          	<c:when test="${not empty subjectConsentVersion.informedConsentSignedDate &&(fn:length(command.studySubject.studySubjectStudyVersions) > 1
			          	|| fn:length(command.studySubject.scheduledEpochs) > 1)}">
			          		<td align="left">${command.studySubject.allConsents[status.index].informedConsentSignedDateStr}
			         		</td>
			          	</c:when>
			          	<c:otherwise>
			          		<td align="left"><tags:dateInput path="studySubject.allConsents[${status.index}].informedConsentSignedDate" 
			         			 size="14" />
			         		</td>
			          	</c:otherwise>
			          </c:choose>
					</tr>
					<tr>
					  <td align="right">
			          	<b><fmt:message key="registration.consentMethod"/></b>
			          	<tags:hoverHint keyProp="studySubject.informedConsentingMethod" />
			          </td>
			          
	                     <c:if test="${fn:length(subjectConsentVersion.consent.consentingMethods) > 1}">
	                     	<td align="left"><form:select id="consetingMethod" path="studySubject.allConsents[${status.index}].consentingMethod">
		                    	<form:option label="Please Select" value=""/>
		                    	<form:option label="Written" value="WRITTEN"/>
		                    	<form:option label="Verbal" value="VERBAL"/>
	                    	</form:select></td>
	                    </c:if>
	                    <c:if test="${fn:length(subjectConsentVersion.consent.consentingMethods) == 1}">
	                    	<td align="left"><form:select id="consetingMethod" path="studySubject.allConsents[${status.index}].consentingMethod"
	                    		disabled="true">
	                    		<form:options items="${subjectConsentVersion.consent.consentingMethods}" itemLabel="code" itemValue="name"/>
	                    	</form:select></td>
	                    	<input type="hidden" name="studySubject.allConsents[${status.index}].consentingMethod" 
	                    		value="${subjectConsentVersion.consent.consentingMethods[0]}"/>
	                    </c:if>
					</tr>
				</table>
			</td>
			<td width="50%">
				<table>
					<tr>
					  <td align="right">
			          	<b><fmt:message key="registration.consentDeliveredDate"/></b>
			          	<tags:hoverHint keyProp="studySubject.informedConsentFormDeliveredDate" />
			          </td>
			          <c:choose>
			          		<c:when test="${not empty subjectConsentVersion.informedConsentSignedDate &&(fn:length(command.studySubject.studySubjectStudyVersions) > 1
			          	|| fn:length(command.studySubject.scheduledEpochs) > 1)}">
			          		<td align="left">${command.studySubject.allConsents[status.index].consentDeliveryDateStr}
			         		</td>
			          	</c:when>
			          	<c:otherwise>
			          		<td align="left"><tags:dateInput path="studySubject.allConsents[${status.index}].consentDeliveryDate" 
			         			 size="14" />
			         		</td>
			          	</c:otherwise>
			          </c:choose>
					</tr>
					<tr>
					  <td align="right">
			          	<b><fmt:message key="registration.consentPresenter"/></b>
			          	<tags:hoverHint keyProp="studySubject.informedConsentFormPresenter" />
			          </td>
			          <td align="left"><form:input path ="studySubject.allConsents[${status.index}].consentPresenter" size="14"></form:input></td>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${fn:length(subjectConsentVersion.subjectConsentAnswers) > 0}">
			<tr>
				<td colspan="2">
					<table class="tablecontent">
					<tr>
						 <th width="80%">
							<fmt:message key="study.consent.question.text"/> 	
							<tags:hoverHint id="study.consent.question" keyProp="study.consent.question" />
						</th>
						<th width="20%">
							<fmt:message key="study.answer"/> 	
							<tags:hoverHint id="studySubject.informedConsentQuestion.answer" keyProp="studySubject.informedConsentQuestion.answer" />
						</th>
					</tr>
					<c:forEach items="${subjectConsentVersion.subjectConsentAnswers}" var="subjectConsentAnswer" varStatus="answerStatus">
						<tr> 
							<td align="right" width="80%">${subjectConsentAnswer.consentQuestion.text}</td>
			         		<td align="left" width="20%"><form:select id="allConsents[${status.index }].subjectConsentAnswers[${answerStatus.index}].agreementIndicator" 
			         			path="studySubject.allConsents[${status.index }].subjectConsentAnswers[${answerStatus.index}].agreementIndicator">
									<option value="">Please Select</option>
									<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
								</form:select>
							</td>
						</tr>
					</c:forEach>
			
				</table>
			</td>
		</tr>
		</c:if>
	</table>
</chrome:division>
</c:forEach>
</c:if>
</tags:formPanelBox>

</c:otherwise>
</c:choose>
</body>
</html>
