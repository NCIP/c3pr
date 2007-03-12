<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
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

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

				<td id="current">Create Subject - ${command.firstName}
				${command.lastName}</td>
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
									align="absmiddle"> 1. <a href="participant_add.htm">Subject
								Information </a><img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
									align="absmiddle"></span><span class="current"><img
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
						<!-- RIGHT CONTENT STARTS HERE --> <form:form method="post"
							action="createParticipant">
							<div><input type="hidden" name="_page" value="1"></div>
							<strong>Step 2. Address Information </strong>
							(<span class="red">*</span>
							<em>Required Information </em>)<br>
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
											Street Address:</td>
											<td><form:input path="address.streetAddress" /></td>
											<td width="15%"><span class="red"><form:errors path="address.streetAddress" /><em></em></span></td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>
											City:</td>
											<td><form:input path="address.city" /></td>
											<td width="15%"><span class="red"><form:errors path="address.city" /><em></em></span></td>
										</tr>
										<tr>
											<td class="label"><span class="data"><span
												class="red">*</span><em></em> State:</span></td>
											<td><form:input path="address.stateCode" /></td>
											<td><span class="red">*</span><em></em><strong>Zip:</strong>
											<form:input path="address.postalCode" /> <a href="#"
												onClick="parent.OpenWins('searchZip.htm','searchZip',420,206,1);return false;"><img
												src="images/b-searchZip.gif" alt="Search Zip" width="48"
												height="11" border="0" align="absmiddle"></a> <a href="#"><img
												src="images/b-questionL.gif" alt="What's This?" width="15"
												height="11" border="0" align="absmiddle"></a></td>
										</tr>
										<tr>
											<td class="label"><em></em><em></em> Country:</td>
											<td><form:input path="address.countryCode" /></td>
											<td width="15%"><span class="red"><form:errors path="address.countryCode" /><em></em></span></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							<hr align="left" width="95%">
							<table width="700" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
									<td align="center" colspan="3"><!-- action buttons begins -->
									<table cellpadding="4" cellspacing="0" border="0">
										<tr>
											<td><input type="image" name="_target0"
												src="<tags:imageUrl name="b-prev.gif"/>" border="0" alt="goto previous page">
											<input type="image" name="_target2"
												src="<tags:imageUrl name="b-continue.gif"/>" border="0"
												alt="continue to next page"></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</div>
						</form:form></td>
						<!-- LEFT CONTENT ENDS HERE -->
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
