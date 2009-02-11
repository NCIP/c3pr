<%@ include file="taglibs.jsp"%>
<tags:controlPanel>
	<c:if test="${canEdit}">
		<tags:oneControlPanelItem linkhref="javascript:editRegistrationPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
	</c:if>
	<csmauthz:accesscontrol domainObject="${command.studySubject}" hasPrivileges="UPDATE" authorizationCheckName="domainObjectAuthorizationCheck">
		<tags:oneControlPanelItem linkhref="javascript:changeEpochPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_changeEpoch.png" linktext="Change Epoch" />
		<c:if test="${reconsentRequired}">	
			<tags:oneControlPanelItem linkhref="javascript:reconsentPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_reconsent.png" linktext="Reconsent" />
		</c:if>
    	<c:if test="${takeSubjectOffStudy}">
			<tags:oneControlPanelItem linkhref="javascript:takeSubjectOffStudyPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_takesubjoff.png" linktext="Take subject off study" />
		</c:if>
   	</csmauthz:accesscontrol>
	<tags:oneControlPanelItem linkhref="javascript:$('exportForm')._target.name='xxxx';$('exportForm').submit();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_xml.png" linktext="Export XML" />
	<tags:oneControlPanelItem linkhref="javascript:launchPrint()" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
</tags:controlPanel>