<%@ include file="taglibs.jsp"%>
	<script>
		function submitLocalForm(idParamStr){
			document.location="<c:url value='/pages/registration/manageRegistration'/>?"+idParamStr;
		}
		function submitLocalForm(idParamStr){
			document.location="<c:url value='/pages/registration/manageRegistration'/>?"+idParamStr;
		}
	</script>
<form:form method="post" id="reConsentConfirmation">
<input type="hidden" name="_target${tab.number}" id="_target"/>
<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
 <chrome:body>
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
												<td align="left">${command.reConsentingVersion.name}</td>
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
	<div id="flash-message" class="info">
		<img src="<tags:imageUrl name='check.png'/>" alt="" style="vertical-align:top;" /> 
	   	 	Subject successfully Re-Consented on study version : ${command.reConsentingVersion.name }
	</div>
</chrome:box>

<div align="right">
			<script>
				paramString="<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
			</script>
			<tags:button type="button" color="blue" icon="save" value="Return to Manage Registration" onclick="submitLocalForm(paramString)" />
</div>
</form:form>