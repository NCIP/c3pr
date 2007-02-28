<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
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
					<form:form id="searchParticipant" name="searchParticipant"
						method="post">

						<tr>
							<!-- TITLE STARTS HERE -->
							<td width="99%" height="43" valign="middle" id="title">Subject
							Search</td>
							<td valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="search">
								<tr>
									<td class="labels">&nbsp;</td>
								</tr>
								<tr>
									<td class="searchType">Search Subject by <form:select
										path="searchType">
										<form:options items="${searchType}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
							</table>
							<span class="notation">&nbsp;</span></td>
							<td valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="search">
								<tr>
									<td align="left" class="labels">First Name:</td>
									<td class="labels">&nbsp;</td>
								</tr>
								<tr>
									<td><form:input path="searchText" /></td>
									<td><input name="imageField" type="image" class="button"
										onClick="submitPage()" src="<tags:imageUrl name="b-go.gif"/>" alt="GO"
										align="middle" width="22" height="10" border="0"></td>
								</tr>
							</table>
							</td>
						</tr>
					</form:form>
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
											src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
											align="absmiddle"> 2. Subject Information <img
											src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
											align="absmiddle"></span><span class="tab"><img
											src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
											align="absmiddle"> 3. Address Information <img
											src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
											align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>"
											width="3" height="16" align="absmiddle"> 4. Review and
										Submit <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
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

								<form id="form1">
								<table width="222" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td width="52" class="label"><strong>&nbsp;Fleet</strong>:</td>
										<td width="45">005029</td>
										<td width="57" class="label"><strong>&nbsp;</strong>Sub:</td>
										<td width="68">0034234</td>
									</tr>
								</table>
								<hr>
								<br>
								<strong>Step 2. Subject Information </strong> (<span class="red">*</span><em>Required
								Information </em>)<br>
								<br>
								<div class="review"><strong>Current Information:</strong><br>
								<br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr align="center">
												<td colspan="2"><strong><span class="red">*</span><em></em>First:
												<input name="first" type="text" value="John" size="10">
												&nbsp;&nbsp;&nbsp;<strong>MI:</strong> <input name="mi"
													type="text" maxlength="1" class="mi">
												&nbsp;&nbsp;&nbsp;<span class="red">*</span><em></em><strong>Last:</strong>
												<input name="last" type="text" value="Smith" size="14">
												</strong></td>
											</tr>
											<tr>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
													class="heightControl"></td>
												<td width="65%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Title:</td>
												<td><input name="driverfield1" type="text" size="34"></td>
											</tr>
											<tr>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
													class="heightControl"></td>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
													class="heightControl"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Current
												Address:</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Current
												City:</td>
												<td valign="top">Anytown</td>
											</tr>
											<tr>
												<td class="label"><span class="data"><span
													class="red">*</span><em></em>Current State:</span></td>
												<td valign="top"><span class="state">NY</span> <span
													class="red">*</span><em></em><strong>Zip:</strong> <input
													name="zip" type="text" value="01234" size="3" maxlength="5">
												<a href="#"
													onClick="parent.OpenWins('searchZip.htm','searchZip',420,206,1);return false;"><img
													src="images/b-searchZip.gif" alt="Search Zip" width="48"
													height="11" border="0" align="absmiddle"></a> <a href="#"><img
													src="images/b-questionL.gif" alt="What's This?" width="15"
													height="11" border="0" align="absmiddle"></a></td>
											</tr>
											<tr>
												<td class="label"><em></em><span class="red">*</span><em></em>Current
												County:</td>
												<td>Bronx</td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td width="46%" class="label"><span class="red">*</span><em></em>WWID:</td>
												<td width="54%"><input name="driverfield1" type="text"
													size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>GE
												Billing Levels/RDT:</td>
												<td><input name="driverfield3" type="text" size="34"></td>
											</tr>
											<tr>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
													class="heightControl "></td>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
													class="heightControl"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Email
												Address:</td>
												<td><input name="field7" type="text"
													value="email@address.com" size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Work
												Phone:</td>
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
												<td class="label">Pager:</td>
												<td><input name="field10" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								<hr align="left" width="95%">

								<strong>Temporary Information:<br>
								</strong><br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><span class="red">*</span><em></em>Temp
												Address:</td>
												<td width="65%"><input name="driverfield3" type="text"
													size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>Temp
												City:</td>
												<td valign="top">Anytown</td>
											</tr>
											<tr>
												<td class="label"><span class="data"><span
													class="red">*</span><em></em>Temp State:</span></td>
												<td valign="top"><span class="state">NY</span> <span
													class="red">*</span><em></em><strong>Zip:</strong> <input
													name="zip" type="text" value="01234" size="3" maxlength="5">
												<a href="#"
													onClick="parent.OpenWins('searchZip.htm','searchZip',420,206,1);return false;"><img
													src="images/b-searchZip.gif" alt="Search Zip" width="48"
													height="11" border="0" align="absmiddle"></a> <a href="#"><img
													src="images/b-questionL.gif" alt="What's This?" width="15"
													height="11" border="0" align="absmiddle"></a></td>
											</tr>
											<tr>
												<td class="label"><em></em><span class="red">*</span><em></em>Temp
												County:</td>
												<td>Bronx</td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td width="46%" class="label"><span class="red">*</span><em></em>Temp
												Work Phone:</td>
												<td width="54%"><input name="field9" type="text"
													value="(123) 456-7890" size="13"> ext <input
													name="textfield" type="text" value="25" size="8"></td>
											</tr>
											<tr>
												<td class="label">Temp Home Phone:</td>
												<td><input name="field8" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
											<tr>
												<td class="label">Temp Cell Phone:</td>
												<td><input name="field10" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
											<tr>
												<td class="label">Temp Pager:</td>
												<td><input name="field10" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								<hr align="left" width="95%">
								<strong>New Information:<br>
								</strong><br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><span class="red">*</span><em></em>New
												Address:</td>
												<td width="65%"><input name="driverfield3" type="text"
													size="34"></td>
											</tr>
											<tr>
												<td class="label"><span class="red">*</span><em></em>New
												City:</td>
												<td valign="top">Anytown</td>
											</tr>
											<tr>
												<td class="label"><span class="data"><span
													class="red">*</span><em></em>New State:</span></td>
												<td valign="top"><span class="state">NY</span> <span
													class="red">*</span><em></em><strong>Zip:</strong> <input
													name="zip" type="text" value="01234" size="3" maxlength="5">
												<a href="#"
													onClick="parent.OpenWins('searchZip.htm','searchZip',420,206,1);return false;"><img
													src="images/b-searchZip.gif" alt="Search Zip" width="48"
													height="11" border="0" align="absmiddle"></a> <a href="#"><img
													src="images/b-questionL.gif" alt="What's This?" width="15"
													height="11" border="0" align="absmiddle"></a></td>
											</tr>
											<tr>
												<td class="label"><em></em><span class="red">*</span><em></em>New
												County:</td>
												<td>Bronx</td>
											</tr>
										</table>
										<br>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label">New Information Effective Date:</td>
												<td width="65%"><input name="date" type="text"
													size="12"> <a href="#"
													onClick="parent.OpenWins('calendar.htm','calendar',200,236);return false;"><img
													src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17"
													height="16" border="0" align="absmiddle"></a></td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td width="46%" class="label"><span class="red">*</span><em></em>New
												Work Phone:</td>
												<td width="54%"><input name="field9" type="text"
													value="(123) 456-7890" size="13"> ext <input
													name="textfield" type="text" value="25" size="8"></td>
											</tr>
											<tr>
												<td class="label">New Home Phone:</td>
												<td><input name="field8" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
											<tr>
												<td class="label">New Cell Phone:</td>
												<td><input name="field10" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
											<tr>
												<td class="label">New Pager:</td>
												<td><input name="field10" type="text"
													value="(123) 456-7890" size="13"></td>
											</tr>
										</table>
										</td>
									</tr>
									<tr align="center">
										<td colspan="2" valign="top"><br>
										<br>
										<a href="driver_add3.htm"><img src="<tags:imageUrl name="spacer.gif"/>"
											alt="Continue" width="59" height="16" border="0"></a> <a
											href="/c3pr/" onClick="add();return false;"><img
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
