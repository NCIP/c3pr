<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
   
<%--<tags:includeScriptaculous />--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />

</head>

<body>
<tags:panelBox>
<form:form>

    <c:choose>
	<c:when test="${ (empty command.study.multiInstitutionIndicator) || command.study.multiInstitutionIndicator=='false'}">
	<script language="JavaScript" type="text/JavaScript">
	
var singleHealthcareSiteAutocompleterProps = {
    basename: "healthcareSite",
     softDelete: ${softDelete == 'true'},
    populator: function(autocompleter, text) {

        StudyAjaxFacade.matchHealthcareSites( text,function(values) {
            autocompleter.setChoices(values) 
        })
    },
    valueSelector: function(obj) {
    	 return (obj.name+" ("+obj.nciInstituteCode+")")
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
	 }})
    clearElement=document.getElementById(mode.basename + "-clear")
    if(clearElement!=null){
	    Event.observe(mode.basename + "-clear", "click", function() {
															$(mode.basename)?$(mode.basename).value = "":null
															$(mode.inputElement()).value = ""
    })
    }
}

Event.observe(window, "load", function() {
    acCreate(singleHealthcareSiteAutocompleterProps)
    updateSelectedDisplay(singleHealthcareSiteAutocompleterProps)
    // Element.update("flow-next", "Continue &raquo;")
})

</script>
        <table id="siteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0"	>
               <tr>
                    <th><b><span class="required-indicator">Organization</span></b>
                    &nbsp;<tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                    <th><b>Activation Date</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
                    <th><b>IRB Approval Date</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
                    <th><b>Target Accrual Number</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.targetAccrualNumber"/></th>
                    <th><b>Hosted Mode</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.targetAccrualNumber"/></th>
                </tr>
                
                    <tr>
                     <td>
                			<input class="autocomplete validate-notEmpty" type="text" id="healthcareSite-input"
                       				size="36"
									<c:set var="_codeSite" value="" />
									<c:set var="_nameSite" value="" />
									<c:if test="${fn:length(command.study.studySites)>0}">	
										<c:set var="_codeSite" value="(${command.study.studySites[0].healthcareSite.nciInstituteCode})" />
										<c:set var="_nameSite" value="${command.study.studySites[0].healthcareSite.name}" />
									</c:if>
                      				 value='<c:out value="${_nameSite} ${_codeSite}" />'/>
								<input type="hidden" id="healthcareSite-hidden"
                        			name="study.studySites[0].healthcareSite" value="${fn:length(command.study.studySites)>0?command.study.studySites[0].healthcareSite.id:""}" />
                				<input type="button" id="healthcareSite-clear"
                       				 value="Clear"/>
                  		 	<tags:indicator id="healthcareSite-indicator"/>
                  			<div id="healthcareSite-choices" class="autocomplete" style="display: none;"></div>
							<input type="hidden" name="study.studySites[0].roleCode" value="Affiliate Site"/>
           			 </td>
	                 <td>
		                <input id="studySites[0].startDate"
		                       name="study.studySites[0].startDate"
							   value="${fn:length(command.study.studySites)>0?command.study.studySites[0].startDateStr:""}"
		                       type="text" cssClass="date validate-DATE" valign="top"/>
		                <a href="#" id="studySites[0].startDate-calbutton">
		                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
		                </a>
		            </td>
		            <td>
		                <input id="studySites[0].irbApprovalDate"
		                       name="study.studySites[0].irbApprovalDate" value="${fn:length(command.study.studySites)>0?command.study.studySites[0].irbApprovalDateStr:""}"
		                       type="text"
		                       cssClass="date validate-DATE" />
		                <a href="#" id="studySites[0].irbApprovalDate-calbutton">
		                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
		                </a>
		            </td>
                     <td>
                         <input id="studySites[0].targetAccrualNumber" name="study.studySites[0].targetAccrualNumber" value="${fn:length(command.study.studySites)>0?command.study.studySites[0].targetAccrualNumber:""}"
				 				size="6" type="text" maxlength="6" cssClass="validate-NUMERIC&&NONZERO_NUMERIC"
                          />
                     </td>
                     <td>
                         <input id="study.studySites[0].hostedMode"
                       			name="study.studySites[0].hostedMode" type="checkbox" disabled="disabled"/>
                     </td>
                    </tr>
            </table>
<script>
	inputDateElementLocal="studySites[0].startDate";
    inputDateElementLink="studySites[0].startDate-calbutton";
     Calendar.setup(
     {
         inputField  : inputDateElementLocal,         // ID of the input field
         ifFormat    : "%m/%d/%Y",    // the date format
         button      : inputDateElementLink       // ID of the button
     }
             );
     inputDateElementLocal="studySites[0].irbApprovalDate";
     inputDateElementLink="studySites[0].irbApprovalDate-calbutton";
     Calendar.setup(
     {
         inputField  : inputDateElementLocal,         // ID of the input field
         ifFormat    : "%m/%d/%Y",    // the date format
         button      : inputDateElementLink       // ID of the button
     }
             );
</script>
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
    	return (obj.name+" ("+obj.nciInstituteCode+")")
    },
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
								}
}
var instanceRowInserterProps = {

    add_row_division_id: "siteTable", 	        
    skeleton_row_division_id: "dummy-row",
    initialIndex: ${fn:length(command.study.studySites)},
    softDelete: ${softDelete == 'true'},
    isAdmin: ${isAdmin == 'true'},
    path: "study.studySites",
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
        
<tags:errors path="study.studySites" />

<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>

            <br>
            <table id="siteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
                 <tr id="h-multiSite" <c:if test="${fn:length(command.study.studySites) == 0}">style="display:none;"</c:if>>
                    <th><b><span class="required-indicator">Organization</span></b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                    <th><b>Activation Date</b><tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
                    <th><b>IRB Approval Date</b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
                    <th><b>Target Accrual Number</b><tags:hoverHint keyProp="study.healthcareSite.targetAccrualNumber"/></th>
                    <th><b>Hosted Mode</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.targetAccrualNumber"/></th>
                    <th></th>
                </tr>
                    
                <c:forEach items="${command.study.studySites}" varStatus="status" var="site">
                    <tr id="siteTable-${status.index}">
                    
                    
                     <td>
               				<input type="hidden" id="healthcareSite${status.index}-hidden"
                        			name="study.studySites[${status.index}].healthcareSite"
                      				 value="${command.study.studySites[status.index].healthcareSite.id}"/>
                			<input class="autocomplete validate-notEmpty" type="text" id="healthcareSite${status.index}-input"
                       				size="40"
                      				 value="${command.study.studySites[status.index].healthcareSite.name} (${command.study.studySites[status.index].healthcareSite.nciInstituteCode})"/>
                				<input type="button" id="healthcareSite${status.index}-clear"
                       				 value="Clear"/>
                  		 	<tags:indicator id="healthcareSite${status.index}-indicator"/>
                  			<div id="healthcareSite${status.index}-choices" class="autocomplete" style="display: none;"></div>
           			 </td>

                        <td>
                        	<input type="hidden" name="study.studySites[${status.index}].roleCode" value="Affiliate Site"/>
                            <tags:dateInput path="study.studySites[${status.index}].startDate"/>
                        </td>
                        <td>
                            <tags:dateInput path="study.studySites[${status.index}].irbApprovalDate"/>
                        </td>
                        <td valign="top"> <form:input path="study.studySites[${status.index}].targetAccrualNumber" maxlength="6" cssClass="validate-NUMERIC" size="6"/>
            			</td> 
            			<td><form:checkbox path="study.studySites[${status.index}].hostedMode"/>
            				<input type="hidden" name="${command.study.studySites[status.index].healthcareSite.nciInstituteCode}-wasHosted" value="${command.study.studySites[status.index].hostedMode}"/>
            			<script>
					        <c:if test="${!(command.study.multiInstitutionIndicator && multisiteEnv)}">$('study.studySites[${status.index}].hostedMode').disabled = true;
					        </c:if>
					    </script>
            			</td> 
                        <td><a
                                href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index},'${site.id==null?'HC#':'ID#'}${site.id==null?site.hashCode:site.id}');"><img
                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
<div align="right">
<input id="addEpoch" type="button"
               value="Add"
               onclick="$('h-multiSite').show();javascript:RowManager.addRow(instanceRowInserterProps);"/>
</div>
 </c:otherwise>
  </c:choose>

<div class="content buttons autoclear">
        <div class="flow-buttons">
            <span class="next">
				<input type="submit" value="Save" id="saveAdvanceConfig""/>
 			</span>
        </div>
    </div>
</form:form>
</tags:panelBox>
<div id="dummy-row" style="display:none;">
    <table>
        <tr id="siteTable-PAGE.ROW.INDEX">
                       
            <td>
                <input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden"
                        name="study.studySites[PAGE.ROW.INDEX].healthcareSite"/>
                <input class="autocomplete validate-notEmpty" type="text" id="healthcareSitePAGE.ROW.INDEX-input"
                       size="40"
                       value="${command.study.studySites[PAGE.ROW.INDEX].healthcareSite.name}"/>
                <input type="button" id="healthcareSitePAGE.ROW.INDEX-clear"
                        value="Clear"/>
                   <tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator"/>
                  <div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete" style="display: none;"></div>
            </td>
            
            <td>
            <input type="hidden" name="study.studySites[PAGE.ROW.INDEX].roleCode" value="Affiliate Site"/>
                <input id="studySites[PAGE.ROW.INDEX].startDate"
                       name="study.studySites[PAGE.ROW.INDEX].startDate"
                       type="text"
                       class="date validate-DATE" />
                <a href="#" id="studySites[PAGE.ROW.INDEX].startDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
                </a>
            </td>
            <td>
                <input id="studySites[PAGE.ROW.INDEX].irbApprovalDate"
                       name="study.studySites[PAGE.ROW.INDEX].irbApprovalDate"
                       type="text"
                       class="date validate-DATE" />
                <a href="#" id="studySites[PAGE.ROW.INDEX].irbApprovalDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
                </a>
            </td>
			<td>
                <input id="studySites[PAGE.ROW.INDEX].targetAccrualNumber"
                       name="study.studySites[PAGE.ROW.INDEX].targetAccrualNumber" maxlength="6" size="6"
                       type="text" class="validate-NUMERIC"/>
            </td>
            <td>
                <input id="studySites[PAGE.ROW.INDEX].hostedMode"
                       name="study.studySites[PAGE.ROW.INDEX].hostedMode" type="checkbox" checked/>
                       <input type="hidden" value="1" name="_study.studySites[PAGE.ROW.INDEX].hostedMode" id="_studySites[PAGE.ROW.INDEX].hostedMode"/>
                       <script>
                       <c:if test="${!(command.study.multiInstitutionIndicator && multisiteEnv)}">$('studySites[PAGE.ROW.INDEX].hostedMode').disabled = true;
					        </c:if></script>
            </td>
            <td>
                <a
                    href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX, -1);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
            
        </tr>
    </table>
</div>


</body>
</html>