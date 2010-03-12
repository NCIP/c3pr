<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style>
	#single-fields-interior div.row div.label {
		width:22em;
	}
	#single-fields-interior div.row div.value {
		margin-left:23em;
	}

</style>
</head>
<body>

	<c:choose>
	 <c:when test="${empty command.studySubject.scheduledEpoch.epoch.arms}">
		<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
			<div><fmt:message key="REGISTRATION.NO_ARM_ASSIGNMENT_INVOLVED"/></div><br>
		</tags:formPanelBox>
	 </c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${!command.studySubject.scheduledEpoch.requiresRandomization}">
		  <tags:formPanelBox tab="${tab}" flow="${flow}">
			  <div class="row">
			  	<div class="label"><fmt:message key="registration.selectArm"/></div>
			  	<div class="value">
					<select name="studySubject.scheduledEpoch.scheduledArms[0].arm">
						<option value="" selected>Please Select</option>
						<c:forEach items="${command.studySubject.scheduledEpoch.epoch.arms}" var="arm">
							<option value="${arm.id }" <c:if test="${!empty command.studySubject.scheduledEpoch.scheduledArms[0].arm && arm.id== command.studySubject.scheduledEpoch.scheduledArms[0].arm.id }">selected</c:if>>${arm.name}</option>
						</c:forEach>
					</select>
				  </div>
			  </div>
		  </tags:formPanelBox>
		</c:when>
		<c:when test="${command.studySubject.studySite.study.blindedIndicator}">
	      <tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
				<div><fmt:message key="REGISTRATION.NO_BLINDED_ARM_ASSIGNMENT"/></div><br>
          </tags:formPanelBox>
		</c:when>
		<c:otherwise>
			<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
				<div><fmt:message key="REGISTRATION.RANDOMIZED_ARM_ASSIGNMENT"/></div><br>
		    </tags:formPanelBox>
		</c:otherwise>
		</c:choose>
	
	</c:otherwise>
	</c:choose>

</body>
</html>
