<%@ include file="taglibs.jsp"%>
<script>
var transferEpochId = '${command.studySubject.scheduledEpoch.epoch.id}' ;
var transferToStatus = 'flow' ;
function manageEpochSelection(element){
	$$(".epochCheck").each(function(e){
				e.checked=false;
			}
		);
	element.checked=true;
	transferToStatus = element.id ;
	transferEpochId=element.value;
	if(transferEpochId != '${command.studySubject.scheduledEpoch.epoch.id}'){
		$$(".transferEpochButton")[0].disabled="";
	}else{
		$$(".transferEpochButton")[0].disabled="disabled";
	}
}
function transfer(){
	if(transferEpochId == '${command.studySubject.scheduledEpoch.epoch.id}'){
		alert("Already Registered");
	}else{
		registerSubject(transferEpochId, transferToStatus, '${command.studySubject.parentStudySubject}');
	}
}

function registerSubject(transferEpochId, transferToStatus, parentStudySubject){
	closePopup();
	if(transferToStatus == 'flow' && (parentStudySubject == null || parentStudySubject == '')){
		$("edit_epoch").value=transferEpochId;
		document.getElementById("t_offEpochDate").value=document.getElementById('command.studySubject.scheduledEpoch.offEpochDate').value;
		document.getElementById("t_offEpochReasonText").value=document.getElementById('offEpochReasonText').value;
		$("transferEpoch").submit();
	}else if(transferToStatus == 'flow' && (parentStudySubject != null || parentStudySubject != '')){
		$("edit_epoch_companion").value=transferEpochId;
		$("tc_offEpochDate").value=document.getElementById('command.studySubject.scheduledEpoch.offEpochDate').value;
		$("tc_offEpochReasonText").value=document.getElementById('offEpochReasonText').value;
		$("transferCompanionEpoch").submit();
	}else{
		$("m_manage_epoch").value=transferEpochId;
		$("m_offEpochDate").value=document.getElementById('command.studySubject.scheduledEpoch.offEpochDate').value;
		$("m_offEpochReasonText").value=document.getElementById('offEpochReasonText').value;
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
	<input type="hidden" name="studySubject.scheduledEpoch.offEpochDate" id="m_offEpochDate"/>
	<input type="hidden" name="studySubject.scheduledEpoch.offEpochReasonText" id="m_offEpochReasonText"/>
</form>
<form action="../registration/transferEpochRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="transferEpoch">
	<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
	<input type="hidden" name="_target0" id="_target0" value="0"/>
	<input type="hidden" name="epoch" id="edit_epoch"/>
	<input type="hidden" name="studySubject.scheduledEpoch.offEpochDate" id="t_offEpochDate"/>
	<input type="hidden" name="studySubject.scheduledEpoch.offEpochReasonText" id="t_offEpochReasonText"/>
</form>
<form action="../registration/transferEpochCompanionRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="transferCompanionEpoch">
	<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
	<input type="hidden" name="_target0" id="_target0" value="0"/>
	<input type="hidden" name="epoch" id="edit_epoch_companion"/>
	<input type="hidden" name="studySubject.scheduledEpoch.offEpochDate" id="tc_offEpochDate"/>
	<input type="hidden" name="studySubject.scheduledEpoch.offEpochReasonText" id="tc_offEpochReasonText"/>
</form>
<chrome:box title="Change Epoch">
<chrome:division title="Select Epoch">
<c:choose>
	<c:when test="${command.studySubject.scheduledEpoch.scEpochWorkflowStatus!='REGISTERED'}">
		<tags:instructions code="MANAGEREGISTRATION.UNAPPROVED_CURRENT_EPOCH"/>
	</c:when>
	<c:otherwise><tags:instructions code="MANAGEREGISTRATION.CHANGE_CURRENT_EPOCH"/></c:otherwise>
</c:choose>
<table border="0" cellspacing="5px" cellpadding="5" class="tablecontent"  width="80%">
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
</chrome:division>

<chrome:division title="Off Epoch Details">

<table border="0" cellspacing="5px" cellpadding="5"  width="80%">
	<tr>
		<td><b>Off epoch reason text:</b></td>
		<td><textarea name="offEpochReasonText" id="offEpochReasonText" rows="2" cols="30" class="validate-notEmpty&&maxlength1024"></textarea>
	            	<tags:hoverHint keyProp="scheduledEpoch.offEpochReasonText"/>
	    </td>
		
		<td><b>Off epoch date:</b></td>
		<td>
			<tags:dateInput path="command.studySubject.scheduledEpoch.offEpochDate" validateDate="true" cssClass='validate-Date'/>
			<tags:hoverHint keyProp="scheduledEpoch.offEpochDate"/>
		</td>
	</tr>
</table>
</chrome:division>

</chrome:box>



<div class="flow-buttons">
	<span class="next">
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
		<tags:button type="button" color="green" icon="continue" value="Continue" disabled="disabled" onclick="transfer();" cssClass="transferEpochButton" />
    </span>
</div>  
