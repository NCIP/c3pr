<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="studySiteForm" displayErrors="false">

	<jsp:attribute name="singleFields">
    
    <script language="JavaScript" type="text/JavaScript"><!--
    
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
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
	    							companionStatus=inputElement.id.split("-")[0]+"-companionStudyStatus";
	    							$(companionStatus).innerHTML=selectedChoice.coordinatingCenterStudyStatus.displayName;
								}
}
	var instanceRowInserterProps = {

    add_row_division_id: "companionTable", 	        
    skeleton_row_division_id: "dummy-row",
    initialIndex: ${fn:length(command.study.companionStudyAssociations)},
    softDelete: false,
    isAdmin: ${isAdmin == 'true'},
    path: "study.companionStudyAssociations",
    postProcessRowInsertion: function(object){
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
var win
Event.observe(window, "load", function() {

    $('createCompanion').observe('click', function(event) {
       	var table = document.getElementById("companionTable") ;
       	var length = table.rows.length;
		var rowCount = 0;
       	for(var i=1 ; i < length; i++){
	  		if(table.rows[i].id.indexOf('deleted') == -1){
	  			++ rowCount ;
			}
         }
		win = new Window(
				{title: "Create Companion Study", top:35, left:35, width:1000, height:400, zIndex:100,
				url: "<c:url value='/pages/study/createCompanionStudy?decorator=noheaderDecorator&embeddedStudy=true&flowType=${flowType}&rowCount='/>"+ rowCount, showEffectOptions: {duration:1.5}}
				) 
		win.showCenter(true);
    });
})

function createCompanion(shortTitle){
	RowManager.addRow(instanceRowInserterProps);
	$('companionStudy' + currentRow + '-input').value = shortTitle;
	$('companionStudy' + currentRow + '-hidden').name = "some_dummy_name";
	$('companionStudy' + currentRow + '-input').disabled=true;
	$('companionStudy' + currentRow+'-companionStudyStatus').innerHTML="Pending";
	closePopup();
}

function closePopup() {
	win.close();
}

--></script>
        
<tags:errors path="study.companionStudyAssociations" />

<table border="0" cellspacing="0" cellpadding="0" height="50" width="100%" border="2">
    <tr>
        <td>
            <br>
			<c:choose>
				<c:when test="${empty command.study.studySites || (fn:length(command.study.studySites) == 1 && (empty command.study.studySites[0].healthcareSite))}">
					<tr align="center">
						<td align="center" height="50">Please add study site to the study, click <a href="javascript:document.getElementById('flowredirect-target').name='_target6';document.getElementById('flowredirect').submit();"> Add Sites </a> to add study site</td>
					</tr>
				</c:when>
				<c:otherwise>
					<table id="companionTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
		                <tr>
		                    <th><b><span class="required-indicator">Short Title</span></b>
		                    <tags:hoverHint keyProp="study.companionstudy.name" /></th>
		                    <th><b>Status</b>
		                    <tags:hoverHint keyProp="study.companionstudy.status" /></th>
		                    <th><b>Mandatory</b>
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
		                			<input type="button" id="companionStudy${status.index}-clear" value="Clear"/>
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
				</c:otherwise>
			</c:choose>
 	
        </td>
    </tr>
</table>
<div align="right">
	<c:if test="${! (empty command.study.studySites || (fn:length(command.study.studySites) == 1 && (empty command.study.studySites[0].healthcareSite)))}">
		<input id="addCompanion" type="button" value="Add Companion Study" onclick="javascript:RowManager.addRow(instanceRowInserterProps);" />
		<input id="createCompanion" type="button" value="Create Companion Study" />
	</c:if>
</div>

</jsp:attribute>
<jsp:attribute name="localButtons">
</jsp:attribute>
</tags:tabForm>
<div id="dummy-row" style="display:none;">
    <table>
        <tr id="companionTable-PAGE.ROW.INDEX">
                       
            <td>
                <input type="hidden" id="companionStudyPAGE.ROW.INDEX-hidden"
                        name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy"/>
                <input class="autocomplete validate-notEmpty" type="text" id="companionStudyPAGE.ROW.INDEX-input"
                       size="40"
                       value="${command.study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.shortTitleText}"/>
                <input type="button" id="companionStudyPAGE.ROW.INDEX-clear"
                        value="Clear"/>
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
</body>
</html>