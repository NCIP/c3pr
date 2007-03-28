<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study"%>

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
	document.getElementsByName('_target6')[0].name='_target5';
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form.submit();
}

count=0; // to keep the count of the rows

function add(area)
{
	if (area == 'InclusionTable')
		var str = createInclusionRow(count);
	else
		var str = createExclusionRow(count);

	document.getElementById(area).innerHTML = document.getElementById(area).innerHTML + str;
	document.getElementById('bex-' + count).style.display = 'none';
	Effect.Appear('bex-' + count);
	count++;
	new Ajax.Request('createStudy', {method:'post', postBody:'_target5=5&_page5=5&_action=addInclusionCriteria&_selectedQuestion=', onSuccess:handlerFunc, onFailure:errFunc});
}
var handlerFunc = function(t) {
//alert(t.responseText);
}

var errFunc = function(t) {
    alert('Error ' + t.status + ' -- ' + t.statusText);
}
function remove(count, area)
{
	document.getElementById('bex-' + count).innerHTML = ' ';
}

function createInclusionRow(count)
{
	var str = '<tr id="bex-' + count + '">'+
	'<td width="1%">'+
	'<input type="hidden" name=command.inclusionEligibilityCriterias[' + count + '].questionNumber value='+count+'/>'+
	count+'</td>'+
	'<td width="78%">'+
	'<input type="text" name=command.inclusionEligibilityCriterias[' + count + '].questionText value=command.inclusionEligibilityCriterias[' + count + '].questionText size="150" "'+
	'</td>'+
	'<td width=10%>'+
	'	<input type="checkbox" name=command.inclusionEligibilityCriterias[' + count + '].notApplicableIndicator value=command.inclusionEligibilityCriterias[' + count + '].notApplicableIndicator>NA'+
	'</td>'+
	'<td width=5%>'+
	'	<a href="javascript:remove(count,InclusionTable);"><img'+
	'		 src="<tags:imageUrl name="checkno.gif"/>" border="0">'+
	'	</a>'+
	'</td>'

	return str;
}

function createExclusionRow(count)
{
	var str = '<tr id="bex-' + count + '">'+
	'<td width="1%">'+
	'<input type="hidden" name=command.inclusionEligibilityCriterias[' + count + '].questionNumber value='+count+'/>'+
	count+'</td>'+
	'<td width="78%">'+
	'<input type="text" name=command.inclusionEligibilityCriterias[' + count + '].questionText value=command.inclusionEligibilityCriterias[' + count + '].questionText size="150" "'+
	'</td>'+
	'<td width=10%>'+
	'	<input type="checkbox" name=command.inclusionEligibilityCriterias[' + count + '].notApplicableIndicator value=command.inclusionEligibilityCriterias[' + count + '].notApplicableIndicator>NA'+
	'</td>'+
	'<td width=5%>'+
	'	<a href="javascript:remove(count,InclusionTable);"><img'+
	'		 src="<tags:imageUrl name="checkno.gif"/>" border="0">'+
	'	</a>'+
	'</td>'

	return str;
}


/// AJAX

Effect.OpenUp = function(element) {
     element = $(element);
     new Effect.BlindDown(element, arguments[1] || {});
//     new Effect.Grow(element, arguments[1] || {});
 }

 Effect.CloseDown = function(element) {
     element = $(element);
     new Effect.BlindUp(element, arguments[1] || {});
 }

 Effect.ComboCheck = function(element) {
     element = $(element);
     if(element.style.display == "none") {
          new Effect.OpenUp(element, arguments[1] || {});
      }
 }

 Effect.Combo = function(element,imageStr,title) {
     element = $(element);
     if(element.style.display == "none") {
          new Effect.OpenUp(element, arguments[1] || {});
        //  document.getElementById(imageStr).src="images/b-minus.gif";
      //    new Effect.Grow(document.getElementById(title));
     }else {
          new Effect.CloseDown(element, arguments[1] || {});
       //   document.getElementById(imageStr).src="images/b-plus.gif";
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
<form:form method="post" name="form">
<div>
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value=""></div>
</div>
<!-- MAIN BODY STARTS HERE -->
<tabs:body title="${flow.name}: ${tab.longTitle}">
	<table border="0" id="table1" cellspacing="10" width="100%">
		<tr>
		  	<td valign="top" width="30%">
				<studyTags:studySummary />
		  	</td>
			<td valign="top" width="70%">
			 <table border="0" id="table1" cellspacing="10" width="100%">
			 	 <tr>
				 <td valign="top">
					<tabs:divisionEffects effectsArea="InclusionTable" id="Summary" title="Inclusion Criteria">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="details">
					<tr>
						<p id="instructions">
							*NA - Allow Not Applicable answer
						</p>
						<c:set var="counter" scope="session" value="${counter + 1}" />
						<p>
			 				<b><a href="javascript:add('table_ic');javascript:Effect.ComboCheck('InclusionTable');"><img
								src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>Add a Criteria</b>
							</p>
			 		</tr>
					<tr>
						<td valign="top">
						<table id="" width="100%" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<tr>
								<td>
								<div id="InclusionTable" style="display: none;">
								<table width="100%" id="table_ic">
								</table>
								</div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					</table>
					</tabs:divisionEffects>
				</td>
				</tr>
				<tr>
				<td valign="top" width="70%">
				<tabs:divisionEffects effectsArea="ExclusionTable" id="study-details"  title="Exclusion Criteria">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="details">
					<tr>
						<td width="100%" valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<tr>
								<p id="instructions">
									*NA - Allow Not Applicable answer
								</p>
								<p>
									<b><a href="javascript:add('table_ec');javascript:Effect.ComboCheck('ExclusionTable');"><img
										src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>Add a Criteria</b>
									</p>
							</tr>
							<tr>
								<td valign="top">
								<table id="" width="100%" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td>
										<div id="ExclusionTable" style="display: none;">
										<table width="100%" id="table_ec">
										</table>
										</div>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
			</tabs:divisionEffects>
			</td>
			</tr>
		   </table>
			</td>
		</tr>
	</table>
<!-- MAIN BODY ENDS HERE -->
</tabs:body>
</form:form>

</body>
</html>
