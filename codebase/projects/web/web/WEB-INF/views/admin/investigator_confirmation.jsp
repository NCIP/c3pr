<%--
Copyright Duke Comprehensive Cancer Center and SemanticBits
 
  Distributed under the OSI-approved BSD 3-Clause License.
  See  https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Investigator updated: ${command.firstName} ${command.lastName}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <script type="text/javascript">
    	function postProcess(createPI){
        	if(createPI == 'false'){
    			parent.showStudyInvestigators(); 
    			parent.closePopup();
        	}else{
        		<c:if test="${studyflow && createPI}">
					parent.updatePrincipalInvestigatorSection('${command.healthcareSiteInvestigators[0].healthcareSite.id}','${command.healthcareSiteInvestigators[0].healthcareSite.name}','${command.healthcareSiteInvestigators[0].healthcareSite.primaryIdentifier}','${command.healthcareSiteInvestigators[0].id}', '${command.fullName}', '${command.assignedIdentifier}');
           		</c:if>
        	}
    	}
    </script>
</head>
<body>
<div id="main">
		<div id="controlPanel" <c:if test="${studyflow=='true'}">style="display:none;"</c:if>>
			<tags:controlPanel>
				<tags:oneControlPanelItem linkhref="javascript:document.location='editInvestigator?assignedIdentifier=${command.assignedIdentifier}';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
				<tags:oneControlPanelItem linkhref="javascript:document.location='createInvestigator';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_searchInvestigatorController.png" linktext="Create investigator" />
			</tags:controlPanel>
		</div>
		<div id="flash-message" class="info"><img src="<tags:imageUrl name='check.png'/>" alt="" style="vertical-align:top;" />
				<c:if test="${FLOW == 'EDIT_FLOW'}">
                	 	Investigator successfully updated.
                </c:if>
                <c:if test="${FLOW == 'SAVE_FLOW'}">
                	 	Investigator successfully created.
                </c:if>
		</div>
<chrome:box title="Confirmation" autopad="true">
                <div class="content">
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.firstName"/>:</div>
					<div class="value">${command.firstName}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.lastName"/>:</div>
					<div class="value">${command.lastName}</div>
				</div>
				<c:if test="${not empty command.assignedIdentifier }">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.person.identifier"/>:</div>
						<div class="value">${command.assignedIdentifier}</div>
					</div>
				</c:if>
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.email"/>:</div>
					<div class="value">${command.email}</div>
				</div>
                </div>
</chrome:box>
</div>
<div class="flow-buttons" <c:if test="${empty studyflow || studyflow=='false'}">style="display:none;"</c:if>>
	<span class="next">
		<tags:button type="button" color="red" icon="x" value="Close" onclick="postProcess('${createPI}');" />
	</span>
</div>
</body>
</html>
