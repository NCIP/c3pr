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
										<div><input type="hidden" name="_page" value="1"></div>

										<br>
										<br>
										<table width="700" border="0" cellspacing="0" cellpadding="0"
											id="details">
											<tr>

												<table width="700" border="0" cellspacing="0"
													cellpadding="0" id="table1">
													<tr>
														<td width="150" align="right" class="label"><span
															class="red">*</span><em></em> Street Address:&nbsp;</td>
														<td align="left"><form:input path="address.streetAddress" />&nbsp;&nbsp;&nbsp;<span
															class="red"><form:errors path="address.streetAddress" /><em></em></span></td>
													</tr>
													<tr>
														<td width="150" align="right" class="label"><span
															class="red">*</span><em></em> City:&nbsp;</td>
														<td align="left"><form:input path="address.city" />&nbsp;&nbsp;&nbsp;<span
															class="red"><form:errors path="address.city" /><em></em></span></td>
													</tr>
													<tr>
														<td width="150" align="right" class="label"><span
															class="data"><span class="red">*</span><em></em>
														State:&nbsp;</span></td>
														<td align="left"><form:input path="address.stateCode" />&nbsp;&nbsp;&nbsp;<span
															class="red"><form:errors path="address.stateCode" /><em></em></span>&nbsp;&nbsp;
														<strong>Zip:&nbsp;</strong><form:input
															path="address.postalCode" /></td>
													</tr>
													<tr>
														<td width="150" align="right" class="label"><em></em><em></em>
														Country:&nbsp;</td>
														<td align="left"><form:input path="address.countryCode" />&nbsp;&nbsp;&nbsp;<span
															class="red"><form:errors path="address.countryCode" /><em></em></span></td>
													</tr>
												</table>

											</tr>
										</table>
									</form:form></td>
									<!-- LEFT CONTENT ENDS HERE -->
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div id="copyright"></div>
					<!-- MAIN BODY ENDS HERE --> </tabs:division> </form:form> </tabs:body>
</body>
</html>
