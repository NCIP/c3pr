<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Investigator updated: ${command.firstName} ${command.lastName}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body>
<div id="main">
<chrome:box title="Confirmation" autopad="true">
                <div class="content">
                  <c:if test="${FLOW == 'EDIT_FLOW'}">
                	<div class="row">
                	 	<h2><font color="green">Investigator successfully updated.</font></h2>
	                </div>
                </c:if>
                <c:if test="${FLOW == 'SAVE_FLOW'}">
                	<div class="row">
                	 	<h2><font color="green">Investigator successfully saved.</font></h2>
	                </div>
                </c:if>
                <br>
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
					<div class="value">${command.contactMechanisms[0].value}</div>
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
