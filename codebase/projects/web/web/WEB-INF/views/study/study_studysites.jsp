<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>



<html>
<head>
<tags:includeScriptaculous />
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
var healthcareSiteAutocompleterProps = {
    basename: "healthcareSite",
    populator: function(autocompleter, text) {

        StudyAjaxFacade.matchHealthcareSites( text,function(values) {
            autocompleter.setChoices(values)
        })
    },
    valueSelector: function(obj) {
        return obj.name
    },
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
								}
}
var instanceRowInserterProps = {

    add_row_division_id: "siteTable", 	        
    skeleton_row_division_id: "dummy-row",
    initialIndex: ${fn:length(command.studySites)},
    path: "studySites",
    postProcessRowInsertion: function(object){
        inputDateElementLocal="studySites["+object.localIndex+"].startDate";
        inputDateElementLink="studySites["+object.localIndex+"].startDate-calbutton";
        Calendar.setup(
        {
            inputField  : inputDateElementLocal,         // ID of the input field
            ifFormat    : "%m/%d/%Y",    // the date format
            button      : inputDateElementLink       // ID of the button
        }
                );
        inputDateElementLocal="studySites["+object.localIndex+"].irbApprovalDate";
        inputDateElementLink="studySites["+object.localIndex+"].irbApprovalDate-calbutton";
        Calendar.setup(
        {
            inputField  : inputDateElementLocal,         // ID of the input field
            ifFormat    : "%m/%d/%Y",    // the date format
            button      : inputDateElementLink       // ID of the button
        }
                );
        clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		registerAutoCompleter(clonedRowInserter);
    },
    onLoadRowInitialize: function(object, currentRowIndex){
		clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
		registerAutoCompleter(clonedRowInserter);
    },
};
rowInserters.push(instanceRowInserterProps);
</script>
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="studySiteForm">



    <jsp:attribute name="singleFields">
<tags:errors path="*" />

<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>

            <br>
            <table id="siteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th><b>HealthCare Site</b></th>
                    <th><b>Status</b></th>
                    <th><b>Activation Date</b></th>
                    <th><b>IRB Approval Date</b></th>
                    <th></th>
                </tr>
                <c:forEach items="${command.studySites}" varStatus="status">
                    <tr id="siteTable-${status.index}">
                    
                    
                     <td>
               				<input type="hidden" id="healthcareSite${status.index}-hidden"
                        			name="studySites[${status.index}].healthcareSite"
                      				 value="${command.studySites[status.index].healthcareSite.id}"/>
                			<input class="validate-notEmpty" type="text" id="healthcareSite${status.index}-input"
                       				size="50"
                      				 value="${command.studySites[status.index].healthcareSite.name}"/>
                				<input type="button" id="healthcareSite${status.index}-clear"
                       				 value="Clear"/>
                  		 	<tags:indicator id="healthcareSite${status.index}-indicator"/>
                  			<div id="healthcareSite${status.index}-choices" class="autocomplete"></div>
           			 </td>
                               
                       <td><form:select path="studySites[${status.index}].statusCode"
                                                     cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <form:options items="${studySiteStatusRefData}" itemLabel="desc"
                                          itemValue="desc"/>
                        </form:select>
                            <input type="hidden" name="studySites[${status.index}].roleCode" value="Affiliate Site"/>

                        </td>

                        <td>
                            <tags:dateInput path="studySites[${status.index}].startDate"/>
                        </td>
                        <td>
                            <tags:dateInput path="studySites[${status.index}].irbApprovalDate"/>
                        </td>
                        <td><a
                                href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index});"><img
                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>

                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
    	</jsp:attribute>

    	 <jsp:attribute name="localButtons">
        <input id="addEpoch" type="button"
               value="Add Study Site"
               onclick="javascript:RowManager.addRow(instanceRowInserterProps);"/>
    </jsp:attribute>

</tags:tabForm>
<div id="dummy-row" style="display:none;">
    <table>
        <tr id="siteTable-PAGE.ROW.INDEX">
                       
            <td>
                <input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden"
                        name="studySites[PAGE.ROW.INDEX].healthcareSite"/>
                <input class="validate-notEmpty" type="text" id="healthcareSitePAGE.ROW.INDEX-input"
                       size="50"
                       value="${command.studySites[PAGE.ROW.INDEX].healthcareSite.name}"/>
                <input type="button" id="healthcareSitePAGE.ROW.INDEX-clear"
                        value="Clear"/>
                   <tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator"/>
                  <div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"></div>
            </td>
            
          
            <td>
            </td>
            <td>
                <input id="studySites[PAGE.ROW.INDEX].startDate"
                       name="studySites[PAGE.ROW.INDEX].startDate"
                       type="text"
                       class="date" />
                <a href="#" id="studySites[PAGE.ROW.INDEX].startDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
                </a>
            </td>
            <td>
                <input id="studySites[PAGE.ROW.INDEX].irbApprovalDate"
                       name="studySites[PAGE.ROW.INDEX].irbApprovalDate"
                       type="text"
                       class="date" />
                <a href="#" id="studySites[PAGE.ROW.INDEX].irbApprovalDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
                </a>
            </td>
            <td>
                <a
                    href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>

        </tr>
    </table>
</div>


</body>
</html>