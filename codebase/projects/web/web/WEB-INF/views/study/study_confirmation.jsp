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
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
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
<!-- TOP LOGOS START HERE -->
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

		<td id="current"></td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>
		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						1.study details <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span> <span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						2.identifiers <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						3.study site <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						4. study design <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						5. review and submit <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="current"><img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle">
						6. confirmation <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"> </span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals2"><!-- RIGHT CONTENT STARTS HERE -->
				<form:form name="searchDetailsForm" method="post">
					<div><input type="hidden" name="_page" value="5"></div>
					<tr>
						<td id="current">Study Created Succesfully for Short Title :
						${command.trimmedShortTitleText} on Study Site : ${command.studySites[0].site.name}</td>
					</tr>
					<tr>

						<td class="display"><!-- TABS LEFT START HERE -->
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>

								<!-- LEFT CONTENT STARTS HERE -->
								<td valign="top" class="additionals2"><font color="Green"><!-- LEFT FORM STARTS HERE -->
								<!-- RIGHT CONTENT STARTS HERE --> <input type="hidden"
									name="nextView"> <strong>Please <a
									href="javascript:doNothing()">print</a> and save this
								confirmation in the study records </strong><br>
								<table width="50%" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td width ="20%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
													class="heightControl"></td>
											</tr>
											<tr>
												<td width="20%" class="label">Study Created on:</td>
												<td><%= edu.duke.cabig.c3pr.utils.DateUtil.getCurrentDate("mm/dd/yyyy")%></td>
											</tr>
											<tr>
												<td width="20%" class="label">Study Site:</td>
												<td>${command.studySites[0].site.name}</td>
											</tr>

											<tr>
												<td width="20%" class="label">Study Identifiers</td>
												<td ><c:forEach items="${command.identifiers}" var="id">
													${id.value},
												</c:forEach></td>
											</tr>

											<tr>
												<td class="label">Subject MRN Num:</td>
												<td>${command.primaryIdentifier}</td>
											</tr>

										</table>
										</td>
									</tr>
								</table>
								<hr align="left" width="95%">
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr align="center">
										<td colspan=2 valign="top"><br>
											<br>
											<input type="image" name="_finish" src="<tags:imageUrl name="b-search2.gif"/>" border="0"
												alt="go back to search">
										</td>
									</tr>
								</table>
								</font></td>

								<!-- LEFT CONTENT ENDS HERE -->
							</tr>
						</table>
						</td>
					</tr>
				</form:form> <!-- LEFT CONTENT ENDS HERE -->
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
