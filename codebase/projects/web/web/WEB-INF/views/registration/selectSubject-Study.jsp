<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3PR V2</title>
<script type="text/javascript" src="<c:url value="/dwr/engine.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/util.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function submitPage(s){
//	document.getElementById("searchCategory").value=s;
//	document.getElementById(s).submit();
	
}
new Ajax.Autocompleter("autocomplete", "autocomplete_choices", "/url/on/server", {});
</script>
</head>
<body>

<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="1" cellpadding="0">
	<tr>
		<td width="50%"><tabs:body title="Search Study">
			<form id="searchStudyForm" name="searchStudyForm" method="post"
				action="searchStudy?inRegistration=true">
				<input type="hidden" name="isAjaxRequest" />
			<div><tabs:division id="study-details">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">
						<table width="90%" border="0" cellpadding="0" cellspacing="0"
							id="table1">
							<tr valign="top">
								<td width="10%"><img
									src="<tags:imageUrl name="Study.gif"/>" alt="Study Search"
									width="100" height="100" align="absmiddle"></td>
								<td width="90%">
								<table width="70%" border="0" cellspacing="5" cellpadding="0"
									id="table1">
									<tr>
										<td width="20%" align="left" class="label">Search Studies
										By:</td>
										<td align="left"><select id="searchType"
											name="searchType">
											<c:forEach items="${searchTypeRefDataStudy}"
												var="searchTypePrt">
												<option value="${searchTypePrt.code }">${searchTypePrt.desc
												}</option>
											</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="left" class="label">Search Criteria:</td>
										<td align="left"><input type="text" name="searchText" /></td>
									</tr>
									<tr>
										<td align="left" class="label">&nbsp;</td>
										<td align="left" class="label">&nbsp;</td>
									</tr>
									<tr>
										<td></td>
										<td align="center"><a href=""
											onClick="submitPage('searchStudyForm');return false;"><img
											src="<tags:imageUrl name="b-search2.gif"/>" alt="Continue"
											width="59" height="16" border="0"></a></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<!-- MAIN BODY ENDS HERE -->
			</tabs:division></div>
			</form>
		</tabs:body></td>
		<td><tabs:body title="Search Subject">
			<form id="searchSubjectForm" name="searchSubjectForm" method="post"
				action="searchParticipant?inRegistration=true">
				<input type="hidden" name="isAjaxRequest" />
			<div><tabs:division id="study-details">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">
						<table width="90%" border="0" cellpadding="0" cellspacing="0"
							id="table1">
							<tr valign="top">
								<td width="10%"><img
									src="<tags:imageUrl name="Patient.gif"/>" alt="Study Search"
									width="100" height="100" align="absmiddle"></td>
								<td width="90%">
								<table width="70%" border="0" cellspacing="5" cellpadding="0"
									id="table1">
									<tr>
										<td width="20%" align="left" class="label">Search Subjects
										By:</td>
										<td align="left"><select id="searchType"
											name="searchType">
											<c:forEach items="${searchTypeRefDataPrt}"
												var="searchTypePrt">
												<option value="${searchTypePrt.code }">${searchTypePrt.desc	}</option>
											</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="left" class="label">Search Criteria:</td>
										<td align="left"><input type="text" name="searchText" /></td>
									</tr>
									<tr>
										<td align="left" class="label">&nbsp;</td>
										<td align="left" class="label">&nbsp;</td>
									</tr>
									<tr>
										<td></td>
										<td align="center"><a href=""
											onClick="submitPage('searchSubjectForm');return false;"><img
											src="<tags:imageUrl name="b-search2.gif"/>" alt="Continue"
											width="59" height="16" border="0"></a></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<!-- MAIN BODY ENDS HERE -->
			</tabs:division></div>
			</form>
		</tabs:body></td>
	</tr>
	<tr>
		<td>
			<div id="studySearchResults">
			</div>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
