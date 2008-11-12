<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body>
<div class="TableLikeHeader">${epoch.name}: ${epoch.descriptionText}</div>

<div class="TableLikeColumn">
	<div id="epoch-Info-${epoch.id}" class="epochDescription">
		<c:choose>
			<c:when test="${epochType=='Treatment'}">
				Type: Treatment<br>
			</c:when>
			<c:otherwise>
				Type: Non-Treatment<br>
				Accrual Ceiling: ${epoch.accrualCeiling}<br>
				Accrual Indicator: ${epoch.accrualIndicator}<br>
			</c:otherwise>
		</c:choose>
		<c:if test="${!alreadyRegistered}">
					Requires eligibility check: ${requiresEligibility?"Yes":"No"}<br>
					Requires stratification: ${requiresStratification?"Yes":"No"}<br>
					Requires randomization: ${requiresRandomization?"Yes":"No"}<br>
				</c:if>
		<c:if test="${isCurrentScheduledEpoch}">
			Registration Status: <strong>${command.studySubject.regWorkflowStatus}</strong><br>
			<span class="red"><strong>Current Registration</strong></span>
		</c:if>
	</div>
	<c:choose>
		<c:when test="${isCurrentScheduledEpoch}">
			<div id="currentRegistration">
				<div id="participant1" class="participants" align="center">
					<div><img src="<tags:imageUrl name="Subject.gif"/>"
					alt="Subject" width="80" height="80" align="absmiddle"></div>
					<div>${command.studySubject.participant.firstName} ${command.studySubject.participant.lastName }</div>
				</div>
			</div>
			<c:if test="${command.studySubject.regWorkflowStatus!='OFF_STUDY' && command.studySubject.scheduledEpoch.scEpochWorkflowStatus=='APPROVED'}">
				<script type="text/javascript">subjectDragger=new Draggable('participant1', {revert:false});</script>
			</c:if>
		</c:when>
		<c:when test="${alreadyRegistered}">
			<div id="dragDivision-${epoch.id}" class="DropDraggableArea greyed"><b>Cannot assign subject to this epoch since he has already been registered once on the epoch.</b></div>
		</c:when>
		<c:when test="${acrrualCeilingReached}">
			<div id="dragDivision-${epoch.id}" class="DropDraggableArea greyed"><b>Accrual Ceiling for this Epoch alreay reached. Cannot enroll any more subjects on this epoch.</b></div>
		</c:when>
		<c:when test="${notRegistrable}">
			<div id="dragDivision-${epoch.id}" class="DropDraggableArea greyed"><b>Cannot assign subject to this epoch.</b></div>
		</c:when>		
		<c:otherwise>
				<div id="dragDivision-${epoch.id}" class="DropDraggableArea"><b>Please drag the participant here to assign him this epoch.</b></div>
				<script type="text/javascript">Droppables.add('dragDivision-${epoch.id}', {accept:'participants', onDrop:assignParticipant})
				</script>
				<div id="epochConfirmation-${epoch.id}" style="display:none">
					<div id="epochConfirmation-buttons-${epoch.id}">
					<c:choose>
						<c:when test="${requiresArm||requiresEligibility||requiresStratification||requiresRandomization}">
							To complete registration, you need to fill in more information.<br>Click ok to continue.
						</c:when>
						<c:otherwise>
							Are you sure you want to register the subject on this epoch.
							<!--  input type="button" onClick="registerSubject('manage','${epoch.id }')" value="Ok"><input type="button" onClick="reloadPage('${epoch.id }')" value="Cancel" -->
						</c:otherwise>
					</c:choose>
					<input type="button" onClick="registerSubject('create','${epoch.id }')" value="Ok"><input type="button" onClick="reloadPage('${epoch.id }')" value="Cancel">
					</div>
					<div id="epochUpdate-${epoch.id }" style="display:none"><img src="<tags:imageUrl name="indicator.white.gif"/>"
									alt="Indicator" align="absmiddle">Updating...</div>
				</div>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>
