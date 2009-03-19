<%@ include file="taglibs.jsp"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Research Staff: ${command.firstName} ${command.lastName} - ${command.nciIdentifier}@${command.healthcareSite.name}</title>
</head>
<body>

<div id="main">
<chrome:box title="Confirmation" autopad="true">
                <div class="content">
                  <c:if test="${FLOW == 'EDIT_FLOW'}">
                	<div class="row">
                	 	<h2><font color="green">Research Staff member successfully updated.</font></h2>
	                </div>
                </c:if>
                <c:if test="${FLOW == 'SAVE_FLOW'}">
                	<div class="row">
                	 	<h2><font color="green">Research Staff member successfully created.</font></h2>
	                </div>
                </c:if>
                <br>
							<div class="row">
								<div class="label"><b><fmt:message key="c3pr.common.firstName"/><b></div>
								<div class="value">${command.firstName}</div>
							</div>
							                    <div class="row">
								<div class="label"><b><fmt:message key="c3pr.common.lastName"/><b></div>
								<div class="value">${command.lastName}</div>
							</div>
							<div class="row">
								<div class="label"><b><fmt:message key="c3pr.common.NCIIdentifier"/><b></div>
								<div class="value">${command.nciIdentifier}</div>
							</div>
							<div class="row">
								<div class="label"><b><fmt:message key="c3pr.common.email"/><b></div>
								<div class="value">${command.contactMechanisms[0].value}</div>
							</div>
						</table>
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
