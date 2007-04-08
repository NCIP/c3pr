<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}

</script>
</head>
<body>
<div><tabs:division id="enrollment-details">
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="60%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
		<form:form method="post">
		<tabs:tabFields tab="${tab}" />		
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="table1">
			<tr>
				<td class="label" align=left>The Selected Study does not have Stratification Factors</td>
			</tr>
		</table>
		</form:form>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</div>
</tabs:division>
</div>
</body>
</html>
