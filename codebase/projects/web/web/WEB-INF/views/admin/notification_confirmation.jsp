<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Notification: ${command.name}:${command.nciInstituteCode}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Confirmation</title>
</head>
<body>

	<div id="main">
		<chrome:box title="Confirmation" autopad="true">
           	<div class="row" >
                <h2><font color="green">Notification successfully created.</font></h2>
            </div>
            <div class="row" >
            <table class="tablecontent" width="60%">
				<tr>
					<td width="35%" class="alt" align="left"><b>Events Registered<b></td>
					<td class="alt" align="left">
						<table border="1">	
							<c:forEach var="plannedNotification" varStatus="plannedNotificationStatus" items="${command.plannedNotifications}">
								<tr>
								<td class="alt" align="left">${plannedNotification.eventName.displayName}</td>
								<td class="alt" align="left">${plannedNotification.frequency.displayName}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>						
			</table>
              </div>
		<br/>
		</chrome:box>
	</div>	

</body>
</html>
