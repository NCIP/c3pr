<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@attribute name="registration" required="true" type="edu.duke.cabig.c3pr.domain.StudySubject"%>
<%@attribute  name="newReg" required="true"%>
<%@attribute  name="actionButtonLabel" required="true"%>
<%@attribute  name="requiresMultiSite" required="true"%>
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
<tags:panelBox title="Message" boxId="RegHere">
	<form id="randomization" action="confirm?registrationId=${registration.id}" method="post">
		<c:choose>
		<c:when test="${requiresMultiSite}">
			<font color="Green"><strong>Subject ${newRegistration?"registration":"transfer"} requires co-ordinationg 
			center approval. <c:if test="${registration.scheduledEpoch.requiresArm}">Arm assignment would take place at co-ordinating center.</c:if> <strong></font>
			<br>
			Please click on the button to send registration request. 
		</c:when>
		<c:otherwise>
			<c:if test="${registration.studySite.study.randomizationType.name == 'PHONE_CALL'}">
				<font color="Green"><strong>This epoch requires Phone Call Randomization. To randomize, please call the number give below and provide the stratum group.</strong></font><br><br>
				Phone Number: <strong>${registration.scheduledEpoch.epoch.randomization.phoneNumber}</strong>
				<br>
				Stratum Group: <strong>${registration.stratumGroup}</strong><br>
				<br>
				Select Arm: 
				<select name ="scheduledEpoch.scheduledArms[0].arm" class="validate-notEmpty">
					<option value="" selected>--Please Select--</option>
					<c:forEach items="${registration.scheduledEpoch.treatmentEpoch.arms}" var="arm">
					<option value="${arm.id}">${arm.name }</option>
					</c:forEach>
				</select>
			</c:if>
			<c:if test="${registration.studySite.study.randomizationType.name == 'BOOK'}">
				<font color="Green"><strong>This epoch requires Book Randomization. </strong></font>
				<br>
				Stratum Group: <strong>${registration.stratumGroup}</strong>
			</c:if>
			<br>
			<br>
			Please click on the button to randomize and complete registration.			
		</c:otherwise>
		</c:choose>
		</strong></font>
		<table width="100%">
			<tr>
				<td>
					<input type="button" value="${actionLabel}" onClick="submitRandomization();"/>
				</td>
			</tr>
		</table>
	</form>
</tags:panelBox>