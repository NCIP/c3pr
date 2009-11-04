<%-- This is the standard decorator for all C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>
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
			$('printTitle').innerHTML = window.opener.document.title;
			$('printView').innerHTML = window.opener.document.getElementById('printable').innerHTML;
			Element.show('printTitle');
	    	Element.show('printView');
			window.print();
    })
	</script>
	<style>
		body {
			background-image:none;
			background-color:white;
			text-align:left;
		}
		#printView {
			display:block;
		}
		#C3PR-logo {
			float:left;
		}
		#printTitle {
			padding-top:16px;
			font-size:20px;
			font-weight:bold;
		}
		#printDate {
			margin-top:5px;
			color:#666666;
		}
		.division h3 {
			color:black;
		}
	</style>
</head>

<body>
	<div style="height:110px;">
	<img id="C3PR-logo" src="<tags:imageUrl name="C3PR_logo.png" />" alt="" />
	<div id="printTitle" style="display:none;">Oops!</div>
	<div id="printDate">
		<script language="JavaScript" type="text/javascript" >
			var localTime = new Date();
			document.write(localTime.toLocaleString());
		</script>
	</div>
	</div>
<div id="printView" style="display:none;">
	No printable content.
</div>
</body>
</html>
