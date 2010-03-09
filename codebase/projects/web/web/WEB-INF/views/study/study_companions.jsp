<%@ include file="taglibs.jsp"%>
<link href="themes/mac_os_x.css" rel="stylesheet" type="text/css"/>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" displayErrors="false">
	<jsp:attribute name="singleFields">
    <tags:instructions code="study_companions" />
    <script type="text/javascript">

	function addRow(action){
    	if(action == 'createCompanionStudy'){
    		confirmationPopup();
    	}else{
    		$('dummy-row').innerHTML=$('dummy-row-add').innerHTML;
    		javascript:RowManager.addRow(instanceRowInserterProps);
        }
    }

	var confirmWin ;
	
    function confirmationPopup(){
    	confirmWin = new Window({className :"mac_os_x", title: "Confirm", 
			hideEffect:Element.hide, 
			zIndex:100, width:400, height:180 , minimizable:false, maximizable:false,
			showEffect:Element.show 
			}); 
		confirmWin.setContent($('confirmationMessage')) ;
		confirmWin.showCenter(true);
    }

    function createNewCompanion(){
    	<tags:tabMethod method="createEmbeddedCompanionStudy" viewName="/study/asynchronous/redirect_companion"   divElement="'dummy-div'" formName="'tabMethodForm'" javaScriptParam="'flowType=${flowType}'"/>
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
	    		contentWin = new Window({className :"mac_os_x", closable: false, title: "Create Companion Study", top:35, left:35, width:1000, height:580, zIndex:100, destroyOnClose: true, minimizable: false, maximizable : false}) 
	    		contentWin.setContent(arr[0])
	    		
	    		contentWin.showCenter(true); 
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
</script>
<tags:errors path="study.companionStudyAssociations" />
<div id="dummy-div" style="display: none"></div>
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
		               	<c:choose>
		               		<c:when test="${fn:length(command.study.companionStudyAssociations) == 0 }">
		               			<tr>
					      			<td align="left" id="addCompanionMessage" colspan="3"><fmt:message key="study.companion.addCompanion"/></td>
					      		</tr>
		               		</c:when>
		               		<c:otherwise>
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
				                		<form:select path="study.companionStudyAssociations[${status.index}].mandatoryIndicator" cssClass="required validate-notEmpty">
				                    			<option value="">Please Select</option>
				                    				<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
				                		</form:select>
				            		 </td>
				                     <td>
				                     <csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="UPDATE" authorizationCheckName="studyAuthorizationCheck">
	                     	            	<c:if test="${!companionStudyAssociation.companionStudy.standaloneIndicator && companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'PENDING'}">
	                     	            		<tags:button id="editCompanionStudy" type="button" color="blue" value="Edit" onclick="javascript:document.location='editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}&parentStudyFlow=${flowType}';" size="small"/>
	                     	            	</c:if>
	                     	            	<tags:button id="deleteCompanionStudy" type="button" color="red" value="Delete" onclick="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index},'${companionStudyAssociation.id==null?'HC#':'ID#'}${companionStudyAssociation.id==null?companionStudyAssociation.hashCode:companionStudyAssociation.id}');" size="small"/>
				                     </csmauthz:accesscontrol>	            	
				                    </tr>
				                </c:forEach>
		               		</c:otherwise>
		               	</c:choose>
   			            </table>
			
        </td>
    </tr>
</table>
<div align="right">
<tags:button type="button" color="blue" icon="add" value="Add Existing Companion" onclick="$('addCompanionMessage') != null ? $('addCompanionMessage').hide():'';addRow('addExistingCompanionStudy')" size="small"/>
<tags:button type="button" color="blue" value="Create New Companion" 
onclick="$('addCompanionMessage') != null ? $('addCompanionMessage').hide():'';addRow('createCompanionStudy')" size="small" icon="add"/>
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
	                <select id="companionStudyAssociations[PAGE.ROW.INDEX].mandatoryIndicator" name="study.companionStudyAssociations[PAGE.ROW.INDEX].mandatoryIndicator" class="required validate-notEmpty">
	                    <option value="">Please Select</option>
	                    <c:forEach items="${yesNo}" var="status">
	                        <option value="${status.code}">${status.desc}</option>
	                    </c:forEach>
	                </select>
	            </td>
	            <td>
            		<tags:button id="deleteCompanionStudy" type="button" color="red" value="Delete" onclick="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX, -1);" size="small"/>
                </td>
	        </tr>
	    </table>
</div>
<div id="HiddenPage" style="display:none;">
<div id="confirmationMessage" style="padding: 15px;">
	<img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="STUDY.CREATE.COMPANION.WARNING"/>
	<div id="actionButtons">
		<div class="flow-buttons">
	   	<span class="next">
	   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="confirmWin.close();" />
			<tags:button type="button" color="green" icon="save" onclick="createNewCompanion();" value="Continue" />
		</span>
		</div>
	</div>
</div>
</div>
</body>
</html>