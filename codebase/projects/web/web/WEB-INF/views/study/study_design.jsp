<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script>
function fireAction(action, selectedEpoch, selectedArm){
	document.getElementsByName('_target7')[0].name='_target6';
	document.studyDesignForm._action.value=action;
	document.studyDesignForm._selectedEpoch.value=selectedEpoch;
	document.studyDesignForm._selectedArm.value=selectedArm;
	document.studyDesignForm.submit();
}
</script>
</head>
<body>
<form:form name="studyDesignForm" method="post" cssClass="standard">
<div>
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selectedEpoch" value="">
	<input type="hidden" name="_selectedArm" value="">
</div>
<tabs:tabFields tab="${tab}" />
<div><tabs:division id="study-design">
<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
		<span class="red">*</span>
			<em>Required Information </em>)<br>
			<br>
			<table width="60%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
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
							</td></tr>
						</table>
					</table>
				</td>
			</table>
		</td>
	</tr>
</table>
<!-- MAIN CONTENT ENDS HERE -->
</tabs:division>
</form:form>
</table>
</div>
</body>
</html>