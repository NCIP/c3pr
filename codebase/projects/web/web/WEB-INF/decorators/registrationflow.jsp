<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<tags:include/>   
<tags:stylesheetLink name="tabbedflow"/>
<tags:javascriptLink name="common"/>  
<tags:includeScriptaculous/>
<tags:javascriptLink name="tabbedflow"/>  

<script>
Effect.OpenUp = function(element) {
     element = $(element);
     new Effect.BlindDown(element, arguments[1] || {});
 }

 Effect.CloseDown = function(element) {
     element = $(element);
     new Effect.BlindUp(element, arguments[1] || {});
 }

 Effect.Combo = function(element) {
     element = $(element);
     if(element.style.display == 'none') { 
          new Effect.OpenUp(element, arguments[1] || {}); 
     }else { 
          new Effect.CloseDown(element, arguments[1] || {}); 
     }
 }
</script>
<link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">    
<title>C3PRV2</title>
<decorator:head/>    
</head>
<body>
<layout:header/>
<layout:navigation/>
<c:choose>
	<c:when test="${!empty registrationAltFlow}">
		<c:set var="rFlow" value="${registrationAltFlow}"/> 
	</c:when>
	<c:when test="${!empty registrationFlow}">
		<c:set var="rFlow" value="${registrationFlow}"/> 
	</c:when>
	<c:otherwise>
		<c:set var="rFlow" value="false"/> 
	</c:otherwise>
</c:choose>
<c:set var="rTab" value="${registrationTab}"/>
<form:form id="flowredirect">
    <input type="hidden" name="_target${rTab.targetNumber}" id="flowredirect-target"/>
    <input type="hidden" name="_page${rTab.number}"/>
</form:form>
<%--currentSection.displayName : ${currentSection.displayName} <br>
currentTask.displayName : ${currentTask.displayName }<BR>
rTab.display : ${rTab.display }<br>
rTab.shortTitle : ${rTab.shortTitle }<br>
rTab.showSummary : ${rTab.showSummary }<br>
rTab.subFlow : ${rTab.subFlow }<br>
--%>
<c:choose>
	<c:when test="${currentSection.displayName=='Registration' && rFlow!='false' && rTab.display!='false'}" >
		<%System.out.println("--------setting tabs-------------"); %>
		<tabs:levelOneTabs tab='${rTab}' flow="${rFlow}"/>
		<div class="tabcontent workArea">
		<layout:summaryLayout-1-1 tab='${rTab}' flow="${rFlow}">
		    <c:choose>
		    	<c:when test="${rTab.shortTitle=='Select Study'}">
	    			<layout:studysearch-layout-2-0>
    					<decorator:body/>
	    			</layout:studysearch-layout-2-0>
		    	</c:when>
		    	<c:when test="${rTab.shortTitle=='Select Subject'}">
	    			<layout:selectsubject-layout-2-0>
		    			<decorator:body/>
	    			</layout:selectsubject-layout-2-0>
		    	</c:when>
		    	<c:otherwise>
		    		<tags:panel title="${rTab.shortTitle}">
			    		<decorator:body/>
			    	</tags:panel>
		    	</c:otherwise>
		    </c:choose>
	    </layout:summaryLayout-1-1>
	    </div>
	</c:when> 
	<c:otherwise> 
		<decorator:body/>
	</c:otherwise> 
</c:choose>
<layout:footer />
</body>
</html>