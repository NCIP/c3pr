<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<style type="text/css">
     td .label { width: 12em; text-align: left; padding: 4px;}
</style>

<script language="JavaScript" type="text/JavaScript">
function fireAction(action, selected){
	document.getElementsByName('_target2')[0].name='_target1';
	document.studyIdentifiersForm._action.value=action;
	document.studyIdentifiersForm._selected.value=selected;
	document.studyIdentifiersForm.submit();
}
function clearField(field){
	field.value="";
}
</script>

</head>

<body>
<form:form name="studyIdentifiersForm" method="post">
<div>
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value=""></div>
</div>
<tabs:tabFields tab="${tab}" />
<div><tabs:division id="study-identifiers">
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td valign="top">
		<p id="instructions">
			Add Identifiers associated with the Study (<span class="red">*</span><em>Required Information </em>)<br>
		</p>

		<table width="65%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" >
					<table width="100%" border="0" cellspacing="5" cellpadding="0">
					<tr>
						<td align="left"><a href="javascript:fireAction('addIdentifier','0');"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Identifier"></a>
						</td>
						<td align="left"><b>Assigning Authority<span class="red">*</span></b></td>
						<td align="left"><b>Identifier Type<span class="red">*</span></b></td>
						<td align="left"><b>Identifier<span class="red">*</span></b></td>
						<td align="left"><b>Primary Indicator</b></td>
						<td align="left"></td></b>
					</tr>
					<c:forEach items="${command.identifiers}" varStatus="status">
						<tr>
							<td><a href="javascript:fireAction('removeIdentifier',${status.index});"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
							<td><form:select path="identifiers[${status.index}].source">
								<option value="">--Please Select--
								<form:options items="${identifiersSourceRefData}" itemLabel="name"
									itemValue="name" /></form:select></td>
							<td><form:select path="identifiers[${status.index}].type">
								<option value="">--Please Select--
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="desc" /></form:select></td>
							<td><form:input path="identifiers[${status.index}].value" onclick="javascript:clearField(this)();"/></td>
							<td><form:radiobutton path="identifiers[${status.index}].primaryIndicator" value="true"/></td>
							<td><em><span class="red"><form:errors path="identifiers[${status.index}].source"/>
								<form:errors path="identifiers[${status.index}].type"/>
								<form:errors path="identifiers[${status.index}].value"/>
								</em></span></td>
						</tr>
					</c:forEach>
					</table>
				</td>
		</table>
	</td>
</tr>
</table>
</tabs:division>
</form:form>
</table>
</div>
</body>
</html>