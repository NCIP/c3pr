<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3Pr V2</title>
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<script language="JavaScript" type="text/JavaScript">

function validatePage(){
	if(document.getElementById("longTitleText") != null)
		return true;
	else
		return false;
}

function trim(s) {
   if (s.length >40)
   {
     s = s.substring(0,39);
     s +="...";
   }
   return s;

}
</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<form:form name="searchDetailsForm" method="post" >
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
		<td id="current">Short Title: ${command.trimmedShortTitleText}</td>
		<td>	</td>
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
							src="<tags:imageUrl name="tabWhiteL.gif"/>" width="3" height="16"
							align="absmiddle">
						1.study details <img src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span> <span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						2.identifiers <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						3.study site <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						4. study design <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle">
						5. review and submit <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals2"><!-- RIGHT CONTENT STARTS HERE -->
					<div><input type="hidden" name="_page" value="0"></div>

					<br>
					<strong>Step 1. Study Details </strong> (<span class="red">*</span>
					<em>Required Information </em>)<br>
					<br>

					<table width="75%" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="50%" valign="top">

							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label">Short Title:</td>
									<td><form:textarea path="shortTitleText" rows="2"
										cols="50" /></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Long Title:</td>
									<td><form:textarea path="longTitleText" rows="5" cols="50" /></td>
									<td width="15%"><em><span class="red"><form:errors path="longTitleText" /></em></span></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Precis Text:</td>
									<td><form:textarea path="precisText" rows="2" cols="50" /></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Description Text:</td>
									<td><form:textarea path="descriptionText" rows="3"
										cols="50" /></td>
									<td width="15%"></td>
								</tr>
							</table>
							</td>
							<td width="100%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
							<table width="80%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label">Target Accrual Number:</td>
									<td ><form:input path="targetAccrualNumber"
										size="34" /></td>
									<td width="10%"></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Status:</td>
									<td><form:select path="status">
										<option value="">--Please Select--
										<form:options items="${statusRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors path="status"/></em></span></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em><strong>Disease:</strong>
									<td><form:select path="diseaseCode">
										<option value="">--Please Select--
										<form:options items="${diseaseCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors path="diseaseCode"/></em></span></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><em></em>Monitor:</td>
									<td><form:select path="monitorCode">
										<option value="">--Please Select--
										<form:options items="${monitorCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Phase:</td>
									<td><form:select path="phaseCode">
										<option value="">--Please Select--
										<form:options items="${phaseCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors path="phaseCode" /></em></span></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Sponsor:</td>
									<td><form:select path="sponsorCode">
										<option value="">--Please Select--
										<form:options items="${sponsorCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors path="sponsorCode" /></em></span></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Randomized
									Indicator</td>
									<td><form:select path="randomizedIndicator">
										<form:options items="${randomizedIndicatorRefData}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
									<td width="10%"></td>
								</tr>

								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Multi
									Institution:</td>
									<td><form:select path="multiInstitutionIndicator">
										<form:options items="${multiInstitutionIndicatorRefData}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Blinded Indicator:</td>
									<td><form:select path="blindedIndicator">
										<form:options items="${blindedIndicatorRefData}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Type:</td>
									<td><form:select path="type">
										<option value="">--Please Select--
										<form:options items="${typeRefData}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
									<td width="15%"><em><span class="red"><form:errors path="type" /></em></span></td>
								</tr>

							</table>
							</td>
						<tr>
							<td align="center" colspan="3"><!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
								<td colspan=2 valign="top"><br>
									<br>
									<input type="image" name="_target1" src="<tags:imageUrl name="b-continue.gif"/>" border="0"
										alt="continue to next page">
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</form:form> <!-- LEFT CONTENT ENDS HERE -->
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
