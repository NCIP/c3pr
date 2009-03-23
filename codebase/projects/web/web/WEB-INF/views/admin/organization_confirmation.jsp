<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Organization: ${command.name}:${command.nciInstituteCode}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Confirmation</title>
</head>
<body>

	<div id="main">
		<chrome:box title="Confirmation" autopad="true">
                <div class="content">
                  <c:if test="${FLOW == 'EDIT_FLOW'}">
                	<div class="row">
                	 	<h2><font color="green">Organization successfully updated.</font></h2>
	                </div>
                </c:if>
                <c:if test="${FLOW == 'SAVE_FLOW'}">
                	<div class="row">
                	 	<h2><font color="green">Organization successfully created.</font></h2>
	                </div>
                </c:if>
                
                 <div class="row" >
					<div class="label"><fmt:message key="c3pr.common.name"/>:</div>
					<div class="value">${command.name}</div>
				</div>
				<div class="row" >
					<div class="label"><fmt:message key="organization.NCIInstitueCode"/>:</div>
					<div class="value">${command.nciInstituteCode}</div>
				</div>
		<br/>
		</chrome:box>
	</div>	

</body>
</html>
