<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Subject Created</title>
<!--empty head-->
</head>
<body>
    <chrome:box title="Confirmation" autopad="true">
            	<div class="row" >
                    <h2><font color="green">Subject successfully created.</font></h2>
                </div>
                 <div class="row" >
                 	<table class="tablecontent" width="60%">
						<tr>
							<td width="35%" class="alt" align="left"><b>Full Name</b></td>
							<td class="alt" align="left">${param.lastName} ${param.middleName} ${param.firstName}</td>
						</tr>
						<tr>
							<td class="alt" align="left"><b>Primary Identifier</b></td>
							<td class="alt" align="left">${param.primaryIdentifier}</td>
						</tr>
					</table>
                 </div>
		<br/>
    </chrome:box>
</body>
</html>
