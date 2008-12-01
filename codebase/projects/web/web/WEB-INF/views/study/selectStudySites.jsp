<%@ include file="taglibs.jsp"%>
<script type="text/javascript">
importSites="" ;
function buildNCICodes(){
	$$('.studySiteCheck').each(function(element){
										if(element.checked){
											importSites+=element.value+"|";
										}
									});
	parent.reloadParentStudySites('${studyId}', '${associationId}',importSites, '${parentIndex}');
	closePopup();
}

function closePopup(){
	parent.closePopup();
}
</script>
<chrome:box title="${shortTitle}">
	<table id="companionSiteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0" align="top">
                 <tr>
                    <th><b>Organization</b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                    <th><b>NCI Identifier</b>&nbsp;<tags:hoverHint keyProp="study.nciIdentifier"/></th>
                    <th></th>
                </tr>
                <c:forEach items="${healthcareSiteList}" var="healthcareSite" >
				  	<tr>
				  		<td>
	             			<input size="40"  type="text" value="${healthcareSite.name}" disabled="disabled" />
		   				</td>
	                	<td> 
	                		<input size="40"  type="text" value="${healthcareSite.nciInstituteCode}" disabled="disabled" />
	            		</td> 
	                	<td>
	            			<input class="studySiteCheck" type="checkbox" checked="checked" value="${healthcareSite.nciInstituteCode}"/>
	                	</td>
            		</tr> 
				</c:forEach>
			</table>  
			<br>
			<div class="flow-buttons">
	            <span class="next">
					<input type="button" value="Copy to Companion" onclick="buildNCICodes();"/>
					<input type="button" value="Close" onclick="closePopup();"/>
	 			</span>
        	</div>
			<br>
</chrome:box>
