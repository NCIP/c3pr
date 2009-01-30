<%-- This is the standard decorator for all C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>

<html>
<head>
	<title>Printable View</title>
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    
    <tags:include/>
	<script>
	Event.observe(window, "load", function() {
    		$('printView').innerHTML = window.opener.document.getElementById('printable').innerHTML; 
    })
	</script>
</head>

<body>
<div id="printView">
	No Printable Content.
</div>
</body>
</html>
