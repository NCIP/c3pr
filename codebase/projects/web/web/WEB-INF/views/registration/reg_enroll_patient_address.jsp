<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
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
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"> 1. Select
						Study <img src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						2. Select Subject <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						3. Enrollment Details <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
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
									onClick="getPage('search.htm')" src="<tags:imageUrl name="b-go.gif"/>" alt="GO"
									align="middle" width="22" height="10" border="0"></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

						<td id="current">Create Subject - John Smith</td>
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
											src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
											align="absmiddle"> 1. <a href="reg_enroll_patient.htm">Subject
										Information </a><img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
											height="16" align="absmiddle"></span><span class="current"><img
											src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
											align="absmiddle"> 2. Address Information <img
											src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
											align="absmiddle"></span><span class="tab"><img
											src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
											align="absmiddle"> 3. Review and Submit <img
											src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
											align="absmiddle"></span></td>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
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
								<!-- RIGHT CONTENT STARTS HERE -->

								<form id="form1"><strong>Step 2. Address
								Information </strong> (<span class="red">*</span><em>Required
								Information </em>)<br>
								<br>
								<div class="review"><strong>Home Address:</strong><br>
								<br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><span class="red">*</span><em></em>
												Address:</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><em></em>&nbsp;</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>
												City:</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="data"><span
													class="red">*</span><em></em> State:</span></td>
												<td><input name="driverfield3" type="text" size="2">
												<span class="red">*</span><em></em><strong>Zip:</strong> <input
													name="zip" type="text" value="01234" size="3" maxlength="5">
												<a href="#"
													onClick="parent.OpenWins('searchZip.htm','searchZip',420,206,1);return false;"><img
													src="images/b-searchZip.gif" alt="Search Zip" width="48"
													height="11" border="0" align="absmiddle"></a> <a href="#"><img
													src="images/b-questionL.gif" alt="What's This?" width="15"
													height="11" border="0" align="absmiddle"></a></td>
											</tr>
											<tr>
												<td class="label"><em></em><em></em> County:</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><em></em>Email Address:</td>
												<td><input name="field7" type="text"
													value="email@address.com" size="34"></td>
											</tr>
											<tr>
												<td class="label"><em></em>Work Phone:</td>
												<td><input name="field9" type="text"
													value="(123) 456-7890" size="13"> ext <input
													name="textfield" type="text" value="25" size="8"></td>
											</tr>
											<tr>
												<td class="label">Cell Phone:</td>
												<td><input name="field8" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
											<tr>
												<td class="label">Home Phone:</td>
												<td><input name="field10" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
											<tr>
												<td class="label">Fax:</td>
												<td><input name="field10" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								<hr align="left" width="95%">

								<strong>Hospital Address:<br>
								</strong><br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><span class="red">*</span><em></em>
												Address:</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><em></em>&nbsp;</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>
												City:</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="data"><span
													class="red">*</span><em></em> State:</span></td>
												<td><input name="driverfield3" type="text" size="2">
												<span class="red">*</span><em></em><strong>Zip:</strong> <input
													name="zip" type="text" value="01234" size="3" maxlength="5">
												<a href="#"
													onClick="parent.OpenWins('searchZip.htm','searchZip',420,206,1);return false;"><img
													src="images/b-searchZip.gif" alt="Search Zip" width="48"
													height="11" border="0" align="absmiddle"></a> <a href="#"><img
													src="images/b-questionL.gif" alt="What's This?" width="15"
													height="11" border="0" align="absmiddle"></a></td>
											</tr>
											<tr>
												<td class="label"><em></em><em></em> County:</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><em></em><em></em> Phone:</td>
												<td><input name="driverfield3" type="text" size="13"></td>
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
								<hr align="left" width="95%">
								<strong>Preffered Address:<br>
								</strong><br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><input type=checkbox name="raceCode"
													value="NH"></td>
												<td>Home</td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><input type=checkbox name="raceCode"
													value="NH"></td>
												<td>Hospital</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr align="center">
										<td colspan=2 valign="top"><br>
										<br>
										<a href="reg_enroll_patient_submit.htm"><img
											src="<tags:imageUrl name="spacer.gif"/>" alt="Continue" width="59"
											height="16" border="0"></a> <a href="/c3pr/"
											onClick="add();return false;"><img
											src="<tags:imageUrl name="b-startOver.gif"/>" alt="Start Over" width="67"
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
<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
