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
<link href="calendar-blue.css" rel="stylesheet" type="text/css" />

<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function fireAction(action, selected){
	document.getElementById("_action").value=action;
	document.getElementById("_selected").value=selected;
	document.getElementById('targetPage').name='_noname';
	
	// need to disable validations while removing
	
	identifiersList = 'identifiers';
		source = 'identifiers['+selected+'].source';
	$(source).className='none';
	type = 'identifiers['+selected+'].type';
	$(type).className='none';
	id = 'identifiers['+selected+'].value';
	$(id).className='none';
	document.getElementById("command").submit();
}
</script>
</head>
<body>
<form:form method="post" cssClass="standard">
	<div><input type="hidden" name="_action" id="_action" value=""> <input
		type="hidden" name="_selected" id="_selected" value=""><input
		type="hidden" name="_page" id="_page" value="0"></div>
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
								<!-- RIGHT CONTENT STARTS HERE -->

								<table width="80%" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="40%" valign="top">
										<table width="100%" border="0" cellspacing="1" cellpadding="1"
											id="table1">
											<tr>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
												<td width="75%"><img
													src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
											</tr>
											<tr>
												<td align="right"><span class="red">*</span><em></em> <b>First
												Name: &nbsp;</b></td>
												<td align="left"><form:input path="firstName"
													cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
											</tr>
											<tr>
												<td align="right"><span class="red">*</span><em></em> <b>Last
												Name:</b>&nbsp;</td>
												<td align="left"><form:input path="lastName"
													cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
											</tr>
											<tr>
												<td align="right"><span class="red">*</span> <em></em> <b>Gender:</b>
												&nbsp;</td>
												<td align="left"><form:select
													path="administrativeGenderCode"
													cssClass="validate-notEmpty">
													<option value="">--Please Select--</option>
													<form:options items="${administrativeGenderCode}"
														itemLabel="desc" itemValue="code" />
												</form:select></td>
											</tr>
										</table>
										</td>
										<td width="40%" valign="top">
										<table width="100%" border="0" cellspacing="1" cellpadding="1"
											id="table1">
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
													<option value="">--Please Select--</option>
													<form:options items="${ethnicGroupCode}" itemLabel="desc"
														itemValue="code" />
												</form:select></td>
											</tr>
											<tr>
												<td align="right"><em></em><b>Race(s):</b> &nbsp;</td>
												<td align="left"><form:select path="raceCode">
													<option value="">--Please Select--</option>
													<form:options items="${raceCode}" itemLabel="desc"
														itemValue="code" />
												</form:select></td>
											</tr>

										</table>
										</td>
									</tr>
								</table>

								<hr align="left" width="95%">

								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
										<p id="instructions">Add Identifiers associated with the
										Subject <a href="javascript:fireAction('addIdentifier','0');"><img
											src="<tags:imageUrl name="checkyes.gif"/>" border="0"
											alt="Add another Identifier"></a><br>
										</p>
										<table id="mytable" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<th class="scope=" col" align="left"><b>Assigning Authority<span
													class="red">*</span></b></th>
												<th scope="col" align="left"><b>Identifier Type<span
													class="red">*</span></b></th>
												<th scope="col" align="left"><b>Identifier<span class="red">*</span></b></th>
												<th scope="col" align="left"><b>Primary&nbsp;Indicator</b></th>
												<th class="specalt" scope="col" align="left"></th>
											</tr>
											<c:forEach items="${command.identifiers}" varStatus="status">
												<tr>
													<td class="alt"><form:select
														path="identifiers[${status.index}].source"
														cssClass="validate-notEmpty">
														<option value="">--Please Select--</option>
														<form:options items="${source}" itemLabel="name"
															itemValue="name" />
													</form:select></td>
													<td class="alt"><form:select
														path="identifiers[${status.index}].type"
														cssClass="validate-notEmpty">
														<option value="">--Please Select--</option>
														<form:options items="${identifiersTypeRefData}"
															itemLabel="desc" itemValue="desc" />
													</form:select></td>
													<td class="alt"><form:input
														path="identifiers[${status.index}].value"
														cssClass="validate-notEmpty" /></td>
													<td class="alt"><form:radiobutton
														path="identifiers[${status.index}].primaryIndicator"
														value="true" /></td>
													<td class="tdalt"><a
														href="javascript:fireAction('removeIdentifier',${status.index});"><img
														src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
												</tr>
											</c:forEach>
										</table>
										</td>
									</tr>
								</table>



								</td>

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
