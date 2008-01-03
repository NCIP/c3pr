<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<script language="JavaScript" type="text/JavaScript">
		instanceRowInserterProps.reset(${newGroup?0:fn:length(command.healthcareSite.investigatorGroups[groupIndex].siteInvestigatorGroupAffiliations)});
</script>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<chrome:division title="Group">
<form:form id="groupForm">
	<c:if test="${!newGroup}">
		<input type="hidden" name="groupId" id="groupId" value="${command.healthcareSite.investigatorGroups[groupIndex].id }"/>
	</c:if>
	<input type="hidden" name="decorator" value="nullDecorator"/>
        	<table border="0" width="80%">
	        	<tr id="editGroup">
	        		<td>
	        			<table>
	        				<tr>
	        					<td align="right"><span class="required-indicator"></span><b>Name:</b></td>
	        					<td><input type="text" name="healthcareSite.investigatorGroups[${groupIndex }].name" value="${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].name}" class="validate-notEmpty"/></td>
	        				</tr>
	        				<tr>
	        					<td align="right"><b>Start Date:</b></td> 
	        					<td><input type="text" id="formStartDate" name="healthcareSite.investigatorGroups[${groupIndex }].startDate" value="${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].startDateStr}"/>
	        					 <a href="#" id="linkStartDate">
			                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
			                	</a></td>
			                </tr>
	        				<tr>
	        					<td align="right"><b>End Date:</b></td>
	        					<td><input type="text" id="formEndDate" name="healthcareSite.investigatorGroups[${groupIndex }].endDate" value="${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].endDateStr}"/>
	        					 <a href="#" id="linkEndDate">
			                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
			                	</a></td>
	        				</tr>
	        			</table>
	        		</td> 
	        		<td align="right"><b>Description:</b></td>
	        		<td><textarea name="healthcareSite.investigatorGroups[${groupIndex }].descriptionText" rows="5" cols="50"
	        					 value="${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].descriptionText}" cssClass="validate-notEmpty">${newGroup?'': command.healthcareSite.investigatorGroups[groupIndex].descriptionText}</textarea></td>      
	        	</tr>
        	</table>
        		<c:if test="${!newGroup && fn:length(command.healthcareSite.investigatorGroups[groupIndex].siteInvestigatorGroupAffiliations)>0}">
	        		
		           <br><br>Investigators <br>
		          <select multiple size="10" style="width:400px" id="abc">
		          		<c:forEach items="${command.healthcareSite.investigatorGroups[groupIndex].siteInvestigatorGroupAffiliations}" var="aff">
		              		<option>${aff.healthcareSiteInvestigator.investigator.fullName }</option> 
		              	</c:forEach>
		          </select> <span id="disease-selected-name"></span>
	          </c:if>
	          
	          
	<br>    
	<br>
    <div id="investigators">
     <table border="0" id="investigatorsTable" cellspacing="0" class="tablecontent">
        <tr>
            <th><span class="required-indicator">Investigator</span></th>
            <th>Start Date</span></th>
            <th>End Date</span></th>
        </tr>

    </table>
    </div>
    <div align="right">
        <input type="button" onclick="RowManager.addRow(instanceRowInserterProps);" value="Add Investigator"/>
    </div>
    <br>
    <div class="content buttons">
	    <div align="right">
	        <input value="Save" type="submit"/>
	    </div>
	    <div  align="right" id="savingIndicator">
	    	<tags:indicator id="ind"/> Saving...
	    </div>
	    <div  id="savedIndicator">
	    	Saved successfully
	    </div>
    </div>
</form:form>		
</chrome:division>

<div id="dummy-row" style="display:none;">
    <table width="50%" class="tablecontent">
        <tr  id="investigatorsTable-PAGE.ROW.INDEX">
            <td>
                <input type="hidden" id="investigatorPAGE.ROW.INDEX-hidden"
                        name="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].healthcareSiteInvestigator"/>
                <input class="autocomplete validate-notEmpty" type="text" id="investigatorPAGE.ROW.INDEX-input"
                       size="30""/>
                <input type="button" id="investigatorPAGE.ROW.INDEX-clear"
                        value="Clear"/>
                   <tags:indicator id="investigatorPAGE.ROW.INDEX-indicator"/>
                  <div id="investigatorPAGE.ROW.INDEX-choices" class="autocomplete"></div>
            </td>
            <td>
                <input type="text" id="investigators[PAGE.ROW.INDEX].startDate"
                        name="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].startDate"
                        class="date validate-DATE">
                 <a href="#" id="investigators[PAGE.ROW.INDEX].startDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
                </a>
            </td>
            <td>
                <input type="text" id="investigators[PAGE.ROW.INDEX].endDate"
                        name="healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].endDate"
                        class="date validate-DATE">
                 <a href="#" id="investigators[PAGE.ROW.INDEX].endDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
                </a>
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