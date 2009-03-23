<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Subject Created</title>
<!--empty head-->
</head>
<body>
    <chrome:box title="Confirmation" autopad="true">
            	<div class="label" >
                    <h2><font color="green">Subject successfully created.</font></h2>
                </div>
                <br>
                 <div class="row" >
				     <div class="label" ><fmt:message key="participant.fullName"/>:</div>
				     <div class="value" >${command.fullName}</div>
				 </div>
				 <div class="row" >
				     <div class="label" ><fmt:message key="c3pr.common.primaryIdentifier"/>:</div>
				     <div class="value" >${command.primaryIdentifier}</div>
				 </div>
		<br/>
    </chrome:box>
</body>
</html>
