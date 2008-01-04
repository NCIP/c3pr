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
<script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>
<tags:dwrJavascriptLink objects="createReport"/>
<tags:dwrJavascriptLink objects="reportCommand"/>

<script>
function buildTable(form) {		
    
    params = new Array(8);
	var parameterMap = getParameterMap(form);
	var studyShortTitle = document.getElementById("studyShortTitle").value;
	var studyCoordinatingSite = document.getElementById("studyCoordinatingSite").value;
	var siteName = document.getElementById("siteName").value;
	var siteNciId = document.getElementById("siteNciId").value;
	var regStartDate = document.getElementById("regStartDate").value;
	var regEndDate = document.getElementById("regEndDate").value;
	var birthDate = document.getElementById("birthDate").value;
	var raceCode = document.getElementById("raceCode").value;
	
	params[0] = studyShortTitle;
	params[1] = studyCoordinatingSite;
	params[2] = siteName;
	params[3] = siteNciId;		
	params[4] = regStartDate;	
	params[5] = regEndDate;		
	params[6] = birthDate;	
	params[7] = raceCode;	

	reportCommand.setParams(params);			
	createReport.getTable(parameterMap, params, showTable);		
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
	            		<form:input path="studyCoordinatingSite" size="20"/>
		            </div>
		        </td>
	        </tr>    
            </table>
            </p>
        </chrome:box>
    
        <chrome:box title="Site Criteria" cssClass="paired">  
        	<p>
        	<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="5" >
            <tr>
	            <td align="left">
	            <div class="row" name="inputs"><b>Site Name</b>
	            	<form:input path="siteName" size="25"/>
	            </div>
		        </td>
		        <td align="left">
		        <div class="row" name="inputs"><b>NCI ID</b>
	            	<form:input path="siteNciId" size="25"/>
	            </div>
		        </td>
	        </tr>    
            </table>			
			</p>
        </chrome:box>     
	</div>
	</td>
	</tr>
	<tr>
	<td>
	<div class="autoclear">
        <chrome:box title="Registration Criteria" cssClass="paired">
        	<p>
        	<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="5" >
        	<tr>
	        	<td align="left">
		        	<div class="row" name="inputs">
		        		<b>Start Date</b><tags:dateInput path="regStartDate"/>
		            </div>
	            </td>
	            <td align="left">    
		            <div class="row" name="inputs">
		            	<b>End Date</b><tags:dateInput path="regEndDate"/>
		            </div>
	            </td>
        	</tr>        	
        	</table>           
            </p>
        </chrome:box>
    
        <chrome:box title="Subject Criteria" cssClass="paired">  
        	<p>
        	<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="5" >
        	<tr>
	        	<td align="left">
					<div class="row" name="inputs">
						<b>DOB</b><tags:dateInput path="birthDate"/>
					</div>
				</td>
				<td align="left"><b>Race</b> &nbsp;
					<form:select path="raceCode">
						<option value="">Please Select</option>
						<form:options items="${raceCode}" itemLabel="desc" itemValue="code" />
					</form:select>
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