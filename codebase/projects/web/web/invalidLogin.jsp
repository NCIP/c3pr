<html>
<head>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>

<!-- SUB NAV STARTS HERE -->
<form name="loginForm" method="post" action="success.jsp">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="subNav">
			<tr>
				<td align="center"><span class="red">* Invalid Login. Please Try Again.</span></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>

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
