<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>

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
<tabs:body title="${flow.name}: ${tab.longTitle} - Short Title: ${command.trimmedShortTitleText}">
<form:form name="studyIdentifiersForm" method="post">
<div>
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value=""></div>
</div>
<tabs:tabFields tab="${tab}" />
<div><tabs:division id="study-identifiers">
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td valign="top">
		<br>
		<strong>Add Identifiers associated with the Study </strong> (<span class="red">*</span>
		<em>Required Information </em>)<br>
		<be>

		<table width="70%" border="0" cellspacing="0" cellpadding="0"
			id="details">
			<tr>
				<td width="100%" valign="top">
					<table width="50%" border="0" cellspacing="10" cellpadding="0"
						id="table1">
					<tr align="center" class="label">
						<td width="10%" align="center"></td>
						<td width="15%" align="center">Source<span class="red">*</span></td>
						<td width="15%" align="center">Type<span class="red">*</span></td>
						<td width="15%" align="center">Identifier<span class="red">*</span></td>
						<td width="15%" align="center">Primary Indicator</td>
						<td width="15%" align="center"></td>
					</tr>
					<c:forEach items="${command.identifiers}" varStatus="status">
						<tr align="center" class="results">
							<td width="10%"><a href="javascript:fireAction('removeIdentifier',${status.index});"><img
								src="<tags:imageUrl name="b-delete.gif"/>" border="0"></a></td>
							<td width="20%"><form:select path="identifiers[${status.index}].source">
								<option value="">--Please Select--
								<form:options items="${identifiersSourceRefData}" itemLabel="name"
									itemValue="name" /></form:select></td>
							<td width="20%"><form:select path="identifiers[${status.index}].type">
								<option value="">--Please Select--
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="desc" /></form:select></td>
							<td width="20%"><form:input path="identifiers[${status.index}].value" onclick="javascript:clearField(this)();"/></td>
							<td width="25%" aligh="center"><form:radiobutton path="identifiers[${status.index}].primaryIndicator" value="true"/></td>
							<td width="10%"><em><span class="red"><form:errors path="identifiers[${status.index}].source"/>
								<form:errors path="identifiers[${status.index}].type"/>
								<form:errors path="identifiers[${status.index}].value"/>
								</em></span></td>
						</tr>
					</c:forEach>
					<tr>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
						class="heightControl"></td>
					</tr>
					<tr>
						<td align="center"><a href="javascript:fireAction('addIdentifier','0');"><img
							src="<tags:imageUrl name="b-addLine.gif"/>" border="0" alt="Add another Identifier"></a>
						</td>
					</tr>
					</table>
				</td>
		</table>
	</td>
</tr>
</table>
</tabs:division>
</form:form>
</tabs:body>
</table>
</div>
</body>
</html>