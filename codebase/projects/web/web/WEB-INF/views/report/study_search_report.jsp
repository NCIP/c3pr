<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib uri="http://www.extremecomponents.org" prefix="ec"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<title>${tab.longTitle}</title>
<tags:dwrJavascriptLink objects="createStudyReport"/>
<tags:dwrJavascriptLink objects="studyReportCommand"/>

<script>
function buildTable(form) {		
    
    params = new Array(5);
	var parameterMap = getParameterMap(form);
	
	var studyShortTitle = document.getElementById("studyShortTitle").value;
	var sIdentifier = document.getElementById("studyIdentifier").value;
	var pIdentifier = document.getElementById("participantIdentifier").value;
	var fName = document.getElementById("firstName").value;
	var lName = document.getElementById("lastName").value;
	
	params[0] = studyShortTitle;
	params[1] = sIdentifier;
	params[2] = pIdentifier;
	params[3] = fName;		
	params[4] = lName;		

	studyReportCommand.setParams(params);			
	createStudyReport.getTable(parameterMap, params, showTable);		
}

function clearScreen() {		
	document.getElementById("studyShortTitle").value='';
	document.getElementById("studyIdentifier").value='';
	document.getElementById("participantIdentifier").value='';
	document.getElementById("firstName").value='';
	document.getElementById("lastName").value='';
}

function showTable(table) {
	document.getElementById('tableDiv').innerHTML=table;
}
  
</script>
</head>
<body>
<form:form name="searchForm" id="searchForm" method="post" cssClass="standard autoclear">
	<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="5" >
	<tr>
	<td>
	<div class="autoclear">
        <chrome:box title="Study Criteria" cssClass="paired">
            <p>
            <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="5" >
            <tr>
	            <td align="left">
	            	<div class="row" name="inputs"><b>Short Title</b>
	            		<form:input path="studyShortTitle" size="20"/>
		            </div>
		        </td>
		        <td align="left">
		        	<div class="row" name="inputs"><b>Identifier</b>
	            		<form:input path="studyIdentifier" size="20"/>
		            </div>
		        </td>
	        </tr>    
            </table>
            </p>
        </chrome:box>
    
        <chrome:box title="Participant Criteria" cssClass="paired">  
        	<p>
        	<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="5" >
            <tr>
	            <td align="left">
	            <div class="row" name="inputs"><b>Identifier</b>
	            	<form:input path="participantIdentifier" size="20"/>
	            </div>
		        </td>
		        <td align="left">
		        <div class="row" name="inputs"><b>First Name</b>
	            	<form:input path="firstName" size="20"/>
	            </div>
		        </td>
	        </tr>
	        <tr>
	            <td align="left">
	            <div class="row" name="inputs"><b>Last Name</b>
	            	<form:input path="lastName" size="20"/>
	            </div>
		        </td>
		        <td align="left">
		        <div class="row" name="inputs">	            	
	            </div>
		        </td>
		    </tr>    
            </table>			
			</p>
        </chrome:box>     
	</div>
	</td>
	</tr>
	</table>

	<div class="row" align="center">
	    <input class='ibutton' type='button' onclick="buildTable('searchForm');" value='Search'  title='Search Study'/>
	    <input class='ibutton' type='button' onclick="clearScreen();" value='Clear' />
	</div>
<br />
</form:form>

<chrome:box title="Results">
    <chrome:division id="single-fields">
        <div id="tableDiv">
   			<c:out value="${assembler}" escapeXml="false"/> 
		</div>
	</chrome:division>
</chrome:box>

</body>
</html>