<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="registration" required="true" type="edu.duke.cabig.c3pr.domain.StudySubject"%>
<%@attribute  name="newReg" required="true"%>
<%@attribute  name="actionButtonLabel" required="true"%>
<%@attribute  name="requiresMultiSite" required="true"%>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<style type="text/css">
        .labelL { text-align: left; padding: 4px; font-weight: bold;}
</style>
<style type="text/css">
        .labelC { text-align: center; padding: 4px; font-weight: bold;}
</style>
<script>
function submitRandomization(){
	if(${registration.regWorkflowStatus!='REGISTERERD' && !empty registration.studySite.targetAccrualNumber && registration.studySite.targetAccrualNumber<=registration.studySite.currentAccrualCount}){
		confirmFlag=confirm("This registration will exceed the accrual ceiling at ${command.studySite.healthcareSite.name}. Do you want to continue?");
		if(!confirmFlag)
			return;
	}
	$('randomization').submit();
}
</script>
<tags:panelBox title="Randomize" boxId="RegHere">
	<form id="randomization" action="confirm?registrationId=${registration.id}" method="post">
		<c:choose>
		<c:when test="${requiresMultiSite}">
			<strong>Subject ${newRegistration?"registration":"transfer"} requires co-ordinationg 
			center approval. <c:if test="${registration.scheduledEpoch.requiresRandomization}">Arm assignment would take place at co-ordinating center.</c:if> <strong>
			<br>
			Please click on the button to send registration request. 
		</c:when>
		<c:otherwise>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
			<c:if test="${registration.studySite.study.randomizationType.name == 'PHONE_CALL'}">
				<strong><fmt:message key="REGISTRATION.RANDOMIZATION.PHONE_CALL"/></strong>
				<tr>
				<td class="labelR">Phone Number:</td><td>${registration.scheduledEpoch.epoch.randomization.phoneNumber}</td>
				</tr>
				<tr>
				<c:if test="${registration.studySite.study.stratificationIndicator}">
				<td class="labelR">Stratum Group:</td><td> ${registration.stratumGroup}</td>
				</c:if>
				<tr>
				<c:choose>
					<c:when test="${registration.studySite.study.blindedIndicator}">
						<td class="labelR">Enter Kit Number</td><td><input type="text" name="scheduledEpoch.scheduledArms[0].kitNumber" id="kitNumber" size="20" class="validate-notEmpty"/></td>
					</c:when>
					<c:otherwise>
						<td class="labelR">Select Arm:</td><td>
						<select name ="scheduledEpoch.scheduledArms[0].arm" class="validate-notEmpty">
							<option value="" selected>Please Select</option>
							<c:forEach items="${registration.scheduledEpoch.treatmentEpoch.arms}" var="arm">
							<option value="${arm.id}">${arm.name }</option>
							</c:forEach>
						</select></td>
					</c:otherwise>
				</c:choose>
				</tr>
			</c:if>
			<c:if test="${registration.studySite.study.randomizationType.name == 'BOOK'}">
				<font color="Green"><strong><fmt:message key="REGISTRATION.RANDOMIZATION.BOOK"/> </strong></font>
				<c:if test="${registration.studySite.study.stratificationIndicator}">
				<tr><td class="labelR">Stratum Group:</td><td>${registration.stratumGroup}</td></tr>
				</c:if>
			</c:if>
			</table>
		</c:otherwise>
		</c:choose>
		</strong>
		<div align="right"><input type="button" value="${actionLabel}" onClick="submitRandomization();"/></div>
		<br>
		
	</form>
</tags:panelBox>