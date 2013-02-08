<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
#summary div.row div.label {
	float:left;
	font-weight:bold;
	margin-left:0em;
	text-align:right;
	width:8em;
}
#summary div.row div.value {
	font-weight:normal;
	margin-left:8.3em;
}
</style>
<script>
function changeStudyVersion(){
	win = new Window({className :"mac_os_x", title: "Select Study Version",
							hideEffect:Element.hide,
							zIndex:100, width:400, height:200 , minimizable:false, maximizable:false,
							showEffect:Element.show
							})
	win.setContent($('changeStudyVersionDiv')) ;
	win.showCenter(true);
}
function manageStudyVersionSelection(element){
	$$('.studyVersionSelection').each(function(e){
				e.checked=false;
			}
		);
	element.checked=true;
	$('studyVersion').value = element.value ;
}

function continueStudyVersionSelection(element){
	document.location = 'reConsent' + "?studyVersion=" + $('studyVersion').value +"&<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
	win.close();
}

function submitReConsentForm(){

	$('reConsentDetails').submit();
}
</script>
</head>
<body>
<form:form method="post" name="reConsentDetails" id="reConsentDetails">
<input type="hidden" name="_target${tab.number+1}" id="_target"/>
<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
<input type="hidden" name="studyVersion" value="" id="studyVersion"/>
     <chrome:body title="${flow.name}: ${tab.longTitle}">
     				 <div id="summary">
			                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
								<tr>	
									<td valign="top" width="40%">
										<table>
											<tr>
												<td align="right"><b>Name:</b> </td>
												<td align="left">${command.studySubject.participant.firstName} ${command.studySubject.participant.lastName }</td>
											</tr>
											<tr>
												<td align="right"><b>Subject MRN:</b> </td>
												<td align="left">${command.participant.primaryIdentifierValue}</td>
											</tr>
											<tr>
												<td align="right"><b>Enrolling site:</b> </td>
												<td align="left">(${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studySite.healthcareSite.primaryIdentifier}) ${command.studySubject.studySite.healthcareSite.name }</td>
											</tr>
											
											<tr>
												<td align="right"><b>Registration status:</b> </td>
												<td align="left">${command.studySubject.regWorkflowStatus.displayName}</td>
											</tr>
										</table>
									</td>
									<td valign="top" width="40%">
										<table>
											<tr>
												<td align="right"><b>Study:</b> </td>
												<td align="left">(${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.study.primaryIdentifier}) ${ command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.shortTitleText}</td>
											</tr>
											<tr>
												<td align="right"><b>Study version:</b> </td>
												<td align="left">${command.reConsentingVersion.name}<c:if test="${fn:length(command.reConsentableStudyVersions) >1}"> &nbsp;&nbsp;<a href="#" onclick="changeStudyVersion();">Re-Consent to another Version</a></c:if></td>
											</tr>
											<tr>
												<td align="right"><b>Epoch:</b> </td>
												<td align="left">${command.studySubject.scheduledEpoch.epoch.name}</td>
											</tr>
										</table>
									</td>
									<td valign="top" width="20%"></td>
								</tr>
							</table>
					   </div>
    </chrome:body>
<chrome:box title="Re-Consent: ${tab.longTitle}">
<tags:errors path="*"/>
<input type="hidden" name="_validateForm" id="_validateForm"/>

<c:if test="${fn:length(command.reConsentingStudySubjectConsentVersions) > 0}">
<c:forEach items="${command.reConsentingStudySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
<chrome:division title="Consent: ${studySubjectConsentVersion.consent.name}">
	<table width="100%" cellpadding="2" cellspacing="4">
		<tr>
			<td width="50%">
				<table>
					<tr>
					  <td align="right"><c:if test="${studySubjectConsentVersion.consent.mandatoryIndicator == 'true'}"><tags:requiredIndicator/></c:if>
			          	<b><fmt:message key="registration.consentSignedDate"/></b>
			          	<tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate" />
			          </td>
			         <td align="left"><tags:dateInput path="reConsentingStudySubjectConsentVersions[${status.index}].informedConsentSignedDate" 
			         	 size="14" />
			         </td>
					</tr>
					<tr>
					  <td align="right">
			          	<b><fmt:message key="registration.consentMethod"/></b>
			          	<tags:hoverHint keyProp="studySubject.informedConsentingMethod" />
			          </td>
			          
	                     <c:if test="${fn:length(studySubjectConsentVersion.consent.consentingMethods) > 1}">
	                     	<td align="left"><form:select id="consetingMethod" path="reConsentingStudySubjectConsentVersions[${status.index}].consentingMethod">
		                    	<form:option label="Please Select" value=""/>
		                    	<form:option label="Written" value="WRITTEN"/>
		                    	<form:option label="Verbal" value="VERBAL"/>
	                    	</form:select></td>
	                    </c:if>
	                    <c:if test="${fn:length(studySubjectConsentVersion.consent.consentingMethods) == 1}">
	                    	<td align="left"><form:select id="consetingMethod" path="reConsentingStudySubjectConsentVersions[${status.index}].consentingMethod"
	                    		disabled="true">
	                    		<form:options items="${studySubjectConsentVersion.consent.consentingMethods}" itemLabel="code" itemValue="name"/>
	                    	</form:select></td>
	                    	<input type="hidden" name="reConsentingStudySubjectConsentVersions[${status.index}].consentingMethod" 
	                    		value="${studySubjectConsentVersion.consent.consentingMethods[0]}"/>
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
			          <td align="left"><tags:dateInput path="reConsentingStudySubjectConsentVersions[${status.index}].consentDeliveryDate"
			          	size="14" /></td>
					</tr>
					<tr>
					  <td align="right">
			          	<b><fmt:message key="registration.consentPresenter"/></b>
			          	<tags:hoverHint keyProp="studySubject.informedConsentFormPresenter" />
			          </td>
			          <td align="left"><form:input path ="reConsentingStudySubjectConsentVersions[${status.index}].consentPresenter" size="14"></form:input></td>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${fn:length(studySubjectConsentVersion.subjectConsentAnswers) > 0}">
			<tr>
				<td colspan="2">
					<table class="tablecontent">
					<tr>
						 <th width="80%">
							<fmt:message key="study.version.name"/> 	
							<tags:hoverHint id="study.version.name" keyProp="study.version.name" />
						</th>
						<th width="20%">
							<fmt:message key="study.answer"/> 	
							<tags:hoverHint id="studySubject.informedConsentQuestion.answer" keyProp="studySubject.informedConsentQuestion.answer" />
						</th>
					</tr>
					<c:forEach items="${studySubjectConsentVersion.subjectConsentAnswers}" var="subjectConsentAnswer" varStatus="answerStatus">
						<tr> 
							<td align="right" width="80%">${subjectConsentAnswer.consentQuestion.text}</td>
			         		<td align="left" width="20%"><form:select id="studySubjectConsentVersions[${status.index }].subjectConsentAnswers[${answerStatus.index}].agreementIndicator" 
			         			path="reConsentingStudySubjectConsentVersions[${status.index }].subjectConsentAnswers[${answerStatus.index}].agreementIndicator">
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
</chrome:box>
</form:form>
<div align="right">
			<tags:button type="button" color="blue" icon="save" value="Re-Consent" onclick="submitReConsentForm();" />
</div>
<div id="changeStudyVersionDiv" style="display:none">
		<table class="tablecontent">
					<tr>
						<th width="150"></th>
						<th width="450"><fmt:message key="study.version.name"/></th>
					</tr>
					<tr>
							<td>
								<input class="studyVersionSelection" type="radio" value="${studyVersion.id}" onclick="manageStudyVersionSelection(this);" disabled/>
							</td>
		               		<td>
		               			${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.name} 
		               		</td>
		               	 </tr>
					 <c:forEach items="${command.reConsentableStudyVersions}" var="studyVersion" varStatus="status">
		               	 <tr>
							<td>
								<input class="studyVersionSelection" type="radio" value="${studyVersion.id}" onclick="manageStudyVersionSelection(this);" <c:if test="${studyVersion.id eq command.reConsentingVersion.id}"> checked </c:if>/>
							</td>
		               		<td>
		               			${studyVersion.name} 
		               		</td>
		               	 </tr>
		               </c:forEach>
		</table>
		<div id="actionButtons">
			<div class="flow-buttons">
		   	<span class="next">
		   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="win.close();" />
				<tags:button type="button" color="green" icon="save" onclick="continueStudyVersionSelection();" value="Continue" />
			</span>
			</div>
		</div>
	</div>
		<script>
		$$('.studyVersionSelection').each(function(e){
				if(e.checked){
					$('studyVersion').value = e.value ;
				}
			}
		);
		</script>
	</body>
	</html>
