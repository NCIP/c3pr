<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3PR V2</title>
<link href="/c3pr/resources/styles.css" rel="stylesheet" type="text/css">
<link href="/c3pr/resources/search.css" rel="stylesheet" type="text/css">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="/c3pr/images/c3prLogo.gif" alt="C3PR" width="181"
			height="36" class="mainlogo"></td>
		<td>&nbsp;</td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">
	<tr valign="middle">

		<td width="99%" class="left">&nbsp;</td>
		<td class="right"><img src="/c3pr/images/topDivider.gif"
			width="2" height="20" align="absmiddle" class="divider">&nbsp;</td>
	</tr>
</table>
<!-- TOP NAVIGATION ENDS HERE -->
<!-- SUB NAV STARTS HERE -->
<form:form method="post">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="subNav">
		<c:if test="${!empty msg}">
			<tr>
				<td align="center"><span class="red">* ${msg }</span></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>

		</c:if>

		<tr valign="middle">
			<td>
			<table width="20%" align="center" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="label">Username:</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td class="label">Password:</td>
					<td><input name="password" type="password"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan=2 align="center"><input type="image"
						name="_target5" src="/c3pr/images/b-submit.gif" border="0"
						alt="submit"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>

	</table>
</form:form>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
