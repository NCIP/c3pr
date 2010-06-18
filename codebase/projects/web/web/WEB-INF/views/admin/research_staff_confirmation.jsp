<%@ include file="taglibs.jsp"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Research Staff: ${command.researchStaff.firstName} ${command.researchStaff.lastName} - ${command.researchStaff.assignedIdentifier}</title>
</head>
<body>
<div id="main">
<div id="controlPanel" <c:if test="${studyflow=='true'}">style="display:none;"</c:if>>
	<tags:controlPanel>
		<tags:oneControlPanelItem linkhref="javascript:document.location='editResearchStaff?assignedIdentifier=${command.researchStaff.assignedIdentifier}';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
		<tags:oneControlPanelItem linkhref="javascript:document.location='createResearchStaff';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_searchResearchStaffController.png" linktext="Create research staff" />
	</tags:controlPanel>
</div>
<div id="flash-message" class="info">
	<img src="<tags:imageUrl name='check.png'/>" alt="" style="vertical-align:top;" /> 
	<c:if test="${FLOW == 'EDIT_FLOW'}">
   	 	<fmt:message key="RESEARCH.STAFF.UPDATED_SUCCESSFULLY"></fmt:message>
    </c:if>
    <c:if test="${FLOW == 'SAVE_FLOW'}">
   	 	<fmt:message key="RESEARCH.STAFF.CREATED_SUCCESSFULLY"></fmt:message>
    </c:if> 
</div>
<chrome:box title="Confirmation" autopad="true">
	<div class="content">
		<div class="row">
			<div class="label"><fmt:message key="c3pr.common.firstName"/>:</div>
			<div class="value">${command.researchStaff.firstName}</div>
		</div>
		                    <div class="row">
			<div class="label"><fmt:message key="c3pr.common.lastName"/>:</div>
			<div class="value">${command.researchStaff.lastName}</div>
		</div>
		<c:if test="${not empty command.researchStaff.assignedIdentifier }">
			<div class="row">
				<div class="label"><fmt:message key="c3pr.person.identifier"/>:</div>
				<div class="value">${command.researchStaff.assignedIdentifier}</div>
			</div>
		</c:if>
		<div class="row">
			<div class="label"><fmt:message key="c3pr.common.email"/>:</div>
			<div class="value">${command.researchStaff.email}</div>
		</div>
		
		<div class="row">
			<div class="label"><fmt:message key="c3pr.common.organization"/>:</div>
			<div class="value">
				<c:forEach items="${command.researchStaff.healthcareSites}" var="healthcareSite" varStatus="count">
					${healthcareSite.name} (${healthcareSite.primaryIdentifier})
					<c:if test="${fn:length(command.researchStaff.healthcareSites) > (count.index + 1)}">
						<br>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
</chrome:box>
</div>
<div class="flow-buttons" <c:if test="${empty studyflow || studyflow=='false'}">style="display:none;"</c:if>>
	<span class="next">
		<tags:button type="button" color="red" icon="x" value="Close" onclick="parent.showPersonnel(); parent.closePopup();" />
	</span>
</div>
</body>
</html>
