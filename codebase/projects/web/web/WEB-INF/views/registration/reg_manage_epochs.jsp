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
        .TableLikeHeader {
			background:#D7D9E3 none repeat scroll 0%;
			border-bottom:1px solid #C1DAD7;
			border-right:1px solid #C1DAD7;
			border-top:1px solid #C1DAD7;
			color:#000000;
			font-family:"Trebuchet MS",Verdana,Arial,Helvetica,sans-serif;
			font-size:11px;
			font-size-adjust:none;
			font-stretch:normal;
			font-style:normal;
			font-variant:normal;
			font-weight:bold;
			letter-spacing:2px;
			line-height:normal;
			padding:6px 6px 6px 12px;
			text-align:left;
			text-transform:uppercase;
		}
		.TableLikeColumn{
			background:#FFFFFF none repeat scroll 0%;
			border-bottom:1px solid #C1DAD7;
			border-right:1px solid #C1DAD7;
			color:#4F6B72;
			font-family:auto,"Trebuchet MS",Verdana,Arial,Helvetica,sans-serif;
			font-size:11px;
			font-size-adjust:none;
			font-stretch:normal;
			font-style:normal;
			font-variant:normal;
			font-weight:normal;
			line-height:normal;
			padding:6px 6px 6px 12px;
			height: 150px;
		}
		.DropDraggableArea{
			height: 100%;
		}
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
<tags:formPanelBox tab="${tab}" flow="${flow}">
	<strong>To move participant between epochs drag and drop the participant to the appropriate epoch: </strong><br><br>
	<div id="indicator" style="display:none"><tags:indicator id="indicator-image"/>Updating...</div>
	<table width="80%" border="0" cellspacing="0" cellpadding="0">
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
								Accrual Ceiling: epoch.accrualCeiling<br>
								Accrual Indicator: epoch.accrualIndicator<br>
							</c:otherwise>
						</c:choose>
					</div>
					<c:choose>
						<c:when test="${epoch.id==command.currentScheduledEpoch.epoch.id}">
							<span style='color:#EE3324'><strong>Current Registration</strong></span><br>
							Registration Status: <strong>${command.registrationStatus}</strong>
							<div id="participant1" class="participants" align="center">
								<div><img src="<tags:imageUrl name="Subject.gif"/>"
								alt="Participant" width="80" height="80" align="absmiddle"></div>
								<div>${command.participant.firstName} ${command.participant.lastName }</div>
							</div>
  						    <script type="text/javascript">new Draggable('participant1', {})</script>
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
