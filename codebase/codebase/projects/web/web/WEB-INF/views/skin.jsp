<%@ page import="java.util.Enumeration" %>
<%@ page import="edu.duke.cabig.c3pr.web.admin.ConfigurationCommand" %>
<%-- This is the standard decorator for all C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>

<html>
<head>
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <tags:include/>

    <decorator:head/>

<style>
    .selectobject {
        background-color: #F6F6F6;
        border: 1px dotted #CCC;
        cursor: pointer;
        float: left;
        margin: 2px;
        padding: 20px;
        text-align: center;
    }

    .selectobject:hover {
        border: 1px solid #CCC;
        background-color: #E9E9E9;
    }
</style>
    
</head>

<body>
<%--<layout:header2/>--%>

<center>

    <form:form name="skinForm">

<table width="100%" cellspacing="10" cellpadding="2" border="0">
    <tr>
        <td align="left"><h3><form:radiobutton path="conf['skinPath'].value" value="blue" id="rdBLUE" onchange="selectedValue = 'blue';"/>&nbsp;<label for="rdBLUE" style="font-size:9px;">BLUE</label></h3><br><img class="selectobject" src="<c:url value="/images/screens/blue.gif" />" onclick="$('rdBLUE').click();"></td>
        <td align="left"><h3><form:radiobutton path="conf['skinPath'].value" value="orange" id="rdORANGE" onchange="selectedValue = 'orange';"/>&nbsp;<label for="rdORANGE" style="font-size:9px;">ORANGE</label></h3><br><img class="selectobject" src="<c:url value="/images/screens/orange.gif" />" onclick="$('rdORANGE').click();"></td>
    </tr>
    <tr>
        <td align="left"><h3><form:radiobutton path="conf['skinPath'].value" value="green" id="rdGREEN" onchange="selectedValue = 'green';"/>&nbsp;<label for="rdGREEN" style="font-size:9px;">GREEN</label></h3><br><img class="selectobject" src="<c:url value="/images/screens/green.gif" />" onclick="$('rdGREEN').click();"></td>
    </tr>
    <tr>
        <td colspan="2" align="center"><input id="submitAJAXForm" type="button" value="APPLY" <%-- onclick="submitForm()"--%>></td>
    </tr>
</table>
        
        </form:form>

 </center>

</body>
</html>
