<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/JavaScript">
		instanceRowInserterProps.path="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations";
		instanceRowInserterProps.reset(${newGroup?0:fn:length(command.healthcareSite.investigatorGroups[groupIndex].siteInvestigatorGroupAffiliations)});
		
</script>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<form:form id="groupForm">
<chrome:division title="Investigator Group">
	<c:if test="${!newGroup}">
		<input type="hidden" name="groupId" id="groupId" value="${command.healthcareSite.investigatorGroups[groupIndex].id }"/>
	</c:if>
	<input type="hidden" name="decorator" value="nullDecorator"/>
   	<div id="editGroup">
   		<div class="leftpanel">
   			<div class="row">
   				<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.name"/></div>
	   			<div class="value">
	   				<input type="text" name="healthcareSite.investigatorGroups[${groupIndex }].name" value="${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].name}" class="required validate-notEmpty"/><tags:hoverHint keyProp="investigatorGroup.name"/>
	   			</div>
   			</div>
   			<div class="row">
   				<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.startDate"/></div>
	   			<div class="value">
	   				<input type="text" id="formStartDate" name="healthcareSite.investigatorGroups[${groupIndex }].startDate" value="${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].startDateStr}" class="required validate-notEmpty&&DATE"/>
	        			<a href="#" id="linkStartDate">
			               <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
			            </a>
			            <tags:hoverHint keyProp="investigatorGroup.startDate"/>
	   			</div>
   			</div>
   			<div class="row">
   				<div class="label"><fmt:message key="c3pr.common.endDate"/></div>
	   			<div class="value">
	   				<input type="text" id="formEndDate" name="healthcareSite.investigatorGroups[${groupIndex }].endDate" value="${newGroup?'':command.healthcareSite.investigatorGroups[groupIndex].endDateStr}" class="validate-DATE"/>
	        		    <a href="#" id="linkEndDate">
			                <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
			           	</a>
			           	<tags:hoverHint keyProp="investigatorGroup.endDate"/>
	   			</div>
   			</div>
   		</div>
   		<div class="rightpanel">
   			<div class="row">
   				<div class="label"><fmt:message key="c3pr.common.description"/></div>
   				<div class="value">
   					<textarea name="healthcareSite.investigatorGroups[${groupIndex }].descriptionText" rows="3" cols="33"
	        		value="${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].descriptionText}">${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].descriptionText}</textarea><tags:hoverHint keyProp="investigatorGroup.descriptionText"/>
   				</div>
   			</div>
   		</div>
   	</div>
	<div id="investigators">
		<br>
		<br>  
		<br>  
		<div style="padding-top: 50px;"><table width="70%" class="tablecontent" id="investigatorsTable">
			<tr>
	            <th><tags:requiredIndicator /><fmt:message key="c3pr.common.investigator"/></th>
	            <th><tags:requiredIndicator /><fmt:message key="c3pr.common.startDate"/><tags:hoverHint keyProp="siteInvestigatorGroupAffiliation.startDate"/></th>
	            <th><fmt:message key="c3pr.common.endDate"/><tags:hoverHint keyProp="siteInvestigatorGroupAffiliation.endDate"/></th>
       		</tr>
			<c:forEach items="${command.healthcareSite.investigatorGroups[groupIndex].siteInvestigatorGroupAffiliations}" varStatus="status" var="affiliation">
	            <tr id="investigatorsTable-${status.index}">
                     <c:if test="${affiliation.healthcareSiteInvestigator.statusCode eq 'AC'}">
                     <td>
              			${affiliation.healthcareSiteInvestigator.investigator.fullName}
           			 </td>
           			 <td>
		                <input type="text" id="investigators[${status.index}].startDate"
		                        name="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations[${status.index}].startDate"
		                        value="${command.healthcareSite.investigatorGroups[groupIndex].siteInvestigatorGroupAffiliations[status.index].startDateStr}"
		                        class="required validate-notEmpty&&DATE">
		                 <a href="#" id="investigators[${status.index}].startDate-calbutton">
		                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
		                </a>
		            </td>
		            <td>
		                <input type="text" id="investigators[${status.index}].endDate"
		                        name="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations[${status.index}].endDate"
		                        value="${command.healthcareSite.investigatorGroups[groupIndex].siteInvestigatorGroupAffiliations[status.index].endDateStr}"
		                        class="validate-DATE">
		                 <a href="#" id="investigators[${status.index}].endDate-calbutton">
		                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
		                </a>
		            </td>
		            <td>
		                <a href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index}, '${affiliation.id==null?'HC#':'ID#'}${affiliation.id==null?affiliation.hashCode:affiliation.id}');"><img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a>
		           </td>
		            <script>
			            inputDateElementLocalStartDate="investigators["+${status.index}+"].startDate";
				        inputDateElementLinkStartDate="investigators["+${status.index}+"].startDate-calbutton";
				        Calendar.setup(
				        {
				            inputField  : inputDateElementLocalStartDate,         // ID of the input field
				            ifFormat    : "%m/%d/%Y",    // the date format
				            button      : inputDateElementLinkStartDate       // ID of the button
				        }
				        );
				        inputDateElementLocalEndDate="investigators["+${status.index}+"].endDate";
				        inputDateElementLinkEndDate="investigators["+${status.index}+"].endDate-calbutton";
				        Calendar.setup(
				        {
				            inputField  : inputDateElementLocalEndDate,         // ID of the input field
				            ifFormat    : "%m/%d/%Y",    // the date format
				            button      : inputDateElementLinkEndDate       // ID of the button
				        }
				        );
            		</script> 
		           </c:if>
          		</tr>
           	</c:forEach>
		</table></div>
	</div>
	<br>
    <div align="left">
    	<tags:button type="button" color="blue" icon="add" value="Add Investigator" 
		onclick="RowManager.addRow(instanceRowInserterProps);" size="small"/>
    </div>
    <br>
</chrome:division>
<div class="content buttons">
    <div align="right">
    	<tags:button type="submit" color="green" icon="save" value="Save"/>
    </div>
    <div  align="right" id="savingIndicator">
    	<tags:indicator id="ind"/> Saving...
    </div>
    <div  id="savedIndicator">
    	Saved successfully
    </div>
</div>
</form:form>
<div id="dummy-row" style="display:none;">
    <table width="50%" class="tablecontent">
        <tr  id="investigatorsTable-PAGE.ROW.INDEX">
            <td>
                <input type="hidden" id="investigatorPAGE.ROW.INDEX-hidden"
                        name="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].healthcareSiteInvestigator"/>
                <input class="autocomplete validate-notEmpty" type="text" id="investigatorPAGE.ROW.INDEX-input"
                       size="30""/>
                   <tags:indicator id="investigatorPAGE.ROW.INDEX-indicator"/>
                  <div id="investigatorPAGE.ROW.INDEX-choices" class="autocomplete"></div>
            </td>
            <td>
                <input type="text" id="investigators[PAGE.ROW.INDEX].startDate"
                        name="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].startDate"
                        class="required validate-notEmpty&&DATE">
                 <a href="#" id="investigators[PAGE.ROW.INDEX].startDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
                </a>
            </td>
            <td>
                <input type="text" id="investigators[PAGE.ROW.INDEX].endDate"
                        name="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].endDate"
                        class="validate-DATE">
                 <a href="#" id="investigators[PAGE.ROW.INDEX].endDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
                </a>
            </td>
            <td>
                <a href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX, -1);"><img
                        src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a>
            </td>
        </tr>
    </table>
</div> 
<script>
	Calendar.setup({inputField:"formStartDate", ifFormat:"%m/%d/%Y", button:"linkStartDate"});
	Calendar.setup(
        {
            inputField  : "formEndDate",         // ID of the input field
            ifFormat    : "%m/%d/%Y",    // the date format
            button      : "linkEndDate"       // ID of the button
        }
   );
   new Element.hide("savingIndicator");
   new Element.hide("savedIndicator");
   ValidationManager.submitPostProcess= function(formElement, flag){	
   if(flag==true)
   		{
			submitGroupForm();
		}
	return false;
	}
	ValidationManager.registerForm($("groupForm"));
</script>