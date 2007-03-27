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
	document.getElementsByName('_target4')[0].name='_target3';
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form.submit();

}
function clearField(field){
field.value="";
}

function add(count, area)
{
	var str = createRow(count);
		document.getElementById(area).innerHTML = document.getElementById(area).innerHTML + str;
		document.getElementById('bex-' + count).style.display = 'none';
		Effect.Appear('bex-' + count);
}

function remove(count, area)
{
	document.getElementById('bex-' + count).innerHTML = ' ';
}

function createRow(count)
{
	var str =  '<tr><td width="100%">If suffering from cancer A, is the'+
				'criteria B met?</td><td><input type="checkbox" name="group1" value="Milk">NA</td>'
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

 Effect.Combo = function(element,imageStr,title) {
     element = $(element);
     if(element.style.display == "none") {
          new Effect.OpenUp(element, arguments[1] || {});
          document.getElementById(imageStr).src="images/b-minus.gif";
      //    new Effect.Grow(document.getElementById(title));
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
	<form:form method="post" name="form">
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
						<td valign="top">
						<table id="" width="100%" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<tr id="bex-1">
								<td>
								<div id="InclusionTable" style="display: none;">
								<table width="100%" id="table_ic">
								<tr>
									<td align="center">
									<div id="addLine">
										<b><a href="javascript:add(1,'table_ic')"><img
											src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a></b>
										 </div>
									</td>
								</tr>
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
							<tr id="bex-2">
								<td>
								<div id="ExclusionTable" style="display: none;">
								<table width="100%" id="table_ec">
									<tr>
										<td align="center">
										<div id="addLine">
											<b><a href="javascript:add(2,'table_ec')"><img
												src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a></b>
											 </div>
										</td>
									</tr>
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
		   </table>
			</td>
		</tr>
	</table>
</form:form>
<!-- MAIN BODY ENDS HERE -->
</tabs:body>
</body>
</html>
