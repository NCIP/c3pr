<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
var action = confirm ("Are you sure you want to create this patient?")
if (action){
	alert("Patient successfully created.");
	parent.window.location="reg_enroll_patient.htm";
}}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3PR" width="181"
			height="36" class="gelogo"></td>
		<td align="right"><img src="images/t-drivers.gif" alt="Study"
			width="200" height="79"></td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">

	<tr valign="middle">
		<td width="99%" class="left"><img src="images/topNavL.gif"
			width="2" height="20" align="absmiddle" class="currentL"><span
			class="current"><img src="images/topNavArrowDown.gif"
			width="5" height="20" align="absmiddle"> Registration</span><a
			href="protocol.htm"> Study </a><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="/c3pr/searchparticipant.do">Participant</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"><a href="analysis">Reports</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"></td>

		<td class="right"><img src="images/topDivider.gif" width="2"
			height="20" align="absmiddle" class="divider"><a href="logOff">Log
		Off</a></td>
	</tr>
</table>
<!-- TOP NAVIGATION ENDS HERE -->
<!-- SUB NAV STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="subNav">
	<tr>
		<td width="99%" valign="middle" class="welcome">Welcome, User
		Name</td>
		<td valign="middle" class="right"><a href="help">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="display">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"> <img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle"> 1. <a href="reg_protocol_search.jsp">Select
						Study </a><img src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						2. Enroll Patient <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. Check Eligibility <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. Stratify <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 3. Randomize <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						5. Review and Submit <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span></td>
						<td><img src="images/spacer.gif" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="images/spacer.gif"
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
					<tr>
						<!-- TITLE STARTS HERE -->
						<td width="99%" height="43" valign="middle" id="title">Patient
						Search</td>
						<td valign="top">
						<form method="post" action="" name="searchMeth" class="search">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="search">
							<tr>
								<td class="labels">&nbsp;</td>
							</tr>
							<tr>
								<td class="searchType">Search <select name="select"
									class="field1">
									<option selected>Patient</option>
								</select> by <select name="select" class="field1">
									<option selected>Patient Name</option>
									<option>Patient Registration#</option>
								</select></td>
							</tr>
						</table>
						</form>
						<span class="notation">&nbsp;</span></td>
						<td valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="search">
							<tr>
								<td align="left" class="labels">Last Name:</td>
								<td align="left" class="labels">First Name:</td>
								<td class="labels">&nbsp;</td>
							</tr>
							<tr>
								<td><input name="textfield2" type="text" class="field1"
									size="25"></td>
								<td><input name="textfield3" type="text" class="field1"
									size="25"></td>
								<td><input name="imageField" type="image" class="button"
									onClick="getPage('search.htm')" src="images/b-go.gif" alt="GO"
									align="middle" width="22" height="10" border="0"></td>
							</tr>
						</table>
						<span class="notation">^ Minimum two characters for Last
						Name search.</span></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

						<td id="current">Confirm Patient - John Smith</td>
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
											src="images/tabGrayL.gif" width="3" height="16"
											align="absmiddle"> 1. <a href="reg_enroll_patient.htm">Patient
										Information </a><img src="images/tabGrayR.gif" width="3"
											height="16" align="absmiddle"><img
											src="images/tabGrayL.gif" width="3" height="16"
											align="absmiddle"> 2. <a href="reg_enroll_patient_address.htm">Address Information</a> <img
											src="images/tabGrayR.gif" width="3" height="16"
											align="absmiddle"></span><span class="current"></span><span class="current"><img
											src="images/tabGrayL.gif" width="3" height="16"
											align="absmiddle"> 3. Review and Submit <img
											src="images/tabGrayR.gif" width="3" height="16"
											align="absmiddle"></span></td>
										<td><img src="images/spacer.gif" width="7" height="1"></td>
									</tr>
									<tr>
										<td colspan="2" class="tabBotL"><img
											src="images/spacer.gif" width="1" height="7"></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>

								<!-- LEFT CONTENT STARTS HERE -->
								<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
								<!-- RIGHT CONTENT STARTS HERE -->

								<form id="form1"><strong>Step 1. Patient
								Information </strong><br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl"></td>
												<td><img src="images/spacer.gif" width="1"
													height="1" class="heightControl"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>First
												Name:</td>
												<td>John</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Last
												Name:</td>
												<td>Smith</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Middle
												Name:</td>
												<td>symond&nbsp;&nbsp;Suffix: Jr</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Gender:
												</td>
												<td>Not Reported</td>
											</tr>
											<tr>
												<td width="46%" class="label"><span class="red">*</span><em></em>Primary Id:</td>
												<td width="54%">1234567XYZ</td>
											</tr>
											<tr>
												<td width="46%" class="label"><span class="red">*</span><em></em>Primary Id Source:</td>
												<td width="54%">NCI-Frederick / MD066</td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><span class="red">*</span><em></em>Social
												Security Number:</td>
												<td>111-22-3333</td>
											</tr>
											<tr>
												<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
												Date:</td>
												<td valign="top">01/01/1981</td>
											</tr>
											<tr>
												<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>First
												Visit Date:</td>
												<td valign="top">01/01/2001</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Ethnicity:
												</td>
												<td>Not Hispanic or Latino</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Race(s):</td>
												<td>Not Reported</td>
											</tr>											
										</table>
										</td>
									</tr>
								</table>
								<a href="reg_enroll_patient.htm"><img src="images/b-edit.gif"
									alt="Edit" width="39" height="16" border="0"></a>
								
								<hr align="left" width="95%">
								<strong><br>Step 2. Address
								Information </strong> <br><br>
								<div class="review"><strong>Home Address:</strong><br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><em></em>
												Address:</td>
												<td>xyc, route 23, yyy</td>
											</tr>
											<tr>
												<td class="label"><em></em>
												City:</td>
												<td>XXYYZZ</td>
											</tr>
											<tr>
												<td class="label"><em></em> State:</span></td>
												<td>NY<em></em><strong>&nbsp;&nbsp;&nbsp;Zip:</strong>12345</td>
											</tr>
											<tr>
												<td class="label"><em></em><em></em>
												County:</td>
												<td>USA</td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><em></em>Email
												Address:</td>
												<td>abc@xyz.com</td>
											</tr>
											<tr>
												<td class="label"><em></em>Work
												Phone:</td>
												<td>102-123-1111</td>
											</tr>
											<tr>
												<td class="label">Cell Phone:</td>
												<td></td>
											</tr>
											<tr>
												<td class="label">Home Phone:</td>
												<td></td>
											</tr>
											<tr>
												<td class="label">Fax:</td>
												<td></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>

								<strong>Hospital Address:<br>
								</strong>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><em></em>
												Address:</td>
												<td>xyc, route 23, yyy</td>
											</tr>
											<tr>
												<td class="label"><em></em>
												City:</td>
												<td>XXYYZZ</td>
											</tr>
											<tr>
												<td class="label"><em></em> State:</span></td>
												<td>NY<em></em><strong>&nbsp;&nbsp;&nbsp;Zip:</strong>12345</td>
											</tr>
											<tr>
												<td class="label"><em></em><em></em>
												County:</td>
												<td>USA</td>
											</tr>
											<tr>
												<td class="label"><em></em><em></em>
												Phone:</td>
												<td>123-456-7890</td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
										</table>
										</td>
									</tr>
								</table>
								<br>
								<strong>Preffered Address: Home<br>
								</strong><br>
								<a href="reg_enroll_patient_address.htm"><img src="images/b-edit.gif"
									alt="Edit" width="39" height="16" border="0"></a>
								
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr align="center">
										<td colspan=2 valign="top"><br>
										<br>
										<a href="reg_enroll_patient_submit.htm" onClick="addPatient();return false;"><img src="images/b-submit.gif"
											alt="Continue" width="59" height="16" border="0"></a> <a
											href="/c3pr/" onClick="add();return false;"><img
											src="images/b-startOver.gif" alt="Start Over" width="67"
											height="16" border="0"></a></td>
									</tr>
								</table>

								</div>
								</form>
								</td>

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
<div id="copyright">&copy; 2006 SemanticBits Company. All Rights
Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
