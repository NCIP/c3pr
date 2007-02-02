<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/portletUI" prefix="ui"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<html>
<head>
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>

<portlet:defineObjects />

<ui:form>
	<table bgcolor='white' width="100%" border="0" cellpadding="0"
		cellspacing="0">
		<tr valign="top">
			<td><img src="images/Protocol.gif" alt="Protocol Search"
				width="100" height="100" align="absmiddle"></td>
			<td width="99%">
			<h3>Patient Search</h3>
			<strong>1. Search Patients by:</strong> <select name="select">
				<option>Last Name</option>
			</select> <br>
			<br>
			<strong>2. Fill in the Fields:</strong><br>
			<br>
			<table border="0" cellspacing="0" cellpadding="0" id="search">
				<div id="foo">
				<tr>


					<td align="left" class="labels">Protocol Name:</td>
				</tr>
				<tr>
					<td><ui:textfield size="20" beanId="name" /></td>
				</tr>
				</div>
			</table>

			^ Minimum two characters for Protocol Name search.<br>
			<br>
			<ui:actionsubmit action="showResult" value="Search" />
		</tr>
	</table>
</ui:form>

</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
