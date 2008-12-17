<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
    
<style type="text/css">
        .label { width: 20em; text-align: right; padding: 2px; }
		.epochDescription{
			height: 30%;
			width: 100%;
		}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script>
assignParticipant=function(element,epoch,event){
	epochId=epoch.id.split("-")[1];
	new Element.update("dragDivision-"+epochId,"");
	new Effect.SlideDown("epochConfirmation-"+epochId);
}
function registerSubject(flowName,epochId){
	if(flowName=="edit"){
		$("edit_epoch").value=epochId;
		$("edit").submit();
	}else{
		new Element.hide('epochConfirmation-buttons-'+epochId);
		new Element.show('epochUpdate-'+epochId);		
		$("m_manage_epoch").value=epochId;
		$("manage").submit();
	}
}
function reloadPage(Id){
	//write code to reload the page
	new Element.update("dragDivision-"+Id,"<b>Please drag the participant here to assign him this epoch.</b>");
	htmlString=$('mockDrag').innerHTML;
	new Element.remove("participant1");
	new Element.update("currentRegistration",htmlString);
	new Effect.Appear('participant1');
	new Draggable('participant1', {revert:false});
	new Effect.SlideUp('epochConfirmation-'+Id);
}
paramString="<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
</script>
</head>
<body>
<form action="../registration/editRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="edit">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="epoch" id="edit_epoch"/>
</form>
<form:form action="../registration/manageRegistration">
<tags:tabFields tab="${tab}"/>
<input type="hidden" name="epoch" id="manage_epoch"/>
</form:form>
<form:form action="../registration/manageRegistration" id="manage">
<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
<input type="hidden" name="_finish" id="_finish"/>
<input type="hidden" name="epoch" id="m_manage_epoch"/>
</form:form>
<tags:panelBox title="Change Epoch">
	<c:choose>
		<c:when test="${command.studySubject.regWorkflowStatus=='OFF_STUDY'}">
			<fmt:message key="REGISTRATION.OFF_STUDY"/>
		</c:when>
		<c:when test="${command.studySubject.scheduledEpoch.scEpochWorkflowStatus!='REGISTERED'}">
			<fmt:message key="MANAGEREGISTRATION.UNAPPROVED_CURRENT_EPOCH"/>
		</c:when>
		<c:otherwise>To move subject between epochs, drag and drop the subject to the appropriate epoch</c:otherwise>
	</c:choose>
	<br><br>
	<table border="0" cellspacing="0" cellpadding="0">
		<c:forEach items="${command.studySubject.studySite.study.epochs}" var="epoch" varStatus="epochStatus">
			<c:if test="${epochStatus.index%3==0}">
				<tr>
			</c:if>
			<td>
				<div id="epochsSection-${epoch.id }"><img src="<tags:imageUrl name="indicator.white.gif"/>"
								alt="Indicator" align="absmiddle">Updating...</div>
				<script>
					<tags:tabMethod method="getEpochSection" viewName="/registration/asynchronous/epochSection" divElement="'epochsSection-${epoch.id }'" params="epochId=${epoch.id}"/>
				</script>
			</td>
			<c:if test="${epochStatus.index%3==2}">
			<tr></tr>
			</c:if>
		</c:forEach>
	</table>
</tags:panelBox>
<c:if test="${command.studySubject.regWorkflowStatus!='OFF_STUDY' && command.studySubject.scheduledEpoch.scEpochWorkflowStatus=='REGISTERED'}">
<div id="mockDrag" style="display:none">
	<div id="participant1" class="participants" align="center" style="display:none">
		<div><img src="<tags:imageUrl name="Subject.gif"/>"
		alt="Subject" width="80" height="80" align="absmiddle"></div>
		<div>${command.studySubject.participant.firstName} ${command.studySubject.participant.lastName }</div>
	</div>
</div>
</c:if>
</body>
</html>
