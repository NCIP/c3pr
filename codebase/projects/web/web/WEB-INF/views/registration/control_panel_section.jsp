<%@ include file="taglibs.jsp"%>
<tags:controlPanel>
		<csmauthz:accesscontrol domainObject="${command.studySubject}" hasPrivileges="UPDATE" authorizationCheckName="domainObjectAuthorizationCheck">
			<c:if test="${canEdit}">
				<tags:oneControlPanelItem linkhref="javascript:editRegistrationPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
			</c:if>
			<c:if test="${canChangeEpoch}">
				<tags:oneControlPanelItem linkhref="javascript:changeEpochPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_changeEpoch.png" linktext="Change Epoch" />
			</c:if>
	    	<c:if test="${takeSubjectOffStudy}">
				<tags:oneControlPanelItem linkhref="javascript:takeSubjectOffStudyPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_takesubjoff.png" linktext="Take subject off study" />
			</c:if>
			<c:if test="${canBroadcast}">
				<tags:oneControlPanelItem linkhref="javascript:confirmBroadcastRegistration();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_broadcast.png" linktext="Broadcast Registration" />
			</c:if>
    	</csmauthz:accesscontrol>
		<tags:oneControlPanelItem linkhref="javascript:C3PR.disableAjaxLoadingIndicator=true;$('exportForm')._target.name='xxxx';$('exportForm').submit();C3PR.disableAjaxLoadingIndicator=false;" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_xml.png" linktext="Export XML" />
		<tags:oneControlPanelItem linkhref="javascript:launchPrint()" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
	</tags:controlPanel>