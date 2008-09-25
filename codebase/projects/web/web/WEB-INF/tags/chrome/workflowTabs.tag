<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="tab" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" required="true" %>
<%@attribute name="flow" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" required="true" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%--<script language="JavaScript1.2">
<c:forEach items="${flow.tabs}" var="atab" varStatus="status">
    <c:set var="selected" value="${atab.number == tab.number}"/>
    <c:out value='ssmItems[${status.count - 1}]=' />["<c:out value="${atab.shortTitle}" />", "#", "${not empty mandatory ? 'mandatory' : ''}"]
</c:forEach>

buildMenu(<c:out value="${tab.number}" />, "<c:out value='${currentTask.displayName}' />");
    
</script>
--%>
<!--/*-->
<!--ssmItems[0] = ["Create registration"] //create header-->
<!--ssmItems[1] = ["Select Subject & Study", "#", ""]-->
<!--ssmItems[2] = ["Select Subject & Study", "#", ""]-->
<!--ssmItems[3] = ["Select Subject & Study", "#", ""]-->
<!--ssmItems[4] = ["Select Subject & Study", "#", ""]-->
<!--*/-->

<%--
<ul id="workflow-tabs" class="tabs autoclear">
<c:forEach items="${flow.tabs}" var="atab" varStatus="status">
    <c:set var="selected" value="${atab.number == tab.number}"/>
    <li class="tab ${selected ? 'selected' : ''} ${status.last ? 'last' : ''} ${not empty mandatory ? 'mandatory' : ''}"><div>
        <a href="#" class="tab${atab.number}">${atab.number + 1}. ${atab.shortTitle}</a>
    </div></li>
</c:forEach>
</ul>    
--%>

<c:forEach items="${flow.tabs}" var="atab" varStatus="status">
    <c:set var="selected" value="${atab.number == tab.number}"/>
	<c:if test="${selected}">
		<tags:pageHelp propertyKey="${tab.class.name}" />
	</c:if>
</c:forEach> 
