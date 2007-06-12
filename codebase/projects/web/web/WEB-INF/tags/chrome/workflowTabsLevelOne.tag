<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="tab" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab" required="true"%>
<%@attribute name="flow" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlow" required="true" %>
<ul id="workflow-tabs" class="tabs autoclear">
<%int loopCount=1; %>
<c:forEach items="${flow.tabs}" var="atab" varStatus="status">
	<c:if test="${atab.display!='false'}">
		<c:set var="selected" value="${atab.number == tab.number}"/>
   			<li class="tab ${selected ? 'selected' : ''} ${status.last ? 'last' : ''}"><div>
        	<c:choose>
        		<c:when test="${atab.showLink=='false'}">
       				<a href="javascript:doNothing();" class="tab${atab.number}"><%=loopCount %>. ${atab.shortTitle}</a>
        		</c:when>
        		<c:otherwise>
	        		<a href="#" class="tab${atab.number}"><%=loopCount %>. ${atab.shortTitle}</a>
		        </c:otherwise>
        	</c:choose>
        	</div></li>
	    <%loopCount++; %>
	</c:if>
</c:forEach>
</ul>
