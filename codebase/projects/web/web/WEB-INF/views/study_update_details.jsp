<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
<title>C3Pr V2</title>
<link href="resources/styles.css" rel="stylesheet" type="text/css"/>
<link href="resources/search.css" rel="stylesheet" type="text/css"/>
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<script language="JavaScript" type="text/JavaScript">
function OpenWins(target,name,width,height,scrolling){
	// I've used a var to refer to the opened window
	features = 'location=no,width='+width +',height='+height+',left=300,top=260,scrollbars='+scrolling;
	myWin = window.open(target,name,features);
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">

	<tr>

		<td width="99%"><img src="images/c3prLogo.gif" alt="C3Pr V2"
			width="181" height="36" class="gelogo"/></td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">
	<tr valign="middle">

		<td width="99%" class="left"><a href="/c3pr/SearchAndRegister.do">Registration</a><img
			src="images/topNavL.gif" width="2" height="20" align="absmiddle"
			class="currentL"/><span class="current"><img
			src="images/topNavArrowDown.gif" width="5" height="20"
			align="absmiddle"/> Study </span><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"/><a
			href="/c3pr/searchparticipant.do">Subject</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"/><a href="analysis">Reports</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"/></td>
		<td class="right"><img src="images/topDivider.gif" width="2"
			height="20" align="absmiddle" class="divider"/><a href="logOff">Log
		Off</a></td>
	</tr>
</table>
<!-- TOP NAVIGATION ENDS HERE -->
<!-- SUB NAV STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="subNav">
	<tr>
		<td width="99%" valign="middle"><img src="images/arrowRight.gif"
			width="3" height="5" align="absmiddle"/> Study Management <img
			src="images/spacer.gif" width="1" height="20" align="absmiddle"
			class="spacer"/><a href="/c3pr/createstudy.do">Add Study</a></td>
		<td valign="middle" class="right"><a href="help">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<form:form id="searchForm" name="searchForm" method="post">	
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">
		<tr>
			<!-- TITLE STARTS HERE -->

			<td width="99%" height="43" valign="middle" id="title"></td>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td class="labels">&nbsp;</td>
				</tr>			
			</table>
			<span class="notation">&nbsp;</span></td>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td class="labels">&nbsp;</td>
				</tr>				
			</table>
			<span class="notation">&nbsp;</span></td>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td align="left" class="labels">Search text:</td>
					<td class="labels">&nbsp;</td>
				</tr>
				<tr>
					<td><form:input path="searchStudyCommand.searchTypeText" size="25" /></td>
					<td><input name="imageField" type="image" class="button"
						onClick="submitPage('study');return false;"
						src="images/b-go.gif" alt="GO" align="middle" width="22"
						height="10" border="0"></td>
				</tr>
			</table>
			<span class="notation">^ Minimum two characters for search.</span></td>
		</tr>
</table>
<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
		<td id="current">Short Title: ${command.study.shortTitleText}</td>
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
							align="absmiddle"> Study Summary <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span></td>
						<td><img src="images/spacer.gif" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="images/spacer.gif"
							width="1" height="7"></td>
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
							align="absmiddle"> Details <img src="images/tabWhiteR.gif"
							width="3" height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="#">Identifiers</a> <img src="images/tabGrayR.gif"
							width="3" height="16" align="absmiddle">
						<span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="#">Study Sites</a> <img src="images/tabGrayR.gif"
							width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="#">Registered Patients</a> <img src="images/tabGrayR.gif"
							width="3" height="16" align="absmiddle"></span></td>
						<td><img src="images/spacer.gif" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotR"><img src="images/spacer.gif"
							width="1" height="7"></td>
					</tr>
				</table>
				<!-- TABS RIGHT END HERE --></td>
			</tr>
			<tr>
				<td valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					id="table1">
					<tr valign="top">
						<td><img src="images/spacer.gif" width="1" height="1"
							class="heightControl"/></td>							
							<tr>
								<td><img src="images/spacer.gif" width="1" height="1"
									class="heightControl"/></td>
								<td width="65%"><img src="images/spacer.gif" width="1"
									height="1" class="heightControl"/></td>
							</tr>
							<tr>
								<td class="label">Short Title:</td>
								<td>${command.study.shortTitleText}</td>
							</tr>
							<tr>
								<td class="label">Long Title:</td>
								<td>${command.study.longTitleText}</td>
							</tr>
							<tr>
								<td class="label">Target Accrual Number:</td>
								<td>${command.study.targetAccrualNumber}</td>
							</tr>
							<tr>
								<td class="label">Status:</td>
								<td>{command.study.status}</td>
							</tr>
							<tr>
								<td class="label">Sponsor:</td>
								<td>{command.study.sponsorCode}</td>
							</tr>
							<tr>
								<td class="label">Long Title:</td>
								<td>{command.study.longTitleText}</td>
							</tr>
					</tr>
				</table>
				<!-- LEFT CONTENT ENDS HERE --></td>
				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<!-- RIGHT CONTENT STARTS HERE -->
				<td valign="top" class="contentR">
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					id="details">
					<tr>
						<td width="100%" valign="top" class="contentAreaL">
						<form name="study_update_details" method="post" action="" id="form1">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="table1">							
							<tr>
								<td class="label">Short Title:</td>
								<td>${command.study.shortTitleText}</td>
							</tr>
							<tr>
								<td class="label">Long Title:</td>
								<td>${command.study.longTitleText}</td>
							</tr>
							<tr>
								<td class="label">Target Accrual Number:</td>
								<td>${command.study.targetAccrualNumber}</td>
							</tr>
							<tr>
								<td class="label">Status:</td>
								<td>{command.study.status}</td>
							</tr>
							<tr>
								<td class="label">Sponsor:</td>
								<td>{command.study.sponsorCode}</td>
							</tr>
							<tr>
								<td class="label">Long Title:</td>
								<td>{command.study.longTitleText}</td>
							</tr>
						</table>
						</form>
						</td>
						<td width="16%" valign="top" class="contentAreaR"><strong><strong><strong><a
							href="#additional"><img
							src="images/ViewregistrationHistory.gif"
							alt="View Additional Vehicles" width="140" height="16" border="0"
							align="right"></a></strong></strong>Principal Investigator</strong><br>
						<br>
						<table width="315" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<tr>
								<td><img src="images/spacer.gif" width="1" height="1"
									class="heightControl"></td>
								<td><img src="images/spacer.gif" width="1" height="1"
									class="heightControl"></td>
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
					src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
				Registration History <img src="images/tabWhiteR.gif" width="3"
					height="16" align="absmiddle"></span></td>
				<td width="7"><img src="images/spacer.gif" width="7" height="1"></td>
			</tr>
			<tr>
				<td colspan="2" class="tabBotL"><img src="images/spacer.gif"
					width="1" height="7"></td>
			</tr>
			<tr>
				<td colspan="2" valign="top" class="additionals">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="40%" align="right"><a href="#"><img
							src="images/b-prev.gif" alt="Previous" width="41" height="16"
							border="0" align="absmiddle"></a></td>
						<td width="20%" align="center"><strong>Showing 1-2
						of 2 </strong></td>
						<td width="40%"><a href="#"><img src="images/b-next.gif"
							alt="Next" width="41" height="16" border="0" align="absmiddle"></a></td>
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
						<td><img src="images/currentView.gif" alt="Currently Viewing"
							width="11" height="11"></td>
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
</form:form>
<div id="copyright">&copy; 2006 SemanticBits Company. All Rights
Reserved</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
