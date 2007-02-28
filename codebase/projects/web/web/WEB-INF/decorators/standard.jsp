<%-- This is the standard decorator for all C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>C3PR</title>
<tags:include/>
<decorator:head />
</head>
<body>
<layout:header/>
<layout:navigation/>
<br>
<decorator:body/>
<layout:footer />
</body>
</html>
