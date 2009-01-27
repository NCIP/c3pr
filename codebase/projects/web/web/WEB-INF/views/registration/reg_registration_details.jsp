<%@ include file="taglibs.jsp"%>
        
<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<tags:dwrJavascriptLink objects="anatomicDiseaseSite" />
<script>
function manageField(box){
	if(box.value=='' && box.selectedIndex!=0){
		new Effect.Appear('otherDisease');
	}else{
		$('otherDisease').style.display="none";
	}
}
function managePhysicianField(box){
	if(box.value=='' && box.selectedIndex!=0){
		new Effect.Appear('otherTreatingPhysician');
	}else{
		$('otherTreatingPhysician').style.display="none";
	}
}

var ajaxDiseaseSite="";
var diseaseSiteAutocompleterProps = {
	basename: "diseaseSite",
	isFreeTextAllowed: true,
    populator: function(autocompleter, text) {
			        anatomicDiseaseSite.matchDiseaseSites(text, function(values) {
																    	autocompleter.setChoices(values)
																	   })
			    },
    valueSelector: function(obj) {
						return obj.name
			    	},
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=diseaseSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
	    							ajaxDiseaseSite=selectedChoice.name;
								}
}
AutocompleterManager.addAutocompleter(diseaseSiteAutocompleterProps);
ValidationManager.submitPostProcess=function(formElement, flag){
							if(formElement.id!='command' || !flag)
								return flag;
							//if($("treatingPhysician").value!=""){
							//	$('otherTreatingPhysician').value="";
							//}
							if($("stuydDiseaseSelect").value!=""){
								$('otherDisease').value="";
							}
							return flag;
						}
						
		function setVersion(box){
			cv = document.getElementById('consentVersion');
			icv = document.getElementById('studySubject.informedConsentVersion');
	    	if (box.checked) {
	    		icv.value=cv.value;       
	        }else {
	        	icv.value="";             
	        }       
	    }    						
</script>
<style>
	#single-fields-interior div.row div.label {
		width:22em;
	}
	#single-fields-interior div.row div.value {
		margin-left:23em;
	}
	#main {
		top:35px;
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
<%System.out.println("In otherwise"); %>
<tags:formPanelBox tab="${tab}" flow="${flow}">
<input type="hidden" name="studySubject.consentVersion" id="consentVersion" value="${command.studySubject.studySite.study.consentVersion}"/>
<form:hidden path="studySubject.informedConsentVersion"/>
<%--<tags:instructions code="enrollment_details" />--%>

	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="registration.consentSignedDate"/></div>
		<div class="value"><tags:dateInput path="studySubject.informedConsentSignedDate" /><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate"/></div>
	</div>
	<div class="row">
		<div class="label"><tags:requiredIndicator />Current Consent Version is <em>${command.studySubject.studySite.study.consentVersion}</em></div>
		<div class="value"><input type="checkbox" name="studySubject.currentVersionIndicator" value="true" onclick="setVersion(this);" 
				<c:if test="${!empty command.studySubject.informedConsentVersion}"> checked </c:if>/><tags:hoverHint keyProp="studySubject.informedConsentSignedVersion"/></div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="registration.startDate"/></div>
		<div class="value"><tags:dateInput path="studySubject.startDate" /><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.startDate"/></div>
	</div>
	<div class="row">
		<div class="label"><em></em><fmt:message key="registration.enrollingPhysician"/></div>
		<div class="value">
		<c:choose>
		<c:when test="${fn:length(command.studySubject.studySite.activeStudyInvestigators)>0}">
			<select id ="treatingPhysician" name="studySubject.treatingPhysician">
				<option value="">Please Select</option>
				<c:forEach items="${command.studySubject.studySite.activeStudyInvestigators}" var="activeInv">
					<option value="${activeInv.id }" ${!empty command.studySubject.treatingPhysician && command.studySubject.treatingPhysician.id==activeInv.id?'selected':''}>${activeInv.healthcareSiteInvestigator.investigator.fullName }</option>
				</c:forEach>
			</select>
			<%--<form:select path="studySubject.treatingPhysician">
				<option value="">Please Select</option>
				<form:options
					items="${command.studySubject.studySite.activeStudyInvestigators}" itemLabel="healthcareSiteInvestigator.investigator.fullName" itemValue="id" />
			</form:select>--%>
			<c:if test="${empty command.studySubject.otherTreatingPhysician }">
				<c:set var="physicianStyle" value="display: none;"></c:set>			 
			</c:if>
			<form:input path="studySubject.otherTreatingPhysician" cssStyle="${physicianStyle}"/>
		</c:when>
		<c:otherwise>
		No active physician found
		</c:otherwise>
		</c:choose>
		<tags:hoverHint keyProp="studySubject.treatingPhysician"/>
		</div>
	</div>
	<div class="row">
		<div class="label">Primary Disease</div>
		<div class="value">
			<form:select id="stuydDiseaseSelect" path="studySubject.diseaseHistory.studyDisease" onchange="manageField(this);">
				<option value="">Please Select</option>
				<form:options items="${command.studySubject.studySite.study.studyDiseases}" itemLabel="diseaseTerm.term" itemValue="id"/>
				<option value="">Other</option>
			</form:select>
			<tags:hoverHint keyProp="studySubject.primaryDisease"/>
			<c:if test="${empty command.studySubject.diseaseHistory.otherPrimaryDiseaseCode}">
				<c:set var="diseaseStyle" value="display:none;"></c:set>			 
			</c:if>
			<span id="otherDisease" style="${diseaseStyle}">
				<form:input id="otherDisease" path="studySubject.diseaseHistory.otherPrimaryDiseaseCode" />
				<tags:hoverHint keyProp="studySubject.otherDisease"/>
			</span>
		</div>
	</div>
	<div class="row">
		<div class="label">Primary Disease Site</div>
		<div class="value">
			<form:input id="diseaseSite-input" path="studySubject.diseaseHistory.otherPrimaryDiseaseSiteCode" cssClass="autocomplete"/>
			<form:hidden id="diseaseSite-hidden" path="studySubject.diseaseHistory.anatomicSite"/>
			<input type="button" id="diseaseSite-clear" value="Clear" onClick="$('diseaseSite-hidden').value='';"/>
			<tags:indicator id="diseaseSite-indicator"/>
			<div id="diseaseSite-choices" class="autocomplete" style="display: none;"></div>
			<tags:hoverHint keyProp="studySubject.diseaseSite"/>
		</div>
	</div>
	<div class="row">
		<div class="label">Payment Method</div>
		<div class="value">
			<form:select id="paymentMethod" path="studySubject.paymentMethod">
				<option value="">Please Select</option>
				<form:options items="${paymentMethods}" itemLabel="desc" itemValue="code"/>
			</form:select>
			<tags:hoverHint keyProp="studySubject.primaryDisease"/>
		</div>
	</div>


<!-- MAIN BODY ENDS HERE -->
</tags:formPanelBox>
</c:otherwise>
</c:choose>
</body>
</html>
