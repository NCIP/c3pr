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

function fireAction(action, selected, area){
	document.getElementsByName('_target6')[0].name='_target5';
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form.submit();
}

function add(area, count)
{

	//if (area == 'InclusionTable')
		var str = createInclusionRow(count);
	//else
	//	var str = createExclusionRow(count);
	document.getElementById(area).innerHTML = document.getElementById(area).innerHTML + str;
	document.getElementById('bex-' + count).style.display = 'none';
	Effect.Appear('bex-' + count);
	count++;
	new Ajax.Request('createStudy', {method:'post', postBody:'_target5=5&_page5=5&_action=addInclusionCriteria&_selected=', onSuccess:handlerFunc, onFailure:errFunc});
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
	'<input type="hidden" name=command.incCriterias[' + count + '].questionNumber value='+count+'/>'+
	count+'</td>'+
	'<td width="78%">'+
	'<input type="text" name=command.incCriterias[' + count + '].questionText value=command.inclusionEligibilityCriterias[' + count + '].questionText size="150" "'+
	'</td>'+
	'<td width=10%>'+
	'	<input type="checkbox" name=command.incCriterias[' + count + '].notApplicableIndicator value=command.inclusionEligibilityCriterias[' + count + '].notApplicableIndicator>NA'+
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
	'<input type="hidden" name=command.excCriterias[' + count + '].questionNumber value='+count+'/>'+
	count+'</td>'+
	'<td width="78%">'+
	'<input type="text" name=command.excCriterias[' + count + '].questionText value=command.inclusionEligibilityCriterias[' + count + '].questionText size="150" "'+
	'</td>'+
	'<td width=10%>'+
	'	<input type="checkbox" name=command.excCriterias[' + count + '].notApplicableIndicator value=command.inclusionEligibilityCriterias[' + count + '].notApplicableIndicator>NA'+
	'</td>'+
	'<td width=5%>'+
	'	<a href="javascript:remove(count,ExclusionTable);"><img'+
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
          document.getElementById(imageStr).src="<tags:imageUrl name="b-minus.gif"/>";
         // new Effect.Grow(document.getElementById(title));
     }else {
          new Effect.CloseDown(element, arguments[1] || {});
           document.getElementById(imageStr).src="<tags:imageUrl name="b-plus.gif"/>";
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
<table border="0" id="table1" cellspacing="10" width="100%">
	<tr>
		<td valign="top" width="25%">
			<studyTags:studySummary />
		</td>
		<td valign="top" width="75%">
		 <table border="0" id="table1" cellspacing="0" width="100%">
			 <tr>
			 <td valign="top">
				<tabs:divisionEffects effectsArea="InclusionTable" imgExpandArea="expandIncl" id="Summary" title="Inclusion Criteria">
				<div id="InclusionTable" <c:if test="${currentOperation eq 'exclusion'}">style ="display: none;" </c:if>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="details">
				<tr>
					<p id="instructions">
						*NA - Allow Not Applicable answer.  Yes and No are permissible answers
						for both types of Criterias
					</p>
					<p>
						<b><a href="javascript:fireAction('addInclusionCriteria',0,'InclusionTable');"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>Add an Inclusion Criteria</b>
					</p>
				</tr>
				<tr>
					<td valign="top">
					<table id="" width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
						<tr>
							<td>
							<table width="100%" border="0" id="table_ic">
							<tr>
								<td align="left"><b>No</b></td>
								<td align="left"><b>Question<span class="red">*</span></b></td>
								<td align="left"><b>NA</b></td>
								<td align="left"></td></b>
							</tr>
							<c:forEach varStatus="status" items="${command.incCriterias}">
							<tr id="bex-${status.index}">
								<td width="2%">
								<form:hidden path="incCriterias[${status.index}].questionNumber"/>${status.index+1}
								</td>
								<td width="88%">
								<form:textarea path="incCriterias[${status.index}].questionText" rows="1" cols="90"/>
								</td>
								<td width="5%">
									<form:checkbox path="incCriterias[${status.index}].notApplicableIndicator"/>
								</td>
								<td width="5%">
									<a href="javascript:fireAction('removeInclusionCriteria',${status.index},'InclusionTable');">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
								</td>
							</tr>
							</c:forEach>
							</table>
								</td>
						</tr>
					</table>
					</td>
				</tr>
				</table>
				</div>
				</tabs:divisionEffects>
			</td>
			</tr>
			<tr>
			<td valign="top" width="75%">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="details">
				<tr>
					<td valign="top">

					<tabs:divisionEffects effectsArea="ExclusionTable" imgExpandArea="expandExcl" id="Summary" title="Exclusion Criteria">
					<div id="ExclusionTable" <c:if test="${currentOperation eq 'inclusion'}">display: none;" </c:if>>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
						<tr>
							<p id="instructions">
								*NA - Allow Not Applicable answer. Yes and No are permissible answers
								for both types of Criterias
							</p>
							<p>
								<b><a href="javascript:fireAction('addExclusionCriteria',0,'ExclusionTable');"><img
									src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>Add an Exclusion Criteria</b>
							</p>
						</tr>
						<tr>
							<td valign="top">
							<table id="" width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
								<tr>
									<td>
									<table width="100%" border="0" id="table_ec">
									<tr>
										<td align="left"><b>No</b></td>
										<td align="left"><b>Question<span class="red">*</span></b></td>
										<td align="left"><b>NA</b></td>
										<td align="left"></td></b>
									</tr>
									<c:forEach varStatus="status" items="${command.excCriterias}">
									<tr id="bex-${status.index}">
										<td width="2%">
										<form:hidden path="excCriterias[${status.index}].questionNumber"/>${status.index+1}
										</td>
										<td width="88%">
										<form:textarea path="excCriterias[${status.index}].questionText" rows="1" cols="90"/>
										</td>
										<td width="5%">
											<form:checkbox path="excCriterias[${status.index}].notApplicableIndicator"/>
										</td>
										<td width="5%">
											<a href="javascript:fireAction('removeExclusionCriteria',${status.index},'ExclusionTable');">
											<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
										</td>
									</tr>
									</c:forEach>
									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</tabs:divisionEffects>
					</td>
				</tr>
			</table>
		</td>
		</tr>
	   </table>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</form:form>

</body>
</html>