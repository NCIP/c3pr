<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<title>${tab.longTitle}</title>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}

function fireAction(action){
	document.form._action.value=action;
	document.form.submit();
}

</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->

<tags:searchStudy action="searchStudy"/>

<tabs:body title="Edit Study : ${command.trimmedShortTitleText}">
<form:form method="post" name="studyForm">
	<table border="0" id="table1" cellspacing="10" width="100%">
		<tr>
		 <td valign="top" width="30%">
			<studyTags:studySummary />
		</td>
		<td width="40%" valign="top">
		<tabs:levelTwoTabs tab="${tab}" flow="${flow}"/>
			<tabs:division id="Editing">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="details">
					<tr>
						<td align="left" width="50%" border="0" valign="top" class="contentAreaL">
						<form name="form2" method="post" action="" id="form1">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<td width="50%" valign="top" class="contentAreaL">
								<br>
								<table width="50%" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td class="label">Short Title:</td>
										<td><form:textarea path="shortTitleText" rows="2"
											cols="30" /></td>
										<td width="15%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Long
										Title:</td>
										<td><form:textarea path="longTitleText" rows="2" cols="30" /></td>
										<td width="15%"><em><span class="red"><form:errors
											path="longTitleText" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label">Precis Text:</td>
										<td><form:textarea path="precisText" rows="2" cols="30" /></td>
										<td width="15%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label">Description Text:</td>
										<td><form:textarea path="descriptionText" rows="2"
											cols="30" /></td>
										<td width="15%"></td>
									</tr>
								</table>
								<td>
								<table width="50%" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td class="label">Target Accrual Number:</td>
										<td><form:input path="targetAccrualNumber" size="34" /></td>
										<td width="10%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Status:</td>
										<td><form:select path="status">
											<option value="">--Please Select-- <form:options
												items="${statusRefData}" itemLabel="desc" itemValue="desc" />
										</form:select></td>
										<td width="10%"><em><span class="red"><form:errors
											path="status" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em><strong>Disease:</strong>
										<td><form:select path="diseaseCode">
											<option value="">--Please Select-- <form:options
												items="${diseaseCodeRefData}" itemLabel="desc"
												itemValue="desc" />
										</form:select></td>
										<td width="10%"><em><span class="red"><form:errors
											path="diseaseCode" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><em></em>Monitor:</td>
										<td><form:select path="monitorCode">
											<option value="">--Please Select-- <form:options
												items="${monitorCodeRefData}" itemLabel="desc"
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
											<option value="">--Please Select-- <form:options
												items="${phaseCodeRefData}" itemLabel="desc" itemValue="desc" />
										</form:select></td>
										<td width="10%"><em><span class="red"><form:errors
											path="phaseCode" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Sponsor:</td>
										<td><form:select path="sponsorCode">
											<option value="">--Please Select-- <form:options
												items="${sponsorCodeRefData}" itemLabel="desc"
												itemValue="desc" />
										</form:select></td>
										<td width="10%"><em><span class="red"><form:errors
											path="sponsorCode" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Randomized</td>
										<td><form:select path="randomizedIndicator">
											<form:options items="${randomizedIndicatorRefData}"
												itemLabel="desc" itemValue="code" />
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
												<form:options items="${multiInstitutionIndicatorRefData}"
													itemLabel="desc" itemValue="code" />
											</form:select></td>
										<td width="15%"></td>
										</tr>
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>
										<tr>
											<td class="label">Blinded</td>
											<td><form:select path="blindedIndicator">
												<form:options items="${blindedIndicatorRefData}"
													itemLabel="desc" itemValue="code" />
											</form:select></td>
											<td width="15%"></td>
										</tr>
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>
										<tr>
											<td class="label"><span class="red">*</span><em></em>Type:</td>
											<td><form:select path="type">
												<option value="">--Please Select-- <form:options
													items="${typeRefData}" itemLabel="desc" itemValue="code" />
											</form:select></td>
											<td width="15%"><em><span class="red"><form:errors
												path="type" /></em></span></td>
									</tr>
								</table>
							</td>
						</table>
						</form>
						</td>
					</tr>
				</table>
			</tabs:division>
		</td>
		<td valign="top" width="30%">
			<studyTags:subjectAssignments />
		</td>
		</tr>
	</table>
</form:form>
</tabs:body>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
