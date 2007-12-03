<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head>
<!--empty head-->
</head>
<body>
    <chrome:box title="Confirmation" autopad="true">
            	<div class="row" >
                    <h2><font color="green">Study successfully created.</font></h2>
                </div>
                 <div class="row" >
                 	<table class="tablecontent">
						<tr>
							<td class="alt" align="left"><b>Short Title<b></td>
							<td class="alt" align="left">${command.trimmedShortTitleText}</td>
						</tr>
						<tr>
							<td class="alt" align="left"><b>Coordinating Center Study Identifier<b></td>
							<td class="alt" align="left">${command.organizationAssignedIdentifiers[0].value}</td>
						</tr>
						<tr>
							<td class="alt" align="left"><b>Study Status<b></td>
							<td class="alt" align="left">${command.coordinatingCenterStudyStatus.code}</td>
						</tr>
					</table>
                 </div>
		<br/>
    </chrome:box>
</body>
</html>
