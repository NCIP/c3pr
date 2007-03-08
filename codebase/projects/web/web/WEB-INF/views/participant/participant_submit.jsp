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
function add(){
var action = confirm ("You have not completed adding this protocol.\r\rStarting over will lose current protocol data?")
if (action){
	parent.window.location="reg_enroll_patient.htm";
}}
function addPatient(){
var action = confirm ("Are you sure you want to create this participant?")
if (action){
	alert("Subject successfully created.");
	parent.window.location="participant_details.htm";
}}
</script>
</head>
<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

				<td id="current">Confirm Subject - ${command.firstName}
				${command.lastName}</td>
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
									src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
									height="16" align="absmiddle"> 1. <a href="participant_add.htm">Subject
								Information </a><img src="<tags:imageUrl name="tabGrayR.gif"/>"
									width="3" height="16" align="absmiddle"><img
									src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
									height="16" align="absmiddle"> 2. <a
									href="participant_address.htm">Address Information</a> <img
									src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
									height="16" align="absmiddle"></span><span class="current"></span><span
									class="current"><img src="<tags:imageUrl name="tabGrayL.gif"/>"
									width="3" height="16" align="absmiddle"> 3. Review and Submit <img
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
							<div><input type="hidden" name="_page" value="1"></div>
							<strong>Step 1. Subject Information </strong>
							<br>
							<table width="700" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
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
											<td class="label"><span class="red">*</span><em></em>First
											Name:</td>
											<td>${command.firstName}</td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>Last
											Name:</td>
											<td>${command.lastName}</td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>Gender:
											</td>
											<td>${command.administrativeGenderCode}</td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>Subject
											MRN:</td>
											<td>${command.primaryIdentifier }</td>
										</tr>
									</table>
									</td>
									<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
									<table width="308" border="0" cellspacing="0" cellpadding="0"
										id="table1">
										<tr>
											<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
											Date:</td>
											<td valign="top">${command.birthDateStr}</td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>Ethnicity:
											</td>
											<td>${command.ethnicGroupCode}</td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>Race(s):</td>
											<td>${command.raceCode}</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							<a href="reg_enroll_patient.htm"><img
								src="<tags:imageUrl name="b-edit.gif"/>" alt="Edit" width="39"
								height="16" border="0"></a>

							<hr align="left" width="95%">
							<strong><br>
							Step 2. Address Information </strong>
							<br>
							<br>
							<div class="review"><strong>Home Address:</strong><br>
							<table width="700" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
									<td width="50%" valign="top">
									<table width="308" border="0" cellspacing="0" cellpadding="0"
										id="table1">
										<tr>
											<td class="label"><em></em> Address:</td>
											<td>${command.address.streetAddress}</td>
										</tr>
										<tr>
											<td class="label"><em></em> City:</td>
											<td>${command.address.city}</td>
										</tr>
										<tr>
											<td class="label"><em></em> State:</span></td>
											<td>${command.address.stateCode}<em></em><strong>&nbsp;&nbsp;&nbsp;Zip:</strong>${command.address.postalCode}</td>
										</tr>
										<tr>
											<td class="label"><em></em><em></em> County:</td>
											<td>${command.address.countryCode}</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</div>
							<a href=""><img src="<tags:imageUrl name="b-edit.gif"/>" alt="Edit" width="39" height="16"
								border="0"></a>
							<hr align="left" width="95%">
							<strong><br>
								Step 3. Identifiers Information </strong>
								<br>
								<br>
								<div class="review"><br>
								<table width="50%" border="0" cellspacing="0" cellpadding="0"
									id="details">
								<tr>
									<td valign="top">
									<table width="100%" border="1" cellspacing="0" cellpadding="0"
										id="table1">
										<tr>
											<td width="20% align="left" class="label">Source</td>
											<td width="20%" align="left" class="label">Type</td>
											<td width="20%" align="left" class="label">Identifier</td>
										</tr>
										<c:forEach items="${command.identifiers}" var="identifier">
										<c:if test="${identifier.type!=''}">
											<tr class="results">
												<td>${identifier.source}</td>
												<td>${identifier.type}</td>
												<td>${identifier.value}</td>
											</tr>
										</c:if>
										</c:forEach>

									</table>
									</td>
								</tr>
								</table>
								</div>
								<br>
								<a href=""><img src="<tags:imageUrl name="b-edit.gif"/>" alt="Edit" width="39" height="16"
										border="0"></a>
							<table width="700" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
									<td align="center" colspan="3"><!-- action buttons begins -->
									<table cellpadding="4" cellspacing="0" border="0">
										<tr>
											<td><input type="image" name="_target1"
												src="<tags:imageUrl name="b-prev.gif"/>" border="0"
												alt="goto previous page"> <input type="image" name="_finish"
												src="<tags:imageUrl name="b-submit.gif"/>" border="0"
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
