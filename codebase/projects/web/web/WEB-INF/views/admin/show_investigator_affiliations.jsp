<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
 <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
 </style>

</head>
<table>
	<c:forEach var="invGroupAffiliation" items="${siteAffiliations}" varStatus="siteInvGroupCount">
		<div class="row">
			<div class="label">  
				Name:
			</div>
			<div class="value">
			${invGroupAffiliation.healthcareSiteInvestigator.investigator.fullName}
			</div>
		</div>
	</c:forEach>
    </table>
</div>
    </body>
</html>

