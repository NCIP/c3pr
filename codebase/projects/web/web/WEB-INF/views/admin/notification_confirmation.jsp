<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Notification: ${command.healthcareSite.name}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body>

	<div id="main">
		<chrome:box title="Confirmation" autopad="true">
           	<div class="row" >
                <h2><font color="green">Notification successfully created.</font></h2>
            </div>
            <div class="row" >
            <table class="tablecontent" width="60%">
            	<tr><th width="35%" class="alt" align="left"><b><fmt:message key="notification.eventRegistered"/><b></th>
            		<th width="35%" class="alt" align="left"><b><fmt:message key="notification.frequency"/><b></th>
            	</tr>
            	<c:forEach var="plannedNotification" varStatus="plannedNotificationStatus" items="${command.healthcareSite.plannedNotifications}">
				<c:if test="${plannedNotification.retiredIndicator == 'false'}">
				<tr>
					<td class="alt" align="left">
							${plannedNotification.eventName.displayName}
					</td>
					<td class="alt" align="left">
							${plannedNotification.frequency.displayName}
					</td>
				</tr>	
				</c:if>
				</c:forEach>			
			</table>
              </div>
		<br/>
		</chrome:box>
	</div>	

</body>
</html>
