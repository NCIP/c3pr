<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="registration" required="true" type="edu.duke.cabig.c3pr.domain.StudySubject"%>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<style type="text/css">
        .labelL { text-align: left; padding: 4px; font-weight: bold;}
</style>
<style type="text/css">
        .labelC { text-align: center; padding: 4px; font-weight: bold;}
</style>
<chrome:division title="Randomization">
	<c:choose>
		<c:when test="${registration.scheduledEpoch.requiresRandomization}">
			<c:if test="${registration.studySite.study.randomizationType.name == 'PHONE_CALL' && registration.scheduledEpoch.epoch.randomizedIndicator}">
		      	<strong><fmt:message key="REGISTRATION.RANDOMIZATION.PHONE_CALL"/></strong>
			</c:if>
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
				<c:if test="${registration.studySite.study.randomizationType.name == 'PHONE_CALL'&& registration.scheduledEpoch.epoch.randomizedIndicator}">
					<tr>
						<td align="left" colspan="2"></td>
					</tr>
					<tr>
						<td class="labelR" width="150">Phone Number:</td>
						<td >${registration.scheduledEpoch.epoch.randomization.phoneNumber}</td>
					</tr>
					<tr>
						<c:if test="${registration.scheduledEpoch.epoch.stratificationIndicator}">
							<td class="labelR">Stratum Group:</td>
							<td> ${registration.scheduledEpoch.stratumGroup}</td>
						</c:if>
					<tr>
						<c:choose>
							<c:when test="${registration.studySite.study.blindedIndicator}">
								<td class="labelR">Enter Kit Number</td>
								<td><input type="text" name="studySubject.scheduledEpoch.scheduledArms[0].kitNumber" id="kitNumber" size="20" class="validate-notEmpty"/></td>
							</c:when>
							<c:otherwise>
								<td class="labelR">Select Arm:</td><td>
									<select name ="studySubject.scheduledEpoch.scheduledArms[0].arm" class="validate-notEmpty">
										<option value="" selected>Please Select</option>
										<c:forEach items="${registration.scheduledEpoch.epoch.arms}" var="arm">
											<option value="${arm.id}">${arm.name }</option>
										</c:forEach>
									</select>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:if>
				<c:if test="${registration.studySite.study.randomizationType.name == 'BOOK' && registration.scheduledEpoch.epoch.randomizedIndicator}">
					<font color="Green"><strong><fmt:message key="REGISTRATION.RANDOMIZATION.BOOK"/> </strong></font>
					<c:if test="${registration.scheduledEpoch.epoch.stratificationIndicator}">
						<tr>
							<td class="labelR">Stratum Group:</td>
							<td>${registration.scheduledEpoch.stratumGroup}</td>
						</tr>
					</c:if>
				</c:if>
			</table>
		</c:when>
		<c:otherwise>
			This epoch does not require randomization. 
		</c:otherwise>
	</c:choose>
	
</chrome:division>