<%-- This is the standard tabbedFlow decorator for respective C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>"
          type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>C3PRV2</title>

    <tags:include/>
    <tags:stylesheetLink name="tabbedflow"/>
    <tags:javascriptLink name="tabbedflow"/>
    <decorator:head/>
</head>

<body>
<div id="content">
    <layout:header/>
    <layout:navigation/>

    <div class="tabpane">
        <div class="tabcontent workArea">
           <div class="body">
            <decorator:body/>
        </div>
  		<div class="tabcontrols autoclear">    		
            <a href="javascript:fireAction('update','0');" id="flow-next">
                Save   				
			</a>    		
		</div>
        </div>
        
    </div>
    

    <form:form id="flowredirect">
        <input type="hidden" name="_target${tab.targetNumber}"
               id="flowredirect-target"/>
        <input type="hidden" name="_page${tab.number}"/>
    </form:form>

</div>

<div id="footer">
    <layout:footer/>
</div>
<tags:jsLogs debug="false"/>
<tags:enableRowDeletion/>
</body>
</html>
