<%@ include file="taglibs.jsp"%>

<html>
<head>
<title>Search Subject</title>
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
						<td><%--<img src="<tags:imageUrl name="Patient.gif"/>"
							alt="Subject Search" width="100" height="100" align="absmiddle">--%></td>
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
								<td align="right"><span class="label"><b>Search Criteria:</b></span></td>
								<td align="left"><form:input path="searchText" cssClass="value"/></td>
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
		<participanttags:searchResults url="viewParticipant" />
		<!-- MAIN BODY ENDS HERE -->
		</div>
	</form:form>
</chrome:search>
</body>
</html>
<!-- SUBJECT SEARCH ENDS HERE -->
