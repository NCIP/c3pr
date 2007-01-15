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
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3PR"
			width="181" height="36" class="gelogo"></td>
		
			
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
			href="/c3pr/searchparticipant.do">Subject</a><img
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
						2. Select Subject <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. Enrollment Details <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. Check Eligibility <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						5. Stratify <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 6. Randomize <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						7. Review and Submit <img src="images/tabGrayR.gif" width="3"
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
						<td width="99%" height="43" valign="middle" id="title">Subject
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
									<option selected>Subject</option>
								</select> by <select name="select" class="field1">
									<option selected>Subject Name</option>
									<option>Subject Registration#</option>
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

						<td id="current">Create Subject</td>
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
										<td width="100%" id="tabDisplay"><span class="current"><img
											src="images/tabWhiteL.gif" width="3" height="16"
											align="absmiddle"> 1. Assign Subject Id <img
											src="images/tabWhiteR.gif" width="3" height="16"
											align="absmiddle"></span><span class="tab"><img
											src="images/tabGrayL.gif" width="3" height="16"
											align="absmiddle"> 2. Subject Information <img
											src="images/tabGrayR.gif" width="3" height="16"
											align="absmiddle"><img src="images/tabGrayL.gif"
											width="3" height="16" align="absmiddle"> 3. Internal
										Contact Information <img src="images/tabGrayR.gif" width="3"
											height="16" align="absmiddle"><img
											src="images/tabGrayL.gif" width="3" height="16"
											align="absmiddle"> 4. Review and Submit <img
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
								<!-- RIGHT CONTENT STARTS HERE --> <strong>Step 1.
								Assign Subject Id </strong> (<span class="red">*</span><em>Required
								Information </em>)<br>
								<br>
								<form name="form1" method="post" action="" id="form1">
								<table width="550" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td width="103" class="label"><strong>&nbsp;<SPAN
											class=red>*</SPAN></strong>Fleet:</td>
										<td width="84"><strong> <input name="first"
											type="text" size="8"> </strong></td>
										<td width="77" class="label"><strong>&nbsp;<SPAN
											class=red>*</SPAN></strong>Sub:</td>
										<td width="286"><strong> <select name="select">
											<option>Sub List Values Go Here</option>
										</select> </strong></td>
									</tr>
									<tr>
										<td class="label">&nbsp;</td>
										<td>&nbsp;</td>
										<td class="label">&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="label">&nbsp;</td>
										<td colspan="3"><a href="patient_add2.htm"><img
											src="images/b-continue.gif" alt="Continue" width="59"
											height="16" border="0"></a></td>
									</tr>
								</table>
								<br>
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
<div id="copyright">
</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
