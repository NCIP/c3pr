<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ include file="/WEB-INF/tags/includeYUI.tag" %>

<html>
<head>
    <title>Search Results</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>${tab.longTitle}</title>
<script>
YAHOO.example.Data = {
	    subjectList: [
					<c:forEach items="${subjects}" var="subject" varStatus="status">
					        {
					            subjectFullName: "${subject.fullName}",
					            identifierSource: "${subject.primaryIdentifierSource}",
					            identifier: "${subject.primaryIdentifierValue}",
					            subjectGender: "${subject.administrativeGenderCode}",
					            subjectEthnicity: "${subject.ethnicGroupCode}",
						        subjectBirthDate:  "${subject.birthDateStr}",
						        subjectBirthDateSort:  "${subject.birthDate}",
						        identifierStr:  "<tags:identifierParameterString identifier='${subject.systemAssignedIdentifiers[0] }'/>"
					         }
					         <c:if test="${!status.last}">,</c:if>
					</c:forEach>
					 ]
};

<c:if test="${studyOrganization.class.name == 'edu.duke.cabig.c3pr.domain.StudyFundingSponsor' && !command.hasFundingSponsorAsStudySite}">
<c:set var="canDisplay" value="true"/>				
<c:set var="orgType" value="Funding Sponsor"/>																										
</c:if>

YAHOO.util.Event.addListener(window, "load", function() {
    YAHOO.example.CustomSort = function() {
        var myColumnDefs = [
            {key:"subjectFullName",       label:"Full Name",       sortable:true,      resizeable:true , minWidth:250},
            {key:"identifierSource",   label:"Primary Identifier Source", sortable:true,      resizeable:true},
            {key:"identifier",         label:"Primary Identifier", sortable:true,      resizeable:true},
            {key:"subjectGender",         label:"Gender",          sortable:true,      resizeable:true},
            {key:"subjectEthnicity",      label:"Ethnicity",       sortable:true,      resizeable:true},
            {key:"subjectBirthDate",      label:"Birthdate",       sortable:true,    sortOptions: { field: "subjectBirthDateSort" },   resizeable:true}
        ];
        
        var subjectDataSource = new YAHOO.util.DataSource(YAHOO.example.Data.subjectList);
        subjectDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        subjectDataSource.responseSchema = {
            fields: ["subjectFullName", "identifierSource", "identifier", "subjectGender", "subjectEthnicity", "subjectBirthDate", "subjectBirthDateSort", "identifierStr"]
        };

        //Create config
        var oConfigs = {
        		width : "70em" ,
        		paginator: new YAHOO.widget.Paginator({ 
        			rowsPerPage: 10, 
        			rowsPerPageOptions : [10,25,50,  {value:100000000,text:'All'}], 
        			template : "{FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown} {ShowAllLink}" 
        			}), 
				draggableColumns:true
			};
        var subjectDataTable = new YAHOO.widget.ScrollingDataTable("subjectTable", myColumnDefs, subjectDataSource, oConfigs);
        subjectDataTable.subscribe("rowMouseoverEvent", subjectDataTable.onEventHighlightRow); 
        subjectDataTable.subscribe("rowMouseoutEvent", subjectDataTable.onEventUnhighlightRow); 
        subjectDataTable.subscribe("rowClickEvent", function (oArgs) {
        	var elTarget = oArgs.target;
        	var oRecord = this.getRecord(elTarget);
        	document.location='/c3pr/pages/personAndOrganization/participant/viewParticipant?'+ oRecord.getData("identifierStr");
        }); 
        
        // Shows dialog, creating one when necessary
        var newCols = true;
        var showDlg = function(e) {
            YAHOO.util.Event.stopEvent(e);
            if(newCols) {
                // Populate Dialog
                // Using a template to create elements for the SimpleDialog
                var allColumns = subjectDataTable.getColumnSet().keys;
                var elPicker = YAHOO.util.Dom.get("dt-dlg-picker");
                var elTemplateCol = document.createElement("div");
                YAHOO.util.Dom.addClass(elTemplateCol, "dt-dlg-pickercol");
                var elTemplateKey = elTemplateCol.appendChild(document.createElement("span"));
                YAHOO.util.Dom.addClass(elTemplateKey, "dt-dlg-pickerkey");
                var elTemplateBtns = elTemplateCol.appendChild(document.createElement("span"));
                YAHOO.util.Dom.addClass(elTemplateBtns, "dt-dlg-pickerbtns");
                var onclickObj = {fn:handleButtonClick, obj:this, scope:false };
                // Create one section in the SimpleDialog for each Column
                var elColumn, elKey, elButton, oButtonGrp;
                for(var i=0,l=allColumns.length;i<l;i++) {
                    var oColumn = allColumns[i];
                    // Use the template
                    elColumn = elTemplateCol.cloneNode(true);
                    // Write the Column key
                    elKey = elColumn.firstChild;
                    elKey.innerHTML = oColumn.label;
                    // Create a ButtonGroup
                    oButtonGrp = new YAHOO.widget.ButtonGroup({ 
                                    id: "buttongrp"+i, 
                                    name: oColumn.getKey(), 
                                    container: elKey.nextSibling
                    });
                    oButtonGrp.addButtons([
                        { label: "Show", value: "Show", checked: ((!oColumn.hidden)), onclick: onclickObj},
                        { label: "Hide", value: "Hide", checked: ((oColumn.hidden)), onclick: onclickObj}
                    ]);
                    elPicker.appendChild(elColumn);
                }
                newCols = false;
        	}
            myDlg.show();
        };
        var hideDlg = function(e) {
            this.hide();
        };
        var handleButtonClick = function(e, oSelf) {
            var sKey = this.get("name");
            if(this.get("value") === "Hide") {
                // Hides a Column
                subjectDataTable.hideColumn(sKey);
            }
            else {
                // Shows a Column
                subjectDataTable.showColumn(sKey);
            }
        };
        
        // Create the SimpleDialog
        YAHOO.util.Dom.removeClass("dt-dlg", "inprogress");
        var myDlg = new YAHOO.widget.SimpleDialog("dt-dlg", {
                width: "30em",
			    visible: false,
			    modal: true,
			    x: 700,
			    y: 300,
			    buttons: [ 
					{ text:"Close",  handler:hideDlg }
                ],
                constrainToViewport: true
		});
		myDlg.render();
        // Nulls out myDlg to force a new one to be created
        subjectDataTable.subscribe("columnReorderEvent", function(){
            newCols = true;
            YAHOO.util.Event.purgeElement("dt-dlg-picker", true);
            YAHOO.util.Dom.get("dt-dlg-picker").innerHTML = "";
        }, this, true);
		
		// Hook up the SimpleDialog to the link
		YAHOO.util.Event.addListener("dt-options-link", "click", showDlg, this, true);
        return {
            oDS: subjectDataSource,
            oDT: subjectDataTable
        };
    }();
    
});

</script>
</head>
<body>
<!--  tags:instructions code="participant_search_report"/>  -->
<chrome:box title="Search Results">
<chrome:division>
	<c:if test="${fn:length(subjects)>0}">
		${fn:length(subjects)} records found.
	</c:if>
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
		<a style="text-decoration:none; color:black; font-weight:bold;" href="<c:url value="/pages/report/advancedSearch/advanceSearchResultsExport"/>" />&nbsp;
		<span style="behavior: url('/c3pr/js/button-pseudoclass-IE-hack.htc');background:#330033;border:medium none;font-size:11px;	color:white;
			cursor:default; text-align:center;	vertical-align:middle;	padding:2px;"><b>export</b></span>
	</div>
	<div id="printable">
		<div id="dt-example">
			<div id="dt-options"><a id="dt-options-link" href="fallbacklink.html">Table Options</a></div>
		</div>
		<div id="subjectTable" class="yui-skin-sam"></div>
		<div id="dt-dlg" class="yui-skin-sam">
		    <span class="corner_tr"></span>
		    <span class="corner_tl"></span>
		    <span class="corner_br"></span>
		    <span class="corner_bl"></span>
		    <div class="hd">
		        Choose which columns you would like to see:
		    </div>
		    <div id="dt-dlg-picker" class="bd">
	    	</div>
		</div>
		
	</div>
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
		<a style="text-decoration:none; color:black; font-weight:bold;" href="<c:url value="/pages/report/advancedSearch/advanceSearchResultsExport"/>" />&nbsp;
		<span style="behavior: url('/c3pr/js/button-pseudoclass-IE-hack.htc');background:#330033;border:medium none;font-size:11px;	color:white;
			cursor:default; text-align:center;	vertical-align:middle;	padding:2px;"><b>export</b></span>
	</div>
</chrome:division>
</chrome:box>
</body>
</html>
