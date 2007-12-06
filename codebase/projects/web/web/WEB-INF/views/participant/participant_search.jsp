<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="participantTags" tagdir="/WEB-INF/tags/participant"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

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
<!-- SUBJECT SEARCH STARTS HERE -->
<chrome:search title="Search">
	<form:form id="searchForm" name="searchForm" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="top">
				<table width="50%" border="0" cellpadding="0" cellspacing="0"
					id="table1">
					<tr valign="top">
						<td width="15%"><img src="<tags:imageUrl name="Patient.gif"/>"
							alt="Subject Search" width="100" height="100" align="absmiddle"></td>
						<td width="85%">
						<table width="100%" border="0" cellspacing="5" cellpadding="0"
							id="table1">
							<tr>
								<td width="30%" align="right" class="label"><b>Search By:</b></td>
								<td align="left"><form:select path="searchType">
									<form:options items="${searchTypeRefData}" itemLabel="desc"
										itemValue="code" />
								</form:select></td>
							</tr>
							<tr>
								<td align="right"><span class="required-indicator"><b>Search Criteria:</b></span></td>
								<td align="left"><form:input path="searchText" cssClass="validate-notEmpty"/></td>
							</tr>
							<tr>
								<td></td>
								<td align="left"><input type="button" value="Search" onClick="submitPage();return false;">
									</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<br>
		<br>
		<br>
		<participantTags:searchResults url="viewParticipant" />
		<!-- MAIN BODY ENDS HERE -->
		</div>
	</form:form>
</chrome:search>
</body>
</html>
<!-- SUBJECT SEARCH ENDS HERE -->
