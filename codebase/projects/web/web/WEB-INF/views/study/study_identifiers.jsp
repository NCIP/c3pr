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
	document.getElementById('command').targetPage.name='_noname';
	document.form._action.value=action;
	document.form._selected.value=selected;
	
	// need to disable validations while removing
	source = 'identifiers['+selected+'].source';
	$(source).className='none';
	type = 'identifiers['+selected+'].type';
	$(type).className='none';
	id = 'identifiers['+selected+'].value';
	$(id).className='none';
	
	document.form.submit();
}
</script>
</head>
<body>
<form:form name="form" method="post">
<div>
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
</div>
<tabs:tabFields tab="${tab}" />
<tabs:division id="study-identifiers">
<!-- MAIN BODY STARTS HERE -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		<p id="instructions">
			Add Identifiers associated with the Study
			<a href="javascript:fireAction('addIdentifier','0');"><img
				src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Identifier"></a><br>
		</p>
		<table id="mytable" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th class="scope="col" align="left"><b>Assigning Authority<span class="red">*</span></b></th>
			<th scope="col" align="left"><b>Identifier Type<span class="red">*</span></b></th>
			<th scope="col" align="left"><b>Identifier<span class="red">*</span></b></th>
			<th scope="col" align="left"><b>Primary&nbsp;Indicator</b></td>
			<th class="specalt" scope="col" align="left"></th>
		</tr>
		<c:forEach items="${command.identifiers}" varStatus="status">
			<tr>
				<td class="alt"><form:select path="identifiers[${status.index}].source" cssClass="validate-notEmpty">
					<option value="">--Please Select--</option>
					<form:options items="${identifiersSourceRefData}" itemLabel="name"
						itemValue="name" /></form:select></th>
				<td class="alt"><form:select path="identifiers[${status.index}].type" cssClass="validate-notEmpty">
					<option value="">--Please Select--</option>
					<form:options items="${identifiersTypeRefData}" itemLabel="desc" itemValue="desc" /></form:select></td>
				<td class="alt"><form:input path="identifiers[${status.index}].value" cssClass="validate-notEmpty"/></td>
				<td class="alt"><form:radiobutton path="identifiers[${status.index}].primaryIndicator" value="true"/></td>
				<td class="tdalt"><a href="javascript:fireAction('removeIdentifier',${status.index});"><img
					src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
			</tr>
		</c:forEach>
		</table>
	</td>
</tr>
</table>
</tabs:division>
</table>
</form:form>
</body>
</html>