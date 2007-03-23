<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<title>${tab.longTitle}</title>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}

function fireAction(action, selected){
	document.getElementsByName('_target2')[0].name='_target1';
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form.submit();
}
function clearField(field){
field.value="";
}

</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->

<tags:searchStudy action="searchStudy"/>

<tabs:body title="Edit Study">
<form:form method="post" id="form" name="form">
	<table border="0" id="table1" cellspacing="10" width="100%">
		<tr>
		 <td valign="top" width="30%">
			<studyTags:studySummary />
		</td>
		<td width="40%" valign="top">
		<tabs:levelTwoTabs tab="${tab}" flow="${flow}"/>
			<tabs:division id="Editing">
				<table width="70%" border="0" cellspacing="0" cellpadding="0" id="details">
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
										src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
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
									src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Identifier"></a>
								</td>
							</tr>
							</table>
						</td>
					</table>
			</tabs:division>
		</td>
		<td valign="top" width="30%">
			<studyTags:subjectAssignments />
		</td>
		</tr>
	</table>
</form:form>
</tabs:body>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
