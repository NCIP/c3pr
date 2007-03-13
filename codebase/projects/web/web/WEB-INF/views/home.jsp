<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3PR V2</title>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function submitPage(s){
	document.getElementById("searchCategory").value=s;
	document.getElementById("searchForm").submit();
}
</script>
</head>
<body>

<!-- MAIN BODY STARTS HERE -->

<table width="100%" border="0" cellspacing="0" cellpadding="0" id="details">
	<form:form id="searchForm" name="searchForm" method="post">
		<form:hidden path="searchCategory" />		
		<tr>
			<td class="display"><!-- TABS LEFT START HERE -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="tabs">
						<tr>
							<td width="100%" id="tabDisplay"><span class="current"><img
								src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
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
					<!-- TABS LEFT END HERE --></td>
					<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2" height="1"></td>
					<td width="50%"><!-- TABS RIGHT START HERE -->
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="tabs">
						<tr>

							<td width="100%" id="tabDisplay"><span class="current"><img
								src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
								align="absmiddle"> Subject Search <img
								src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
								align="absmiddle"></span></td>
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
						</tr>
						<tr>
							<td colspan="2" class="tabBotR"><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="7"></td>
						</tr>
					</table>
					<!-- TABS RIGHT END HERE --></td>
				</tr>
				<tr>
					<!-- LEFT CONTENT STARTS HERE -->
					<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0"  id="table1">
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
									<a href="" onClick="submitPage('study');return false;"><img
										src="<tags:imageUrl name="b-search2.gif"/>" alt="Search" border="0"></a>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<!-- LEFT FORM ENDS HERE --></td>
					<!-- LEFT CONTENT ENDS HERE -->
					<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2" height="1"></td>
					<td valign="top" class="additionals2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr valign="top">
							<td><img src="<tags:imageUrl name="Patient.gif"/>" alt="Subject Search"
								width="100" height="100" align="absmiddle"></td>
							<td width="100%">
							<table width="50%"  border="0" cellspacing="5" cellpadding="0" id="table1">
								<tr>
									<td align="left" class="label">Search Subject By:</td>
									<td align="left">
									<form:select path="searchType">
										<form:options items="${partSearchTypeRefData}" itemLabel="desc" itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td align="left" class="label">Search Criteria: </td>
									<td align="left"><form:input path="searchTypePart" /></td>
								</tr>
								<tr>
									<td align="left" class="label">&nbsp;</td>
									<td align="left" class="label">&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td align="center">
									<a href="" onClick="submitPage('participant');return false;"><img
										src="<tags:imageUrl name="b-search2.gif"/>" alt="Search" border="0"></a>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</form:form>
</table>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
