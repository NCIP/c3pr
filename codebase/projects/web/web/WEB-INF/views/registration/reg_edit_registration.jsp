<%@ include file="taglibs.jsp"%>
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
						
function editRegistration(){
	$('editRegistrationForm').submit();
} 

</script>
<form:form id="editRegistrationForm">
<chrome:box title="Edit Registration" cssClass="editRegistrationClass">
	<tags:tabFields tab="${tab}"/>
	<c:if test="${fn:length(command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions) == 1}">
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="registration.consentSignedDate"/></div>
			<div class="value">
				<tags:dateInput path="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate" validateDate="true" cssClass='required validate-notEmpty$$DATE'/>
			</div>
		</div>
	</c:if>
	<c:if test="${command.studySubject.isDirectArmAssigment}">
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="study.epoch.arm"/></div>
		<div class="value">
			<form:select id="ArmSelection" path="studySubject.scheduledEpoch.scheduledArms[0].arm" >
				<form:options items="${command.studySubject.scheduledEpoch.epoch.arms}" itemLabel="name" itemValue="id"/>
			</form:select>
			<tags:hoverHint keyProp="studySubject.selectArm"/>
		</div>
	</div>
	</c:if>
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="registration.startDate"/></div>
		<div class="value">
			<input type="text" name="studySubject.startDate" id="startDate" value="${ command.studySubject.startDateStr}" class="date validate-DATE$$notEmpty" />
            <a href="#" id="startDate-calbutton">
           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           	</a>
				<tags:hoverHint keyProp="studySubject.startDate"/>
		</div>
	</div>
	<div class="row">
		<div class="label"><em></em><fmt:message key="registration.enrollingPhysician"/></div>
		<div class="value">
		<c:choose>
		<c:when test="${fn:length(command.studySubject.studySite.activeStudyInvestigators)>0}">
			<select id ="treatingPhysician" name="studySubject.treatingPhysician">
				<option value="">Please select...</option>
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
			No physician found for this Study
		</c:otherwise>
		</c:choose>
		<tags:hoverHint keyProp="studySubject.treatingPhysician"/>
		</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="registration.primaryDisease"/></div>
		<div class="value">
			<form:select id="stuydDiseaseSelect" path="studySubject.diseaseHistory.studyDisease" onchange="manageField(this);">
				<option value="">Please select...</option>
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
		<div class="label"><fmt:message key="registration.primaryDiseaseSite"/></div>
		<div class="value">
			<form:input id="diseaseSite-input" path="studySubject.diseaseHistory.otherPrimaryDiseaseSiteCode" cssClass="autocomplete"/>
			<form:hidden id="diseaseSite-hidden" path="studySubject.diseaseHistory.icd9DiseaseSite"/>
			<tags:indicator id="diseaseSite-indicator"/>
			<div id="diseaseSite-choices" class="autocomplete" style="display: none;"></div>
			<tags:hoverHint keyProp="studySubject.diseaseSite"/>
		</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="registration.paymentMethod"/></div>
		<div class="value">
			<form:select id="paymentMethod" path="studySubject.paymentMethod">
				<option value="">Please select...</option>
				<form:options items="${paymentMethods}" itemLabel="desc" itemValue="code"/>
			</form:select>
			<tags:hoverHint keyProp="studySubject.primaryDisease"/>
		</div>
	</div>
	
<!-- MAIN BODY ENDS HERE -->
<!--  CONSENT DIV BEGINS -->
<c:if test="${fn:length(command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions) > 1}">
<chrome:division title="Consents">
	<table class="tablecontent">
		<tr>
		  <th>
          	<fmt:message key="c3pr.common.name"/>
          	<tags:hoverHint keyProp="study.consent.name" />
          </th>
          <th>
          	<fmt:message key="registration.consentSignedDate"/>
          	<tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate" />
          </th>
		</tr>
		<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
			<tr>
				<td>
					${studySubjectConsentVersion.consent.name}
				</td>
				<td>
					<tags:dateInput path="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[${status.index}].informedConsentSignedDate" />
				</td>
			</tr>
		</c:forEach>
	</table>
</chrome:division>
</c:if>
</chrome:box>
<div class="flow-buttons">
	<span class="next">
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
		&nbsp;&nbsp;&nbsp;
		<tags:button type="button" color="green" icon="save" value="Save" onclick="editRegistration();" />
	</span>
</div>
</form:form>
