<%@ include file="taglibs.jsp"%>

<html>
<head>
<title>Search Subject</title>
</head>
<body>
<chrome:search title="Search">
 <tags:instructions code="participant_search" />
	<form:form id="searchForm" name="searchForm" method="post">
				<table width="50%" border="0" cellpadding="0" cellspacing="0"
					id="table1">
					<tr valign="top">
						<td>
							<%--<img src="<tags:imageUrl name="Patient.gif"/>" alt="Subject Search" width="100" height="100" align="absmiddle">--%>
						</td>
						<td width="85%">
						<table width="100%" border="0" cellspacing="5" cellpadding="0" id="table1">
							<tr>
								<td width="30%" align="right" class="label">
									<b><fmt:message key="c3pr.common.searchBy"/></b>
								</td>
								<td align="left">
									<form:select path="searchType">
										<form:options items="${searchTypeRefData}" itemLabel="desc" itemValue="code" />
									</form:select>
								</td>
							</tr>
							<tr>
								<td align="right">
									<span class="label">
										<b><fmt:message key="c3pr.common.searchCriteria"/></b>
									</span>
								</td>
								<td align="left">
									<form:input path="searchText" cssClass="value"/>
								</td>
							</tr>
							<tr>
								<td></td>
								<td align="left">
									<tags:button type="submit" icon="search" size="small" color="blue" value="Search"/>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
		<participanttags:searchResults url="viewParticipant" />
		<!-- MAIN BODY ENDS HERE -->
	</form:form>
</chrome:search>
</body>
</html>
<!-- SUBJECT SEARCH ENDS HERE -->
