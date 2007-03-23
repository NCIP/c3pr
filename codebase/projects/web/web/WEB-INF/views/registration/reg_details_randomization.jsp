<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3Pr V2</title>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link href="css/search.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<script type="text/javascript" src="js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
								var cal1 = new CalendarPopup();
							</script>

<script language="JavaScript" type="text/JavaScript">
function OpenWins(target,name,width,height,scrolling){
	// I've used a var to refer to the opened window
	features = 'location=no,width='+width +',height='+height+',left=300,top=260,scrollbars='+scrolling;
	myWin = window.open(target,name,features);
}
function submitSearchPage(){
	document.getElementById("searchForm").submit();
}
function validatePage(){
	return true;
}
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}
function updateAction(action){
	if(validatePage()){
		document.getElementById("_updateaction").value=action;
		document.getElementById("form").submit();
	}
}

</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->

<tags:search action="../participant/searchParticipant"/>
<!-- TITLE/QUICK SEARCH AREA ENDS HERE --> <!-- CONTENT AREA STARTS HERE -->


<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>

		<td id="current">Registration details for Glen Suares on Test Short
		Title</td>
		<!--  TITLE ENDS HERE -->
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
							src="<tags:imageUrl name="tabWhiteL.gif"/>" width="3" height="16"
							align="absmiddle"> Summary <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7"
							height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img
							src="<tags:imageUrl name="spacer.gif"/>" width="1" height="7"></td>
					</tr>

				</table>
				<!-- TABS LEFT END HERE --></td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2"
					height="1"></td>
				<td width="70%"><!-- TABS RIGHT START HERE -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
							align="absmiddle"><a href="javascript:updatePage('_target0');">Enrollment Details</a><img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
							align="absmiddle"><a href="javascript:updatePage('_target1');">Identifiers</a>
						<img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
							align="absmiddle"> <a href="javascript:updatePage('_target2');">Eligibility</a>
						<img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
							align="absmiddle"> <a href="javascript:updatePage('_target3');">Stratification</a>
						<img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="current"><img
							src="<tags:imageUrl name="tabWhiteL.gif"/>" width="3" height="16"
							align="absmiddle">Randomization
						<img src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
							align="absmiddle"> <a href="javascript:updatePage('_target5');">Registration Status</a> <img src="<tags:imageUrl name="tabGrayR.gif"/>"
							width="3" height="16" align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7"
							height="1"></td>
					</tr>

					<tr>
						<td colspan="2" class="tabBotR"><img
							src="<tags:imageUrl name="spacer.gif"/>" width="1" height="7"></td>
					</tr>
				</table>
				<!-- TABS RIGHT END HERE --></td>
			</tr>
			<tr>
				<td valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->
				<form:form method="post" name="form" id="form">
					<div><input type="hidden" name="_page" id="_page" value="0"> <input
						type="hidden" name="_updateaction" id="_updateaction" value=""></div>
					<table width="200" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr valign="top">
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
								height="1" class="heightControl"></td>
							<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
						</tr>
						<tr valign="top">
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
								height="1" class="heightControl"></td>
							<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
						</tr>
						<tr>
							<td height="2" border="0"><b><span class="black">Subject details:</span></b></td>

						</tr>
						<tr align="center" valign="top">
							<td colspan="2"><strong>First Name :</strong> Glen</td>
							<td colspan="2"><strong>Last Name :</strong> Suares</td>
						</tr>
						<tr valign="top">
							<td class="label"><em></em>Gender:</td>
							<td>Male</td>
							<td class="label"><em></em>Birth Date:</td>
							<td>02/18/1967</td>
						</tr>
						<tr>
							<td class="label"><em></em>Ethnicity:</td>
							<td>Not Reported</td>
							<td class="label"><em></em>Race(s):</td>
							<td>Unknown</td>
						</tr>
						<tr>
							<td class="label"><em></em>Primary
							Identifier:</td>
							<td>893274</td>
							<td class="label"><em></em>Source:</td>
							<td>C3D</td>
						</tr>
						<tr valign="top">
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
								height="1" class="heightControl"></td>
							<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
						</tr>


					</table>
					<!-- LEFT CONTENT ENDS HERE --></td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2"
					height="1"></td>
				<!-- RIGHT CONTENT STARTS HERE -->
				<td valign="top" class="contentR">
				<table width="500" border="0" cellspacing="0" cellpadding="0"
					id="details">
					<tr>
						<td align="left" width="50%" border="0" valign="top"
							class="contentAreaL"><br>
						<form name="form2" method="post" action="" id="form1">
						<table width="700" border="0" cellspacing="0" cellpadding="0"
							id="table1">

							<tr valign="top">
								<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
									height="1" class="heightControl"></td>
								<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
									width="1" height="1" class="heightControl"></td>
							</tr>

							<tr>
								<td class="label"><span class="red">*</span><em></em>Randomization Type:</td>
								<td><select name="select" class="field1">
									<option selected>book</option>
									<option>call-out</option>
									<option>hub</option>
								</select></td>
							</tr>
							
							<tr valign="top">
								<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
									height="1" class="heightControl"></td>
								<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
									width="1" height="1" class="heightControl"></td>
							</tr>
								
							<tr valign="top">
								<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
									height="1" class="heightControl"></td>
								<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
									width="1" height="1" class="heightControl"></td>
							</tr>

												
							<tr>
								<td class="label"><span class="red">*</span><em></em>Glen Suares is assigned to </td>
								<td><select name="select" class="field1">
									<option selected>Arm A</option>
									<option>Arm B</option>
									<option>Arm C</option>
								</select></td>
							</tr>

							<tr valign="top">
								<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
									height="1" class="heightControl"></td>
								<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
									width="1" height="1" class="heightControl"></td>
							</tr>

																										
							<tr>
								<td align="center" colspan="3"><!-- action buttons begins -->
								<table cellpadding="4" cellspacing="0" border="0">
									<tr>
										<td colspan=2 valign="top"><br>
										<br>
										<a href="javascript:updateAction('update');"><img
												src="<tags:imageUrl name="b-saveChanges.gif"/>" border="0"
												alt="Save the Changes"></a>
									</tr>
								</table>
								</td>
							</tr>

						</table>
						</form>
						</td>
						<td valign="top">
						<table width="200" border="0" cellspacing="0" cellpadding="0"
							id="details">
							<tr>
								<td align="left" width="50%" border="0" valign="top"
									class="contentAreaR"><br>
								<br>


								<table width="200" border="0" cellspacing="0" cellpadding="0"
									id="table1">

									<tr>
										<td height="2" border="0"><b><span class="black">Study
										details:</span></b></td>

									</tr>

									<tr>
										<td class="label"><em></em>Short Title:</td>
										<td>Test Short Title</td>
									</tr>
									<tr>
										<td class="label"><em></em>Primary Identifier:</td>
										<td>CALGB-34545423</td>
									</tr>
									<tr>
										<td class="label"><em></em>Status:</td>
										<td>Active</td>
									</tr>
									<tr>
										<td class="label"><em></em>Phase:</td>
										<td>II</td>
									</tr>
									<tr>
										<td class="label"><em></em>Sponsor:</td>
										<td>GLAXO</td>
									</tr>
									<tr>
										<td class="label"><em></em>Monitor:</td>
										<td>CTEP</td>
									</tr>

								</table>
								</td>

							</tr>
						</table>
						</td>
						<!-- RIGHT CONTENT ENDS HERE -->


					</tr>
				</table>
				</td>
				<!-- BOTTOM LIST STARTS HERE --> <a name="additional"></a>
		<!-- BOTTOM LIST STARTS HERE --> <a name="additional"></a>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="tabs">
			<tr>
				<td width="100%" id="tabDisplay"><span class="current"><img
					src="<tags:imageUrl name="tabWhiteL.gif"/>" width="3" height="16" align="absmiddle">
				Registration History <img src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3"
					height="16" align="absmiddle"></span></td>
				<td width="7"><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
			</tr>
			<tr>
				<td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
					width="1" height="7"></td>
			</tr>
			<tr>
				<td colspan="2" valign="top" class="additionals">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="40%" align="right"><a href="#"><img
							src="<tags:imageUrl name="b-prev.gif"/>" alt="Previous" width="41" height="16"
							border="0" align="absmiddle"></a></td>
						<td width="20%" align="center"><strong>Showing 1-2
						of 2 </strong></td>
						<td width="40%"><a href="#"><img src="<tags:imageUrl name="b-next.gif"/>"
							alt="Next" width="41" height="16" border="0" align="absmiddle"></a></td>
					</tr>
				</table>
				<br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="additionalList">
					<tr align="center" class="label">
						<td width="11">&nbsp;</td>
						<td>Registration<br> Id</td>
						<td>Short<br>
						Title</td>
						<td>Status</td>
						<td>Disease<br>
						Code</td>
						<td>Amendment<br>
						Date</td>
						<td>Randomization<br>
						 Indicator</td>
						<td>Current Accrual<br>
						Number</td>
						<td>Target Accrual<br>
						Number</td>
					</tr>
					<tr align="center" class="current">
						<td><img src="<tags:imageUrl name="currentView.gif"/>" alt="Currently Viewing"
							width="11" height="11"></td>
						<td>Filter1</td>
						<td>01-123456</td>
						<td>CALGB_XYZ</td>
						<td>Active</td>
						<td>1234</td>
						<td>02/05/2006</td>
						<td>True</td>
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
						<td>False</td>
						<td>67</td>
						<td>67</td>
					</tr>
					</a>
				</table>
				</td>
			</tr>
		</table>
		<!-- BOTTOM LIST ENDS HERE --></td>
				
		</table>
		</form:form>


		<div id="copyright"></div>
		<!-- MAIN BODY ENDS HERE -->
</body>
</html>
