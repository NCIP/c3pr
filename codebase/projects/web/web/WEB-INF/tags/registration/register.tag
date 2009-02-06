<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	if(${registration.regWorkflowStatus!='ENROLLED' && !empty registration.studySite.targetAccrualNumber && registration.studySite.targetAccrualNumber<=registration.studySite.currentAccrualCount}){
		confirmFlag=confirm("This registration will exceed the accrual ceiling at ${command.studySubject.studySite.healthcareSite.name}. Do you want to continue?");
		if(!confirmFlag)
			return;
	}
	$('randomization').action = "confirm?" + "<tags:identifierParameterString identifier='${registration.systemAssignedIdentifiers[0]}'/>" ;
	$('randomization').submit();
}
paramString="<tags:identifierParameterString identifier='${registration.systemAssignedIdentifiers[0]}'/>";
</script>
	<form id="randomization" action="" method="post">  
	<c:choose>
	<c:when test="true">
		<c:choose>
		<c:when test="${requiresMultiSite}">
			<strong>Subject ${newRegistration?"registration":"transfer"} requires co-ordinationg 
			center approval. <c:if test="${registration.scheduledEpoch.requiresRandomization}">Arm assignment would take place at co-ordinating center.</c:if> <strong>
			<br>
			Please click on the button to send registration request. 
		</c:when>
		<c:otherwise>
		<chrome:division title="${(fn:length(registration.childStudySubjects) > 0 && registration.scheduledEpoch.scEpochWorkflowStatus.code != 'Registered But Not Randomized')?'':registration.studySite.study.shortTitleText}">
            <c:if test="${registration.studySite.study.randomizationType.name == 'PHONE_CALL' && registration.scheduledEpoch.epoch.randomizedIndicator}">
                <strong><fmt:message key="REGISTRATION.RANDOMIZATION.PHONE_CALL"/></strong>
            </c:if>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
            <c:if test="${registration.studySite.study.randomizationType.name == 'PHONE_CALL'&& registration.scheduledEpoch.epoch.randomizedIndicator}">
                <tr><td align="left" colspan="2"></td></tr>
				<tr><td class="labelR" width="150"><fmt:message key="registration.phoneNumber"/>:</td><td >${registration.scheduledEpoch.epoch.randomization.phoneNumber}</td></tr>
				<tr>
				<c:if test="${registration.scheduledEpoch.epoch.stratificationIndicator}">
				<td class="labelR"><fmt:message key="registration.stratumGroup"/>:</td><td> ${registration.scheduledEpoch.stratumGroup}</td>
				</c:if>
				<tr>
				<c:choose>
					<c:when test="${registration.studySite.study.blindedIndicator}">
						<td class="labelR"><fmt:message key="registration.enterKitNumber"/></td><td><input type="text" name="studySubject.scheduledEpoch.scheduledArms[0].kitNumber" id="kitNumber" size="20" class="validate-notEmpty"/></td>
					</c:when>
					<c:otherwise>
						<td class="labelR"><fmt:message key="registration.selectArm"/></td><td>
						<select name ="studySubject.scheduledEpoch.scheduledArms[0].arm" class="validate-notEmpty">
							<option value="" selected>Please Select</option>
							<c:forEach items="${registration.scheduledEpoch.epoch.arms}" var="arm">
							<option value="${arm.id}">${arm.name }</option>
							</c:forEach>
						</select></td>
					</c:otherwise>
				</c:choose>
				</tr>
			</c:if>
			<c:if test="${registration.studySite.study.randomizationType.name == 'BOOK' && registration.scheduledEpoch.epoch.randomizedIndicator}">
				<font color="Green"><strong><fmt:message key="REGISTRATION.RANDOMIZATION.BOOK"/> </strong></font>
				<c:if test="${registration.scheduledEpoch.epoch.stratificationIndicator}">
				<tr><td class="labelR"><fmt:message key="registration.stratumGroup"/>:</td><td>${registration.scheduledEpoch.stratumGroup}</td></tr>
				</c:if>
			</c:if>
			</table>
			</chrome:division>
			<br>
			<c:forEach items="${registration.childStudySubjects}" var="childStudySubject" varStatus="status">
				<c:if test="${childStudySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Registered But Not Randomized'}">
				<chrome:division title="${childStudySubject.studySite.study.shortTitleText}">
					<c:if test="${childStudySubject.studySite.study.randomizationType.name == 'PHONE_CALL' && childStudySubject.scheduledEpoch.epoch.randomizedIndicator}">
		                <strong><fmt:message key="REGISTRATION.RANDOMIZATION.PHONE_CALL"/></strong>
		            </c:if>
		            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
		            <c:if test="${childStudySubject.studySite.study.randomizationType.name == 'PHONE_CALL'&& childStudySubject.scheduledEpoch.epoch.randomizedIndicator}">
		                <tr><td align="left" colspan="2"></td></tr>
						<tr><td class="labelR" width="150"><fmt:message key="registration.phoneNumber"/>:</td><td >${childStudySubject.scheduledEpoch.epoch.randomization.phoneNumber}</td></tr>
						<tr>
						<c:if test="${childStudySubject.scheduledEpoch.epoch.stratificationIndicator}">
						<td class="labelR"><fmt:message key="registration.stratumGroup"/>:</td><td> ${childStudySubject.scheduledEpoch.stratumGroup}</td>
						</c:if>
						<tr>
						<c:choose>
							<c:when test="${childStudySubject.studySite.study.blindedIndicator}">
								<td class="labelR"><fmt:message key="registration.enterKitNumber"/></td><td><input type="text" name="studySubject.childStudySubjects[${status.index}].scheduledEpoch.scheduledArms[0].kitNumber" id="kitNumber" size="20" class="validate-notEmpty"/></td>
							</c:when>
							<c:otherwise>
								<td class="labelR"><fmt:message key="registration.selectArm"/></td><td>
								<select name ="studySubject.childStudySubjects[${status.index}].scheduledEpoch.scheduledArms[0].arm" class="validate-notEmpty">
									<option value="" selected>Please Select</option>
									<c:forEach items="${childStudySubject.scheduledEpoch.epoch.arms}" var="arm">
									<option value="${arm.id}">${arm.name }</option>
									</c:forEach>
								</select></td>
							</c:otherwise>
						</c:choose>
						</tr>
					</c:if>
					<c:if test="${childStudySubject.studySite.study.randomizationType.name == 'BOOK' && childStudySubject.scheduledEpoch.epoch.randomizedIndicator}">
						<font color="Green"><strong><fmt:message key="REGISTRATION.RANDOMIZATION.BOOK"/> </strong></font>
						<c:if test="${childStudySubject.scheduledEpoch.epoch.stratificationIndicator}">
						<tr><td class="labelR"><fmt:message key="registration.stratumGroup"/>:</td><td>${childStudySubject.scheduledEpoch.stratumGroup}</td></tr>
						</c:if>
					</c:if>
					</table>
				</chrome:division>
					<br>
				</c:if>
			</c:forEach>
		</c:otherwise>
		</c:choose>
		</strong>
		
	</c:when>
	<c:otherwise>
		<br> Please click on the button to register the subject on the study and the companion studies. </br>
	</c:otherwise>
	</c:choose>
	<div align="left">
		<c:if test="${actionButtonLabel == 'Enroll' }">
			<font color="Green"><strong>Click on the enroll button to complete the registration.</strong></font>
		</c:if>
	</div>
	<div align="right">
		<input type="button" value="${actionButtonLabel}" onClick="submitRandomization();"/>
	</div>
	<br>
</form>
