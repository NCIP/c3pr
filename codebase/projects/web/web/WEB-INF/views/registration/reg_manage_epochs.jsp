<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<style type="text/css">
        .label { width: 20em; text-align: right; padding: 2px; }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script>
assignParticipant=function(element,epoch,event){
	epochId=epoch.id.split("-")[1];
	$('command')._target.name="_target"+${tab.number};
	$('command')._target.value="${tab.number}";
	new Element.hide("epochDropMessage-"+epochId);
	postBodyString=Form.serialize('command')+'&asynchronous=true&epochId='+epochId;
	new Ajax.Request(document.URL, {method:'post', postBody:postBodyString, onLoading:function(request){Element.show('indicator')}, onComplete:requiresWorkFlow, evalScripts:true, asynchronous:true})
}
requiresWorkFlow=function(request){
	Element.hide('indicator')
	returnStrings=request.responseText.split("||");
	alreadyRegistered=returnStrings[0];
	requiresStratification=returnStrings[1];
	idString=returnStrings[2];
	if(alreadyRegistered=="true"){
		alert("Subject already registered on this epoch. Please select a different epoch");
		reloadPage(idString);
		return;
	}
	if(requiresStratification=="true"){
		$("create_epoch").value=idString;
		confirm("This epoch requires eligibility check, stratifications and Randomization. Do you want to continue?")?$("create").submit():reloadPage(idString);
	}else{
		$("manage_epoch").value=idString;
		// write code to assign non treatment epoch
	}
}
function reloadPage(Id){
	//write code to reload the page
	new Element.show("epochDropMessage-"+Id);
	new Element.update("dragDivision-"+Id,"");
}
</script>
</head>
<body>
<form action="../registration/createRegistration" method="post" id="create">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target3" id="_target3" value="3"/>
	<input type="hidden" name="registrationId" value="${command.id }"/>
	<input type="hidden" name="epoch" id="create_epoch"/>
</form>
<tags:formPanelBox tab="${tab}" flow="${flow}">
	To move subject between epochs drag and drop the subject to the appropriate epoch: <br><br>
	<div id="indicator" style="display:none"><img src="<tags:imageUrl name="indicator.white.gif"/>"
								alt="Indicator" align="absmiddle">Updating...</div>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
		<c:forEach items="${command.studySite.study.epochs}" var="epoch">
			<td>
				<div class="TableLikeHeader">${epoch.name}: ${epoch.descriptionText}</div>
				<div class="TableLikeColumn">
					<div id="epoch-Info">
						<c:choose>
							<c:when test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch'}">
								Type: Treatment<br>
							</c:when>
							<c:otherwise>
								Type: Non-Treatment<br>
								Accrual Ceiling: ${epoch.accrualCeiling}<br>
								Accrual Indicator: ${epoch.accrualIndicator}<br>
							</c:otherwise>
						</c:choose>
					</div>
					<c:choose>
						<c:when test="${epoch.id==command.currentScheduledEpoch.epoch.id}">
							<span style='color:#EE3324'><strong>Current Registration</strong></span><br>
							Registration Status: <strong>${command.registrationStatus}</strong>
							<div id="participant1" class="participants" align="center">
								<div><img src="<tags:imageUrl name="Subject.gif"/>"
								alt="Subject" width="80" height="80" align="absmiddle"></div>
								<div>${command.participant.firstName} ${command.participant.lastName }</div>
							</div>
  						    <script type="text/javascript">subjectDragger=new Draggable('participant1', {revert:true})</script>
						</c:when>
						<c:otherwise>
						<div id="epochDropMessage-${epoch.id}">Please drag the participant here to assign him this epoch.</div>
						<div id="dragDivision-${epoch.id}" class="DropDraggableArea">  
						</div>
						<script type="text/javascript">Droppables.add('dragDivision-${epoch.id}', {accept:'participants', onDrop:assignParticipant})
						</script>
						</c:otherwise>
					</c:choose>
				</div>
			</td>
		</c:forEach>
		</tr>
	</table>
	<input type="hidden" name="epoch" id="manage_epoch"/>
</tags:formPanelBox>
<div id="message" style="display:none" />
</body>
</html>
