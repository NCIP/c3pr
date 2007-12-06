<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Confirmation</title>
</head>
<body>
<div class="tabpane">
  <ul id="workflow-tabs" class="tabs autoclear">
    <li class="tab"><div>
        <a href="../admin/searchInvestigator">Search Investigator</a>
    </div></li>
    <li class="tab selected"><div>
        <a href="../admin/createInvestigator">Create Investigator</a>
    </div></li>
  </ul>
</div>

<div id="main">
<br/>
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
