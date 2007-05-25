<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">
function doNothing(){
}
function validatePage(){
	if(document.getElementById("longTitleText") != null)
		return true;
	else
		return false;
}
</script>
</head>
<body>
<form:form method="post" cssClass="standard">
	<tabs:tabFields tab="${tab}" />
	<div><tabs:division id="study-details">
		<!-- MAIN BODY STARTS HERE -->
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right" colspan="2">
				<h3><font color="green">Study Created Successfully</font></h3>
				</td>
			</tr>
			<tr>
				<td align="right"><b>Short Title:&nbsp;</b></td>
				<td>${command.trimmedShortTitleText}</td>
			</tr>

			<tr>
				<td align="right"><b>Sponsor Study Identifier:&nbsp;</b></td>
				<td>${command.primaryIdentifier}</td>
			</tr>

		</table>
		<!-- MAIN BODY ENDS HERE -->
	</tabs:division></div>
</form:form>
</body>
</html>
