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
function add(){
var action = confirm ("You have not completed adding this protocol.\r\rStarting over will lose current protocol data?")
if (action){
	parent.window.location="reg_enroll_patient.htm";
}}
function addPatient(){
var action = confirm ("Are you sure you want to create this participant?")
if (action){
	alert("Subject successfully created.");
	parent.window.location="participant_details.htm";
}}
</script>
</head>
<body>
<tabs:body title="${flow.name} ">
<form:form method="post">
<input type="hidden" name="_finish" value="true"/>		
<div><tabs:division id="subject-review-submit">


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
										<div><input type="hidden" name="_page" value="1"></div>
										<strong>Details </strong>
										<br>
										<table width="700" border="0" cellspacing="0" cellpadding="0"
											id="details">
											<tr>
												<td width="50%" valign="top">
												<table width="308" border="0" cellspacing="0"
													cellpadding="0" id="table1">
													<tr>
														<td><img src="<tags:imageUrl name="spacer.gif"/>"
															width="1" height="1" class="heightControl"></td>
														<td><img src="<tags:imageUrl name="spacer.gif"/>"
															width="1" height="1" class="heightControl"></td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em>First Name:&nbsp;</td>
														<td align="left">${command.firstName}</td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em>Last Name:&nbsp;</td>
														<td align="left">${command.lastName}</td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em>Gender:&nbsp; </td>
														<td align="left">${command.administrativeGenderCode}</td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em>Subject MRN:&nbsp;</td>
														<td align="left">${command.primaryIdentifier }</td>
													</tr>
												</table>
												</td>
												<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
												<table width="308" border="0" cellspacing="0"
													cellpadding="0" id="table1">
													<tr>
														<td><img src="<tags:imageUrl name="spacer.gif"/>"
															width="1" height="1" class="heightControl"></td>
														<td><img src="<tags:imageUrl name="spacer.gif"/>"
															width="1" height="1" class="heightControl"></td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em>Birth Date:&nbsp;</td>
														<td align="left"valign="top">${command.birthDateStr}</td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em>Ethnicity:&nbsp;</td>
														<td align="left">${command.ethnicGroupCode}</td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em>Race(s):&nbsp;</td>
														<td align="left">${command.raceCode}</td>
													</tr>
												</table>
												</td>
											</tr>
										</table>
										<a href="reg_enroll_patient.htm"><img
											src="<tags:imageUrl name="b-edit.gif"/>" alt="Edit"
											width="39" height="16" border="0"></a>

										<hr align="left" width="95%">
										<strong><br>
										Address</strong>
										<br>
										<br>
										<table width="700" border="0" cellspacing="0" cellpadding="0"
											id="details">
											<tr>
												<td width="50%" valign="top">
												<table width="308" border="0" cellspacing="0"
													cellpadding="0" id="table1">
													<tr>
														<td align="right" class="label"><em></em> Street Address:&nbsp;</td>
														<td align="left" >${command.address.streetAddress}</td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em> City:&nbsp;</td>
														<td align="left">${command.address.city}</td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em> State:&nbsp;</td>
														<td align="left">${command.address.stateCode}<em></em><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Zip:&nbsp;</strong>${command.address.postalCode}</td>
													</tr>
													<tr>
														<td align="right" class="label"><em></em><em></em> Country:&nbsp;</td>
														<td align="left">${command.address.countryCode}</td>
													</tr>
												</table>
												</td>
											</tr>
										</table>
										</div>
										<a href=""><img src="<tags:imageUrl name="b-edit.gif"/>"
											alt="Edit" width="39" height="16" border="0"></a>
										<hr align="left" width="95%">
										<strong><br>
										Identifiers</strong>
										<br>
										<br>
										<div class="review"><br>
										<table width="50%" border="0" cellspacing="0" cellpadding="0"
											id="details">
											<tr>
												<td valign="top">
												<table width="100%" border="1" cellspacing="0"
													cellpadding="0" id="table1">
													<tr>
														<th align="left" width="40%" class="label">&nbsp;Assigning Authority:</th>
														<th align="left" width="30%" class="label">&nbsp;Identifier Type:</th>
														<th align="left" width="30%" class="label">&nbsp;Identifier:</th>
													</tr>
													<c:forEach items="${command.identifiers}" var="identifier">
														<c:if test="${identifier.type!=''}">
															<tr class="results">
																<td align="center">${identifier.source}</td>
																<td align="center">${identifier.type}</td>
																<td align="center">${identifier.value}</td>
															</tr>
														</c:if>
													</c:forEach>

												</table>
												</td>
											</tr>
										</table>
										</div>
										<br>
										<a href=""><img src="<tags:imageUrl name="b-edit.gif"/>"
											alt="Edit" width="39" height="16" border="0"></a>
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
			<!-- MAIN BODY ENDS HERE -->
		</tabs:division>
	</form:form>
</tabs:body>
</body>
</html>
