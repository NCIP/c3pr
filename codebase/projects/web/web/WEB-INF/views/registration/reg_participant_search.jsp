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
function submitPage(s){
	document.getElementById("searchCategory").value=s;
	document.getElementById("searchForm").submit();
}
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function doNothing(){
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="display">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current">
						<img src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"> 1. Select Subject <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"> </span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						2. Select Study <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						3. Enrollment Details </a> <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						4. Check Eligibility <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						5. Stratify <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
							height="16" align="absmiddle"> 6. Review and Submit <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- MAIN BODY STARTS HERE -->
			<tr>
				<td>
				<div class="workArea">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="titleArea">
					<form:form id="searchForm" name="searchForm" method="post">
						<form:hidden path="searchCategory" />
						<tr>
							<!-- TITLE STARTS HERE -->
							<td width="99%" height="43" valign="middle" id="title"><a
								href="../participant/createParticipant?url=/c3pr/pages/registration/searchStudyInRegister">Create
							new Subject for Registration</a> or select a subject from below.</td>
							<td valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="search">
								<tr>
									<td class="labels">&nbsp;</td>
								</tr>
								<tr>
									<td class="searchType">Search Subject by <form:select
										path="searchTypePart">
										<form:options items="${partSearchTypeRefData}"
											itemLabel="desc" itemValue="code" />
									</form:select></td>
								</tr>
							</table>
							<span class="notation">&nbsp;</span></td>
							<td valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="search">
								<tr>
									<td align="left" class="labels">Search Criteria:</td>
									<td class="labels">&nbsp;</td>
								</tr>
								<tr>
									<td><form:input path="searchTypeTextPart" size="25" /></td>
									<td><input name="imageField" type="image" class="button"
										onClick="submitPage('participant');return false;"
										src="<tags:imageUrl name="b-go.gif"/>" alt="GO" align="middle" width="22"
										height="10" border="0"></td>
								</tr>
							</table>
							</td>
						</tr>
					</form:form>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
						<td id="current">Subject Search Results</td>
						<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
					</tr>
					<tr>
						<td class="display"><!-- TABS LEFT START HERE -->
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>

								<td valign="top" class="additionals">
								<br>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									id="additionalList">
									<tr align="center" class="label">
										<td>Last Name, First Name</td>
										<td>Primary Identifier</td>
										<td>Gender</td>
										<td>Race</td>
										<td>Birth Date</td>
										<td></td>
									</tr>
									<%
									int i = 0;%>
									<c:forEach items="${participants}" var="part">
										<tr align="center" id="row<%= i++ %>" class="results" onMouseOver="navRollOver('row<%= i-1 %>', 'on')"
											onMouseOut="navRollOver('row<%= i-1 %>', 'off')"
											onClick="document.location='searchStudyInRegister?participantId=${part.id}'">
											<td>${part.lastName},${part.firstName}</td>
											<td>${part.primaryIdentifier}</td>
											<td>${part.administrativeGenderCode}</td>
											<td>${part.raceCode}</td>
											<td>${part.birthDateStr}</td>
										</a>
										</tr>
									</c:forEach>
								</table>
								<br>
								<!-- LEFT FORM ENDS HERE --></td>
								<!-- LEFT CONTENT ENDS HERE -->
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
</table>
<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
