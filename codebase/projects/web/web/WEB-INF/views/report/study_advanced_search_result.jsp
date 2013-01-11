<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
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
	    studyList: [
					<c:forEach items="${studies}" var="study" varStatus="status">
					        {
					            studyShortTitle: "${study.trimmedShortTitleText}",
					            identifier: "${study.primaryIdentifier}",
					            studyStatus: "${study.coordinatingCenterStudyStatus}",
					            studyPhase: "${study.phaseCode}",
					            studyType: "${study.type}",
						        studyTargetAccrual:  "${study.targetAccrualNumber}",
						        companionIndicator:"${study.companionIndicator ? 'Yes':'No'}",
						        id:  "${study.id}"
					         }
					         <c:if test="${!status.last}">,</c:if>
					</c:forEach>
					 ]
}

YAHOO.util.Event.addListener(window, "load", function() {
    YAHOO.example.CustomSort = function() {
        var myColumnDefs = [
            {key:"studyShortTitle",       label:"Short Title",       sortable:true,      resizeable:true , minWidth:250},
            {key:"identifier",         label:"Primary Identifier", sortable:true,      resizeable:true},
            {key:"studyStatus",         label:"Status",          sortable:true,      resizeable:true},
            {key:"studyPhase",      label:"Phase",       sortable:true,      resizeable:true},
            {key:"studyType",      label:"Type",       sortable:true,      resizeable:true},
            {key:"studyTargetAccrual",      label:"Target Accrual",       sortable:true,      resizeable:true},
            {key:"companionIndicator",      label:"Companion",       sortable:true,      resizeable:true}
        ];
        
        var studyDataSource = new YAHOO.util.DataSource(YAHOO.example.Data.studyList);
        studyDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        studyDataSource.responseSchema = {
            fields: ["studyShortTitle", "identifier", "studyStatus", "studyPhase", "studyType", "studyTargetAccrual", "companionIndicator","id"]
        };

        //Create config
        var oConfigs = {
        		width:"70em" ,
        		paginator: new YAHOO.widget.Paginator({ 
        			rowsPerPage: 20, 
        			rowsPerPageOptions : [10,25,50,  {value:100000000,text:'All'}], 
        			template : "{FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown} {ShowAllLink}" 
        			}), 
				draggableColumns:true
			};
        var studyDataTable = new YAHOO.widget.ScrollingDataTable("studyTable", myColumnDefs, studyDataSource, oConfigs);
        studyDataTable.subscribe("rowMouseoverEvent", studyDataTable.onEventHighlightRow); 
        studyDataTable.subscribe("rowMouseoutEvent", studyDataTable.onEventUnhighlightRow); 
        studyDataTable.subscribe("rowClickEvent", function (oArgs) {
        	var elTarget = oArgs.target;
        	var oRecord = this.getRecord(elTarget);
        	document.location='/c3pr/pages/study/viewStudy?studyId='+ oRecord.getData("id");
        }); 
        // Shows dialog, creating one when necessary
        var newCols = true;
        var showDlg = function(e) {
            YAHOO.util.Event.stopEvent(e);
            if(newCols) {
                // Populate Dialog
                // Using a template to create elements for the SimpleDialog
                var allColumns = studyDataTable.getColumnSet().keys;
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
                studyDataTable.hideColumn(sKey);
            }
            else {
                // Shows a Column
                studyDataTable.showColumn(sKey);
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
        studyDataTable.subscribe("columnReorderEvent", function(){
            newCols = true;
            YAHOO.util.Event.purgeElement("dt-dlg-picker", true);
            YAHOO.util.Dom.get("dt-dlg-picker").innerHTML = "";
        }, this, true);
		
		// Hook up the SimpleDialog to the link
		YAHOO.util.Event.addListener("dt-options-link", "click", showDlg, this, true);
        return {
            oDS: studyDataSource,
            oDT: studyDataTable
        };
    }();
    
});


</script>
<style type="text/css">
#search td {
color:white;
}
</style>
</head>
<body>
<!--  tags:instructions code="participant_search_report"/>  -->
<chrome:box title="Search Results">
<chrome:division>
	<c:if test="${fn:length(studies)>0}">
		${fn:length(studies)} records found.
	</c:if>
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
		<a style="text-decoration:none; color:black; font-weight:bold;" href="<c:url value="/pages/report/advancedSearch/advanceSearchResultsExport"/>" />&nbsp;
		<span style="behavior: url('/c3pr/js/button-pseudoclass-IE-hack.htc');background:#330033;border:medium none;font-size:11px;	color:white;
			cursor:default; text-align:center;	vertical-align:middle;	padding:2px;"><b>export</b></span></a>
	</div>
	
	<div id="dt-example">
			<div id="dt-options"><a id="dt-options-link" href="fallbacklink.html">Table Options</a></div>
	</div>
	<div id="printable">
		<div id="studyTable" class="yui-skin-sam"></div>
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
					
	</div>
	
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
		<a style="text-decoration:none; color:black; font-weight:bold;" href="<c:url value="/pages/report/advancedSearch/advanceSearchResultsExport"/>" />&nbsp;
		<span style="behavior: url('/c3pr/js/button-pseudoclass-IE-hack.htc');background:#330033;border:medium none;font-size:11px;	color:white;
			cursor:default; text-align:center;	vertical-align:middle;	padding:2px;"><b>export</b></span></a>
	</div>
</chrome:division>
</chrome:box>
</body>
</html>
