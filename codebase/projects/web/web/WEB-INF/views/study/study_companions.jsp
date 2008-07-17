<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>



<html>
<head>
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />

</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}"
	formName="studySiteForm" displayErrors="false">

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
								}
}
	var instanceRowInserterProps = {

    add_row_division_id: "companionTable", 	        
    skeleton_row_division_id: "dummy-row",
    initialIndex: ${fn:length(command.companionStudyAssociations)},
    softDelete: ${softDelete == 'true'},
    isAdmin: ${isAdmin == 'true'},
    path: "companionStudyAssociations",
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

Event.observe(window, "load", function() {

    $('createCompanion').observe('click', function(event) {
            Lightview.show({
              href: "<c:url value='/pages/study/createCompanionStudy?decorator=noheaderDecorator&embeddedStudy=true' />",
              rel: 'iframe',
              title: ':: Companion Study::',
              options: {
              autosize: false,
              width: 1400,
              height:600,
              ajax: {
                    onComplete: function() {
                        $('submitAJAXForm').observe('click', postSubmitSkinForm);
                    }
                }
              }
            });
    });
})

function createCompanion(shortTitle){
	RowManager.addRow(instanceRowInserterProps);
	$('companionStudy' + currentRow + '-input').value = shortTitle;
	closePopup();
}

function closePopup() {
	Lightview.hide();
}

function postSubmitSkinForm() {
    var action = document.forms[0].action;
    var value = getSelectedValue();

    var url = action + "?conf['skinPath'].value=" + value;
//    alert(url);
//    return;
    
    new Ajax.Request(url, {
      method: 'post',
      onSuccess: function(transport) {
          Lightview.hide();
          setTimeout('reloadPage()', 500);
      }
    });
}


--></script>
        
<tags:errors path="companionStudyAssociations" />

<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <br>
            <table id="companionTable" class="tablecontent" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th><b><span class="required-indicator">Companion Study Short Title</span></b>
                    <tags:hoverHint keyProp="study.companionstudy.name" /></th>
                    <th><b>Status</b>
                    <tags:hoverHint keyProp="study.companionstudy.status" /></th>
                    <th><b>Mandatory</b>
                    <tags:hoverHint keyProp="study.companionstudy.mandatory" /></th>
                    <th></th>
                </tr>
                   
                   <c:forEach items="${command.companionStudyAssociations}" varStatus="status" var="companionStudyAssociation">
                    <tr id="companionTable-${status.index}">
                     <td>
               				<input type="hidden" id="companionStudy${status.index}-hidden"
                        			name="companionStudyAssociations[${status.index}].companionStudy"
                      				 value="${command.companionStudyAssociations[status.index].companionStudy.id}"/>
                			<input class="autocomplete validate-notEmpty" type="text" id="companionStudy${status.index}-input"
                       				size="40"
                      				 value="${command.companionStudyAssociations[status.index].companionStudy.shortTitleText}"/>
                				<input type="button" id="companionStudy${status.index}-clear"
                       				 value="Clear"/>
                  		 	<tags:indicator id="companionStudy${status.index}-indicator"/>
                  			<div id="companionStudy${status.index}-choices" class="autocomplete"></div>
           			 </td>
           			 <td class="alt" align="center">${command.companionStudyAssociations[status.index].companionStudy.coordinatingCenterStudyStatus.code}</td>
                     <td>
                		<form:select path="companionStudyAssociations[${status.index}].mandatoryIndicator" cssClass="validate-notEmpty">
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
	<input id="addCompanion" type="button" value="Add Companion Study" onclick="javascript:RowManager.addRow(instanceRowInserterProps);" />
	<input id="createCompanion" type="button" value="Create Companion Study" />
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
                        name="companionStudyAssociations[PAGE.ROW.INDEX].companionStudy"/>
                <input class="autocomplete validate-notEmpty" type="text" id="companionStudyPAGE.ROW.INDEX-input"
                       size="40"
                       value="${command.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.shortTitleText}"/>
                <input type="button" id="companionStudyPAGE.ROW.INDEX-clear"
                        value="Clear"/>
                   <tags:indicator id="companionStudyPAGE.ROW.INDEX-indicator"/>
                  <div id="companionStudyPAGE.ROW.INDEX-choices" class="autocomplete"></div>
            </td>
			<td/>
            <td>
                <select id="companionStudyAssociations[PAGE.ROW.INDEX].mandatoryIndicator" name="companionStudyAssociations[PAGE.ROW.INDEX].mandatoryIndicator" class="validate-notEmpty">
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