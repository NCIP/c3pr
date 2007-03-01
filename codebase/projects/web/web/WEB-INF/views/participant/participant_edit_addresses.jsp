<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
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
function submitSearchPage(){
	document.getElementById("searchForm").submit();
}
function updatePage(s){

	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}

function validatePage(){
	return true;
}
function fireAction(action, selected){
	if(validatePage()){
		document.getElementById("_action").value=action;
		document.getElementById("_selected").value=selected;
		document.getElementById("command").submit();
	}
}
function updateAction(action){
	if(validatePage()){
		document.getElementById("_updateaction").value=action;
		document.getElementById("form").submit();
	}
}
function clearField(field){
field.value="";
}
</script>
</head>
<body>

<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">

	<tr>
		<!-- TITLE STARTS HERE -->
		<td width="100%" valign="middle" id="title"></td>
		<!-- TITLE STARTS HERE -->

		<!-- <td width="99%" height="43" valign="middle" id="title"></td>  -->

		<td valign="top"><form:form id="searchForm" name="searchForm"
			method="post" action="searchParticipant">

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">

				<tr>
					<td class="labels">&nbsp;</td>
				</tr>
				<tr>
					<td class="searchType">Search Subject by <select name="searchType">
						<c:forEach items="${searchType}" var="opt">
							<option value="${opt.code }">${opt.desc }</option>
						</c:forEach></td>
				</tr>
			</table>
			<span class="notation">&nbsp;</span></td>
		<td valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="search">
			<tr>
				<td align="left" class="labels">Search Criteria:</td>
				<td class="labels">&nbsp;</td>
			</tr>
			<tr>
				<td><input type=text name="searchText" size="25" /></td>
				<td><input name="imageField" type="image" class="button"
					onClick="submitSearchPage();return false;" src="<tags:imageUrl name="b-go.gif"/>"
					alt="GO" align="middle" width="22" height="10" border="0"></td>
			</tr>

		</table>
		</form:form></td>

	</tr>



</table>

<!-- TITLE/QUICK SEARCH AREA ENDS HERE --> <!-- CONTENT AREA STARTS HERE -->


<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>

		<td id="current">${command.firstName } ${command.lastName }</td>
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
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"> Subject Summary <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>

				</table>
				<!-- TABS LEFT END HERE --></td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2" height="1"></td>
				<td width="70%"><!-- TABS RIGHT START HERE -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle"><a
							href="javascript:updatePage('_target0');">Details</a> <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle"><a
							href="javascript:updatePage('_target1');"> Identifiers</a> <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"></span><span
							class="current"><img src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3"
							height="16" align="absmiddle"> Address <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target3');">Contact Info</a> <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotR"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>
				</table>
				<!-- TABS RIGHT END HERE --></td>
			</tr>
			<tr>
				<td valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->
				<form:form method="post" name="form" id="form">
					<div><input type="hidden" name="_page" id="_page" value="2"></div>
					<table width="200" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr valign="top">
							<td><img src="images/spacer.gif" width="1" height="1"
								class="heightControl"></td>
							<td width="75%"><img src="images/spacer.gif" width="1" height="1"
								class="heightControl"></td>
						</tr>
						<tr align="center" valign="top">
							<td colspan="2"><strong>First Name :</strong> ${ command.firstName}
							&nbsp;&nbsp;&nbsp;<strong>Last Name:</strong>
							${command.lastName }</td>
						</tr>
						<tr valign="top">
							<td><img src="images/spacer.gif" width="1" height="1"
								class="heightControl"></td>
							<td width="75%"><img src="images/spacer.gif" width="1" height="1"
								class="heightControl"></td>
						</tr>
						<tr valign="top">
							<td class="label"><span class="red">*</span><em></em>Gender:</td>
							<td>${command.administrativeGenderCode }</td>
						</tr>
						<tr>
							<td class="label"><em></em>Birth
							Date:</td>
							<td>${command.birthDateStr }</td>
						</tr>
						<tr>
							<td class="label"><em></em>Ethnicity:</td>
							<td>${command.ethnicGroupCode }</td>
						</tr>
						<tr>
							<td class="label"><em></em>Race(s):</td>
							<td>${command.raceCode }</td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Primary Identifier:</td>
							<td>${command.primaryIdentifier }</td>
						</tr>
					</table>
					<!-- LEFT CONTENT ENDS HERE --></td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2" height="1"></td>
				<!-- RIGHT CONTENT STARTS HERE -->
				<td valign="top" class="contentR">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="details">
					<tr>
						<td height="2" border="0"><b><span class="black">${updateMessageRefData.desc}</span></b></td>
					</tr>
					<tr>
						<td width="50%" valign="top" class="contentAreaL"><br>
						<br>
						<form:form method="post" name="form" id="form">
							<div><input type="hidden" name="_page" value="2"> <input
								type="hidden" name="_action" id="_action" value=""> <input type="hidden"
								name="_selected" id="_selected" value=""> <input
								type="hidden" name="_updateaction" id="_updateaction" value=""></div>

							<table width="700" border="0" cellspacing="0" cellpadding="0"
								id="table1">

								<tr>
									<td width="20%" align="center"><span class="red">*</span><em></em><B>Street
									Address:</td>
									<td width="20%" align="center"><span class="red">*</span><em></em><B>City:</td>
									<td width="20%" align="center"><span class="red">*</span><em></em><B>State:</td>
									<td width="20%" align="center"><span class="red">*</span><em></em><B>Zip:</td>
									<td width="20%" align="center"><span class="red">*</span><em></em><B>Country:</td>
								</tr>

								<tr>
									<td width="20%" align="center"><form:input
										path="address.streetAddress" /></td>
									<td width="20%" align="center"><form:input path="address.city" /></td>
									<td width="20%" align="center"><form:input
										path="address.stateCode" /></td>
									<td width="20%" align="center"><form:input
										path="address.postalCode" /></td>
									<td width="20%" align="center"><form:input
										path="address.countryCode" /></td>

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
						</form:form></td>
						<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong><a
							href=""><img src="<tags:imageUrl name="viewRegistrationHistory.gif"/>"
							alt="View Registration Information" width="140" height="16"
							border="0" align="right"></a></strong></strong>Current
						Registration</strong><br>
						<br>
						<table width="315" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<tr>
								<td class="label"><span class="red">*</span><em></em>Registration ID:</td>
								<td width="75%" valign="top">${command.studyParticipantAssignments[0].id
								}</td>
							</tr>
							<tr>
								<td class="label"><span class="red">*</span><em></em>Short
								Title:</td>
								<td width="75%" valign="top">${command.studyParticipantAssignments[0].studySite.study.shortTitleText
								}</td>
							</tr>
							<tr>
								<td class="label"><span class="red">*</span><em></em>Status:</td>
								<td width="75%" valign="top">${command.studyParticipantAssignments[0].studySite.study.status
								}</td>
							</tr>
							<tr>
								<td class="label"><span class="red">*</span><em></em>Disease
								Code:</td>
								<td width="75%" valign="top">${command.studyParticipantAssignments[0].studySite.study.diseaseCode
								}</td>
							</tr>
							<tr>
								<td class="label"><span class="red">*</span><em></em>Phase Code:</td>
								<td width="75%" valign="top">${command.studyParticipantAssignments[0].studySite.study.phaseCode
								}</td>
							</tr>
							<tr>
								<td class="label"><span class="red">*</span><em></em>Sponsor
								Code:</td>
								<td width="75%" valign="top">${command.studyParticipantAssignments[0].studySite.study.sponsorCode
								}</td>
							</tr>
							<tr>
								<td class="label"><span class="red">*</span><em></em>Target
								Accrual Number:</td>
								<td width="75%" valign="top">${command.studyParticipantAssignments[0].studySite.study.targetAccrualNumber
								}</td>
							</tr>
							<tr>
								<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
									class="heightControl "></td>
								<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
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
		</td>
	</tr>
</table>
</form:form>

<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
