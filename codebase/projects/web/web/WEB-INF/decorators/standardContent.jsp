<%-- This is the standard decorator for all C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
 <%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>

<html>
<head>
    <title><decorator:title /></title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <tags:include/>
    <decorator:head/>
</head>

<body>
<tags:ajaxLoadingIndicator/>
<div id="all">
    <layout:header/>

<div id="content">
    <c:set var="__decorator_title"><decorator:title/></c:set>
    <decorator:body/>
</div>

    <layout:footer/>
    <tags:jsLogs debug="false"/>
    <tags:enableRowDeletion/>
    <tags:tabMethodForm/>
</div>
</body>
</html>
