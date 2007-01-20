<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
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
						3. Enrollment Details </a> <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. Check Eligibility <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						5. Stratify <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 6. Review and Submit <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"></span></td>
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
						<td width="99%" height="43" valign="middle" id="title"><a
							href="reg_enroll_patient_submit.htm">Select this patient</a></td>
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
						<td id="current">John Smith</td>
						<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
					</tr>
					<tr>
						<td class="display"><!-- TABS LEFT START HERE -->
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="30%">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="tabs">
									<tr>
										<td width="100%" id="tabDisplay"><span class="current"><img
											src="images/tabWhiteL.gif" width="3" height="16"
											align="absmiddle"> Subject Summary <img
											src="images/tabWhiteR.gif" width="3" height="16"
											align="absmiddle"></span></td>
										<td><img src="images/spacer.gif" width="7" height="1"></td>
									</tr>
									<tr>
										<td colspan="2" class="tabBotL"><img
											src="images/spacer.gif" width="1" height="7"></td>
									</tr>
								</table>
								<!-- TABS LEFT END HERE --></td>
								<td><img src="images/spacer.gif" width="2" height="1"></td>
								<td width="70%"><!-- TABS RIGHT START HERE -->
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="tabs">
									<tr>
										<td width="100%" id="tabDisplay"><span class="current"><img
											src="images/tabWhiteL.gif" width="3" height="16"
											align="absmiddle"> Details <img
											src="images/tabWhiteR.gif" width="3" height="16"
											align="absmiddle"></span><span class="tab"><img
											src="images/tabGrayL.gif" width="3" height="16"
											align="absmiddle"> <a href="driver_fields.htm">Disease
										Association</a> <img src="images/tabGrayR.gif" width="3"
											height="16" align="absmiddle"></span></td>
										<td><img src="images/spacer.gif" width="7" height="1"></td>
									</tr>
									<tr>
										<td colspan="2" class="tabBotR"><img
											src="images/spacer.gif" width="1" height="7"></td>
									</tr>
								</table>
								<!-- TABS RIGHT END HERE --></td>
							</tr>
							<tr>
								<td valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr valign="top">
										<td><img src="images/spacer.gif" width="1" height="1"
											class="heightControl"></td>
										<td width="75%"><img src="images/spacer.gif" width="1"
											height="1" class="heightControl"></td>
									</tr>
									<tr align="center" valign="top">
										<td colspan="2"><strong>First:</strong> John
										&nbsp;&nbsp;&nbsp;<strong>MI:</strong> -&nbsp;&nbsp;&nbsp;<strong>Last:</strong>
										Smith</td>
									</tr>
									<tr valign="top">
										<td><img src="images/spacer.gif" width="1" height="1"
											class="heightControl"></td>
										<td width="75%"><img src="images/spacer.gif" width="1"
											height="1" class="heightControl"></td>
									</tr>
									<tr valign="top">
										<td><img src="images/spacer.gif" width="1" height="1"
											class="heightControl"></td>
										<td width="75%"><img src="images/spacer.gif" width="1"
											height="1" class="heightControl"></td>
									</tr>
									<tr valign="top">
										<td class="label"><span class="red">*</span><em></em>Gender:
										</td>
										<td>Not Reported</td>
									</tr>
									<tr valign="top">
										<td class="label"><span class="red">*</span><em></em>Primary
										Id:</td>
										<td>1234567XYZ</td>
									</tr>
									<tr valign="top">
										<td class="label"><span class="red">*</span><em></em>Primary
										Id Source:</td>
										<td>NCI-Frederick / MD066NCI-Frederick /</td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>SSN:</td>
										<td>111-22-3333</td>
									</tr>
									<tr>
										<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
										Date:</td>
										<td valign="top">01/01/1981</td>
									</tr>
									<tr>
										<td class="label"><em></em>First Visit Date:</td>
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
									<tr>
										<td class="label"><em></em> Address:</td>
										<td>xyc, route 23, yyy</td>
									</tr>
									<tr>
										<td class="label"><em></em> City:</td>
										<td>XXYYZZ</td>
									</tr>
									<tr>
										<td class="label"><em></em> State:</span></td>
										<td>NY<em></em><strong>&nbsp;&nbsp;&nbsp;Zip:</strong>12345</td>
									</tr>
									<tr>
										<td class="label"><em></em><em></em> County:</td>
										<td>USA</td>
									</tr>
								</table>
								<!-- LEFT CONTENT ENDS HERE --></td>
								<td><img src="images/spacer.gif" width="2" height="1"></td>
								<!-- RIGHT CONTENT STARTS HERE -->
								<td valign="top" class="contentR">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top" class="contentAreaL"><strong>Subject
										Details </strong>(<span class="red">*</span><em>Required
										Information </em>)<br>
										<br>
										<form name="form2" method="post" action="" id="form1">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl"></td>
												<td width="65%"><img src="images/spacer.gif" width="1"
													height="1" class="heightControl"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>First
												Name:</td>
												<td><input name="driverfield1" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Last
												Name:</td>
												<td><input name="driverfield1" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Middle
												Name:</td>
												<td><input name="driverfield1" type="text" size="15">
												&nbsp;&nbsp;Suffix <select name="select" class="field1">
													<option selected>--</option>
													<option>I</option>
													<option>II</option>
													<option>III</option>
													<option>Jr</option>
													<option>Sr</option>
												</select></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Gender:
												</td>
												<td><select name="select" class="field1">
													<option selected>--</option>
													<option>Male</option>
													<option>Female</option>
													<option>Not Reported</option>
													<option>Unknown</option>
												</select></td>
											</tr>
											<tr>
												<td width="46%" class="label"><span class="red">*</span><em></em>Social
												Security Number:</td>
												<td width="54%"><input name="driverfield1" type="text"
													size="11"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
												Date:</td>
												<td valign="top"><input name="driverfield3" type="text"
													size="10">&nbsp;<a href="#"
													onClick="parent.OpenWins('calendar.htm','calendar',200,236);return false;"><img
													src="images/b-calendar.gif" alt="Calendar" width="17"
													height="16" border="0" align="absmiddle"></a></td>
											</tr>
											<tr>
												<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>First
												Visit Date:</td>
												<td valign="top"><input name="driverfield3" type="text"
													size="10">&nbsp;<a href="#"
													onClick="parent.OpenWins('calendar.htm','calendar',200,236);return false;"><img
													src="images/b-calendar.gif" alt="Calendar" width="17"
													height="16" border="0" align="absmiddle"></a></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Ethnicity
												</td>
												<td><select name="select" class="field1">
													<option selected>--</option>
													<option>Hispanic or Latino</option>
													<option>Not Hispanic or Latino</option>
													<option>Not Reported</option>
													<option>Unknown</option>
												</select></td>
											<tr>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl  style1"></td>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl"></td>
											</tr>
											<tr>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl  style1"></td>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl"></td>
											</tr>

											<tr align="center">
												<td colspan="2"><a href="#"
													onClick="parent.OpenWins('saveDriver.htm','save',350,175);return false;"><img
													src="images/b-save.gif" width="45" height="16" border="0"></a>
												<a href="#"><img src="images/b-cancel.gif" alt="Cancel"
													width="54" height="16" border="0"></a></td>
											</tr>
										</table>
										</form>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong><a
											href="#additional"><img
											src="images/ViewregistrationHistory.gif"
											alt="View Additional Vehicles" width="140" height="16"
											border="0" align="right"></a></strong></strong>Current Registration</strong><br>
										<br>
										<table width="315" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><span class="red">*</span><em></em>Short
												Title:</td>
												<td width="75%" valign="top">CALGB_XYZ</td>
											</tr>
											<tr>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl"></td>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Status:</td>
												<td>active</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em><strong>Disease
												Code:</strong>
												<td valign="top">ABC</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>NCI
												Identifier:</td>
												<td>NCI_IDENTIFIER</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Navy
												NCI Idententifier:</td>
												<td>NAVY_NCI_IDENTIFIER</td>
											</tr>
											<tr>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl "></td>
												<td><img src="images/spacer.gif" width="1" height="1"
													class="heightControl"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Long
												Title:</td>
												<td>CALGB_Long_Title</td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Phase
												code:</td>
												<td>Phase_Code</td>
											</tr>
											<tr>
												<td class="label">Amendment Date:</td>
												<td>02/05/2006</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
								<!-- RIGHT CONTENT ENDS HERE -->
							</tr>
						</table>
						<!-- BOTTOM LIST STARTS HERE --> <a name="additional"></a>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="tabs">
							<tr>
								<td width="100%" id="tabDisplay"><span class="current"><img
									src="images/tabWhiteL.gif" width="3" height="16"
									align="absmiddle"> Registration History <img
									src="images/tabWhiteR.gif" width="3" height="16"
									align="absmiddle"></span></td>
								<td width="7"><img src="images/spacer.gif" width="7"
									height="1"></td>
							</tr>
							<tr>
								<td colspan="2" class="tabBotL"><img
									src="images/spacer.gif" width="1" height="7"></td>
							</tr>
							<tr>
								<td colspan="2" valign="top" class="additionals">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%" align="right"><a href="#"><img
											src="images/b-prev.gif" alt="Previous" width="41" height="16"
											border="0" align="absmiddle"></a></td>
										<td width="20%" align="center"><strong>Showing
										1-2 of 2 </strong></td>
										<td width="40%"><a href="#"><img
											src="images/b-next.gif" alt="Next" width="41" height="16"
											border="0" align="absmiddle"></a></td>
									</tr>
								</table>
								<br>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									id="additionalList">
									<tr align="center" class="label">
										<td width="11">&nbsp;</td>
										<td>Status<br>
										<select name="select">
											<option>Filter</option>
											<option>Status</option>
											<option>Filter2</option>
										</select></td>
										<td>Reg Id</td>
										<td>Short<br>
										Title</td>
										<td>Status</td>
										<td>Disease<br>
										Code</td>
										<td>Amendment<br>
										Date</td>
										<td>Stratification<br>
										Factor</td>
										<td>Randomization</td>
										<td>Current Accrual<br>
										Number</td>
										<td>Target Accrual<br>
										Number</td>
									</tr>
									<tr align="center" class="current">
										<td><img src="images/currentView.gif"
											alt="Currently Viewing" width="11" height="11"></td>
										<td>Filter1</td>
										<td>01-123456</td>
										<td>CALGB_XYZ</td>
										<td>Active</td>
										<td>1234</td>
										<td>02/05/2006</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>35</td>
										<td>50</td>
									</tr>
									<a href="#" onMouseOver="navRollOver('row1', 'on')"
										onMouseOut="navRollOver('row1', 'off')">
									<tr align="center" id="row1" class="results">
										<td>&nbsp;</td>
										<td>Filter2</td>
										<td>08-34987</td>
										<td>DUKE_AAA</td>
										<td>Inactive</td>
										<td>678AT67</td>
										<td>05/18/2003</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>67</td>
										<td>67</td>
									</tr>
									</a>
								</table>
								</td>
							</tr>
						</table>
						<!-- BOTTOM LIST ENDS HERE --></td>
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
