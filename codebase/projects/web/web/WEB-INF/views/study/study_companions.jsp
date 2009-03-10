<%@ include file="taglibs.jsp"%>
<link href="themes/mac_os_x.css" rel="stylesheet" type="text/css"/>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="studySiteForm" displayErrors="false">

	<jsp:attribute name="singleFields">
     <tags:instructions code="study_companions" />
    <script language="JavaScript" type="text/JavaScript"><!--

	function addRow(action){
    	if(action == 'createCompanionStudy'){
    		$('dummy-row').innerHTML=$('dummy-row-create').innerHTML;
    	}else{
    		$('dummy-row').innerHTML=$('dummy-row-add').innerHTML;
        }
    	 javascript:RowManager.addRow(instanceRowInserterProps);
    }
    
    var currentRow = -1;
    
	var companionStudyAssociationsAutocompleterProps = {
	    basename: "companionStudy",
	    populator: function(autocompleter, text) {
	
	        StudyAjaxFacade.matchComapanionStudies( text, function(values) {
	            autocompleter.setChoices(values)
	        })
	    },
	    valueSelector: function(obj) {
	        return obj.shortTitleText
	    },
	    afterUpdateElement: 
		    function(inputElement, selectedElement, selectedChoice) {
				hiddenField=inputElement.id.split("-")[0]+"-hidden";
				$(hiddenField).value=selectedChoice.id;
				companionStatus=inputElement.id.split("-")[0]+"-companionStudyStatus";
				$(companionStatus).innerHTML=selectedChoice.coordinatingCenterStudyStatus.displayName;
			}
	}
var contentWin;
	var statusIndex;
	
	var instanceRowInserterProps = {
	    add_row_division_id: "companionTable", 	        
	    skeleton_row_division_id: "dummy-row",
	    initialIndex: ${fn:length(command.study.companionStudyAssociations)},
	    softDelete: false,
	    isAdmin: ${isAdmin == 'true'},
	    path: "study.companionStudyAssociations",
	    postProcessRowInsertion: function(object){
	    	statusIndex = object.localIndex;
	        var arr=$$("#companionTable-"+statusIndex+" .dummy-row-createStudy");
	    	if(arr.length==1){
	    		contentWin = new Window({className :"mac_os_x", closable: false, title: "Create Companion Study", top:35, left:35, width:1000, height:400, zIndex:100, hideEffect:Element.hide, showEffect:Element.show, destroyOnClose: true, minimizable: false, maximizable : false}) 
	    		contentWin.setContent(arr[0])
	    		
	    		contentWin.showCenter(true); 
	    		// initializing calender in popup
	    		inputDateElementLocal="study.companionStudyAssociations["+statusIndex+"].companionStudy.consentVersion";
	    		inputDateElementLink="study.companionStudyAssociations["+statusIndex+"].companionStudy.consentVersion-calbutton";
	    		Calendar.setup(
	    		  {
	    		      inputField  : inputDateElementLocal,         // ID of the input field
	    		      ifFormat    : "%m/%d/%Y",    // the date format
	    		      button      : inputDateElementLink       // ID of the button
	    		  }
	    		);
	    		
	    		myObserver = {
	    	    	onDestroy: function(eventName, win) {
	    		      if (win == contentWin) {
	    		    	  $$("#companionTable-"+statusIndex+" #container")[0].appendChild($(arr[0]));
	    		        contentWin = null;
	    		        Windows.removeObserver(this);
	    		      }
	    		    }
	    		  }
	    		  Windows.addObserver(myObserver);
	    	}
			currentRow = object.localIndex;
	        clonedRowInserter=Object.clone(companionStudyAssociationsAutocompleterProps);
			clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
			AutocompleterManager.registerAutoCompleter(clonedRowInserter);
			
	    },
	    onLoadRowInitialize: function(object, currentRowIndex){
			clonedRowInserter=Object.clone(companionStudyAssociationsAutocompleterProps);
			clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
			AutocompleterManager.registerAutoCompleter(clonedRowInserter);
	    }
	};
	

	RowManager.addRowInseter(instanceRowInserterProps);

function createCompanion(shortTitle){
	//RowManager.addRow(instanceRowInserterProps);
	$('companionStudy' + currentRow + '-input').value = shortTitle;
	$('companionStudy' + currentRow + '-hidden').name = "some_dummy_name";
	$('companionStudy' + currentRow + '-input').disabled=true;
	$('companionStudy' + currentRow+'-companionStudyStatus').innerHTML="Pending";
	closePopup(false);
}

function closePopup(deleteRow) {
	if(deleteRow){
		javascript:RowManager.deleteRow(instanceRowInserterProps,currentRow, -1);
	}
	contentWin.close();
}


// SCRIPTS FOR COMPANION


--></script>
        
<tags:errors path="study.companionStudyAssociations" />

<table border="0" cellspacing="0" cellpadding="0" height="50" width="100%" border="2">
    <tr>
        <td>
            <br>
					<table id="companionTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                    <th><b><tags:requiredIndicator /><fmt:message key="study.shortTitle"/></b>
		                    <tags:hoverHint keyProp="study.companionstudy.name" /></th>
		                    <th><b><fmt:message key="c3pr.common.status"/></b>
		                    <tags:hoverHint keyProp="study.companionstudy.status" /></th>
		                    <th><b><fmt:message key="c3pr.common.mandatory"/></b>
		                    <tags:hoverHint keyProp="study.companionstudy.mandatory" /></th>
		                    <th></th>
		                </tr>
		                   
		                   <c:forEach items="${command.study.companionStudyAssociations}" varStatus="status" var="companionStudyAssociation">
		                    <tr id="companionTable-${status.index}">
		                     <td>
		               				<input type="hidden" id="companionStudy${status.index}-hidden"
		                        			name="study.companionStudyAssociations[${status.index}].companionStudy"
		                      				 value="${command.study.companionStudyAssociations[status.index].companionStudy.id}"/>
		                			<input class="autocomplete validate-notEmpty" type="text" id="companionStudy${status.index}-input"
		                       				size="40"  value="${command.study.companionStudyAssociations[status.index].companionStudy.shortTitleText}"/>
		                       		<c:if test="${empty command.study.companionStudyAssociations[status.index].companionStudy.id}">
		                      			<script>
		                      			$('companionStudy${status.index}-hidden').name="some_dummy_value";
		                      			$('companionStudy${status.index}-input').disabled=true;
		                      			</script>
		                      		</c:if>
		                  		 	<tags:indicator id="companionStudy${status.index}-indicator"/>
		                  			<div id="companionStudy${status.index}-choices" class="autocomplete" style="display:none;"></div>
		           			 </td>
		           			 <td class="alt" align="center">
								<div id="companionStudy${status.index}-companionStudyStatus">
									${command.study.companionStudyAssociations[status.index].companionStudy.coordinatingCenterStudyStatus.displayName}
								</div>								 
							</td>
		                     <td>
		                		<form:select path="study.companionStudyAssociations[${status.index}].mandatoryIndicator" cssClass="validate-notEmpty">
		                    			<option value="">Please Select</option>
		                    				<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
		                		</form:select>
		            		 </td>
		                     <td><a
		                                href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index},'${companionStudyAssociation.id==null?'HC#':'ID#'}${companionStudyAssociation.id==null?companionStudyAssociation.hashCode:companionStudyAssociation.id}');"><img
		                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		
		                    </tr>
		                </c:forEach>
		            </table>
			
        </td>
    </tr>
</table>
<div align="right">

<tags:button type="button" color="blue" icon="add" value="Add Existing Companion" 
onclick="addRow('addExistingCompanionStudy')" size="small"/>
<tags:button type="button" color="blue" value="Create New Companion" 
onclick="addRow('createCompanionStudy')" size="small"/>
</div>

</jsp:attribute>
<jsp:attribute name="localButtons">
</jsp:attribute>
</tags:tabForm>
<div id="dummy-row" style="display:none;">
   
</div>
<div id="dummy-row-add" style="display:none;">
	 <table>
	        <tr id="companionTable-PAGE.ROW.INDEX">
	                       
	            <td>
	                <input type="hidden" id="companionStudyPAGE.ROW.INDEX-hidden"
	                        name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy"/>
	                <input class="autocomplete validate-notEmpty" type="text" id="companionStudyPAGE.ROW.INDEX-input"
	                       size="40"
	                       value="${command.study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.shortTitleText}"/>
	                   <tags:indicator id="companionStudyPAGE.ROW.INDEX-indicator"/>
	                  <div id="companionStudyPAGE.ROW.INDEX-choices" class="autocomplete" style="display:none;"></div>
	            </td>
				<td >
					<div id="companionStudyPAGE.ROW.INDEX-companionStudyStatus" >
					</div>
				</td>
	            <td>
	                <select id="companionStudyAssociations[PAGE.ROW.INDEX].mandatoryIndicator" name="study.companionStudyAssociations[PAGE.ROW.INDEX].mandatoryIndicator" class="validate-notEmpty">
	                    <option value="">Please Select</option>
	                    <c:forEach items="${yesNo}" var="status">
	                        <option value="${status.code}">${status.desc}</option>
	                    </c:forEach>
	                </select>
	            </td>
	            <td>
	                <a
                    href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX, -1);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	        </tr>
	    </table>
</div>

<div id="dummy-row-create" style="display:none;">
	 	<table>
	        <tr id="companionTable-PAGE.ROW.INDEX">
	            <td>
	                <input type="hidden" id="companionStudyPAGE.ROW.INDEX-hidden" />
	                <input class="validate-notEmpty" type="text" id="companionStudyPAGE.ROW.INDEX-input" 
	                       size="40" disabled="true"
	                       value="${command.study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.shortTitleText}"/>
	                   <tags:indicator id="companionStudyPAGE.ROW.INDEX-indicator"/>
	                  <div id="companionStudyPAGE.ROW.INDEX-choices" class="autocomplete" style="display:none;"></div>
	            </td>
				<td >
					<div id="companionStudyPAGE.ROW.INDEX-companionStudyStatus" >
					</div>
				</td>
	            <td>
	                <select id="companionStudyAssociations[PAGE.ROW.INDEX].mandatoryIndicator" name="study.companionStudyAssociations[PAGE.ROW.INDEX].mandatoryIndicator" class="validate-notEmpty" >
	                    <option value="">Please Select</option>
	                    <c:forEach items="${yesNo}" var="status">
	                        <option value="${status.code}">${status.desc}</option>
	                    </c:forEach>
	                </select>
	            </td>
	            <td>
	                <a href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX, -1);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
                    <div id="container" style="display:none;">
	                    <div id="dummy-row-createStudy" class="dummy-row-createStudy">
					    	<%@ include file="study_create_companion.jsp"%>
					    	
					    </div>
				    </div>
                </td>
	        </tr>
	    </table>
</div>
</body>
</html>