<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Organization: ${command.name}:${command.primaryIdentifier}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Confirmation</title>
</head>
<body>
	<div id="main">
		<div id="controlPanel">
			<tags:controlPanel>
				<tags:oneControlPanelItem linkhref="javascript:document.location='editOrganization?primaryIdentifier=${command.primaryIdentifier}';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
				<tags:oneControlPanelItem linkhref="javascript:document.location='createOrganization';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_searchOrganizationController.png" linktext="Create organization" />
			</tags:controlPanel>
		</div>
		<div id="flash-message" class="info"><img src="<tags:imageUrl name='check.png'/>" alt="" style="vertical-align:top;" /> 
				<c:if test="${FLOW == 'EDIT_FLOW'}">
                	 	Organization successfully updated.
                </c:if>
                <c:if test="${FLOW == 'SAVE_FLOW'}">
                	 	Organization successfully created.
                </c:if> 
		</div>
		<chrome:box title="Confirmation" autopad="true">
                <div class="content">
                 <div class="row" >
					<div class="label"><fmt:message key="c3pr.common.name"/>:</div>
					<div class="value">${command.name}</div>
				</div>
				<div class="row" >
					<div class="label"><fmt:message key="c3pr.common.CTEPIdentifier"/>&nbsp;(Primary):</div>
					<div class="value">${command.primaryIdentifier}</div>
				</div>
				</div>
		<br/>
		</chrome:box>
	</div>	

</body>
</html>
