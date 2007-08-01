<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<tags:dwrJavascriptLink objects="anatomicDiseaseSite" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
</script>
<script>
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
function manageField(box){
	if(box.value=='' && box.selectedIndex!=0){
		new Effect.Appear('studyDiseaseDiv');
	}else{
		$('studyDiseaseDiv').style.display="none";
	}
}
submitPostProcess=function(){
							if($('diseaseSite-input').value!=ajaxDiseaseSite){
								$('otherDiseaseSite-hidden').value=$('diseaseSite-input').value;
								$('diseaseSite-hidden').value="";
							}else{
								$('otherDiseaseSite-hidden').value='';
							}
							if($("stuydDiseaseSelect").value!=""){
								$('otherDisease').value="";
							}
							return true;
						}
</script>
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}">
	<strong>Select Disease and Disease Site </strong><br>
	<table width="60%" border="0" cellspacing="0" cellpadding="0" id="table1">
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td class="label">Primary Disease:</td>
			<td>
				<form:select id="stuydDiseaseSelect" path="diseaseHistory.studyDisease" onchange="manageField(this)">
					<option value="">--Please Select--</option>
					<form:options items="${command.studySite.study.studyDiseases}" itemLabel="diseaseTerm.term" itemValue="id"/>
					<option value="">Other</option>
				</form:select>
			</td>
			<td width="25%">
				<div id="studyDiseaseDiv" <c:if test="${empty command.diseaseHistory.otherPrimaryDiseaseCode }">style="display: none;" </c:if>>
				<form:input id="otherDisease" path="diseaseHistory.otherPrimaryDiseaseCode"/>
				</div>
				
			<td>
		</tr>
		<tr>
			<td class="label" width="25%">Primary Disease Site:</td>
			<td>
				<input id="diseaseSite-input" type="text" value="${command.diseaseHistory.anatomicSite==null?command.diseaseHistory.otherPrimaryDiseaseSiteCode:command.diseaseHistory.anatomicSite.name }"/>
				<form:hidden id="diseaseSite-hidden" path="diseaseHistory.anatomicSite"/>
				<form:hidden id="otherDiseaseSite-hidden" path="diseaseHistory.otherPrimaryDiseaseSiteCode"/>
				<input type="button" id="diseaseSite-clear" value="Clear" onclick="$('diseaseSite-hidden').value='';"/>
				<tags:indicator id="diseaseSite-indicator"/>
				<div id="diseaseSite-choices" class="autocomplete"></div>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</tags:formPanelBox>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
