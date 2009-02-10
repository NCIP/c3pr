<%@ include file="taglibs.jsp"%>
<script>
var transferEpochId = '${command.studySubject.currentScheduledEpoch.epoch.id}' ;
var transferToStatus = 'flow' ;
function manageEpochSelection(element){
	$$(".epochCheck").each(function(e){
				e.checked=false;
			}
		);
	element.checked=true;
	transferToStatus = element.id ;
	transferEpochId=element.value;
	if(transferEpochId != '${command.studySubject.currentScheduledEpoch.epoch.id}'){
		$$(".transferEpochButton")[0].src="/c3pr/images/flow-buttons/save_btn.png";
		$$(".transferEpochButton")[0].disabled="";
	}else{
		$$(".transferEpochButton")[0].src="/c3pr/images/flow-buttons/save_btn_disabled.png";
		$$(".transferEpochButton")[0].disabled="disabled";
	}
}
function transfer(){
	if(transferEpochId == '${command.studySubject.currentScheduledEpoch.epoch.id}'){
		alert("Already Registered");
	}else{
		registerSubject(transferEpochId, transferToStatus);
	}
}

function registerSubject(transferEpochId, transferToStatus){
	parent.closePopup();
	if(transferToStatus == 'flow'){
		$("edit_epoch").value=transferEpochId;
		$("transferEpoch").submit();
	}else{
		$("regWorkflowStatus").value=transferToStatus;
		$("m_manage_epoch").value=transferEpochId;
		$("manageTransferEpoch").submit();
	}
}
</script>
<form action="../registration/manageRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" id="manageTransferEpoch" method="post" >
	<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
	<input type="hidden" name="_finish" id="_finish"/>
	<input type="hidden" name="_target0" id="_target0" value="0"/>
	<input type="hidden" name="epoch" id="m_manage_epoch"/>
	<input type="hidden" name="studySubject.regWorkflowStatus" id="regWorkflowStatus"/>
</form>
<form action="../registration/transferEpochRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="transferEpoch">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="epoch" id="edit_epoch"/>
</form>
<chrome:box title="Change Epoch">
<c:choose>
	<c:when test="${command.studySubject.scheduledEpoch.scEpochWorkflowStatus!='REGISTERED'}">
		<fmt:message key="MANAGEREGISTRATION.UNAPPROVED_CURRENT_EPOCH"/>
	</c:when>
	<c:otherwise><fmt:message key="MANAGEREGISTRATION.CHANGE_CURRENT_EPOCH"/></c:otherwise>
</c:choose>
<br><br> 
<table border="0" cellspacing="5px" cellpadding="5px" class="tablecontent"  width="100%">
	<tr>
		<th width="5%">Select</th>
		<th scope="col" width="20%"><b><fmt:message key="registration.epochName"/></b></th>
		<th scope="col"><b><fmt:message key="c3pr.common.description"/></b></th>
		<th scope="col" width="12%"><b><fmt:message key="c3pr.common.type"/></b></th>
		<th scope="col" width="25%"><b><fmt:message key="c3pr.common.notes"/></b></th>
	</tr>
	<c:forEach items="${command.studySubject.studySite.study.epochs}" var="epoch" varStatus="epochStatus">
			<tr id="epochsSection-${epoch.id }"></tr>
			<script>
				<tags:tabMethod method="getEpochSection" viewName="/registration/asynchronous/epochSectionNew" divElement="'epochsSection-${epoch.id }'" params="epochId=${epoch.id}"/>
			</script>
	</c:forEach>
</table>
</chrome:box>
<div class="flow-buttons">
	<span class="next">
	 	<input type="image" src="/c3pr/images/flow-buttons/save_btn_disabled.png" onclick="transfer();"  disabled="disabled" class="transferEpochButton"/>
        <input type="image" src="/c3pr/images/flow-buttons/cancel_btn.png" onclick="parent.closePopup();"/>
    </span>
</div>  
