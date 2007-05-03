<%@page contentType="text/css" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

#mytable {
	width: 700px;
	padding: 0;
	margin: 0;
	font: normal 11px auto "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #4f6b72;
	background: #E6EAE9;
}

a {
	color: #c75f3e;
}

#mytable caption {
	padding: 0 0 5px 0;
	width: 700px;	 
	font: italic 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	text-align: right;
}

#mytable th {
	font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #000000;
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	border-top: 1px solid #C1DAD7;
	letter-spacing: 2px;
	text-transform: uppercase;
	text-align: left;
	padding: 6px 6px 6px 12px;
	background: #D7D9E3;
}

#mytable th.nobg {
	border-top: 0;
	border-left: 0;
	border-right: 1px solid #C1DAD7;
	background: none;
}

#mytable td {
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	background: #FFFFFF;
	padding: 6px 6px 6px 12px;
	color: #4f6b72;
	font: normal 11px auto "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;	
}


#mytable td.alt {
	background: #F5F5F8;
	color: #797268;
}

#mytable th.spec {
	border-left: 1px solid #C1DAD7;
	border-top: 0;
	background: #fff url(<tags:imageUrl name="bullet1.gif"/>) no-repeat;
	font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
}

#mytable th.specalt {
	border-left: 1px solid #C1DAD7;
	border-top: 0;
	background: #f5fafa url(<tags:imageUrl name="bullet2.gif"/>) no-repeat;
	font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #797268;
}