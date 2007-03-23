<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<tags:includeScriptaculous/>
<tags:dwrJavascriptLink objects="createStudy"/>

<title>${tab.longTitle}</title>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function fireAction(action, selected){
	document.getElementsByName('_target4')[0].name='_target3';
	document.studySiteForm._action.value=action;
	document.studySiteForm._selected.value=selected;
	document.studySiteForm.submit();

}
function clearField(field){
field.value="";
}

function chooseSites(){
	document.getElementsByName('_target4')[0].name='_target3';
	document.studySiteForm._action.value="siteChange";
	document.studySiteForm._selected.value=document.getElementById('site').value;
	document.studySiteForm.submit();
}

function chooseSitesfromSummary(selected){
	document.getElementsByName('_target4')[0].name='_target3';
	document.studySiteForm._action.value="siteChange";
	document.studySiteForm._selected.value=selected;
	document.studySiteForm.submit();
}

function fireAction1(action, selected, studysiteindex){
	document.getElementsByName('_target4')[0].name='_target3';
	document.studySiteForm._action.value=action;
	document.studySiteForm._selected.value=selected;
	document.studySiteForm._studysiteindex.value=studysiteindex;
	document.studySiteForm.submit();

}

/// AJAX

function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function add(){
var action = confirm ("You have not completed adding this protocol.\r\rStarting over will lose current protocol data?")
if (action){
	parent.window.location="reg_enroll_patient.htm";
}}
function addPatient(){
	parent.window.location="reg_stratify.htm";
}
function manageCriterias(){

}
Effect.OpenUp = function(element) {
     element = $(element);
     new Effect.BlindDown(element, arguments[1] || {});
//     new Effect.Grow(element, arguments[1] || {});
 }

 Effect.CloseDown = function(element) {
     element = $(element);
     new Effect.BlindUp(element, arguments[1] || {});
 }

 Effect.Combo = function(element,imageStr,title) {
     element = $(element);
     if(element.style.display == "none") {
          new Effect.OpenUp(element, arguments[1] || {});
          document.getElementById(imageStr).src="images/b-minus.gif";
          new Effect.Grow(document.getElementById(title));
     }else {
          new Effect.CloseDown(element, arguments[1] || {});
          document.getElementById(imageStr).src="images/b-plus.gif";
     }
 }
function hideTextArea(a,b){
	if(document.getElementById(a).checked==true){
		new Effect.OpenUp(document.getElementById(b));
	}else{
		new Effect.CloseDown(document.getElementById(b));
	}
}

</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->

<tabs:body title="${flow.name}: ${tab.longTitle}">
	<form:form method="post" name="studySiteForm">

	<table border="0" id="table1" cellspacing="10" width="100%">
		<tr>
		 <td valign="top" width="50%">
			<tabs:division id="Summary" title="Inclusion Criteria">
			<!-- RIGHT CONTENT STARTS HERE -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="details">
				<tr>
					<td width="50%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr BGCOLOR=#EEEEEE
							onClick()="Effect.Combo('InclusionTable','expandIncl','InclusionTitle')">
							<td width="100%">
							<div id="InclusionTitle">&nbsp;<img id="expandIncl"
								src="images/b-plus.gif"> <img
								src="<tags:imageUrl name="arrowDown.gif"/>" border="0" alt="expand"></div>
							</td>
						</tr>
						<tr>
							<td>
							<div id="InclusionTable" style="display: none;">
							<table width="100%">
								<tr>
									<td width="100%">If suffering from cancer A, is the
									criteria B met?</td>
									<td><input type="checkbox" name="group1" value="Milk">
									NA</td>
								</tr>
								<tr>
									<td width="100%">If suffering from cancer A, is the
									criteria B met?</td>
									<td><input type="checkbox" name="group1" value="Milk">
									NA</td>
								</tr>
								<tr>
									<td width="100%">If suffering from cancer A, is the
									criteria B met?</td>
									<td><input type="checkbox" name="group1" value="Milk">
									NA</td>
								</tr>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				</table>
			</tabs:division>
		</td>
		<td width="50%" valign="top">
		<tabs:division id="study-details"  title="Exclusion Criteria">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="details">
			<tr>
				<td width="100%" valign="top">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="table1">

					<tr BGCOLOR=#EEEEEE
						onClick()="Effect.Combo('ExclusionTable','expandExcl','ExclusionTitle')">
						<td width="100%">
						<div id="ExclusionTitle">&nbsp;<img id="expandExcl"
							src="images/b-plus.gif"> <img
								src="<tags:imageUrl name="arrowDown.gif"/>" border="0" alt="expand"></div>
						</td>
					</tr>
					<tr>
						<td>
						<div id="ExclusionTable" style="display: none;">
						<table width="100%">
							<tr>
								<td width="100%">If suffering from cancer A, is the
								criteria B met?</td>
								<td><input type="checkbox" name="group1" value="Milk">
								NA</td>
							</tr>
							<tr>
								<td width="100%">If suffering from cancer A, is the
								criteria B met?</td>
								<td><input type="checkbox" name="group1" value="Milk">
								NA</td>
							</tr>
							<tr>
								<td width="100%">If suffering from cancer A, is the
								criteria B met?</td>
									<td><input type="checkbox" name="group1" value="Milk">
								NA</td>
							</tr>
						</table>
						</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
				</table>

		  </tabs:division>
		  </td>
		  <tr>
		  <td width="50%" valign="top">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="5%"><input type="checkbox" id='eligibilityIndicator' onClick="hideTextArea('eligibilityIndicator','WaiveEligibility')" />
					</td>
					<td align="left"><b>Waive Eligibility</td>
					</tr>
					<tr>
					<td colspan=2>
						<div id="WaiveEligibility" style="display:none;">
						<textarea rows="5" cols="50"> Type Eligibility Waiver Reason. </textarea>
						</div>
					</td>
				</tr>
			</table>
		</td>
		</tr>
	  </tr>
	</table>
</form:form>
<!-- MAIN BODY ENDS HERE -->
</tabs:body>
</body>
</html>
