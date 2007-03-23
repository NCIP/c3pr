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
function submitSearchPage(){
	document.getElementById("searchForm").submit();
}
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}

function fireAction(action, selectedEpoch, selectedArm){
		document.getElementsByName('_target4')[0].name='_target3';
		document.form._action.value=action;
		document.form._selectedEpoch.value=selectedEpoch;
		document.form._selectedArm.value=selectedArm;
		document.form.submit();
	}
</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->
<studyTags:searchStudy action="searchStudy"/>

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
				<table width="60%" border="0" cellspacing="0" cellpadding="0" id="table1">
					<td width="100%" valign="top">
						<table width="70%" border="0" cellspacing="10" cellpadding="0"
							id="table1">
						<tr align="center" class="label">
							<td width="5%" align="center"></td>
							<td width="20%" align="center">Epoch <span class="red">*</span></td>
							<td width="20%" align="center">Description</td>
							<td width="3%" align="center">(add arms)(<span class="red">*</span></td>
							<td width="50%" align="center">[Name, Target Accrual Number]</td>

						</tr>
						<c:forEach items="${command.epochs}" var="epoch" varStatus="status">
						<tr align="center" class="results">
							<td width="8%"><a href="javascript:fireAction('removeEpoch',${status.index},'0');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
							</td>
							<td width="20%"><form:input path="epochs[${status.index}].name" /></td>
							<td width="20%"><form:input path="epochs[${status.index}].descriptionText" /></td>
							<td width="3%"><a href="javascript:fireAction('addArm',${status.index},'0');"><img
								src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
							<td width="50%" >
								<table width="100%" border="1" cellspacing="0" cellpadding="0"
									id="table1">
									<c:forEach items="${epoch.arms}" var="arm" varStatus="statusArms">

									<tr align="center" class="results">
										<td width="8%"><a href="javascript:fireAction('removeArm',${status.index},${statusArms.index});"><img
											src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
										</td>
										<td ><form:input path="epochs[${status.index}].arms[${statusArms.index}].name" /></td>
										<td ><form:input path="epochs[${status.index}].arms[${statusArms.index}].targetAccrualNumber" /></td>
									</tr>
									</c:forEach>
								</table>

							</td>
						</tr>
						</c:forEach>
						<tr>
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
							class="heightControl"></td>
						</tr>
						<tr>
							<td align="center"><a href="javascript:fireAction('addEpoch','0');"><img
								src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Epoch"></a>
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
