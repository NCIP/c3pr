<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
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
autoCompleters.push(diseaseSiteAutocompleterProps);
submitPostProcess=function(formElement, flag){
							if(formElement.id!='command' || !flag)
								return flag;
							if($('diseaseSite-input').value!=ajaxDiseaseSite){
								$('otherDiseaseSite-hidden').value=$('diseaseSite-input').value;
								$('diseaseSite-hidden').value="";
							}else{
								$('otherDiseaseSite-hidden').value='';
							}
							if($("treatingPhysician").value!=""){
								$('otherTreatingPhysician').value="";
							}
							if($("stuydDiseaseSelect").value!=""){
								$('otherDisease').value="";
							}
							return flag;
						}
</script>
</head>
<body>
<c:choose>
<c:when test="${empty command.scheduledEpoch}">
<tags:panelBox>
The participant is already registered on this epoch. If u want to move this subject to another epoch of this study. Please <a href="javascript:alert('Functionality under construction..');">click</a> here
</tags:panelBox>
</c:when>
<c:otherwise>

<tags:formPanelBox tab="${tab}" flow="${flow}" action="createRegistration">
<strong>Informed Consent Details </strong><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td class="label" width="50%">Informed Consent Signed Date:</td>
		<td><tags:dateInput path="informedConsentSignedDate" /><em> (mm/dd/yyyy)</em></td>
	</tr>
	<tr>
		<td class="label">Informed Consent Version:</td>
		<td><form:input path="informedConsentVersion"/></td>
	</tr>
</table>
<hr align="left" width="95%">

<strong>Enrolling Physician Details </strong><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td colspan='2'>&nbsp;</td></tr>
	<tr>
		<td class="label" width="50%"><em></em>Enrolling Physician:</td>
		<td>
			<form:select path="treatingPhysician" onchange="managePhysicianField(this)">
				<option value="">--Please Select--</option>
				<form:options
					items="${command.studySite.studyInvestigators}" itemLabel="healthcareSiteInvestigator.investigator.fullName" itemValue="id" />
				<option value="">Other</option>
			</form:select>
			<c:if test="${empty command.otherTreatingPhysician }">
				<c:set var="physicianStyle" value="display: none;"></c:set>			 
			</c:if>
			<form:input path="otherTreatingPhysician" cssStyle="${physicianStyle}"/>
		<td>
	</tr>
</table>
<hr align="left" width="95%">

<strong>Select Disease and Disease Site </strong><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td class="label" width="40%">Primary Disease:</td>
		<td>
			<form:select id="stuydDiseaseSelect" path="diseaseHistory.studyDisease" onchange="manageField(this);">
				<option value="">--Please Select--</option>
				<form:options items="${command.studySite.study.studyDiseases}" itemLabel="diseaseTerm.term" itemValue="id"/>
				<option value="">Other</option>
			</form:select>
			<c:if test="${empty command.diseaseHistory.otherPrimaryDiseaseCode }">
				<c:set var="diseaseStyle" value="display: none;"></c:set>			 
			</c:if>
			<form:input id="otherDisease" path="diseaseHistory.otherPrimaryDiseaseCode" cssStyle="${diseaseStyle}"/>
		<td>
	</tr>
	<tr>
		<td class="label" width="40%">Primary Disease Site:</td>
		<td>
			<input id="diseaseSite-input" type="text" value="${command.diseaseHistory.anatomicSite==null?command.diseaseHistory.otherPrimaryDiseaseSiteCode:command.diseaseHistory.anatomicSite.name }"/>
			<form:hidden id="diseaseSite-hidden" path="diseaseHistory.anatomicSite"/>
			<form:hidden id="otherDiseaseSite-hidden" path="diseaseHistory.otherPrimaryDiseaseSiteCode"/>
			<input type="button" id="diseaseSite-clear" value="Clear" onclick="$('diseaseSite-hidden').value='';"/>
			<tags:indicator id="diseaseSite-indicator"/>
			<div id="diseaseSite-choices" class="autocomplete"></div>
		</td>
	</tr>
</table>

<!-- MAIN BODY ENDS HERE -->
</tags:formPanelBox>
</c:otherwise>
</c:choose>
</body>
</html>
