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
	        <a href="../admin/searchOrganization">Search Organization</a>
	    </div></li>
	    <li class="tab selected"><div>
	        <a href="../admin/createOrganization">Create Organization</a>
	    </div></li>
	  </ul>
	</div>

	<div id="main">
	<br />
		<chrome:box title="Confirmation" autopad="true">
               	<div class="row" >
                    <h2><font color="green">Organization successfully created.</font></h2>
                </div>
                 <div class="row" >
                 	<table class="tablecontent">
						<tr>
							<td class="alt" align="right"><b>Name:<b></td>
							<td class="alt" align="left">${command.name}</td>
						</tr>
						<tr>
							<td class="alt" align="right"><b>NCI Institue Code:<b></td>
							<td class="alt" align="left">${command.nciInstituteCode}</td>
						</tr>
					</table>
                 </div>
		<br/>
		</chrome:box>
	</div>	

</body>
</html>
