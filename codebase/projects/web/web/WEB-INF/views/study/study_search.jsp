<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3Pr V2</title>
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function submitPage(){
	document.getElementById("searchForm").submit();
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>

		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="<tags:imageUrl name="tabWhiteL.gif"/>" width="3" height="16"
							align="absmiddle"> Study Search <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>

				</table>
				</td>
			</tr>
			<tr>
				<!-- LEFT CONTENT STARTS HERE -->
				<td valign="top" class="additionals">
				<form:form id="searchForm" name="searchForm" method="post">

				<table width="50%" border="0" cellpadding="0" cellspacing="0"  id="table1">
					<tr valign="top">
						<td><img src="<tags:imageUrl name="Study.gif"/>" alt="Study Search"
							width="100" height="100" align="absmiddle"></td>
						<td width="100%">
						<table width="50%"  border="0" cellspacing="5" cellpadding="0" id="table1">
							<tr>
								<td align="left" class="label">Search Studies By:</td>
								<td align="left" >
								<form:select path="searchType">
									<form:options items="${studySearchTypeRefData}" itemLabel="desc" itemValue="code" />
								</form:select></td>
							</tr>
							<tr>
								<td align="left" class="label">Search Criteria: </td>
								<td align="left"><form:input path="searchTypeText" /></td>
							</tr>
							<tr>
								<td align="left" class="label">&nbsp;</td>
								<td align="left" class="label">&nbsp;</td>
							</tr>
							<tr>
								<td></td>
								<td align="center">
								<a href="" onClick="submitPage();return false;"><img
									src="<tags:imageUrl name="b-search2.gif"/>" alt="Continue" width="59"
									height="16" border="0"></a>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<!-- LEFT FORM ENDS HERE --></td>
			</tr>
		</table>
		</form:form>

		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
