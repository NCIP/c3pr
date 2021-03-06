<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<chrome:division title="${parentStudyAssociation.parentStudy.shortTitleText}">
	<table id="companionSiteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
               <tr <c:if test="${fn:length(parentStudyAssociation.studySites) == 0}">style="display:none;"</c:if>>
                  <th><b><fmt:message key="c3pr.common.organization"/></b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                  <th><b><fmt:message key="site.activationDate"/></b><tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
                  <th><b><fmt:message key="site.IRBApprovalDate"/></b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
                  <th><b><fmt:message key="c3pr.common.targetAccrual"/></b><tags:hoverHint keyProp="study.healthcareSite.targetAccrualNumber"/></th>
                  <th></th>
              </tr>
              <c:forEach items="${parentStudyAssociation.studySites}" var="companionStudySite" varStatus="status">
		  	<tr>
		  		<td>
            		<input size="40"  type="text" value="${companionStudySite.healthcareSite.name} (${companionStudySite.healthcareSite.primaryIdentifier})" disabled="disabled" />
   				</td>
               	<td>
               		<input size="12"  type="text" name="study.parentStudyAssociations[${parentIndex}].studySites[${status.index}].startDate" id="companionStudySites[${status.index}].startDate" class="date validate-DATE" value="${companionStudySite.startDateStr}" />
               		<a href="#" id="companionStudySites[${status.index}].startDate-calbutton">
              		    	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
              			</a>
           
               	</td>
               	<td>
               		<input size="12" type="text" name="study.parentStudyAssociations[${parentIndex}].studySites[${status.index}].irbApprovalDate" id="companionStudySites[${status.index}].irbApprovalDate" class="date validate-DATE" value="${companionStudySite.irbApprovalDateStr}" />
               		<a href="#" id="companionStudySites[${status.index}].irbApprovalDate-calbutton">
              		    	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
              			</a>
               	</td>
               	<td> 
               		<input type="text" name="study.parentStudyAssociations[${parentIndex}].studySites[${status.index}].targetAccrualNumber" value="${companionStudySite.targetAccrualNumber}" class="validate-NUMERIC" size="6" />
           		</td>
           		<td>
               	 	<a href="javascript:deleteCompanionStudySiteAssociation(${companionStudySite.id},${status.index});" ><img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
               	</td> 
          		</tr> 
			<script>
				inputDateElementLocal1="companionStudySites["+${status.index}+"].startDate";
			    inputDateElementLink1="companionStudySites["+${status.index}+"].startDate-calbutton";
			    Calendar.setup(
			    {
			        inputField  : inputDateElementLocal1,         // ID of the input field
			        ifFormat    : "%m/%d/%Y",    // the date format
			        button      : inputDateElementLink1       // ID of the button
			    }
			    );
			    inputDateElementLocal2="companionStudySites["+${status.index}+"].irbApprovalDate";
			    inputDateElementLink2="companionStudySites["+${status.index}+"].irbApprovalDate-calbutton";
			    Calendar.setup(
			    {
			        inputField  : inputDateElementLocal2,         // ID of the input field
			        ifFormat    : "%m/%d/%Y",    // the date format
			        button      : inputDateElementLink2       // ID of the button
			    }
			    );
			</script>	
		</c:forEach>
	</table>
</chrome:division>
