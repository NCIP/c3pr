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
	    registrationList: [
					<c:forEach items="${registrations}" var="registration" varStatus="status">
					        {
					            studyIdentifier: "${registration.studySite.study.primaryIdentifier}",
					            subjectFullName: "${registration.participant.fullName}",
					            subjectPrimaryIdentifier: "${registration.participant.primaryIdentifierValue}",
						        studySite:  "${registration.studySite.healthcareSite.name}",
						        registrationStatus:  "${registration.regWorkflowStatus.code}",
						        registrationDate:  "${registration.startDateStr}", 
						        registrationDateSort:  "${registration.startDate}",
						        registrationTreatingPhysician:  "${registration.treatingPhysicianFullName}",
						        identifierStr:  "<tags:identifierParameterString identifier='${registration.systemAssignedIdentifiers[0] }'/>"
					         }
					         <c:if test="${!status.last}">,</c:if>
					</c:forEach>
					 ]
}

YAHOO.util.Event.addListener(window, "load", function() {
    YAHOO.example.CustomSort = function() {
        var myColumnDefs = [
            {key:"studyIdentifier",         	label:"Study Title", 		sortable:true,      resizeable:true},
            {key:"studyIdentifier",         	label:"Study Identifier", 	sortable:true,      resizeable:true},
            {key:"subjectFullName",         	label:"Subject Name",       sortable:true,      resizeable:true},
            {key:"subjectPrimaryIdentifier",    label:"Subject Identifier", sortable:true,      resizeable:true},
            {key:"studySite",      				label:"Study Site",       	sortable:true,     	resizeable:true},
            {key:"registrationStatus",        	label:"Registration Status",sortable:true,      resizeable:true}//,
            
            //{key:"registrationDate",         	label:"Registration Date",  sortable:true,      resizeable:true , sortOptions: { field: "registrationDateSort" }  },
            //{key:"registrationTreatingPhysician",label:"Treating Physician",sortable:true,      resizeable:true}
        ];
        
        var registrationDataSource = new YAHOO.util.DataSource(YAHOO.example.Data.registrationList);
        registrationDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        registrationDataSource.responseSchema = {
            fields: [ "studyIdentifier", "subjectFullName", "subjectPrimaryIdentifier", "studySite", "registrationStatus", "registrationDate", "registrationTreatingPhysician", "identifierStr", "registrationDateSort"]
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
        var registrationDataTable = new YAHOO.widget.ScrollingDataTable("registrationTable", myColumnDefs, registrationDataSource, oConfigs);
        
        registrationDataTable.subscribe("rowMouseoverEvent", registrationDataTable.onEventHighlightRow); 
        registrationDataTable.subscribe("rowMouseoutEvent", registrationDataTable.onEventUnhighlightRow); 
        registrationDataTable.subscribe("rowClickEvent", function (oArgs) {
        	var elTarget = oArgs.target;
        	var oRecord = this.getRecord(elTarget);
        	document.location='/c3pr/pages/registration/manageRegistration?'+ oRecord.getData("identifierStr");
        }); 
        
        
        // Shows dialog, creating one when necessary
        var newCols = true;
        var showDlg = function(e) {
            YAHOO.util.Event.stopEvent(e);
            if(newCols) {
                // Populate Dialog
                // Using a template to create elements for the SimpleDialog
                var allColumns = registrationDataTable.getColumnSet().keys;
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
                    elKey.innerHTML = oColumn.getKey();
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
                registrationDataTable.hideColumn(sKey);
            }
            else {
                // Shows a Column
                registrationDataTable.showColumn(sKey);
            }
        };
        
        // Create the SimpleDialog
        YAHOO.util.Dom.removeClass("dt-dlg", "inprogress");
        var myDlg = new YAHOO.widget.SimpleDialog("dt-dlg", {
                width: "30em",
			    visible: false,
			    modal: true,
			    buttons: [ 
					{ text:"Close",  handler:hideDlg }
                ],
                fixedcenter: true,
                constrainToViewport: true
		});
		myDlg.render();
        // Nulls out myDlg to force a new one to be created
        registrationDataTable.subscribe("columnReorderEvent", function(){
            newCols = true;
            YAHOO.util.Event.purgeElement("dt-dlg-picker", true);
            YAHOO.util.Dom.get("dt-dlg-picker").innerHTML = "";
        }, this, true);
		
		// Hook up the SimpleDialog to the link
		YAHOO.util.Event.addListener("dt-options-link", "click", showDlg, this, true);
		
		
        return {
            oDS: registrationDataSource,
            oDT: registrationDataTable
        };
    }();
    
});


</script>
<style>

/* custom styles for this example */
#dt-example {width:45em;margin:0 auto;}
#dt-options {text-align:right;margin:1em 0;}
#dt-dlg {visibility:hidden;border:1px solid #808080;background-color:#E3E3E3;}
#dt-dlg .hd {font-weight:bold;padding:1em;background:none;background-color:#E3E3E3;border-bottom:0;}
#dt-dlg .ft {text-align:right;padding:.5em;background-color:#E3E3E3;}
#dt-dlg .bd {height:10em;margin:0 1em;overflow:auto;border:1px solid black;background-color:white;}
#dt-dlg .dt-dlg-pickercol {clear:both;padding:.5em 1em 3em;border-bottom:1px solid gray;}
#dt-dlg .dt-dlg-pickerkey {float:left;}
#dt-dlg .dt-dlg-pickerbtns {float:right;}

/* Container workarounds for Mac Gecko scrollbar issues */
.yui-panel-container.hide-scrollbars #dt-dlg .bd {
    /* Hide scrollbars by default for Gecko on OS X */
    overflow: hidden;
}
.yui-panel-container.show-scrollbars #dt-dlg .bd {
    /* Show scrollbars for Gecko on OS X when the Panel is visible  */
    overflow: auto;
}
#dt-dlg_c .underlay {overflow:hidden;}



/* rounded corners */
#dt-dlg .corner_tr {
    background-image: url( assets/img/tr.gif);
    position: absolute;
    background-repeat: no-repeat;
    top: -1px;
    right: -1px;
    height: 4px;
    width: 4px;
}
#dt-dlg .corner_tl {
    background-image: url( assets/img/tl.gif);
    background-repeat: no-repeat;
    position: absolute;
    top: -1px;
    left: -1px;
    height: 4px;
    width: 4px;
}
#dt-dlg .corner_br {
    background-image: url( assets/img/br.gif);
    position: absolute;
    background-repeat: no-repeat;
    bottom: -1px;
    right: -1px;
    height: 4px;
    width: 4px;
}
#dt-dlg .corner_bl {
    background-image: url( assets/img/bl.gif);
    background-repeat: no-repeat;
    position: absolute;
    bottom: -1px;
    left: -1px;
    height: 4px;
    width: 4px;
}

.inprogress {position:absolute;} /* transitional progressive enhancement state */

</style>	
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
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
		<tags:button color="blue" value="export" size="small" icon="export" onclick=""/>
	</div>
	<div id="printable">
	<div id="dt-example">
			<div id="dt-options"><a id="dt-options-link" href="fallbacklink.html">Table Options</a></div>
		</div>
		<div id="registrationTable" class="yui-skin-sam"></div>
		
		<div id="dt-dlg">
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
		<tags:button color="blue" value="export" size="small" icon="export"/>
	</div>
</chrome:division>
</chrome:box>
</body>
</html>