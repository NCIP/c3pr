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
	document.getElementById('command').targetPage.name='_noname';
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
<table border="0" cellspacing="0" cellpadding="0"">
	<tr>
		<td>
		<p id="instructions">
			Add Epochs & Arms associated with the Study <a href="javascript:fireAction('addEpoch','0');"><img
				src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Epoch"></a><br>
		</p>
		<br>
		<table border="0" cellspacing="0" cellpadding="0" id="mytable">
		<tr>
			<th scope="col" align="left"></th>
			<th scope="col" align="left"><b>Epoch <span class="red">*</span></b></th>
			<th scope="col" align="left"><b>Description</b></th>
			<th scope="col" align="left"><b>add&nbsp;arms<span class="red">*</span></b></th>
			<th scope="col" align="left"></th>
		</tr>
		<c:forEach items="${command.epochs}" var="epoch" varStatus="status">
		<tr>
			<td class="alt"><a href="javascript:fireAction('removeEpoch',${status.index},'0');"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
			</td>
			<td class="alt"><form:input path="epochs[${status.index}].name" /></td>
			<td class="alt"><form:textarea path="epochs[${status.index}].descriptionText" cols="25" rows="1"/></td>
			<td class="alt"><a href="javascript:fireAction('addArm',${status.index},'0');"><img
				src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
			<td>
				<table border="0" cellspacing="0" cellpadding="0" id="table1" width="50%">
					<tr>
						<td class="alt">Arm </td>
						<td class="alt">Target&nbsp;Accrual&nbsp;Number</td>
						<td class="alt"></td>
					</tr>
					<c:forEach items="${epoch.arms}" var="arm" varStatus="statusArms">
					<tr>
						<td class="alt"><form:input path="epochs[${status.index}].arms[${statusArms.index}].name" cssClass="validate-notEmpty"/></td>
						<td class="alt"><form:input path="epochs[${status.index}].arms[${statusArms.index}].targetAccrualNumber" size="12" maxlength="10" cssClass="validate-numeric"/></td>
						<td class="alt"><a href="javascript:fireAction('removeArm',${status.index},${statusArms.index});"><img
							src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		</c:forEach>
		</table>
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