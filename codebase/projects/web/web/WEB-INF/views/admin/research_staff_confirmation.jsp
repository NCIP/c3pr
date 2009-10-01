<%@ include file="taglibs.jsp"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Research Staff: ${command.firstName} ${command.lastName} - ${command.nciIdentifier}@${command.healthcareSite.name}</title>
</head>
<body>

<div id="main">
<div id="controlPanel">
			<tags:controlPanel>
				<tags:oneControlPanelItem linkhref="javascript:document.location='editResearchStaff?emailId=${command.email}';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
				<tags:oneControlPanelItem linkhref="javascript:document.location='createResearchStaff';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_searchResearchStaffController.png" linktext="Create research staff" />
			</tags:controlPanel>
		</div>
		<div id="flash-message" class="info"><img src="<tags:imageUrl name='check.png'/>" alt="" style="vertical-align:top;" /> 
				<c:if test="${FLOW == 'EDIT_FLOW'}">
                	 	Research Staff member successfully updated.
                </c:if>
                <c:if test="${FLOW == 'SAVE_FLOW'}">
                	 	Research Staff member successfully created.
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
							<div class="row">
								<div class="label"><fmt:message key="c3pr.common.NCIIdentifier"/>:</div>
								<div class="value">${command.nciIdentifier}</div>
							</div>
							<div class="row">
								<div class="label"><fmt:message key="c3pr.common.email"/>:</div>
								<div class="value">${command.email}</div>
							</div>
							<div class="row">
								<div class="label"><fmt:message key="c3pr.common.role"/>(s):</div>
								<div class="value">
									<c:forEach items="${command.groups}" var="group">
										${group.displayName} <br>
									</c:forEach>
								</div>
							</div>
                    </div>
</chrome:box>
</div>
<div class="flow-buttons" <c:if test="${empty studyflow || studyflow=='false'}">style="display:none;"</c:if>>
	<span class="next">
		<tags:button type="button" color="red" icon="x" value="Close" onclick="parent.showDiseases(); parent.closePopup();" />
	</span>
</div>
</body>
</html>
