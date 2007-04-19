<%-- This is the standard decorator for all C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>C3PR v2</title>
    <tags:include/>
    <decorator:head/>
<script>
Effect.OpenUp = function(element) {
    element = $(element);
    new Effect.BlindDown(element, arguments[1] || {});
}

Effect.CloseDown = function(element) {
    element = $(element);
    new Effect.BlindUp(element, arguments[1] || {});
}

Effect.Combo = function(element) {
    element = $(element);
    if (element.style.display == 'none') {
        new Effect.OpenUp(element, arguments[1] || {});
    } else {
        new Effect.CloseDown(element, arguments[1] || {});
    }
}
</script>    
</head>

<body>
<div id="content">
    <layout:header/>

    <%--navigation bar is optional--%>
    <div style='visibility:<decorator:getProperty property="meta.navigationBarVisibility" default="visible"/>;'>
        <layout:navigation/>
    </div>
    <decorator:body/>

</div>

<!--footer should go at the the bottom-->
<div id="footer">
    <layout:footer/>
</div>

</body>
</html>
