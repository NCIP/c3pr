<html>
	<head>
		<title>C3PR dummy.jsp</title>
		<link rel="stylesheet" type="text/css" media="all" href="css/dummy.css" />
		<script language="JavaScript" type="text/javascript" src="js/dummy.js"></script>
	</head>
	<body onload="dummyform.datefield.value=todaysDate()">
		<p>This is dummy.jsp.  It should be deleted</p>
		<p>Let's test CSS.  How does the font look?</p>
		<p>Let's test jsp.  Here is the browser: <%= request.getHeader("user-agent") %></p>
		<form name="dummyform"><p>Let's test javascript.  This is the date: <input type="text" name="datefield"/></p></form>
		<p>Let's test servlets.  Go <a href="DummyServlet">here</a></p>
	</body>
</html>