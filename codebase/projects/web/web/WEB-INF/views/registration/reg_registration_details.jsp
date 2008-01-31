<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
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
			icv = document.getElementById('informedConsentVersion');
	    	if (box.checked) {
	    		icv.value=cv.value;       
	        }else {
	        	icv.value="";             
	        }       
	    }    						
</script>
</head>
<body>
<c:choose>
<c:when test="${alreadyRegistered!=null}">
	<tags:panelBox>
	<form id="manage" name="manage" action="../registration/manageRegistration" method="get">
	<input type="hidden" name="registrationId" id="manage_registrationId" value=""/>
	</form>
	<font color="red">The participant is already registered on this epoch. If you want to move this subject to another epoch of this study, 
	please use Manage Registration module.You can navigate to Manage Registration by searching the registration and then clicking on the registration record.
	</font>
	</tags:panelBox>
</c:when>
<c:otherwise>
<%System.out.println("In otherwise"); %>
<tags:formPanelBox tab="${tab}" flow="${flow}" action="createRegistration">
<input type="hidden" name="consentVersion" id="consentVersion" value="${command.studySite.study.consentVersion}"/>
<form:hidden path="informedConsentVersion"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td class="label" width="50%">Informed Consent Signed Date:</td>
		<td><tags:dateInput path="informedConsentSignedDate" /><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate"/></td>
	</tr>
	<tr>
		<td class="label">Current Consent Version is <em>${command.studySite.study.consentVersion}</em>:</td>
		<td><input type="checkbox" name="currentVersionIndicator" value="true" onclick="setVersion(this);" 
				<c:if test="${!empty command.informedConsentVersion}"> checked </c:if>/><tags:hoverHint keyProp="studySubject.informedConsentSignedVersion"/></td>
	</tr>
	<tr>
		<td class="label" width="50%">Registration Start Date:</td>
		<td><tags:dateInput path="startDate" /><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.startDate"/></td>
	</tr>
	<tr>
		<td class="label" width="50%"><em></em>Enrolling Physician:</td>
		<td>
		<c:choose>
		<c:when test="${fn:length(command.studySite.activeStudyInvestigators)>0}">
			<form:select path="treatingPhysician">
				<option value="">Please Select</option>
				<form:options
					items="${command.studySite.activeStudyInvestigators}" itemLabel="healthcareSiteInvestigator.investigator.fullName" itemValue="id" />
			</form:select>
			<c:if test="${empty command.otherTreatingPhysician }">
				<c:set var="physicianStyle" value="display: none;"></c:set>			 
			</c:if>
			<form:input path="otherTreatingPhysician" cssStyle="${physicianStyle}"/>
		</c:when>
		<c:otherwise>
		No active physician found
		</c:otherwise>
		</c:choose>
		<tags:hoverHint keyProp="studySubject.treatingPhysician"/>
		</td>
	</tr>
	<tr>
		<td class="label" width="40%">Primary Disease:</td>
		<td>
			<form:select id="stuydDiseaseSelect" path="diseaseHistory.studyDisease" onchange="manageField(this);">
				<option value="">Please Select</option>
				<form:options items="${command.studySite.study.studyDiseases}" itemLabel="diseaseTerm.term" itemValue="id"/>
				<option value="">Other</option>
			</form:select>
			<c:if test="${empty command.diseaseHistory.otherPrimaryDiseaseCode }">
				<c:set var="diseaseStyle" value="display: none;"></c:set>			 
			</c:if>
			<form:input id="otherDisease" path="diseaseHistory.otherPrimaryDiseaseCode" cssStyle="${diseaseStyle}"/>
			<tags:hoverHint keyProp="studySubject.otherDisease"/>
		<td>
	</tr>
	<tr>
		<td class="label" width="40%">Primary Disease Site:</td>
		<td>
			<form:input id="diseaseSite-input" path="diseaseHistory.otherPrimaryDiseaseSiteCode" cssClass="autocomplete"/>
			<form:hidden id="diseaseSite-hidden" path="diseaseHistory.anatomicSite"/>
			<input type="button" id="diseaseSite-clear" value="Clear" onclick="$('diseaseSite-hidden').value='';"/>
			<tags:indicator id="diseaseSite-indicator"/>
			<div id="diseaseSite-choices" class="autocomplete"></div>
			<tags:hoverHint keyProp="studySubject.diseaseSite"/>
		</td>
		
	</tr>
</table>

<!-- MAIN BODY ENDS HERE -->
</tags:formPanelBox>
</c:otherwise>
</c:choose>
</body>
</html>
