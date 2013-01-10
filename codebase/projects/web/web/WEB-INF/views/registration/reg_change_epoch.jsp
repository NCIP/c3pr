<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<script>
function changeEpoch(){
	if(${command.studySubject.parentStudySubject==null}){
		$('changeEpochForm').action = "transferEpochRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
	}else{
		$('changeEpochForm').action = "transferEpochCompanionRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
	}
	$('changeEpochForm').submit();
}
function submitForm(){
	if($('changeEpochId').value == null || $('changeEpochId').value == ''){
		Element.show('notSelected');
	}else{
		$('command').submit();
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
<c:set var="studySubject" value="${command}" scope="request"/>
<div id="summary">
    <registrationTags:registrationSummary/>
</div>
<form:form>
<input type="hidden" name="_finish"/>
<input type="hidden" id="changeEpochId" name="changeEpochId"/>
<chrome:box title="Change Epoch">
<chrome:division title="Select Epoch">
<table border="0" cellspacing="5px" cellpadding="5" class="tablecontent"  width="80%">
	<tr>
		<th width="5%">&nbsp;</th>
		<th scope="col" width="20%"><b><fmt:message key="c3pr.common.epoch"/></b><tags:hoverHint keyProp="study.treatmentEpoch.name" /></th>
		<th scope="col"><b><fmt:message key="c3pr.common.description"/></b><tags:hoverHint keyProp="study.treatmentEpoch.description" /></th>
		<th scope="col" width="12%"><b><fmt:message key="c3pr.common.type"/></b><tags:hoverHint keyProp="study.epoch.type" /></th>
	</tr>
	<c:forEach items="${command.studySubject.studySite.study.epochs}" var="epoch" varStatus="epochStatus">
		<c:set var="assignable" value="true"/>
		<c:if test="${epoch.epochOrder < command.studySubject.scheduledEpoch.epoch.epochOrder}">
			<c:set var="assignable" value="false"/>
		</c:if>
		<c:forEach items="${command.studySubject.scheduledEpochs}" var="schEpoch" varStatus="schEpochIndex">
				<c:if test="${epoch.name eq schEpoch.epoch.name}">
					<c:set var="assignable" value="false"/>
				</c:if>
		</c:forEach>
		<c:set var="currentScheduledEpoch" value="false"/>
		<c:if test="${epoch.id == command.studySubject.scheduledEpoch.epoch.id}">
			<c:set var="currentScheduledEpoch" value="true"/>
		</c:if>
		<tr id="epochsSection-${epoch.id }">
			<td class="${!assignable?'no-selection':'' }">
				<input name="epochCheckbox" type="radio" value="${epoch.id}" ${!assignable?'disabled':'' } id="${transferToStatus}" ${currentScheduledEpoch?'checked':'' } onclick="$('changeEpochId').value = this.value;"/>
			</td>
			<td align="left" class="${!assignable?'no-selection':'' }">${epoch.name}</td>
			<td align="left" class="${!assignable?'no-selection':'' }">${epoch.descriptionText}</td>
			<td align="left" class="${!assignable?'no-selection':'' }">${epoch.type.code}</td>
		</tr>
	</c:forEach>
</table>
<div class="errors" id="notSelected" style="display:none;">
	<ul class="errors">
	    <li>Cannot continue. No epoch selected</li>
	</ul>
</div>
</chrome:division>
</chrome:box>
<div class="flow-buttons">
	<span class="next">
		<tags:button type="button" color="green" icon="continue" value="Continue" onclick="submitForm();"/>
    </span>
</div>  
</form:form>
