<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- author: Vinay G --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>C3PR Inbox Page</title>
</head>
<body>   
	<tags:inbox recipientScheduledNotification="${recipientScheduledNotification}" url="../admin/viewInbox"/>
        
        
	<%--<chrome:box title="Results">
	    <chrome:division id="single-fields">
	        <div id="tableDiv">
	   			<c:out value="${assembler}" escapeXml="false"/> 
			</div>
		</chrome:division>
	</chrome:box>	--%>
</body>
</html>
