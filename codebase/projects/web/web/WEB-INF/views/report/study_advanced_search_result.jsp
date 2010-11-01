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
    <title>Study Search Results</title>
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
            {key:"studyTargetAccrual",      label:"Target Accrual",       sortable:true,      resizeable:true}
        ];
        
        var studyDataSource = new YAHOO.util.DataSource(YAHOO.example.Data.studyList);
        studyDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        studyDataSource.responseSchema = {
            fields: ["studyShortTitle", "identifier", "studyStatus", "studyPhase", "studyType", "studyTargetAccrual", "id"]
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
        var studyDataTable = new YAHOO.widget.DataTable("studyTable", myColumnDefs, studyDataSource, oConfigs);
        studyDataTable.subscribe("rowMouseoverEvent", studyDataTable.onEventHighlightRow); 
        studyDataTable.subscribe("rowMouseoutEvent", studyDataTable.onEventUnhighlightRow); 
        studyDataTable.subscribe("rowClickEvent", function (oArgs) {
        	var elTarget = oArgs.target;
        	var oRecord = this.getRecord(elTarget);
        	document.location='/c3pr/pages/study/viewStudy?studyId='+ oRecord.getData("id");
        }); 
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
<chrome:box title="study Search Results">
<chrome:division>
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
	</div>
	<div id="printable">
	<div id="studyTable" class="yui-skin-sam"></div>
	</div>
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print" onclick="javascript:launchPrint();"/>
	</div>
</chrome:division>
</chrome:box>
</body>
</html>