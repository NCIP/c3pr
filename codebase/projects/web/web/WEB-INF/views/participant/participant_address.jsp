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
		<tabs:division id="subject-details">

			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
					<td><!-- TABS LEFT START HERE -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>

							<!-- LEFT CONTENT STARTS HERE -->
							<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
							<!-- RIGHT CONTENT STARTS HERE --> <form:form method="post"
								action="createParticipant">
								<div><input type="hidden" name="_page" value="1"></div>
								<td>

								<table width="700" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td width="150" align="right" ><span class="red">*</span><em></em>
										<b>Street Address:</b>&nbsp;</td>
										<td width align="left"><form:input size="58"
											path="address.streetAddress" />&nbsp;&nbsp;&nbsp;<span
											class="red"><form:errors path="address.streetAddress" /><em></em></span></td>
									</tr>
									<tr>
										<td width="150" align="right"><span class="red">*</span><em></em>
										<b>City:</b>&nbsp;</td>
										<td align="left"><form:input path="address.city" />&nbsp;&nbsp;&nbsp;<span
											class="red"><form:errors path="address.city" /><em></em></span></td>
									</tr>
									<tr>
										<td width="150" align="right" ><span class="data"><span
											class="red">*</span><em></em> <b>State:</b>&nbsp;</span></td>
										<td align="left"><form:input path="address.stateCode" />&nbsp;&nbsp;&nbsp;<span
											class="red"><form:errors path="address.stateCode" /><em></em></span>&nbsp;&nbsp;
										<b>Zip:</b>&nbsp;<form:input
											path="address.postalCode" /></td>
									</tr>
									<tr>
										<td width="150" align="right" ><em></em><em></em>
										<b>Country:</b>&nbsp;</td>
										<td align="left"><form:input path="address.countryCode" />&nbsp;&nbsp;&nbsp;<span
											class="red"><form:errors path="address.countryCode" /><em></em></span></td>
									</tr>
								</table>
							</form:form></td>
							<!-- LEFT CONTENT ENDS HERE -->
						</tr>
					</table>
				</tr>
			</table>
			<!-- MAIN BODY ENDS HERE -->
		</tabs:division>
	</form:form>
</tabs:body>
</body>
</html>
