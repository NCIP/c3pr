<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<script type="text/javascript">
importSites="" ;
irbApprovalSites="|";
function manageImportStudySites(){
	$$('.studySiteCheck').each(function(element){
										if(element.checked){
											importSites+=element.value+"|";
										}
									});

	$$('.studySiteIRBDate').each(function(element){
		if(element.checked){
			irbApprovalSites+=element.value+"|";
		}
	});
	
	parent.reloadParentStudySites('${studyId}', '${associationId}',importSites, '${parentIndex}', irbApprovalSites);
	closePopup();
}

function closePopup(){
	parent.closePopup();
}
</script>
<chrome:box title="${shortTitle}">
	<table id="companionSiteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0" align="top">
                 <tr>
                 	 <th></th>
                    <th><b><fmt:message key="c3pr.common.organization"/></b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                    <th><b><fmt:message key="c3pr.common.CTEPIdentifier"/></b>&nbsp;<tags:hoverHint keyProp="organization.ctepCode"/></th>
                    <th><b><fmt:message key="site.copyIRBApproval"/></b>&nbsp;<tags:hoverHint keyProp="study.irbApprovalFlag"/></th>
                   
                </tr>
                <c:forEach items="${healthcareSiteList}" var="healthcareSite" >
				  	<tr>
				  		<td>
	            			<input class="studySiteCheck" type="checkbox" checked="checked" value="${healthcareSite.primaryIdentifier}"/>
	                	</td>
				  		<td>
	             			<input size="40"  type="text" value="${healthcareSite.name}" disabled="disabled" />
		   				</td>
	                	<td> 
	                		<input size="20"  type="text" value="${healthcareSite.primaryIdentifier}" disabled="disabled" />
	            		</td> 
	                	<td>
	            			<input class="studySiteIRBDate" type="checkbox" checked="checked" value="${healthcareSite.primaryIdentifier}"/>
	                	</td>
	                	
            		</tr> 
				</c:forEach>
			</table>  
</chrome:box>
<div class="flow-buttons">
    <span class="next">
     	<tags:button type="button" color="blue" value="Copy" onclick="manageImportStudySites();" size="small"/>
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" size="small"/>
	</span>
</div>
