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
<%@taglib uri="http://www.extremecomponents.org" prefix="ec"%>

<html>
<head>
    <title>Registration Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<title>${tab.longTitle}</title>
<tags:dwrJavascriptLink objects="createReport"/>
<tags:dwrJavascriptLink objects="reportCommand"/>

<script>
function buildTable(form) {		
	document.getElementById("formSubmit-indicator").style.display="";
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
	document.getElementById("formSubmit-indicator").style.display="none";
	Element.show("superResults");
}

function clearScreen() {		
	document.getElementById("studyShortTitle").value='';
	document.getElementById("studyCoordinatingSite").value='';
	document.getElementById("siteName").value='';
	document.getElementById("siteNciId").value='';
	document.getElementById("regStartDate").value='';
	document.getElementById("regEndDate").value='';
	document.getElementById("birthDate").value='';
	document.getElementById("raceCode").value='';
}
 
</script>
<style type="text/css">
	.eXtremeTable .filter input[type="text"] {
		width:103px;
		}
</style>
</head>
<body>
<tags:instructions code="registration_search_report" />
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
	            	<div class="row" name="inputs"><b><fmt:message key="study.shortTitle"/></b>
	            		<form:input path="studyShortTitle" size="20"/>
		            </div>
		        </td>
		        <td align="left">
		        	<div class="row" name="inputs"><b><fmt:message key="c3pr.common.identifier"/></b>
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
	            <div class="row" name="inputs"><b><fmt:message key="site.siteName"/></b>
	            	<form:input path="siteName" size="25"/>
	            </div>
		        </td>
		        <td align="left">
		        <div class="row" name="inputs"><b><fmt:message key="c3pr.common.CTEPIdentifier"/></b>
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
		        		<b><fmt:message key="c3pr.common.startDate"/></b><tags:dateInput path="regStartDate"/>
		            </div>
	            </td>
	            <td align="left">    
		            <div class="row" name="inputs">
		            	<b><fmt:message key="c3pr.common.endDate"/></b><tags:dateInput path="regEndDate"/>
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
						<b><fmt:message key="search.dateOfBirth"/></b><tags:dateInput path="birthDate"/>
					</div>
				</td>
				<td align="left" width="40%" >
					<div class="row" name="inputs">
						<b><fmt:message key="search.race"/></b> &nbsp;
						<form:select path="raceCode" cssStyle="width:205px;">
							<option value="">Please Select</option>
							<form:options items="${raceCode}" itemLabel="desc" itemValue="code" />
						</form:select>
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
		<div>
			<tags:button type="button" icon="search" size="small" color="blue" value="Search Registration" onclick="buildTable('searchForm');"/>
			<tags:button type="button" size="small" color="blue" value="Clear" onclick="clearScreen();"/>
		</div>
		<div>
			<img id="formSubmit-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
		</div>
	</div>
<br />
</form:form>


<div id="superResults">
<chrome:box title="Results" >
    <chrome:division id="single-fields">
        <div id="tableDiv">
   			<c:out value="${assembler}" escapeXml="false" /> 
		</div>
	</chrome:division>
</chrome:box>
</div>

<script>
if($('assembler_table').rows.length <= 3){
	Element.hide("superResults");
}
</script>

</body>
</html>
