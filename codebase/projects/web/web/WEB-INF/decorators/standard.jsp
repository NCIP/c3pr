<%-- This is the standard decorator for all C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
          prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>C3PR v2</title>
    <tags:include/>
    <decorator:head/>
   
</head>
<body>
<div id="content">
    <layout:header/>
    <layout:navigation/>
    <decorator:body/>
</div>
<!--footer should go at the the bottom-->
<div id="footer">
    <layout:footer />
</div>
</body>
</html>
