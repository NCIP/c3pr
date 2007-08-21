<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<head>
<link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3PRV2</title>
<tags:include/>
<tags:stylesheetLink name="tabbedflow"/>
<tags:includeScriptaculous/>
<tags:javascriptLink name="tabbedflow"/>
<tags:javascriptLink name="autocomplete"/>
<decorator:head/>
<style type="text/css">
        .label { width: 20em; text-align: right; padding: 2px; font-weight: bold;}
</style>
</head>

<body>

<div id="content">
    <layout:header/>
    <c:set var="studySubject" value="${command}" scope="request"/>
    <form:form id="flowredirect">
        <input type="hidden" name="_target${tab.targetNumber}" id="flowredirect-target"/>
        <input type="hidden" name="_page${tab.number}" value="${tab.number}"/>
    </form:form>
    <%-- currentSection.displayName : ${currentSection.displayName} <br>
    currentTask.displayName : ${currentTask.displayName }<BR>
    rTab.display : ${rTab.display }<br>
    rTab.shortTitle : ${rTab.shortTitle }<br>
    rTab.showSummary : ${rTab.showSummary }<br>
    rTab.subFlow : ${rTab.subFlow }<br>
    rFlow :  ${rFlow}<br>
    rTab :  ${rTab}<br>
    registrationTab : ${registrationTab}<br>
    ${currentSection.displayName=='Registration' && rFlow!='false' && rTab.display!='false'}<br>
    
    <%= request.getSession().getAttribute("registrationFlow")!=null?"not empty":"empty" %><br>
    --%>
    <c:choose>
        <c:when test="${tab.display!='false'}">
            <%System.out.println("--------setting tabs-------------"); %>
            <c:if test="${not empty flow}"><chrome:workflowTabsLevelOne tab='${tab}' flow="${flow}"/></c:if>
            <div class="tabcontent workArea">
            	<c:choose>
            		<c:when test="${!empty tab && tab.showSummary!='false'}">
		                <layout:summaryLayout-1-1 tab='${tab}' flow="${flow}">
		                    <decorator:body/>
		                </layout:summaryLayout-1-1>
            		</c:when>
            		<c:otherwise>
            			<decorator:body/>
            		</c:otherwise>
            	</c:choose>
            </div>
        </c:when>
        <c:otherwise>
            <decorator:body/>
        </c:otherwise>
    </c:choose>
</div>
<tags:jsLogs debug="false"/>
<div id="footer">
    <layout:footer/>
</div>
</body>
</html>