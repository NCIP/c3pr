<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
 <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
 </style>

</head>
		<table border="0" cellspacing="0" cellpadding="0" class="tablecontent" id="groupsTable"
			width="50%">
			<tr>
	            <th width="40%"scope="col" align="left">Name</th>
	            <th width="60%"scope="col" align="left">Description</th>
	        </tr>
			<c:forEach items="${command.healthcareSite.investigatorGroups}"
				var="invGroup" varStatus="status">
					<tr id="groupsTable-${treatmentEpochCount.index}">
						<td align="left">${invGroup.name}
							</td>
					</tr>
			</c:forEach>
		</table>
</html>

