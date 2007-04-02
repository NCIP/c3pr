<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function submitPage(){
	document.getElementById("searchForm").submit();
}
</script>
</head>
<!-- MAIN BODY STARTS HERE -->
<body>
<tabs:body title="Search Study">
<form:form id="searchForm" name="searchForm" method="post">
<div><tabs:division id="study-details">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
		<table width="50%" border="0" cellpadding="0" cellspacing="0"  id="table1">
			<tr valign="top">
				<td width="10%"><img src="<tags:imageUrl name="Study.gif"/>" alt="Study Search"
					width="100" height="100" align="absmiddle"></td>
				<td width="90%">
				<table width="70%"  border="0" cellspacing="5" cellpadding="0" id="table1">
					<tr>
						<td width="20%" align="left" class="label">Search Studies By:</td>
						<td align="left" >
						<form:select path="searchType">
							<form:options items="${searchTypeRefData}" itemLabel="desc" itemValue="code" />
						</form:select></td>
					</tr>
					<tr>
						<td align="left" class="label">Search Criteria: </td>
						<td align="left"><form:input path="searchText" /></td>
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
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</tabs:division>
</form:form>
</tabs:body>
</table>
</body>
</html>