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
                    <div class="row">
	                 	<table class="tablecontent" width="60%">
							<tr>
								<td width="35%" class="alt" align="left"><b><fmt:message key="c3pr.common.firstName"/></b></td>
								<td class="alt" align="left">${command.firstName}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b><fmt:message key="c3pr.common.lastName"/></b></td>
								<td class="alt" align="left">${command.lastName}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b><fmt:message key="c3pr.common.NCIIdentifier"/></b></td>
								<td class="alt" align="left">${command.nciIdentifier}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b><fmt:message key="c3pr.common.email"/></b></td>
								<td class="alt" align="left">${command.contactMechanisms[0].value}</td>
							</tr>
						</table>
                    </div>
                </div>
</chrome:box>
</div>
<div class="flow-buttons" <c:if test="${empty studyflow || studyflow=='false'}">style="display:none;"</c:if>>
	<span class="next">
		<input id="close" type="button" value="Close" onclick=" parent.showDiseases(); parent.closePopup();"/>
	</span>
</div>
</body>
</html>
