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
				     <div class="label" ><b><fmt:message key="participant.fullName"/></b></div>
				     <div class="value" >${command.fullName}</div>
				 </div>
				 <div class="row" >
				     <div class="label" ><b><fmt:message key="c3pr.common.primaryIdentifier"/></b></div>
				     <div class="value" >${command.primaryIdentifier}</div>
				 </div>
		<br/>
    </chrome:box>
</body>
</html>
