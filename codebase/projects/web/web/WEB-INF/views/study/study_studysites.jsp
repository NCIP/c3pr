<%@ include file="taglibs.jsp"%>
<link href="themes/mac_os_x.css" rel="stylesheet" type="text/css"/>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
   
<%--<tags:includeScriptaculous />--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />
<script type="text/javascript">
var win;
function selectStudySites(studyId, parentAssociationId, parentIndex){
	win = new Window({title: "Select Study Sites", 
			scrollbar: false, zIndex:100, width:900, height:325 ,
			recenterAuto:true, className :"mac_os_x",
			url: "<c:url value='/pages/study/selectStudySites?decorator=noheaderDecorator&parentAssociationId='/>" + parentAssociationId  +"&parentIndex=" + parentIndex  +"&studyId=" + studyId , 
			showEffectOptions: {duration:1.5}
				}
			) 
	win.showCenter()
}

function closePopup() {
	win.close();
}

function reloadParentStudySites(studyId , studyAssociationId , nciCodes , parentIndex, irbApprovalSites){
	$('nciCodes').value=nciCodes;
	$('irbApprovalSites').value=irbApprovalSites;
	$('studyAssociationId').value=studyAssociationId;
	<tags:tabMethod method="associateParentStudySites" divElement="'parentStudySiteDiv-'+parentIndex" formName="'parentStudySiteForm'"  viewName="/study/parentStudySiteSection" javaScriptParam="'parentIndex='+parentIndex"/>
}

function deleteCompanionStudySiteAssociation(studySiteId, parentIndex){
	<tags:tabMethod method="removeCompanionStudyAssociation" divElement="'parentStudySiteDiv-'+parentIndex" formName="'parentStudySiteForm'"  viewName="/study/parentStudySiteSection" javaScriptParam="'studySiteId='+studySiteId+'&parentIndex='+parentIndex"/>
}
</script>
</head>

<body>
<form:form id="parentStudySiteForm">
<input type="hidden" id="nciCodes" name="nciCodes"/>
<input type="hidden" id="irbApprovalSites" name="irbApprovalSites"/>
<input type="hidden" id="studyAssociationId" name="studyAssociationId"/>
<input type="hidden" id="studyId" name="studyId"/>
<input type="hidden" name="_target${tab.number}" id="_target"/>
<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
<input type="hidden" name="_doNotSave" value="true" id="_doNotSave"/>
</form:form>
<tags:panelBox>
<form:form>
	<input type="hidden" name="submitted" value="true"/>
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
                    <th><b><tags:requiredIndicator />Organization</b>
                    &nbsp;<tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                    <th><b>Activation Date</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
                    <th><b>IRB Approval Date</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
                    <th><b>Target Accrual Number</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.targetAccrualNumber"/></th>
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
<c:if test="${fn:length(command.study.companionStudyAssociations) != 0}">
	<p id="flashMessage" style="display: none">Please add study sites to associated companion studies.</p>
</c:if>
<tags:errors path="study.studySites" />

<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>

            <br>
            <table id="siteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
                 <tr id="h-multiSite" <c:if test="${fn:length(command.study.studySites) == 0}">style="display:none;"</c:if>>
                    <th><b><tags:requiredIndicator />Organization</b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                    <th><b>Activation Date</b><tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
                    <th><b>IRB Approval Date</b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
                    <th><b>Target Accrual Number</b><tags:hoverHint keyProp="study.healthcareSite.targetAccrualNumber"/></th>
                    <c:if test="${command.study.multiInstitutionIndicator && multisiteEnv}"><th><b>Hosted Mode</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.hostedMode"/></th></c:if>
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
            			<td>
            			<c:if test="${command.study.multiInstitutionIndicator && multisiteEnv}">
            			<form:checkbox path="study.studySites[${status.index}].hostedMode"/>
            				<input type="hidden" name="${command.study.studySites[status.index].healthcareSite.nciInstituteCode}-wasHosted" value="${command.study.studySites[status.index].hostedMode}"/>
            			</td> 
            			</c:if>
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

<!--  This section belongs to study site for companion study associations -->
<div id="companionStudyAssocition-studySite" <c:if test="${!command.study.companionIndicator}">style="display:none;"</c:if>>
 	<c:forEach items="${command.study.parentStudyAssociations}" var="parentStudyAssociation" varStatus="parentStudySiteStaus">
 		<div id="parentStudySiteDiv-${parentStudySiteStaus.index}">
			<chrome:division title="${parentStudyAssociation.parentStudy.shortTitleText}">
				<table id="companionSiteTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
	                 <tr <c:if test="${fn:length(parentStudyAssociation.studySites) == 0}">style="display:none;"</c:if>>
	                    <th><b>Organization</b><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
	                    <th><b>Activation Date</b><tags:hoverHint keyProp="study.healthcareSite.startDate"/></th>
	                    <th><b>IRB Approval Date</b><tags:hoverHint keyProp="study.healthcareSite.irbApprovalDate"/></th>
	                    <th><b>Target Accrual Number</b><tags:hoverHint keyProp="study.healthcareSite.targetAccrualNumber"/></th>
	                    <th></th>
	                </tr>
	                <c:forEach items="${parentStudyAssociation.studySites}" var="companionStudySite" varStatus="status">
					  	<tr>
					  		<td>
		             			<input size="40"  type="text" value="${companionStudySite.healthcareSite.name} (${companionStudySite.healthcareSite.nciInstituteCode})" disabled="disabled" />
			   				</td>
		                	<td>
		                		<input size="12"  type="text" name="study.parentStudyAssociations[${parentStudySiteStaus.index}].studySites[${status.index}].startDate" id="companionStudySites[${status.index}].startDate" class="date validate-DATE" value="${companionStudySite.startDateStr}" />
		                		<a href="#" id="companionStudySites[${status.index}].startDate-calbutton">
	                		    	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
	                			</a>
		            
		                	</td>
		                	<td>
		                		<input size="12" type="text" name="study.parentStudyAssociations[${parentStudySiteStaus.index}].studySites[${status.index}].irbApprovalDate" id="companionStudySites[${status.index}].irbApprovalDate" class="date validate-DATE" value="${companionStudySite.irbApprovalDateStr}" />
		                		<a href="#" id="companionStudySites[${status.index}].irbApprovalDate-calbutton">
	                		    	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
	                			</a>
		                	</td>
		                	<td> 
		                		<input type="text" name="study.parentStudyAssociations[${parentStudySiteStaus.index}].studySites[${status.index}].targetAccrualNumber" value="${companionStudySite.targetAccrualNumber}" class="validate-NUMERIC" size="6" />
		            		</td> 
		                	 <td>
		                	 	<a href="javascript:deleteCompanionStudySiteAssociation(${companionStudySite.id},${parentStudySiteStaus.index});" ><img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
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
			<br>
		</div>
		<br>
				<div class="flow-buttons">
		            <span class="next">
						<input type="button" value="Select From Parent" onclick="selectStudySites(${command.study.id},${parentStudyAssociation.id}, ${parentStudySiteStaus.index})" align="right"/>
		 			</span>
	        	</div>
				<br>
	</c:forEach>
</div>

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
            <c:if test="${command.study.multiInstitutionIndicator && multisiteEnv}">
            <td>
                <input id="studySites[PAGE.ROW.INDEX].hostedMode"
                       name="study.studySites[PAGE.ROW.INDEX].hostedMode" type="checkbox" checked/>
                       <input type="hidden" value="1" name="_study.studySites[PAGE.ROW.INDEX].hostedMode"/>
            </td>
            </c:if>
            <td>
                <a
                    href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX, -1);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
            
        </tr>
    </table>
</div>


</body>
</html>
