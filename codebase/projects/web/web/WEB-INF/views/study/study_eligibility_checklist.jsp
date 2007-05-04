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
	document.getElementById('command').targetPage.name='_noname';
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
//AJAX
Effect.OpenUp = function(element) {
     element = $(element);
     new Effect.BlindDown(element, arguments[1] || {});
}

Effect.CloseDown = function(element) {
	element = $(element);
	new Effect.BlindUp(element, arguments[1] || {});
}

Effect.Combo = function(element) {
    element = $(element);
    if (element.style.display == 'none') {
        new Effect.OpenUp(element, arguments[1] || {});
    } else {
        new Effect.CloseDown(element, arguments[1] || {});
    }
}
function PanelCombo(element) {
    panelDiv = $(element);
    imageId= element+'-image';
    imageSource=document.getElementById(imageId).src;
    if (panelDiv.style.display == 'none') {
        new Effect.OpenUp(panelDiv, arguments[1] || {});
        document.getElementById(imageId).src=imageSource.replace('minimize','maximize');
    } else {
        new Effect.CloseDown(panelDiv, arguments[1] || {});
        document.getElementById(imageId).src=imageSource.replace('maximize','minimize');
    }
}
function displayDiv(id,flag){
	if(flag=='true'){
		document.getElementById(id).style.display='block';
	}else
		document.getElementById(id).style.display='none';	
}
</script>
</head>
<body>
<form:form method="post" name="form">
<div>
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value=""></div>
</div>
<tabs:tabFields tab="${tab}" />
<!-- MAIN BODY STARTS HERE -->
<table border="0" id="table1" cellspacing="10" width="60%">
	<tr>
		<td valign="top" width="100%">
		 <table border="0" id="table1" cellspacing="0" width="100%">
			 <tr>
			 <td valign="top">
			 <tags:panel id="InclusionTable" title="Inclusion Criterias">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="details">
				<tr>
					<p id="instructions">
						*NA - Allow Not Applicable answer<br>
						Yes and No are permissible answers
						</p>
					<p>
						<b>Inclusion Criteria</b><a href="javascript:fireAction('addInclusionCriteria',0,'InclusionTable');"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>
					</p>
				</tr>
				<tr>
					<td valign="top">
					<table border="0" cellspacing="0" cellpadding="0" id="mytable">
						<tr>
							<th scope="col"><b>Question<span class="red">*</span></b></td>
							<th scope="col"><b>NA</b></td>
							<th scope="col" class="specalt"></td></b>
						</tr>
						<c:forEach varStatus="status" items="${command.incCriterias}">
						<tr id="bex-${status.index}">
							<td class="alt">
								<form:hidden path="incCriterias[${status.index}].questionNumber"/>
								<form:textarea path="incCriterias[${status.index}].questionText" rows="1" cols="90"/>
							</td>
							<td class="alt">
								<form:checkbox path="incCriterias[${status.index}].notApplicableIndicator"/>
							</td>
							<td class="alt">
								<a href="javascript:fireAction('removeInclusionCriteria',${status.index},'InclusionTable');">
								<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
							</td>
						</tr>
						</c:forEach>							
					</table>
					</td>
				</tr>
				</table>
				
				</tags:panel>
			</td>
			</tr>
			<tr>
			<td valign="top" width="75%">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="details">
				<tr>
					<td valign="top">
				   <tags:panel id="ExclusionTable" title="Inclusion Criterias">
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
						<tr>
							<p id="instructions">
								*NA - Allow Not Applicable answer <br>
								Yes and No are permissible answers
							</p>
							<p>
								<b>Exclusion Criteria</b><a href="javascript:fireAction('addExclusionCriteria',0,'ExclusionTable');"><img
									src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>
							</p>
						</tr>
						<tr>
							<td valign="top">
							<table border="0" cellspacing="0" cellpadding="0" id="mytable">
								<tr>
									<th scope="col"><b>Question<span class="red">*</span></b></th>
									<th scope="col"><b>*NA</b></th>
									<th scope="col"></th></b>
								</tr>
								<c:forEach varStatus="status" items="${command.excCriterias}">
								<tr id="bex-${status.index}">
									  <td class="alt" align="left">      
										<form:hidden path="excCriterias[${status.index}].questionNumber"/>
										<form:textarea path="excCriterias[${status.index}].questionText" rows="1" cols="90"/>
									</td>
									<td class="alt" align="left">      
										<form:checkbox path="excCriterias[${status.index}].notApplicableIndicator"/>
									</td>
									 <td class="alt" align="left">      <td width="5%">
										<a href="javascript:fireAction('removeExclusionCriteria',${status.index},'ExclusionTable');">
										<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
									</td>
								</tr>
								</c:forEach>								
							</table>
							</td>
						</tr>
					</table>
			
				</tags:panel>
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