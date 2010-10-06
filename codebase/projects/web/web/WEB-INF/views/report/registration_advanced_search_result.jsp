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
        		paginator: new YAHOO.widget.Paginator({ 
        			rowsPerPage: 10, 
        			rowsPerPageOptions : [10,25,50,  {value:100000000,text:'All'}], 
        			template : "{PreviousPageLink} {PageLinks} {NextPageLink} {RowsPerPageDropdown} {ShowAllLink}" 
        			}), 
				draggableColumns:true
			};
        var registrationDataTable = new YAHOO.widget.DataTable("registrationTable", myColumnDefs, registrationDataSource, oConfigs);
        registrationDataTable.subscribe("rowMouseoverEvent", registrationDataTable.onEventHighlightRow); 
        registrationDataTable.subscribe("rowMouseoutEvent", registrationDataTable.onEventUnhighlightRow); 
        registrationDataTable.subscribe("rowClickEvent", function (oArgs) {
        	var elTarget = oArgs.target;
        	var oRecord = this.getRecord(elTarget);
        	document.location='/c3pr/pages/registration/manageRegistration?'+ oRecord.getData("identifierStr");
        }); 
        return {
            oDS: registrationDataSource,
            oDT: registrationDataTable
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
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
		<tags:button color="blue" value="export" size="small" icon="export" onclick=""/>
	</div>
	<div id="printable">
	<div id="registrationTable" class="yui-skin-sam"></div>
	</div>
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
		<tags:button color="blue" value="export" size="small" icon="export"/>
	</div>
</chrome:division>
</chrome:box>
</body>
</html>