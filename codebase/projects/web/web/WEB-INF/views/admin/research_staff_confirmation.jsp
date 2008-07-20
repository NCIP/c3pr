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
                
                    <div class="row">
	                 	<table class="tablecontent" width="60%">
							<tr>
								<td width="35%" class="alt" align="left"><b>First Name<b></td>
								<td class="alt" align="left">${command.firstName}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b>Last Name<b></td>
								<td class="alt" align="left">${command.lastName}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b>NCI Identifier<b></td>
								<td class="alt" align="left">${command.nciIdentifier}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b>E-mail<b></td>
								<td class="alt" align="left">${command.contactMechanisms[0].value}</td>
							</tr>
						</table>
                    </div>
                </div>
</chrome:box>
</div>
</body>
</html>
