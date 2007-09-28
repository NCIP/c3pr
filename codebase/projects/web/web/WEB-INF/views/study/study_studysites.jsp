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

</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="studySiteForm">

    <jsp:attribute name="singleFields">
    <c:choose>
	<c:when test="${ (empty command.multiInstitutionIndicator) || command.multiInstitutionIndicator=='false'}">
	<script language="JavaScript" type="text/JavaScript">
	
var singleHealthcareSiteAutocompleterProps = {
    basename: "healthcareSite",
    populator: function(autocompleter, text) {

        StudyAjaxFacade.matchHealthcareSites( text,function(values) {
            autocompleter.setChoices(values)
        })
    },
    valueSelector: function(obj) {
        return obj.name
    }
}

function acPostSelect(mode, selectedChoice) {
    Element.update(mode.basename + "-selected-name", mode.valueSelector(selectedChoice))
    $(mode.basename).value = selectedChoice.id;
    $(mode.basename + '-selected').show()
    new Effect.Highlight(mode.basename + "-selected")
}

function updateSelectedDisplay(mode) {
    if ($(mode.basename).value) {
	Element.update(mode.basename + "-selected-name", $(mode.basename + "-input").value)
	$(mode.basename + '-selected').show()
    }
}

function acCreate(mode) {
	new Autocompleter.DWR(mode.basename + "-input", mode.basename + "-choices",
	mode.populator, {
	valueSelector: mode.valueSelector,
	afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
		hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    $(hiddenField).value=selectedChoice.id;
	},
	indicator: mode.basename + "-indicator"
    })
      Event.observe(mode.basename + "-clear", "click", function() {
	$(mode.basename + "-selected").hide()
	$(mode.basename).value = ""
	$(mode.basename + "-input").value = ""
    })
}

Event.observe(window, "load", function() {
    acCreate(singleHealthcareSiteAutocompleterProps)
    updateSelectedDisplay(singleHealthcareSiteAutocompleterProps)
    // Element.update("flow-next", "Continue &raquo;")
})
</script>
        <table id="siteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th><b><span class="required-indicator">Organization</span></b></th>
                    <th><b>Activation Date</b></th>
                    <th><b>IRB Approval Date</b></th>
                    <th><b>Target Accrual Number</b></th>
                </tr>
                
                    <tr>
                 
                     <td>
               				<form:hidden id="healthcareSite-hidden"
                        			path="studySites[0].healthcareSite"
                      				 />
                			<input class="validate-notEmpty" type="text" id="healthcareSite-input"
                       				size="40"
                      				 value="${command.studySites[0].healthcareSite.name}"/>
                				<input type="button" id="healthcareSite-clear"
                       				 value="Clear"/>
                  		 	<tags:indicator id="healthcareSite-indicator"/>
                  			<div id="healthcareSite-choices" class="autocomplete"></div>
           			 </td>

                        <td>
                        <input type="hidden" name="studySites[0].roleCode" value="Affiliate Site"/>
                            <tags:dateInput path="studySites[0].startDate"/>
                        </td>
                        <td>
                            <tags:dateInput path="studySites[0].irbApprovalDate"/>
                        </td>
                        <td>
                            <form:input path="studySites[0].targetAccrualNumber"/>
                        </td>
                    </tr>
            </table>
    </c:when>
    <c:otherwise>
    
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
    softDelete: ${flowType!='CREATE_STUDY'},
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
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    },
    onLoadRowInitialize: function(object, currentRowIndex){
		clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    }
};
RowManager.addRowInseter(instanceRowInserterProps);
</script>
        
<tags:errors path="*" />

<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>

            <br>
            <table id="siteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th><b><span class="required-indicator">Organization</span></b></th>
                    <th><b>Activation Date</b></th>
                    <th><b>IRB Approval Date</b></th>
                    <th><b>Target Accrual Number</b></th>
                    <th></th>
                </tr>
                <c:forEach items="${command.studySites}" varStatus="status">
                    <tr id="siteTable-${status.index}">
                    
                    
                     <td>
               				<input type="hidden" id="healthcareSite${status.index}-hidden"
                        			name="studySites[${status.index}].healthcareSite"
                      				 value="${command.studySites[status.index].healthcareSite.id}"/>
                			<input class="validate-notEmpty" type="text" id="healthcareSite${status.index}-input"
                       				size="40"
                      				 value="${command.studySites[status.index].healthcareSite.name}"/>
                				<input type="button" id="healthcareSite${status.index}-clear"
                       				 value="Clear"/>
                  		 	<tags:indicator id="healthcareSite${status.index}-indicator"/>
                  			<div id="healthcareSite${status.index}-choices" class="autocomplete"></div>
           			 </td>

                        <td>
                        	<input type="hidden" name="studySites[${status.index}].roleCode" value="Affiliate Site"/>
                            <tags:dateInput path="studySites[${status.index}].startDate"/>
                        </td>
                        <td>
                            <tags:dateInput path="studySites[${status.index}].irbApprovalDate"/>
                        </td>
                        <td> <input id="studySites[${status.index}].targetAccrualNumber"
                      	 	name="studySites[${status.index}].targetAccrualNumber"
                       		type="text"/>
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
 </c:otherwise>
  </c:choose>
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
                       size="40"
                       value="${command.studySites[PAGE.ROW.INDEX].healthcareSite.name}"/>
                <input type="button" id="healthcareSitePAGE.ROW.INDEX-clear"
                        value="Clear"/>
                   <tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator"/>
                  <div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"></div>
            </td>
            
            <td>
            <input type="hidden" name="studySites[PAGE.ROW.INDEX].roleCode" value="Affiliate Site"/>
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
                <input id="studySites[PAGE.ROW.INDEX].targetAccrualNumber"
                       name="studySites[PAGE.ROW.INDEX].targetAccrualNumber"
                       type="text"/>
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