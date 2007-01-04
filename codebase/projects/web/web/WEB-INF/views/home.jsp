<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3PR V2</title>
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function search(s){
	
}
function changeFeilds(s){
	selectedIndex=window.document.protForm.selectProtocol.selectedIndex;
	innerHtml='<table border="0" cellspacing="0" cellpadding="0" id="search">';
	if(s=='protocol'){
		if(window.document.protForm.selectProtocol.options[selectedIndex].text=='Short Title'){
			innerHtml+='<tr><td align="left" class="labels">';
			innerHtml+='Short Title';
			innerHtml+=':</td></tr>';
			innerHtml+='<tr><td><input name="'+'shortTitle'+'" type="text" size="25"></td></td>';
		}
		if(window.document.protForm.selectProtocol.options[selectedIndex].text=='Site Protocol Identifier'){
			innerHtml+='<tr><td align="left" class="labels">';
			innerHtml+='Site Protocol Identifier';
			innerHtml+=':</td></tr>';
			innerHtml+='<tr><td><input name="'+'siteProtId'+'" type="text" size="25"></td></tr>';
		}
		if(window.document.protForm.selectProtocol.options[selectedIndex].text=='Primary Sponsor Identifier'){
			innerHtml+='<tr><td align="left" class="labels">';
			innerHtml+='Primary Sponsor Identifier';
			innerHtml+=':</td></tr>';
			innerHtml+='<tr><td><input name="'+'sponsorId'+'" type="text" size="25"></td></td>';
		}
		innerHtml+='</table>';
		document.getElementById("protocol_search_feilds").innerHTML=innerHtml;
	}
	if(s=='participant'){
		if(window.document.protForm.selectProtocol.options[selectedIndex].text=='Name'){
			innerHtml+='<tr><td align="left" class="labels">';
			innerHtml+='Short Title';
			innerHtml+=':</td></tr>';
			innerHtml+='<tr><td><input name="'+'shortTitle'+'" type="text" size="25"></td></td>';
		}
		if(window.document.protForm.selectProtocol.options[selectedIndex].text=='Site Protocol Identifier'){
			innerHtml+='<tr><td align="left" class="labels">';
			innerHtml+='Site Protocol Identifier';
			innerHtml+=':</td></tr>';
			innerHtml+='<tr><td><input name="'+'siteProtId'+'" type="text" size="25"></td></tr>';
		}
		if(window.document.protForm.selectProtocol.options[selectedIndex].text=='Primary Sponsor Identifier'){
			innerHtml+='<tr><td align="left" class="labels">';
			innerHtml+='Primary Sponsor Identifier';
			innerHtml+=':</td></tr>';
			innerHtml+='<tr><td><input name="'+'sponsorId'+'" type="text" size="25"></td></td>';
		}
		innerHtml+='</table>';
		document.getElementById("protocol_search_feilds").innerHTML=innerHtml;
	}
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="images/C3PRLogo.gif" alt="C3PR" width="181"
			height="36" class="gelogo"></td>
		<td>&nbsp;</td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">
	<tr valign="middle">

		<td width="99%" class="left"><span class="current"><img
			src="images/topNavArrowDown.gif" width="5" height="20"
			align="absmiddle"> Registration </span><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="protocol.jsp">Protocol</a><img src="images/topDivider.gif"
			width="2" height="20" align="absmiddle" class="divider"><a
			href="participant.jsp">Participant</a><img
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

		<td width="99%" valign="middle">&nbsp;</td>
		<td valign="middle" class="right"><a href="help">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">

	<tr>

		<!-- TITLE STARTS HERE -->

		<td width="99%" height="43" valign="middle" id="title">Welcome
		User</td>
		<!-- TITLE ENDS HERE -->

		<!-- SEARCH STARTS HERE -->

		<!-- SEARCH ENDS HERE -->
	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT PROTOCOL/Participant TITLE STARTS HERE -->

		<td id="current">Search</td>
		<!-- CURRENT PROTOCOL/Participant TITLE ENDS HERE -->
	</tr>
	<tr>
		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="50%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>

						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle"> Protocol Search <img
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
				<td width="50%"><!-- TABS RIGHT START HERE -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>

						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle"> Participant Search <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span></td>
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
			<form:form name="searchForm" method="post">
				<!-- LEFT CONTENT STARTS HERE -->
				<td valign="top" class="searchL"><!-- LEFT FORM STARTS HERE -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr valign="top">
						<td><img src="images/Protocol.gif" alt="Protocol Search"
							width="100" height="100" align="absmiddle"></td>
						<td width="99%">
						<h3>Protocol Search</h3>
						<strong>1. Search Protocols by:</strong> 
						<form:select path="searchTypeProtocol">
							<form:options items="${protocolList}" itemLabel="desc" itemValue="code" />
						</form:select> <br>
						<br>
						<strong>2. Fill in the Fields:</strong><br>
						<br>
						<div id="protocol_search_feilds">
						<table border="0" cellspacing="0" cellpadding="0" id="search">
							<tr>
								<td align="left" class="labels">Short Title:</td>
							</tr>
							<tr>
								<td><form:input path="searchValueProtocol" /></td>

							</tr>
						</table>
						</div>
						^ Minimum two characters search.<br>
						<br>
						<a href="reg_protocol_search.jsp"><img
							src="images/SerachProtocols.gif" alt="Search Drivers" width="100"
							height="16" border="0" onClick="search('byProt');"></a></td>
					</tr>
				</table>

				<!-- LEFT FORM ENDS HERE --></td>
				<!-- LEFT CONTENT ENDS HERE -->
				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<td valign="top" class="searchR">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr valign="top">
						<td><img src="images/Patient.gif" alt="Participant Search"
							width="100" height="100" align="absmiddle"></td>
						<td width="99%">
						<h3>Participant Search</h3>
						<strong>1. Search Participant by:</strong>
						<form:select path="searchTypePart">
							<form:options items="${participantList}" itemLabel="desc" itemValue="code" />
						</form:select> <br>
						<br>

						<strong>2. Fill in the Fields:</strong><br>
						<br>
						<div id="participant_search_feilds">						
						<table border="0" cellspacing="0" cellpadding="0" id="search">
							<tr>
								<td align="left" class="labels">Last Name:</td>
								<td align="left" class="labels">First Name:</td>
							</tr>
							<tr>
								<td><input name="textield1" type="text" size="25"></td>
								<td><input name="textfield2" type="text" size="25"></td>
							</tr>
						</table>
						</div>

						<p><a href="#"><img
							src="images/SerachPatients.gif" alt="Search Vehicles" width="100"
							height="16" border="0"></a></p>
						</td>
					</tr>
				</table>
				</td>
				</form:form>
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
