<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
</script>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
								var cal1 = new CalendarPopup();
							</script>
</head>
<body>
<div class="workArea">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>

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
									src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
									height="16" align="absmiddle"> 1. Subject Information <img
									src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
									height="16" align="absmiddle"></span><span class="tab"><img
									src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
									height="16" align="absmiddle"> 2. Address Information <img
									src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
									height="16" align="absmiddle"><img
									src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
									height="16" align="absmiddle"> 3. Review and Submit <img
									src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
									height="16" align="absmiddle"></span></td>
								<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7"
									height="1"></td>
							</tr>
							<tr>
								<td colspan="2" class="tabBotL"><img
									src="<tags:imageUrl name="spacer.gif"/>" width="1" height="7"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>

						<!-- LEFT CONTENT STARTS HERE -->
						<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
						<!-- RIGHT CONTENT STARTS HERE --> <form:form method="post"
							action="createParticipant">
							<div><input type="hidden" name="_page" value="0"></div>
							<strong>Step 1. Subject Information </strong>
							(<span class="red">*</span>
							<em>Required Information </em>)<br>
							<br>
							<div class="review"><strong>Current Information:</strong>

							<table width="700" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
									<td width="50%" valign="top">
									<table width="308" border="0" cellspacing="0" cellpadding="0"
										id="table1">
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
											<td width="65%"><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em> First
											Name</td>
											<td><form:input path="firstName" /></td>
											<td width="10%"><span class="red"><form:errors
												path="firstName" /><em></em></span></td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em> Last
											Name</td>
											<td><form:input path="lastName" /></td>
											<td width="10%"><span class="red"><form:errors
												path="lastName" /><em></em></span></td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span> <em></em> Gender</td>
											<td><form:select path="administrativeGenderCode">
												<form:options items="${administrativeGenderCode}"
													itemLabel="desc" itemValue="code" />
											</form:select></td>
											<td width="10%"><span class="red"><form:errors
												path="administrativeGenderCode" /><em></em></span></td>
										</tr>
									</table>
									</td>
									<td width="50%" valign="top">
									<table width="308" border="0" cellspacing="0" cellpadding="0"
										id="table1">
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
										</tr>
										<tr>
											<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
											Date</td>
											<td valign="top"><form:input path="birthDate" />&nbsp;<a
												href="#"
												onClick="cal1.select(document.getElementById('birthDate'),'anchor1','MM/dd/yyyy');return false;"
												name="anchor1" id="anchor1"><img
												src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar"
												width="17" height="16" border="0" align="absmiddle"></a></td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>Ethnic
											Group Code</td>
											<td><form:select path="ethnicGroupCode">
												<form:options items="${ethnicGroupCode}" itemLabel="desc"
													itemValue="code" />
											</form:select></td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>Race
											Code</td>
											<td><form:select path="raceCode">
												<form:options items="${raceCode}" itemLabel="desc"
													itemValue="code" />
											</form:select></td>
										</tr>

									</table>
									</td>
								</tr>
							</table>

							<hr align="left" width="95%">
							<table width="700" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td align="center"><span class="red">*</span><em></em><B> Type:</td>
									<td align="center"><span class="red">*</span><em></em><B>
									Value:</td>
									<td align="center"><span class="red">*</span><em></em><B>
									Source:</td>
									<td align="center"><B>Primary:</td>
								</tr>

								<c:forEach var="index" begin="0" end="4">
									<tr>
										<td align="center"><form:select
											path="identifiers[${index}].type">
											<form:options items="${identifiersTypeRefData}"
												itemLabel="desc" itemValue="code" />
										</form:select></td>
										<td align="center"><form:input
											path="identifiers[${index}].value" /></td>
										<td align="center"><form:select
											path="identifiers[${index}].source">
											<form:options items="${source}" itemLabel="name"
												itemValue="name" />
										</form:select></td>

										<td align="center"><form:radiobutton
											path="identifiers[${index}].primaryIndicator" value="true" /></td>
									</tr>

								</c:forEach>

							</table>

							<hr align="left" width="95%">
							<table width="700" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr align="center">
									<td colspan="4"><br>
									<br>
								<tr>
									<td align="center" colspan="3"><!-- action buttons begins -->
									<table cellpadding="4" cellspacing="0" border="0">
										<tr>
											<td><input type="image" name="_target1"
												src="<tags:imageUrl name="b-continue.gif"/>" border="0"
												alt="continue to next page"></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</div>
						</form:form></td>

						<!-- LEFT CONTENT ENDS HERE -->
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<br>
		</form>
		</td>
		<!-- LEFT CONTENT ENDS HERE -->
	</tr>
</table>

<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
