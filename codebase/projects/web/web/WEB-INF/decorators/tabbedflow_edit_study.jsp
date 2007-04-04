<%-- This is the standard tabbedFlow decorator for respective C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
          prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">    
    <title>C3PRV2</title>
    <tags:include/>   
    <tags:stylesheetLink name="tabbedflow"/>
    <tags:javascriptLink name="tabbedflow"/>  
    <decorator:head/>    
</head>
<body>
<layout:header/>
<layout:navigation/>
<div class="tabpane">
	<tabs:levelTwoTabs tab="${tab}" flow="${flow}"/>   
	<tabs:body title="${tab.longTitle} - Short Title: ${command.trimmedShortTitleText}">       
    <div class="tabcontent workArea">
    <table border="0" id="table1" cellspacing="10" width="100%">
		<tr>
		<td valign="top" width="20%">
			<studyTags:studySummary />
		</td>		
		<td width="40%" valign="top">
		<div class="body">
            <decorator:body/>
        </div>
        <div class="tabcontrols autoclear">    		
            <a href="javascript:fireAction('update','0');" id="flow-next">
                Save   				
			</a>    		
		</div>
		</td>			
    	</tr>  
    	<tr>
    	<td valign="top" width="20%">	
		    <studyTags:subjectAssignments/>					     
	    </td>
    	</tr>   
    </tabs:body>  	
    </table>
</div>
<form:form id="flowredirect">
    <input type="hidden" name="_target${tab.targetNumber}" id="flowredirect-target"/>
    <input type="hidden" name="_page${tab.number}"/>
</form:form>
<layout:footer />
</body>
</html>
