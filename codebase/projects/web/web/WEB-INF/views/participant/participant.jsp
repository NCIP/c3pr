<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>
</head>
<body>
<tabs:body title="${flow.name} ">
	<form:form method="post" cssClass="standard">
		<tabs:tabFields tab="${tab}" />
		<div><tabs:division id="subject-details">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td><!-- TABS LEFT START HERE -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr>

									<!-- LEFT CONTENT STARTS HERE -->
									<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
									<!-- RIGHT CONTENT STARTS HERE --> <form:form method="post"
										action="createParticipant">
										<div><input type="hidden" name="_page" value="0"></div>

										<table width="700" border="0" cellspacing="0" cellpadding="0"
											id="details">
											<tr>
												<td width="200" valign="top">
												<table width="308" border="0" cellspacing="0"
													cellpadding="0" id="table1">
													<tr>
														<td><img src="<tags:imageUrl name="spacer.gif"/>"
															width="1" height="1" class="heightControl"></td>
														<td width="65%"><img
															src="<tags:imageUrl name="spacer.gif"/>" width="1"
															height="1" class="heightControl"></td>
													</tr>
													<tr>
														<td align="right" class="label"><span class="red">*</span><em></em>
														First Name: &nbsp;</td>
														<td align="left"><form:input path="firstName" /><span
															class="red">&nbsp;&nbsp;&nbsp;<form:errors
															path="firstName" /></span><em></em></td>
													</tr>
													<tr>
														<td align="right" class="label"><span class="red">*</span><em></em>
														Last Name:&nbsp;</td>
														<td align="left"><form:input path="lastName" /><span
															class="red">&nbsp;&nbsp;&nbsp;<form:errors
															path="lastName" /></span><em></em></td>
													</tr>
													<tr>
														<td align="right" class="label"><span class="red">*</span>
														<em></em> Gender: &nbsp;</td>
														<td align="left"><form:select
															path="administrativeGenderCode">
															<form:options items="${administrativeGenderCode}"
																itemLabel="desc" itemValue="code" />
														</form:select></td>
													</tr>
												</table>
												</td>
												<td width="200" valign="top">
												<table width="308" border="0" cellspacing="0"
													cellpadding="0" id="table1">
													<tr>
														<td><img src="<tags:imageUrl name="spacer.gif"/>"
															width="1" height="1" class="heightControl"></td>
														<td><img src="<tags:imageUrl name="spacer.gif"/>"
															width="1" height="1" class="heightControl"></td>
													</tr>
													<tr>
														<td align="right" class="label"><span class="red">&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
														Date &nbsp;</td>
														<td align="left" valign="top"><form:input path="birthDate" />&nbsp;<a
															href="#"
															onClick="cal1.select(document.getElementById('birthDate'),'anchor1','MM/dd/yyyy');return false;"
															name="anchor1" id="anchor1"><img
															src="<tags:imageUrl name="b-calendar.gif"/>"
															alt="Calendar" width="17" height="16" border="0"
															align="absmiddle"></a>&nbsp;&nbsp;&nbsp;<span class="red"><form:errors
															path="birthDate" /><em></em></span></td>
														</tr>
													<tr>
														<td align="right" class="label"><span class="red">*</span><em></em>Ethnicity:
														&nbsp;</td>
														<td align="left"><form:select path="ethnicGroupCode">
															<form:options items="${ethnicGroupCode}" itemLabel="desc"
																itemValue="code" />
														</form:select></td>
													</tr>
													<tr>
														<td align="right" class="label"><span class="red">*</span><em></em>Race(s): &nbsp;
														</td>
														<td align="left"><form:select path="raceCode">
															<form:options items="${raceCode}" itemLabel="desc"
																itemValue="code" />
														</form:select></td>
													</tr>

												</table>
												</td>
											</tr>
										</table>

										<hr align="left" width="95%">
										<table width="700" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td align="center"><span class="red">*</span><em></em><B>
												Type:</td>
												<td align="center"><span class="red">*</span><em></em><B>
												Source:</td>
												<td align="center"><span class="red">*</span><em></em><B>
												Value:</td>
												<td align="center"><B>Primary:</td>
											</tr>

											<c:forEach var="index" begin="0" end="4">
												<tr>
													<td align="center"><form:select
														path="identifiers[${index}].type">
														<form:options items="${identifiersTypeRefData}"
															itemLabel="desc" itemValue="code" />
													</form:select></td>
													<td align="center"><form:select
														path="identifiers[${index}].source">
														<form:options items="${source}" itemLabel="name"
															itemValue="name" />
													</form:select></td>
													<td align="center"><form:input
														path="identifiers[${index}].value" /></td>

													<td align="center"><form:radiobutton
														path="identifiers[${index}].primaryIndicator" value="true" /></td>
												</tr>

											</c:forEach>

										</table>


									</form:form></td>

									<!-- LEFT CONTENT ENDS HERE -->
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<br>
					</td>
					<!-- LEFT CONTENT ENDS HERE -->
				</tr>
			</table>

			<!-- MAIN BODY ENDS HERE -->
		</tabs:division>
	</form:form>
</tabs:body>
</body>
</html>
