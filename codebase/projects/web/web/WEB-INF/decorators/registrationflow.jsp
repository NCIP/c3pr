<%-- This is the standard tabbedFlow decorator for respective C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
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
    <tags:javascriptLink name="tabbedflow"/>
    <decorator:head/>
    <style type="text/css">
        .label { width: 17em; text-align: right; padding: 2px; font-weight: bold;}
	</style>
</head>

<body>
<layout:header/>
<c:set var="studySubject" value="${command}" scope="request"/>
<div class="tabpane">
<c:choose>
    <c:when test="${tab.display!='false'}">
    <chrome:workflowTabs tab="${tab}" flow="${flow}"/>

    <chrome:body title="${flow.name}: ${tab.longTitle}">
        <c:set var="hasSummary" value="${not empty summary}"/>
        <c:if test="${hasSummary}">
            <div id="summary-pane" class="pane">
                <chrome:box title="Summary">
                    <c:forEach items="${summary}" var="summaryEntry">
                        <div class="row">
                            <div class="label">${summaryEntry.key}</div>
                            <div class="value">${empty summaryEntry.value ? '<em class="none">None</em>' : summaryEntry.value}</div>
                        </div>
                    </c:forEach>
                </chrome:box>
            </div>
        </c:if>

        <div id="main${hasSummary ? '' : '-no-summary'}-pane" class="pane">
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
    </chrome:body>
    </c:when>
    <c:otherwise>
        <decorator:body/>
    </c:otherwise>
</c:choose>


    <form:form id="flowredirect">
        <input type="hidden" name="_target${tab.targetNumber}" id="flowredirect-target"/>
        <input type="hidden" name="_page${tab.number}"/>
    </form:form>

</div>
<tags:jsLogs debug="false"/>
<tags:enableRowDeletion/>
<tags:tabMethodForm/>
<layout:footer/>
<c:if test="${!empty disableForm && disableForm}">
<tags:disableForm/>
</c:if>
</body>
</html>
