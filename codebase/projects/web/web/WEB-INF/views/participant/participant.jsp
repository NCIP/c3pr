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
<link href="calendar-blue.css" rel="stylesheet" type="text/css"/>

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

									<table width="60%" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="30%" valign="top">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" id="table1">
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
													<td width="65%"><img
														src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
												</tr>
												<tr>
													<td align="right"><span class="red">*</span><em></em> <b>First
													Name: &nbsp;</b></td>
													<td align="left"><form:input path="firstName" cssClass="validate-notEmpty" /><span
														class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
												</tr>
												<tr>
													<td align="right"><span class="red">*</span><em></em> <b>Last
													Name:</b>&nbsp;</td>
													<td align="left"><form:input path="lastName" cssClass="validate-notEmpty" /><span
														class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
												</tr>
												<tr>
													<td align="right"><span class="red">*</span> <em></em> <b>Gender:</b>
													&nbsp;</td>
													<td align="left"><form:select
														path="administrativeGenderCode" cssClass="validate-notEmpty">
														<option value="">--Please Select-- </option>
														<form:options items="${administrativeGenderCode}"
															itemLabel="desc" itemValue="code" />
													</form:select></td>
												</tr>
											</table>
											</td>
											<td width="30%" valign="top">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" id="table1">
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
												</tr>
												<tr>
													<td align="right"><span class="red">&nbsp;&nbsp;&nbsp;*</span><em></em><b>Birth
													Date: </b>&nbsp;</td>
													<td><form:input path="birthDate" cssClass="validate-date" />&nbsp;(mm/dd/yyyy)&nbsp;&nbsp;<span
														class="red"><em></em></span></td>
												</tr>
												<tr>
													<td align="right"><em></em><b>Ethnicity:</b> &nbsp;</td>
													<td align="left"><form:select path="ethnicGroupCode">
														<option value="">--Please Select-- </option>
														<form:options items="${ethnicGroupCode}" itemLabel="desc"
															itemValue="code" />
													</form:select></td>
												</tr>
												<tr>
													<td align="right"><em></em><b>Race(s):</b> &nbsp;</td>
													<td align="left"><form:select path="raceCode">
													<option value="">--Please Select-- </option>
														<form:options items="${raceCode}" itemLabel="desc"
															itemValue="code" />
													</form:select></td>
												</tr>

											</table>
											</td>
										</tr>
									</table>

									<hr align="left" width="95%">
									<table width="60%" border="0" cellspacing="0" cellpadding="0"
										id="table1">
										<tr>
											<th align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
												class="red">*</span><em></em><B> Assigning Authority</th>
											<th align="left">&nbsp;&nbsp;&nbsp;&nbsp;<span class="red">*</span><em></em><B>
											Identifier Type</th>
											<th align="left">&nbsp;&nbsp;<span class="red">*</span><em></em><B>
											Identifier</th>
											<th align="left"><B>Primary Indicator</th>
										</tr>

										<c:forEach var="index" begin="0" end="4">
											<tr>
												<td align="center"><form:select
													path="identifiers[${index}].source">
													<option value="">--Please Select-- </option>
													<form:options items="${source}" itemLabel="name"
														itemValue="name" />
												</form:select></td>
												<td align="center"><form:select
													path="identifiers[${index}].type">
													<option value="">--Please Select-- </option>
													<form:options items="${identifiersTypeRefData}"
														itemLabel="desc" itemValue="desc" />
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
</body>
</html>
