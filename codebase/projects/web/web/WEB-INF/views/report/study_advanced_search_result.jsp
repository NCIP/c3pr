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
    <title>Subject Search Results</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>${tab.longTitle}</title>
<script>
YAHOO.example.Data = {
	    subjectList: [
					<c:forEach items="${subjectList}" var="subject" varStatus="status">
					        {
					            subjectFullName: "${subject.fullName}",
					            identifier: "${subject.primaryIdentifierValue}",
					            subjectGender: "${subject.administrativeGenderCode}",
					            subjectEthnicity: "${subject.ethnicGroupCode}",
						        subjectBirthDate:  "${subject.formattedBirthDate}"	            
					         }
					         <c:if test="${!status.last}">,</c:if>
					</c:forEach>
					 ]
}

YAHOO.util.Event.addListener(window, "load", function() {
    YAHOO.example.CustomSort = function() {
        var myColumnDefs = [
            {key:"subjectFullName",       label:"Full Name",       sortable:true,      resizeable:true , minWidth:250},
            {key:"identifier",         label:"Primary Identifier", sortable:true,      resizeable:true},
            {key:"subjectGender",         label:"Gender",          sortable:true,      resizeable:true},
            {key:"subjectEthnicity",      label:"Ethnicity",       sortable:true,      resizeable:true},
            {key:"subjectBirthDate",      label:"Birthdate",       sortable:true,      resizeable:true}
        ];

        var subjectDataSource = new YAHOO.util.DataSource(YAHOO.example.Data.subjectList);
        subjectDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
        subjectDataSource.responseSchema = {
            fields: ["subjectFullName", "identifier", "subjectGender", "subjectEthnicity", "subjectBirthDate"]
        };

        //Create config
        var oConfigs = {
        		paginator: new YAHOO.widget.Paginator({ rowsPerPage: 10 }), 
				draggableColumns:true
			};
        var subjectDataTable = new YAHOO.widget.DataTable("subjectTable", myColumnDefs, subjectDataSource, oConfigs);

        return {
            oDS: subjectDataSource,
            oDT: subjectDataTable
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
<chrome:box title="Subject Search Results">
<chrome:division>
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print"/>
		<tags:button color="blue" value="export" size="small" icon="export"/>
	</div>
	<div id="subjectTable" class="yui-skin-sam"></div>
	<div align="right">
		<tags:button color="blue" value="print" size="small" icon="print"/>
		<tags:button color="blue" value="export" size="small" icon="export"/>
	</div>
</chrome:division>
</chrome:box>
</body>
</html>