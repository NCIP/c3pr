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

    <title>C3PR - DASHBOARD</title>
    <tags:include/>

    <jwr:style src="/csslib/blue.css" />
    <decorator:head/>
</head>

<body>
<layout:header2/>

<center>

    <form:form name="skinForm">


<table width="600px" cellspacing="10" cellpadding="10" border="0">
    <tr>
        <td align="left"><h3><form:radiobutton path="conf['skinPath'].value" value="blue" id="rdBLUE"/>&nbsp;<label for="rdBLUE" style="font-size:9px;">BLUE</label></h3><br><img class="selectobject" src="<c:url value="/images/screens/blue.gif" />" onclick="$('rdBLUE').checked = true;"></td>
        <td align="left"><h3><form:radiobutton path="conf['skinPath'].value" value="orange" id="rdORANGE"/>&nbsp;<label for="rdORANGE" style="font-size:9px;">ORANGE</label></h3><br><img class="selectobject" src="<c:url value="/images/screens/blue.gif" />" onclick="$('rdORANGE').checked = true;"></td>
    </tr>
    <tr>
        <td align="left"><h3><form:radiobutton path="conf['skinPath'].value" value="green" id="rdGREEN"/>&nbsp;<label for="rdGREEN" style="font-size:9px;">GREEN</label></h3><br><img class="selectobject" src="<c:url value="/images/screens/blue.gif" />" onclick="$('rdGREEN').checked = true;"></td>
    </tr>
    <tr>
        <td colspan="2" align="center"><input type="submit" value="APPLY"></td>
    </tr>
</table>
        
        </form:form>

 </center>

</body>
</html>
