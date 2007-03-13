<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function doNothing(){
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
							align="absmiddle"> 1. Select Study <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						2. Select Subject <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						3. Enrollment Details <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>"
							width="3" height="16" align="absmiddle"> 4. Check Eligibility <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						5. Stratify <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
							height="16" align="absmiddle"> 6. Review and Submit <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"></span></td>
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
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
						<td id="current">Search Subject</td>
						<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
					</tr>
					<tags:search action="/c3pr/pages/registration/searchSubjectInRegister?studySiteId=${studySiteId}"/>										
				</table>
				</td>
			</tr>

			<tr>
				<td>
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
											align="absmiddle"> 1. Subject Information <img
											src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
											align="absmiddle"></span><span class="tab"><img
											src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
											align="absmiddle"> 2. Address Information <img
											src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
											align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
											height="16" align="absmiddle"> 3. Review and Submit <img
											src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
											align="absmiddle"></span></td>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
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
								<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
								<!-- RIGHT CONTENT STARTS HERE --> <form:form method="post"
									action="createParticipant">
									<div><input type="hidden" name="_page" value="0">
									</div>
									<strong>Step 1 (a). Add Subject Details </strong>
							(<span class="red">*</span>
									<em>Required Information </em>)<br>
									<br>
									<div class="review">
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
													<td width="65%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em> First
													Name</td>
													<td><form:input path="firstName" /></td>
													<td width="10%"><span class="red"><form:errors
														path="firstName" /><em></em></span></td>
												</tr>
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>
													Last Name</td>
													<td><form:input path="lastName" /></td>
													<td width="10%"><span class="red"><form:errors
														path="lastName" /><em></em></span></td>
												</tr>
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span> <em></em>
													Gender</td>
													<td><form:select path="administrativeGenderCode">
														<option value="">--Please Select--
														<form:options items="${administrativeGenderCode}"
															itemLabel="desc" itemValue="code" />
													</form:select></td>
													<td width="10%"><span class="red"><form:errors
														path="administrativeGenderCode" /><em></em></span></td>
												</tr>
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
											</table>
											</td>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><em></em>Birth Date</td>
													<td valign="top"><form:input path="birthDate" />&nbsp;<a
														href="#"
														onClick="cal1.select(document.getElementById('birthDate'),'anchor1','MM/dd/yyyy');return false;"
														name="anchor1" id="anchor1"><img
														src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17"
														height="16" border="0" align="absmiddle"></a><em>  (mm/dd/yyy)</em></td>
												</tr>
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label">Ethnicity</td>
													<td><form:select path="ethnicGroupCode">
														<option value="">--Please Select--
														<form:options items="${ethnicGroupCode}" itemLabel="desc"
															itemValue="code" />
													</form:select></td>
												</tr>
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label">Race
													</td>
													<td><form:select path="raceCode">
														<option value="">--Please Select--
														<form:options items="${raceCode}" itemLabel="desc"
															itemValue="code" />
													</form:select></td>
												</tr>
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>

											</table>
											</td>
										</tr>
									</table>
									<hr align="left" width="95%">
									<strong>Step 1 (b). Add Identifiers </strong>
									<br><br>
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="table1">
										<tr>
											<td align="center"><span class="red">*</span><em></em><B>
											Type:</td>
											<td align="center"><span class="red">*</span><em></em><B>
											Value:</td>
											<td align="center"><span class="red">*</span><em></em><B>
											Source:</td>
											<td align="center"><B>Primary:</td>
										</tr>

										<c:forEach var="index" begin="0" end="4">
											<tr>
												<td align="center"><form:select
													path="identifiers[${index}].type">
													<form:options items="${identifiersTypeRefData}"
														itemLabel="desc" itemValue="code" />
												</form:select></td>
												<td align="center"><form:input
													path="identifiers[${index}].value" /></td>
												<td align="center"><form:select
													path="identifiers[${index}].source">
													<form:options items="${source}" itemLabel="name"
														itemValue="name" />
												</form:select></td>

												<td align="center"><form:radiobutton
													path="identifiers[${index}].primaryIndicator" value="true" /></td>
											</tr>

										</c:forEach>

									</table>

									<hr align="left" width="95%">
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr align="center">
											<td colspan="4"><br>
											<br>
										<tr>
											<td align="center" colspan="3"><!-- action buttons begins -->
											<table cellpadding="4" cellspacing="0" border="0">
												<tr>
													<td><input type="image" name="_target1"
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
				</td>
			</tr>
		</table>
		<div id="copyright"></div>
		</div>
		<!-- MAIN BODY ENDS HERE -->
</body>
</html>
