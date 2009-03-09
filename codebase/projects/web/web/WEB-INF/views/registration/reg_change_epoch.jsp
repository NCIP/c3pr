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
		$$(".transferEpochButton")[0].disabled="";
	}else{
		$$(".transferEpochButton")[0].disabled="disabled";
	}
}
function transfer(){
	if(transferEpochId == '${command.studySubject.currentScheduledEpoch.epoch.id}'){
		alert("Already Registered");
	}else{
		registerSubject(transferEpochId, transferToStatus, '${command.studySubject.parentStudySubject}');
	}
}

function registerSubject(transferEpochId, transferToStatus, parentStudySubject){
	closePopup();
	if(transferToStatus == 'flow' && (parentStudySubject == null || parentStudySubject == '')){
		$("edit_epoch").value=transferEpochId;
		$("transferEpoch").submit();
	}else if(transferToStatus == 'flow' && (parentStudySubject != null || parentStudySubject != '')){
		$("edit_epoch_companion").value=transferEpochId;
		$("transferCompanionEpoch").submit();
	}else{
		$("m_manage_epoch").value=transferEpochId;
		$("manageTransferEpoch").submit();
	}
}
</script>
<style>
.dialog table.tablecontent td, .dialog table.tablecontent th {
    padding: 5px;
}
input[disabled] {
    background-color: transparent;
    border-color: transparent;
}
</style>
<form action="../registration/manageRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" id="manageTransferEpoch" method="post" >
	<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
	<input type="hidden" name="_finish" id="_finish"/>
	<input type="hidden" name="_target0" id="_target0" value="0"/>
	<input type="hidden" name="epoch" id="m_manage_epoch"/>
</form>
<form action="../registration/transferEpochRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="transferEpoch">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="epoch" id="edit_epoch"/>
</form>
<form action="../registration/transferEpochCompanionRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="transferCompanionEpoch">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="epoch" id="edit_epoch_companion"/>
</form>
<chrome:box title="Change Epoch">
<c:choose>
	<c:when test="${command.studySubject.scheduledEpoch.scEpochWorkflowStatus!='REGISTERED'}">
		<tags:instructions code="MANAGEREGISTRATION.UNAPPROVED_CURRENT_EPOCH"/>
	</c:when>
	<c:otherwise><tags:instructions code="MANAGEREGISTRATION.CHANGE_CURRENT_EPOCH"/></c:otherwise>
</c:choose>
<table border="0" cellspacing="5px" cellpadding="5" class="tablecontent"  width="100%">
	<tr>
		<th width="5%">&nbsp;</th>
		<th scope="col" width="20%"><b><fmt:message key="c3pr.common.epoch"/></b><tags:hoverHint keyProp="study.treatmentEpoch.name" /></th>
		<th scope="col"><b><fmt:message key="c3pr.common.description"/></b><tags:hoverHint keyProp="study.treatmentEpoch.description" /></th>
		<th scope="col" width="12%"><b><fmt:message key="c3pr.common.type"/></b><tags:hoverHint keyProp="study.epoch.treatmentIndicator" /></th>
		<th scope="col" width="25%"><b><fmt:message key="c3pr.common.notes"/></b><tags:hoverHint keyProp="study.change_epoch.notes" /></th>
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
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
		<tags:button type="button" color="green" icon="continue" value="Continue" disabled="disabled" onclick="transfer();" cssClass="transferEpochButton" />
    </span>
</div>  
