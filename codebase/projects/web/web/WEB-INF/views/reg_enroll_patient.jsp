<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
function submitPage(){
	document.getElementById("searchParticipant").submit();
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/C3PRLogo.gif" alt="C3PR"
			width="181" height="36" class="gelogo"></td>
		<td align="right"><img src="images/t-drivers.gif" alt="Protocol"
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
			href="protocol.htm"> Protocol </a><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="participant.htm">Participant</a><img
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
							align="absmiddle"> 1. <a href="reg_protocol_search.htm">Select
						Protocol </a><img src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						2. Enroll Patient <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. Check Eligibility <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
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

						<form:form id="searchParticipant" name="searchParticipant" method="post">
							<td valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="search">
								<tr>
									<td class="labels">&nbsp;</td>
								</tr>
								<tr>
									<td class="searchType">Search <select name="select"
										class="field1">
										<option selected>Patient</option>
									</select> by <form:select path="searchType">
										<form:options items="${searchType}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
									</td>
								</tr>
							</table>
							<span class="notation">&nbsp;</span></td>
							<td valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="search">
								<tr>
									<td align="left" class="labels">Search String:</td>
									<td class="labels">&nbsp;</td>
								</tr>
								<tr>
									<td><form:input path="searchText"/></td>
									<td><input name="imageField" type="image" class="button"
										onClick="submitPage()" src="images/b-go.gif" alt="GO"
										align="middle" width="22" height="10" border="0"></td>
								</tr>
							</table>
							<span class="notation">^ Minimum two characters for
							search.</span></td>
						</form:form>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

						<td id="current">Create Patient</td>
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
											src="images/tabGrayL.gif" width="3" height="16"
											align="absmiddle"> 1. Patient Information <img
											src="images/tabGrayR.gif" width="3" height="16"
											align="absmiddle"></span><span class="tab"><img
											src="images/tabGrayL.gif" width="3" height="16"
											align="absmiddle"> 2. Address Information <img
											src="images/tabGrayR.gif" width="3" height="16"
											align="absmiddle"><img src="images/tabGrayL.gif"
											width="3" height="16" align="absmiddle"> 3. Review and
										Submit <img src="images/tabGrayR.gif" width="3" height="16"
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

								<strong>Step 1. Patient
								Information </strong> (<span class="red">*</span><em>Required
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
										</table>
										</td>
										<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
										<table width="308" border="0" cellspacing="0" cellpadding="0"
											id="table1">
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
											</tr>
										</table>
										</td>
									</tr>
								</table>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td>
										<table width="220" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><span class="red">*</span><em></em>Race(s):*
												Check all that apply</td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td>
										<table width="550" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td class="label"><input type=checkbox name="raceCode"
													value="NH">Native Hawaiian or other Pacific
												Islander <input type=checkbox name="raceCode" value="I">American
												Indian or Alaska Native</td>
											</tr>
											<tr>
												<td class="label"><input type=checkbox name="raceCode"
													value="B">Black or African American <input
													type=checkbox name="raceCode" value="N">Unknown <input
													type=checkbox name="raceCode" value="W">White <input
													type=checkbox name="raceCode" value="A">Asian <input
													type=checkbox name="raceCode" value="H">Not
												Reported</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								<hr align="left" width="95%">
								<strong>Primary Id<br>
								</strong><br>
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td align="center"><span class="red">*</span><em></em><B>Primary
										ID:</td>
										<td align="center"><span class="red">*</span><em></em><B>Primary
										Source:</td>
									</tr>
									<tr>
										<td><input name="driverfield1" type="text" size="11"></td>
										<td align="center"><select name="select" class="field1">
											<OPTION value="Select ID Source">Select ID Source</OPTION>
											<OPTION value="1">NCI Center for Cancer Research
											Medical Oncology Clinical Research Unit - Navy Oncology /
											NAVMOC</OPTION>
											<OPTION value="2">NCI Lab of Immune Cell Biology /
											NCILAB</OPTION>
											<OPTION value="3">NCI Lab of Tumor Immunology and
											Biology / NCILTB</OPTION>
											<OPTION value="4">NCI-Frederick / MD066</OPTION>
											<OPTION value="5">NCI/Navy Branch / NV</OPTION>
											<OPTION value="7">National Cancer Institute / NCI</OPTION>
											<OPTION value="8">National Cancer Institute
											Baltimore Cancer Research Center / NCIBAL</OPTION>
											<OPTION value="9">National Cancer Institute
											Biological Therapeutics Branch / NCIBTB</OPTION>
											<OPTION value="10">National Cancer Institute
											Biomedical Engineering and Instrum / NCIBEI</OPTION>
											<OPTION value="11">National Cancer Institute
											Biostatistics and Data Management / NCIBDM</OPTION>
											<OPTION value="12">National Cancer Institute Cancer
											Biology and Diagnosis / NCICBD</OPTION>
											<OPTION value="13">National Cancer Institute Cancer
											Prevention Study / NCICPS</OPTION>
											<OPTION value="14">National Cancer Institute Cancer
											Therapy Evaluation Program / NCICTP</OPTION>
											<OPTION value="15">National Cancer Institute Center
											for Cancer Research Medical Oncology Clinical Research Unit
											(MOCRU) / NCIMOC</OPTION>
											<OPTION value="16">National Cancer Institute
											Clinical Investigations Branch / NCICIB</OPTION>
											<OPTION value="17">National Cancer Institute
											Clinical Oncology Program / NCICOP</OPTION>
											<OPTION value="18">National Cancer Institute
											Dermatology Branch / NCIDER</OPTION>
											<OPTION value="19">National Cancer Institute
											Developmental Therapeutics Program / NCIDTP</OPTION>
											<OPTION value="20">National Cancer Institute
											Diagnostic Radiology Department / NCIDRD</OPTION>
											<OPTION value="21">National Cancer Institute
											Environmental Epidemiology Branch / NCIEEB</OPTION>
											<OPTION value="22">National Cancer Institute
											Epidemiology Branch / NCIEPI</OPTION>
											<OPTION value="23">National Cancer Institute
											Experimental Transplantation & Immunology Branch / NCIETI</OPTION>
											<OPTION value="24">National Cancer Institute
											HIV/AIDS Malignancy Branch / HIVNCI</OPTION>
											<OPTION value="25">National Cancer Institute
											Immunology Branch / NCIIB</OPTION>
											<OPTION value="26">National Cancer Institute
											Intramural Research Program / NCIIRP</OPTION>
											<OPTION value="27">National Cancer Institute
											Investigational Drug Branch / NCIIDB</OPTION>
											<OPTION value="28">National Cancer Institute Lab of
											Immunobiology / NCILOI</OPTION>
											<OPTION value="29">National Cancer Institute Lab of
											Molecular Biology / NCILMB</OPTION>
											<OPTION value="30">National Cancer Institute Lab of
											Pathology / NCILOP</OPTION>
											<OPTION value="31">National Cancer Institute
											Medicine Branch / NCIMB</OPTION>
											<OPTION value="32">National Cancer Institute
											Metabolism Branch / NCIMET</OPTION>
											<OPTION value="33">National Cancer Institute Navy
											Medical Oncology Branch / NCINAV</OPTION>
											<OPTION value="6">National Cancer Institute
											Neuro-Oncology Branch / NCINOB</OPTION>
											<OPTION value="34">National Cancer Institute Nuclear
											Medicine Department / NCINMD</OPTION>
											<OPTION value="35">National Cancer Institute
											Pediatric Oncology Branch / NCIPOB</OPTION>
											<OPTION value="36">National Cancer Institute
											Pharmacology Branch / NCIPHA</OPTION>
											<OPTION value="37">National Cancer Institute
											Radiation Oncology Branch / NCIROB</OPTION>
											<OPTION value="38">National Cancer Institute Surgery
											Branch / NCISB</OPTION>
											<OPTION value="39">National Cancer Institute
											Urologic Oncology Branch / NCIUOB</OPTION>
											<OPTION value="40">National Cancer Institute
											Veterans Administration / NCIVA</OPTION>
										</select></td>
									</tr>
									<tr>
										<td align="center"><span class="red">*</span><em></em><B>Other
										ID:</td>
										<td align="center"><span class="red">*</span><em></em><B>Other
										Source:</td>
									</tr>
									<tr>
										<td><input name="driverfield1" type="text" size="11"></td>
										<td align="center"><select name="select" class="field1">
											<OPTION value="Select ID Source">Select ID Source</OPTION>
											<OPTION value="1">NCI Center for Cancer Research
											Medical Oncology Clinical Research Unit - Navy Oncology /
											NAVMOC</OPTION>
											<OPTION value="2">NCI Lab of Immune Cell Biology /
											NCILAB</OPTION>
											<OPTION value="3">NCI Lab of Tumor Immunology and
											Biology / NCILTB</OPTION>
											<OPTION value="4">NCI-Frederick / MD066</OPTION>
											<OPTION value="5">NCI/Navy Branch / NV</OPTION>
											<OPTION value="7">National Cancer Institute / NCI</OPTION>
											<OPTION value="8">National Cancer Institute
											Baltimore Cancer Research Center / NCIBAL</OPTION>
											<OPTION value="9">National Cancer Institute
											Biological Therapeutics Branch / NCIBTB</OPTION>
											<OPTION value="10">National Cancer Institute
											Biomedical Engineering and Instrum / NCIBEI</OPTION>
											<OPTION value="11">National Cancer Institute
											Biostatistics and Data Management / NCIBDM</OPTION>
											<OPTION value="12">National Cancer Institute Cancer
											Biology and Diagnosis / NCICBD</OPTION>
											<OPTION value="13">National Cancer Institute Cancer
											Prevention Study / NCICPS</OPTION>
											<OPTION value="14">National Cancer Institute Cancer
											Therapy Evaluation Program / NCICTP</OPTION>
											<OPTION value="15">National Cancer Institute Center
											for Cancer Research Medical Oncology Clinical Research Unit
											(MOCRU) / NCIMOC</OPTION>
											<OPTION value="16">National Cancer Institute
											Clinical Investigations Branch / NCICIB</OPTION>
											<OPTION value="17">National Cancer Institute
											Clinical Oncology Program / NCICOP</OPTION>
											<OPTION value="18">National Cancer Institute
											Dermatology Branch / NCIDER</OPTION>
											<OPTION value="19">National Cancer Institute
											Developmental Therapeutics Program / NCIDTP</OPTION>
											<OPTION value="20">National Cancer Institute
											Diagnostic Radiology Department / NCIDRD</OPTION>
											<OPTION value="21">National Cancer Institute
											Environmental Epidemiology Branch / NCIEEB</OPTION>
											<OPTION value="22">National Cancer Institute
											Epidemiology Branch / NCIEPI</OPTION>
											<OPTION value="23">National Cancer Institute
											Experimental Transplantation & Immunology Branch / NCIETI</OPTION>
											<OPTION value="24">National Cancer Institute
											HIV/AIDS Malignancy Branch / HIVNCI</OPTION>
											<OPTION value="25">National Cancer Institute
											Immunology Branch / NCIIB</OPTION>
											<OPTION value="26">National Cancer Institute
											Intramural Research Program / NCIIRP</OPTION>
											<OPTION value="27">National Cancer Institute
											Investigational Drug Branch / NCIIDB</OPTION>
											<OPTION value="28">National Cancer Institute Lab of
											Immunobiology / NCILOI</OPTION>
											<OPTION value="29">National Cancer Institute Lab of
											Molecular Biology / NCILMB</OPTION>
											<OPTION value="30">National Cancer Institute Lab of
											Pathology / NCILOP</OPTION>
											<OPTION value="31">National Cancer Institute
											Medicine Branch / NCIMB</OPTION>
											<OPTION value="32">National Cancer Institute
											Metabolism Branch / NCIMET</OPTION>
											<OPTION value="33">National Cancer Institute Navy
											Medical Oncology Branch / NCINAV</OPTION>
											<OPTION value="6">National Cancer Institute
											Neuro-Oncology Branch / NCINOB</OPTION>
											<OPTION value="34">National Cancer Institute Nuclear
											Medicine Department / NCINMD</OPTION>
											<OPTION value="35">National Cancer Institute
											Pediatric Oncology Branch / NCIPOB</OPTION>
											<OPTION value="36">National Cancer Institute
											Pharmacology Branch / NCIPHA</OPTION>
											<OPTION value="37">National Cancer Institute
											Radiation Oncology Branch / NCIROB</OPTION>
											<OPTION value="38">National Cancer Institute Surgery
											Branch / NCISB</OPTION>
											<OPTION value="39">National Cancer Institute
											Urologic Oncology Branch / NCIUOB</OPTION>
											<OPTION value="40">National Cancer Institute
											Veterans Administration / NCIVA</OPTION>
										</select></td>
									</tr>
									<tr>
										<td><input name="driverfield1" type="text" size="11"></td>
										<td align="center"><select name="select" class="field1">
											<OPTION value="Select ID Source">Select ID Source</OPTION>
											<OPTION value="1">NCI Center for Cancer Research
											Medical Oncology Clinical Research Unit - Navy Oncology /
											NAVMOC</OPTION>
											<OPTION value="2">NCI Lab of Immune Cell Biology /
											NCILAB</OPTION>
											<OPTION value="3">NCI Lab of Tumor Immunology and
											Biology / NCILTB</OPTION>
											<OPTION value="4">NCI-Frederick / MD066</OPTION>
											<OPTION value="5">NCI/Navy Branch / NV</OPTION>
											<OPTION value="7">National Cancer Institute / NCI</OPTION>
											<OPTION value="8">National Cancer Institute
											Baltimore Cancer Research Center / NCIBAL</OPTION>
											<OPTION value="9">National Cancer Institute
											Biological Therapeutics Branch / NCIBTB</OPTION>
											<OPTION value="10">National Cancer Institute
											Biomedical Engineering and Instrum / NCIBEI</OPTION>
											<OPTION value="11">National Cancer Institute
											Biostatistics and Data Management / NCIBDM</OPTION>
											<OPTION value="12">National Cancer Institute Cancer
											Biology and Diagnosis / NCICBD</OPTION>
											<OPTION value="13">National Cancer Institute Cancer
											Prevention Study / NCICPS</OPTION>
											<OPTION value="14">National Cancer Institute Cancer
											Therapy Evaluation Program / NCICTP</OPTION>
											<OPTION value="15">National Cancer Institute Center
											for Cancer Research Medical Oncology Clinical Research Unit
											(MOCRU) / NCIMOC</OPTION>
											<OPTION value="16">National Cancer Institute
											Clinical Investigations Branch / NCICIB</OPTION>
											<OPTION value="17">National Cancer Institute
											Clinical Oncology Program / NCICOP</OPTION>
											<OPTION value="18">National Cancer Institute
											Dermatology Branch / NCIDER</OPTION>
											<OPTION value="19">National Cancer Institute
											Developmental Therapeutics Program / NCIDTP</OPTION>
											<OPTION value="20">National Cancer Institute
											Diagnostic Radiology Department / NCIDRD</OPTION>
											<OPTION value="21">National Cancer Institute
											Environmental Epidemiology Branch / NCIEEB</OPTION>
											<OPTION value="22">National Cancer Institute
											Epidemiology Branch / NCIEPI</OPTION>
											<OPTION value="23">National Cancer Institute
											Experimental Transplantation & Immunology Branch / NCIETI</OPTION>
											<OPTION value="24">National Cancer Institute
											HIV/AIDS Malignancy Branch / HIVNCI</OPTION>
											<OPTION value="25">National Cancer Institute
											Immunology Branch / NCIIB</OPTION>
											<OPTION value="26">National Cancer Institute
											Intramural Research Program / NCIIRP</OPTION>
											<OPTION value="27">National Cancer Institute
											Investigational Drug Branch / NCIIDB</OPTION>
											<OPTION value="28">National Cancer Institute Lab of
											Immunobiology / NCILOI</OPTION>
											<OPTION value="29">National Cancer Institute Lab of
											Molecular Biology / NCILMB</OPTION>
											<OPTION value="30">National Cancer Institute Lab of
											Pathology / NCILOP</OPTION>
											<OPTION value="31">National Cancer Institute
											Medicine Branch / NCIMB</OPTION>
											<OPTION value="32">National Cancer Institute
											Metabolism Branch / NCIMET</OPTION>
											<OPTION value="33">National Cancer Institute Navy
											Medical Oncology Branch / NCINAV</OPTION>
											<OPTION value="6">National Cancer Institute
											Neuro-Oncology Branch / NCINOB</OPTION>
											<OPTION value="34">National Cancer Institute Nuclear
											Medicine Department / NCINMD</OPTION>
											<OPTION value="35">National Cancer Institute
											Pediatric Oncology Branch / NCIPOB</OPTION>
											<OPTION value="36">National Cancer Institute
											Pharmacology Branch / NCIPHA</OPTION>
											<OPTION value="37">National Cancer Institute
											Radiation Oncology Branch / NCIROB</OPTION>
											<OPTION value="38">National Cancer Institute Surgery
											Branch / NCISB</OPTION>
											<OPTION value="39">National Cancer Institute
											Urologic Oncology Branch / NCIUOB</OPTION>
											<OPTION value="40">National Cancer Institute
											Veterans Administration / NCIVA</OPTION>
										</select></td>
									</tr>
									<tr>
										<td><input name="driverfield1" type="text" size="11"></td>
										<td align="center"><select name="select" class="field1">
											<OPTION value="Select ID Source">Select ID Source</OPTION>
											<OPTION value="1">NCI Center for Cancer Research
											Medical Oncology Clinical Research Unit - Navy Oncology /
											NAVMOC</OPTION>
											<OPTION value="2">NCI Lab of Immune Cell Biology /
											NCILAB</OPTION>
											<OPTION value="3">NCI Lab of Tumor Immunology and
											Biology / NCILTB</OPTION>
											<OPTION value="4">NCI-Frederick / MD066</OPTION>
											<OPTION value="5">NCI/Navy Branch / NV</OPTION>
											<OPTION value="7">National Cancer Institute / NCI</OPTION>
											<OPTION value="8">National Cancer Institute
											Baltimore Cancer Research Center / NCIBAL</OPTION>
											<OPTION value="9">National Cancer Institute
											Biological Therapeutics Branch / NCIBTB</OPTION>
											<OPTION value="10">National Cancer Institute
											Biomedical Engineering and Instrum / NCIBEI</OPTION>
											<OPTION value="11">National Cancer Institute
											Biostatistics and Data Management / NCIBDM</OPTION>
											<OPTION value="12">National Cancer Institute Cancer
											Biology and Diagnosis / NCICBD</OPTION>
											<OPTION value="13">National Cancer Institute Cancer
											Prevention Study / NCICPS</OPTION>
											<OPTION value="14">National Cancer Institute Cancer
											Therapy Evaluation Program / NCICTP</OPTION>
											<OPTION value="15">National Cancer Institute Center
											for Cancer Research Medical Oncology Clinical Research Unit
											(MOCRU) / NCIMOC</OPTION>
											<OPTION value="16">National Cancer Institute
											Clinical Investigations Branch / NCICIB</OPTION>
											<OPTION value="17">National Cancer Institute
											Clinical Oncology Program / NCICOP</OPTION>
											<OPTION value="18">National Cancer Institute
											Dermatology Branch / NCIDER</OPTION>
											<OPTION value="19">National Cancer Institute
											Developmental Therapeutics Program / NCIDTP</OPTION>
											<OPTION value="20">National Cancer Institute
											Diagnostic Radiology Department / NCIDRD</OPTION>
											<OPTION value="21">National Cancer Institute
											Environmental Epidemiology Branch / NCIEEB</OPTION>
											<OPTION value="22">National Cancer Institute
											Epidemiology Branch / NCIEPI</OPTION>
											<OPTION value="23">National Cancer Institute
											Experimental Transplantation & Immunology Branch / NCIETI</OPTION>
											<OPTION value="24">National Cancer Institute
											HIV/AIDS Malignancy Branch / HIVNCI</OPTION>
											<OPTION value="25">National Cancer Institute
											Immunology Branch / NCIIB</OPTION>
											<OPTION value="26">National Cancer Institute
											Intramural Research Program / NCIIRP</OPTION>
											<OPTION value="27">National Cancer Institute
											Investigational Drug Branch / NCIIDB</OPTION>
											<OPTION value="28">National Cancer Institute Lab of
											Immunobiology / NCILOI</OPTION>
											<OPTION value="29">National Cancer Institute Lab of
											Molecular Biology / NCILMB</OPTION>
											<OPTION value="30">National Cancer Institute Lab of
											Pathology / NCILOP</OPTION>
											<OPTION value="31">National Cancer Institute
											Medicine Branch / NCIMB</OPTION>
											<OPTION value="32">National Cancer Institute
											Metabolism Branch / NCIMET</OPTION>
											<OPTION value="33">National Cancer Institute Navy
											Medical Oncology Branch / NCINAV</OPTION>
											<OPTION value="6">National Cancer Institute
											Neuro-Oncology Branch / NCINOB</OPTION>
											<OPTION value="34">National Cancer Institute Nuclear
											Medicine Department / NCINMD</OPTION>
											<OPTION value="35">National Cancer Institute
											Pediatric Oncology Branch / NCIPOB</OPTION>
											<OPTION value="36">National Cancer Institute
											Pharmacology Branch / NCIPHA</OPTION>
											<OPTION value="37">National Cancer Institute
											Radiation Oncology Branch / NCIROB</OPTION>
											<OPTION value="38">National Cancer Institute Surgery
											Branch / NCISB</OPTION>
											<OPTION value="39">National Cancer Institute
											Urologic Oncology Branch / NCIUOB</OPTION>
											<OPTION value="40">National Cancer Institute
											Veterans Administration / NCIVA</OPTION>
										</select></td>
									</tr>
									<tr>
										<td><input name="driverfield1" type="text" size="11"></td>
										<td align="center"><select name="select" class="field1">
											<OPTION value="Select ID Source">Select ID Source</OPTION>
											<OPTION value="1">NCI Center for Cancer Research
											Medical Oncology Clinical Research Unit - Navy Oncology /
											NAVMOC</OPTION>
											<OPTION value="2">NCI Lab of Immune Cell Biology /
											NCILAB</OPTION>
											<OPTION value="3">NCI Lab of Tumor Immunology and
											Biology / NCILTB</OPTION>
											<OPTION value="4">NCI-Frederick / MD066</OPTION>
											<OPTION value="5">NCI/Navy Branch / NV</OPTION>
											<OPTION value="7">National Cancer Institute / NCI</OPTION>
											<OPTION value="8">National Cancer Institute
											Baltimore Cancer Research Center / NCIBAL</OPTION>
											<OPTION value="9">National Cancer Institute
											Biological Therapeutics Branch / NCIBTB</OPTION>
											<OPTION value="10">National Cancer Institute
											Biomedical Engineering and Instrum / NCIBEI</OPTION>
											<OPTION value="11">National Cancer Institute
											Biostatistics and Data Management / NCIBDM</OPTION>
											<OPTION value="12">National Cancer Institute Cancer
											Biology and Diagnosis / NCICBD</OPTION>
											<OPTION value="13">National Cancer Institute Cancer
											Prevention Study / NCICPS</OPTION>
											<OPTION value="14">National Cancer Institute Cancer
											Therapy Evaluation Program / NCICTP</OPTION>
											<OPTION value="15">National Cancer Institute Center
											for Cancer Research Medical Oncology Clinical Research Unit
											(MOCRU) / NCIMOC</OPTION>
											<OPTION value="16">National Cancer Institute
											Clinical Investigations Branch / NCICIB</OPTION>
											<OPTION value="17">National Cancer Institute
											Clinical Oncology Program / NCICOP</OPTION>
											<OPTION value="18">National Cancer Institute
											Dermatology Branch / NCIDER</OPTION>
											<OPTION value="19">National Cancer Institute
											Developmental Therapeutics Program / NCIDTP</OPTION>
											<OPTION value="20">National Cancer Institute
											Diagnostic Radiology Department / NCIDRD</OPTION>
											<OPTION value="21">National Cancer Institute
											Environmental Epidemiology Branch / NCIEEB</OPTION>
											<OPTION value="22">National Cancer Institute
											Epidemiology Branch / NCIEPI</OPTION>
											<OPTION value="23">National Cancer Institute
											Experimental Transplantation & Immunology Branch / NCIETI</OPTION>
											<OPTION value="24">National Cancer Institute
											HIV/AIDS Malignancy Branch / HIVNCI</OPTION>
											<OPTION value="25">National Cancer Institute
											Immunology Branch / NCIIB</OPTION>
											<OPTION value="26">National Cancer Institute
											Intramural Research Program / NCIIRP</OPTION>
											<OPTION value="27">National Cancer Institute
											Investigational Drug Branch / NCIIDB</OPTION>
											<OPTION value="28">National Cancer Institute Lab of
											Immunobiology / NCILOI</OPTION>
											<OPTION value="29">National Cancer Institute Lab of
											Molecular Biology / NCILMB</OPTION>
											<OPTION value="30">National Cancer Institute Lab of
											Pathology / NCILOP</OPTION>
											<OPTION value="31">National Cancer Institute
											Medicine Branch / NCIMB</OPTION>
											<OPTION value="32">National Cancer Institute
											Metabolism Branch / NCIMET</OPTION>
											<OPTION value="33">National Cancer Institute Navy
											Medical Oncology Branch / NCINAV</OPTION>
											<OPTION value="6">National Cancer Institute
											Neuro-Oncology Branch / NCINOB</OPTION>
											<OPTION value="34">National Cancer Institute Nuclear
											Medicine Department / NCINMD</OPTION>
											<OPTION value="35">National Cancer Institute
											Pediatric Oncology Branch / NCIPOB</OPTION>
											<OPTION value="36">National Cancer Institute
											Pharmacology Branch / NCIPHA</OPTION>
											<OPTION value="37">National Cancer Institute
											Radiation Oncology Branch / NCIROB</OPTION>
											<OPTION value="38">National Cancer Institute Surgery
											Branch / NCISB</OPTION>
											<OPTION value="39">National Cancer Institute
											Urologic Oncology Branch / NCIUOB</OPTION>
											<OPTION value="40">National Cancer Institute
											Veterans Administration / NCIVA</OPTION>
										</select></td>
									</tr>
									<tr>
										<td><input name="driverfield1" type="text" size="11"></td>
										<td align="center"><select name="select" class="field1">
											<OPTION value="Select ID Source">Select ID Source</OPTION>
											<OPTION value="1">NCI Center for Cancer Research
											Medical Oncology Clinical Research Unit - Navy Oncology /
											NAVMOC</OPTION>
											<OPTION value="2">NCI Lab of Immune Cell Biology /
											NCILAB</OPTION>
											<OPTION value="3">NCI Lab of Tumor Immunology and
											Biology / NCILTB</OPTION>
											<OPTION value="4">NCI-Frederick / MD066</OPTION>
											<OPTION value="5">NCI/Navy Branch / NV</OPTION>
											<OPTION value="7">National Cancer Institute / NCI</OPTION>
											<OPTION value="8">National Cancer Institute
											Baltimore Cancer Research Center / NCIBAL</OPTION>
											<OPTION value="9">National Cancer Institute
											Biological Therapeutics Branch / NCIBTB</OPTION>
											<OPTION value="10">National Cancer Institute
											Biomedical Engineering and Instrum / NCIBEI</OPTION>
											<OPTION value="11">National Cancer Institute
											Biostatistics and Data Management / NCIBDM</OPTION>
											<OPTION value="12">National Cancer Institute Cancer
											Biology and Diagnosis / NCICBD</OPTION>
											<OPTION value="13">National Cancer Institute Cancer
											Prevention Study / NCICPS</OPTION>
											<OPTION value="14">National Cancer Institute Cancer
											Therapy Evaluation Program / NCICTP</OPTION>
											<OPTION value="15">National Cancer Institute Center
											for Cancer Research Medical Oncology Clinical Research Unit
											(MOCRU) / NCIMOC</OPTION>
											<OPTION value="16">National Cancer Institute
											Clinical Investigations Branch / NCICIB</OPTION>
											<OPTION value="17">National Cancer Institute
											Clinical Oncology Program / NCICOP</OPTION>
											<OPTION value="18">National Cancer Institute
											Dermatology Branch / NCIDER</OPTION>
											<OPTION value="19">National Cancer Institute
											Developmental Therapeutics Program / NCIDTP</OPTION>
											<OPTION value="20">National Cancer Institute
											Diagnostic Radiology Department / NCIDRD</OPTION>
											<OPTION value="21">National Cancer Institute
											Environmental Epidemiology Branch / NCIEEB</OPTION>
											<OPTION value="22">National Cancer Institute
											Epidemiology Branch / NCIEPI</OPTION>
											<OPTION value="23">National Cancer Institute
											Experimental Transplantation & Immunology Branch / NCIETI</OPTION>
											<OPTION value="24">National Cancer Institute
											HIV/AIDS Malignancy Branch / HIVNCI</OPTION>
											<OPTION value="25">National Cancer Institute
											Immunology Branch / NCIIB</OPTION>
											<OPTION value="26">National Cancer Institute
											Intramural Research Program / NCIIRP</OPTION>
											<OPTION value="27">National Cancer Institute
											Investigational Drug Branch / NCIIDB</OPTION>
											<OPTION value="28">National Cancer Institute Lab of
											Immunobiology / NCILOI</OPTION>
											<OPTION value="29">National Cancer Institute Lab of
											Molecular Biology / NCILMB</OPTION>
											<OPTION value="30">National Cancer Institute Lab of
											Pathology / NCILOP</OPTION>
											<OPTION value="31">National Cancer Institute
											Medicine Branch / NCIMB</OPTION>
											<OPTION value="32">National Cancer Institute
											Metabolism Branch / NCIMET</OPTION>
											<OPTION value="33">National Cancer Institute Navy
											Medical Oncology Branch / NCINAV</OPTION>
											<OPTION value="6">National Cancer Institute
											Neuro-Oncology Branch / NCINOB</OPTION>
											<OPTION value="34">National Cancer Institute Nuclear
											Medicine Department / NCINMD</OPTION>
											<OPTION value="35">National Cancer Institute
											Pediatric Oncology Branch / NCIPOB</OPTION>
											<OPTION value="36">National Cancer Institute
											Pharmacology Branch / NCIPHA</OPTION>
											<OPTION value="37">National Cancer Institute
											Radiation Oncology Branch / NCIROB</OPTION>
											<OPTION value="38">National Cancer Institute Surgery
											Branch / NCISB</OPTION>
											<OPTION value="39">National Cancer Institute
											Urologic Oncology Branch / NCIUOB</OPTION>
											<OPTION value="40">National Cancer Institute
											Veterans Administration / NCIVA</OPTION>
										</select></td>
									</tr>
								</table>

								<hr align="left" width="95%">
								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td align="center" colspan="2" valign="top"><br>
										<br>
										<a href="reg_enroll_patient_address.htm"><img
											src="images/b-continue.gif" alt="Continue" width="59"
											height="16" border="0"></a> <a href="home.htm"
											onClick="add();return false;"><img
											src="images/b-startOver.gif" alt="Start Over" width="67"
											height="16" border="0"></a></td>
									</tr>
								</table>
								</div>
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
