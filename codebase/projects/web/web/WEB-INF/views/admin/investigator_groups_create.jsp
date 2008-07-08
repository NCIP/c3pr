<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<c:set var="selected_site" value="0"/>
<c:if test="${not empty selectedSite}">
	<c:set var="selected_site" value="${selectedSite}"/>
</c:if>
<head>
<style type="text/css">
        div.content {
            padding: 5px 15px;
        }
 </style>
<%--<tags:includeScriptaculous />--%>
<tags:dwrJavascriptLink objects="OrganizationAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
  var sponsorSiteAutocompleterProps = {
            basename: "healthcareSite",
            populator: function(autocompleter, text) {
                OrganizationAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return (obj.name+" ("+obj.nciInstituteCode+")")
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=sponsorSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
	    							$('flashMessage').hide();
	    							updateGroups(selectedChoice.id,"",false);
			 }
        }
       
    var principalInvestigatorAutocompleterProps = {
            basename: "investigator",
            populator: function(autocompleter, text) {
                OrganizationAjaxFacade.matchOrganizationInvestigatorsGivenOrganizationId(text,document.getElementById("healthcareSite-hidden").value, function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return obj.investigator.fullName
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
			}
        }
        
    var instanceRowInserterProps = {
       add_row_division_id: "investigatorsTable", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row",
       initialIndex: ${newGroup?0:fn:length(command.healthcareSite.investigatorGroups[groupIndex].siteInvestigatorGroupAffiliations)},   /* this is the initial count of the rows when the page is loaded  */
       softDelete: ${softDelete == 'true'},
	   path: "healthcareSite.investigatorGroups[${groupIndex}].siteInvestigatorGroupAffiliations",                            /* this is the path of the collection that holds the rows  */
       postProcessRowInsertion: function(object){
	       	inputDateElementLocal="investigators["+object.localIndex+"].startDate";
	        inputDateElementLink="investigators["+object.localIndex+"].startDate-calbutton";
	        Calendar.setup(
	        {
	            inputField  : inputDateElementLocal,         // ID of the input field
	            ifFormat    : "%m/%d/%Y",    // the date format
	            button      : inputDateElementLink       // ID of the button
	        }
	                );
	        inputDateElementLocal="investigators["+object.localIndex+"].endDate";
	        inputDateElementLink="investigators["+object.localIndex+"].endDate-calbutton";
	        Calendar.setup(
	        {
	            inputField  : inputDateElementLocal,         // ID of the input field
	            ifFormat    : "%m/%d/%Y",    // the date format
	            button      : inputDateElementLink       // ID of the button
	        }
	                );
	        clonedRowInserter=Object.clone(principalInvestigatorAutocompleterProps);
			clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
			AutocompleterManager.registerAutoCompleter(clonedRowInserter);
					}
	};
RowManager.addRowInseter(instanceRowInserterProps);  
  AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);

		function updateGroups(id,selectedId,saveEvent){
		
			OrganizationAjaxFacade.getInvestigatorGroups(id, function(categories) {
		        var sel = $("disease-sub-category")
		       if(categories!=null && categories.length > 0){
		        sel.size = categories.length < 10 ? categories.length + 2 : 10;
		        //sel.size= 10
		        sel.options.length = 0
		        categories.each(function(cat) {
		            var opt = new Option(cat.name, cat.id)
		            opt.groupName=cat.name
		            opt.groupDesc=cat.descriptionText
		            opt.startDate=cat.startDate;
		            opt.endDate=cat.endDate;
		            sel.options.add(opt)
		            if(selectedId!="" && cat.id==selectedId)
		            opt.selected=true;
		        })
		        if(sel.options[0]!=null && selectedId==""){
		         	sel.options[0].selected = true;
		         }
		        showAffiliations(saveEvent)
		        } else {
		        		$("disease-sub-category").options.length=0;
		        			new Element.update($("groupDisplay"),'');
		        	}
		        
	    	})
		};
		
		function showAffiliations(saveEvent) {
		    var categoryId = $("disease-sub-category").value
		    var opts=$("disease-sub-category").options
		    var subCategorySelect = $("disease-sub-category")
		    // If all is selected
		    
	    	new Ajax.Updater('groupDisplay', 'getGroup', {method:"get", asynchronous:true, evalScripts:true, onComplete:function(){ new Effect.Highlight('groupDisplay');}, 
		    												parameters: { decorator:"nullDecorator", groupId: categoryId, healthcareSite: $(sponsorSiteAutocompleterProps.basename+"-hidden").value}
		    											});
		}
		
	function handleAddGroup(){
	if($('healthcareSite-hidden').value != ""){
		new Ajax.Updater('groupDisplay', 'getGroup', {method:"get", asynchronous:true, evalScripts:true, onComplete:function(){ new Effect.Highlight('groupDisplay');}, 
		    												parameters: { decorator:"nullDecorator", healthcareSite: $(sponsorSiteAutocompleterProps.basename+"-hidden").value}
		    											});
		  e1 = document.getElementById("errorsDiv");
	    	if(e1!=null){
				e1.innerHTML='';
			}  											
		    											
		} else {
				$('flashMessage').show()
				new Effect.Highlight('flashMessage');
		}
	}

	Event.observe(window, "load", function() {
	    Event.observe("disease-sub-category", "change", function() {
	    	e1 = document.getElementById("errorsDiv");
	    	if(e1!=null){
				e1.innerHTML='';
			}
	        showAffiliations('true');
	    })
	//    populateSelectsOnLoad();
	
	    // Element.update("flow-next", "Continue &raquo;")
	})
	function submitGroupForm(){
		if(($('formEndDate').value)== '' ||($('formEndDate').value)== null) {
			$('formEndDate').value = null;
			};
		new Element.show("savingIndicator");
		new Ajax.Request($('groupForm').action, {method:'post', asynchronous:true, parameters:Form.serialize('groupForm'),  evalScripts:true,
																	onSuccess:function(t)
																		{ 	updateGroups($(sponsorSiteAutocompleterProps.basename+'-hidden').value, t.responseText.split("/*")[0], true);
																					new Element.show("savedIndicator");
																			new Element.hide("savingIndicator")
																			if(t.responseText.split("/*")[1] == 'no'){
																			e1 = document.getElementById("errorsDiv");
																			if(e1!=null){
																				e1.innerHTML='';
																			}
																			}
																			if(t.responseText.split("/*")[1] == 'yes'){
																				for(count=0;count<t.responseText.split("/*")[2];count++){
																					error = t.responseText.split("/*")[4+2*count];
																					e1 = document.getElementById("errorsDiv");
																					if((e1!=null && error!=null)){
																						e1.innerHTML=' <ul class="errors"> <li>'+ error + '</li> </ul>'	
																					}
																				}
																				
																			} 
																		}
															});
								 return false;
	}
	function handleHealthcarSiteClear(){
		$("healthcareSite-input").value="";
		$("healthcareSite-hidden").value="";
		$("disease-sub-category").options.length=0;
		e1 = document.getElementById("errorsDiv");
		if(e1!=null){
			e1.innerHTML='';
		}
		new Element.update($("groupDisplay"),'');
	}
</script>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>	
</head>
<body>

<div id="main">

<tags:panelBox title="Investigator Groups">
		<div>
			<input type="hidden" name="_finish" value="true"> 
			<input type="hidden" name="type1" value=""> 
			<input type="hidden" name="_addGroup" id="_addGroup" value="false">
			<input type="hidden" name="_action" id="_action" value="">
		    <input type="hidden" name="_selected" id="_selected" value="">
		    <input type="hidden" name="_selectedSite" id="_selectedSite" value="">
		    <input type="hidden" name="_selectedInvestigator" id="_selectedInvestigator" value="">
		    <input type="hidden" name="_siteChanged" id="_siteChanged" value="false">
		</div>
		<tags:errors path="*" />
		

 <chrome:division title="Organization" id="disease">
          Organization<br>
          <input type="hidden" id="healthcareSite-hidden"
					name="healthcareSite" value="${command.healthcareSite.id}" /> <input
					id="healthcareSite-input" size="60" type="text" name="xyz"
					value="${command.healthcareSite.name}"
					class="autocomplete validate-notEmpty" /> 
					<input type="button" onclick="handleHealthcarSiteClear()"
                        value="Clear"/>
					<tags:indicator	id="healthcareSite-indicator" />
				<div id="healthcareSite-choices" class="autocomplete"></div>
				 <p id="flashMessage" style="display: none">Search for an Organization first
					</p>

          <br><br>Select a Group<br>
          <select multiple size="1" style="width:400px" id="disease-sub-category">
          </select>
          <div align="right"><input type="button" value="Add Group" onclick="handleAddGroup()"/></div>

      </chrome:division>
<div id="errorsDiv">
</div>
<div id="groupDisplay"/>
</tags:panelBox></div>
</body>
</html>

