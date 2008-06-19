<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
  User: ion
  Date: Jun 11, 2008
  Time: 10:54:45 AM
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>

<div id="main">
<table width="100%" border="0">
<tr>
    <td valign="top" width="33%" style="background:url(../../images/chrome/li_item.jpg);">
        <chrome:box title="Frequently Used Shortcuts" autopad="true">

                <table width=100% cellspacing="0" cellpadding="0" border="0">
                <tr><td>
                    <c:forEach var="k" items="${links.keys}" varStatus="status">
                        <img src="<c:url value="/images/chrome/li_item.jpg" />">&nbsp;&nbsp;<a href="<c:url value="/${k}" />">${links.p[k]}</a><br/>
                        <c:if test="${status.count == fn:length(links.keys) / 2}"><td></c:if>
                    </c:forEach>
                </tr>
                </table>
            
        </chrome:box>
        <chrome:box title="C3PR Notifications" autopad="true">
            _CONTENT_
        </chrome:box>
        <chrome:box title="C3PR Development Notes" autopad="true">
            _CONTENT_
        </chrome:box>
    </td>
    <td valign="top">
        <chrome:box title="Incomplete Registrations - Most Recent" autopad="true">
            _CONTENT_
        </chrome:box>
        <chrome:box title="Pending Studies - Most Recent" autopad="true">
            _CONTENT_
        </chrome:box>
        <chrome:box title="Most Active Studies" autopad="true">
            _CONTENT_
        </chrome:box>
    </td>
</tr>
</table>
</div>

</body>
</html>