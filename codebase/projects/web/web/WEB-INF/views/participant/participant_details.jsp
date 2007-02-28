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
</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->
<div class="workArea"><form:form method="post">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		class="titleArea">
		<tr>
			<!-- TITLE STARTS HERE -->
			<td width="99%" height="43" valign="middle" id="title"></td>
			<td valign="top">
			<form method="post" action="" name="searchMeth" class="search">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td class="labels">&nbsp;</td>
				</tr>
				<tr>
					<td class="searchType">Search <select name="select" class="field1">
						<option selected>Subject</option>
						<option>Study</option>
					</select> by <select name="select" class="field1">
						<option selected>Subject Name</option>
						<option>Subject Identifier#</option>
					</select></td>
				</tr>
			</table>
			</form>
			<span class="notation">&nbsp;</span></td>
			<td valign="top">

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td align="left" class="labels"><span class="notation"><span
						class="red">*</span><em></em></span>Last Name:</td>
					<td align="left" class="labels">First Name:</td>
					<td class="labels">&nbsp;</td>
				</tr>
				<tr>
					<td><input name="textfield2" type="text" class="field1" size="25"></td>
					<td><input name="textfield3" type="text" class="field1" size="25"></td>
					<td><input name="imageField" type="image" class="button"
						onClick="getPage('search.htm')" src="<tags:imageUrl name="b-go.gif"/>" alt="GO"
						align="middle" width="22" height="10" border="0"></td>
				</tr>
			</table>
			<span class="notation"><span class="labels">(<span class="red">*</span><em>Required
			Information </em>)</span></td>
		</tr>
	</table>
	<!-- TITLE/QUICK SEARCH AREA ENDS HERE -->
	<!-- CONTENT AREA STARTS HERE -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
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
							<td width="100%" id="tabDisplay"><span class="current"><img
								src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
								align="absmiddle"> Details <img src="<tags:imageUrl name="tabWhiteR.gif"/>"
								width="3" height="16" align="absmiddle"></span><span class="tab"><img
								src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
								align="absmiddle"> <a href="driver_fields.htm">Identifiers</a> <img
								src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
								align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
								height="16" align="absmiddle"> <a href="driver_fields.htm">Address</a>
							<img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
								align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
								height="16" align="absmiddle"> <a href="driver_fields.htm">Contact
							Info</a> <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
								align="absmiddle"></span></td>
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr valign="top">
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
								class="heightControl"></td>
							<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
								class="heightControl"></td>
						</tr>
						<tr align="center" valign="top">
							<td colspan="2"><strong>First:</strong> ${ command.firstName}
							&nbsp;&nbsp;&nbsp;<strong>MI:</strong> -&nbsp;&nbsp;&nbsp;<strong>Last:</strong>
							${command.lastName }</td>
						</tr>
						<tr valign="top">
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
								class="heightControl"></td>
							<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
								class="heightControl"></td>
						</tr>
						<tr valign="top">
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
								class="heightControl"></td>
							<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
								class="heightControl"></td>
						</tr>
						<tr valign="top">
							<td class="label"><span class="red">*</span><em></em>Gender:</td>
							<td>${command.administrativeGenderCode }</td>
						</tr>
						<tr valign="top">
							<td class="label"><span class="red">*</span><em></em>Primary Id:</td>
							<td>${command.id }</td>
						</tr>
						<tr valign="top">
							<td class="label"><span class="red">*</span><em></em> Id Source:</td>
							<td>${command.id } /</td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Id:</td>
							<td>${command.id }</td>
						</tr>
						<tr>
							<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
							Date:</td>
							<td valign="top">${command.birthDate }</td>
						</tr>
						<tr>
							<td class="label"><em></em>First Visit Date:</td>
							<td valign="top">${command.lastName }</td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Ethnicity:</td>
							<td>${command.ethnicGroupCode }</td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Race(s):</td>
							<td>${command.raceCode }</td>
							<td>${command.raceCode }</td>
						</tr>
						<tr>
							<td class="label"><em></em> Primary Address:</td>
							<td>${command.address.streetAddress }</td>
							<td>${command.address.city }</td>
							<td>${command.address.stateCode }</td>
							<td>${command.address.postalCode }</td>
							<td>${command.address.countryCode }</td>
						</tr>
						<tr>
							<td class="label"><em></em><em></em> County:</td>
							<td>${command.address.countryCode }</td>
						</tr>
					</table>
					<!-- LEFT CONTENT ENDS HERE --></td>
					<td><img src="<tags:imageUrl name="b-continue.gif"/>" width="2" height="1"></td>

				</tr>
			</table>
			</form:form></td>
		</tr>
	</table>

	<div id="copyright">
	</div></div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
