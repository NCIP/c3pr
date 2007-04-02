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

function fireAction(action, selected){
	document.getElementsByName('_target3')[0].name='_target2';
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form.submit();
}
function clearField(field){
	field.value="";
}

</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->

<tags:search action="searchStudy"/>

<tabs:body title="Edit Study">
<form:form method="post" id="form" name="form">
	<table border="0" id="table1" cellspacing="10" width="100%">
		<tr>
		 <td valign="top" width="30%">
			<studyTags:studySummary />
		</td>
		<td width="40%" valign="top">
		<tabs:levelTwoTabs tab="${tab}" flow="${flow}"/>
			<tabs:division id="Editing">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="table1">

					<td width="70%" valign="top">
						<table width="100%" border="0" cellspacing="10" cellpadding="0"
							id="table1">

						<tr align="center" class="label">
							<td width="5%" align="center"></td>
							<td width="11%" align="center">HealthCare Site<span class="red">*</span></td>
							<td width="11%" align="center">Status<span class="red">*</span></td>
							<td width="11%" align="center">Role<span class="red">*</span></td>
							<td width="17%" align="center">Start Date <br><em> (mm/dd/yyyy)</em><span class="red">*</span> </td>
							<td width="17%" align="center">IRB Approval<br>Date<em> (mm/dd/yyyy)</em><span class="red">*</span></td>
						</tr>
						<c:forEach items="${command.studySites}" varStatus="status">
							<tr align="center" class="results">
								<td width="5%"><a href="javascript:fireAction('removeStudySite',${status.index});"><img
									src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								<td width="11%"><form:select path="studySites[${status.index}].site">
									<form:options items="${healthCareSitesRefData}" itemLabel="name" itemValue="id" />
									</form:select></td>
								<td width="11%"><form:select path="studySites[${status.index}].statusCode">
									<form:options items="${studySiteStatusRefData}" itemLabel="desc"
										itemValue="desc" />
								</form:select></td>
								<td width="11%"><form:select path="studySites[${status.index}].roleCode">
									<form:options items="${studySiteRoleCodeRefData}" itemLabel="desc"
										itemValue="desc" />
								</form:select></td>
								<td width="17%"><form:input
									path="studySites[${status.index}].startDate" />&nbsp;<a href="#"
										onClick="cal1.select(document.getElementById('studySites[${status.index}].startDate'),'anchor1','MM/dd/yyyy');return false;" name="anchor1" id="anchor1"><img
									src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar"
									height="16" border="0"></a></td>
								<td width="17%"><form:input
									path="studySites[${status.index}].irbApprovalDate" />&nbsp;<a href="#"
										onClick="cal1.select(document.getElementById('studySites[${status.index}].irbApprovalDate'),'anchor1','MM/dd/yyyy');return false;" name="anchor1" id="anchor1"><img
									src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar"
									height="16" border="0"></a></td>
							</tr>
						</c:forEach>
						<tr>
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
							class="heightControl"></td>
						</tr>
						<tr>
							<td align="center"><a href="javascript:fireAction('addStudySite','0');"><img
								src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Study Site"></a>
							</td>
						</tr>
						</table>
					</td>

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