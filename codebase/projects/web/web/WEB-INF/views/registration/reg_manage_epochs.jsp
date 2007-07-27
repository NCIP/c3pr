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
	alert("in");
	epochId=epoch.id.split("-")[1];
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
		alert("Participant already registered on this epoch. Please select a different epoch");
		reloadPage();
		return;
	}
	if(requiresStratification=="true"){
		$("create_epoch").value=idString;
		confirm("This epoch requires stratifications. Do you want to stratify?")?$("create").submit():reloadPage();
	}else{
		$("manage_epoch").value=idString;
		// write code to assign non treatment epoch
	}
}
function reloadPage(){
	//write code to reload the page
	alert("functionality to reset page not yet complete..");
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
<strong>To move participant between epochs drag the participant and drop him to the appropriate epoch: </strong><br>
<tags:formPanelBox tab="${tab}" flow="${flow}">
	<div id="indicator" style="display:none"><tags:indicator id="indicator-image"/>Updating...</div>
	<table width="80%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<c:forEach items="${command.studySite.study.epochs}" var="epoch">
			<td>
				<h2>${epoch.name}: ${epoch.descriptionText}</h2>
					<c:choose>
						<c:when test="${epoch.id==command.currentScheduledEpoch.epoch.id}">
							<div id="participant1" class="participants" style="position: relative;">
								<img src="<tags:imageUrl name="Study.gif"/>"
								alt="Participant" width="100" height="100" align="absmiddle"> 
								${command.participant.firstName} ${command.participant.lastName }
							</div>
  						    <script type="text/javascript">new Draggable('participant1', {revert:true})</script>
						</c:when>
						<c:otherwise>
						<div id="dragDivision-${epoch.id}">  
							Please drag the participant here to assign him this epoch.
						</div>
						<script type="text/javascript">Droppables.add('dragDivision-${epoch.id}', {accept:'participants', onDrop:assignParticipant})
						</script>
						</c:otherwise>
					</c:choose>
			</td>
		</c:forEach>
		</tr>
	</table>
	<input type="hidden" name="epoch" id="manage_epoch"/>
</tags:formPanelBox>
<div id="message" style="display:none" />
</body>
</html>
