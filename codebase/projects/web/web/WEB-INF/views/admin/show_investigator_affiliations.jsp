<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>${invGroupAffiliation.healthcareSiteInvestigator.investigator.fullName}</title>
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

