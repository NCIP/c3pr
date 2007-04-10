<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="tab" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab" required="true" %>
<%@attribute name="flow" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow" required="true" %>
<script>
function doNothing(){
}
</script>
<ul id="level2" class="tabs autoclear">
<%int loopCount=1; %>
<c:forEach items="${flow.tabs}" var="atab">
	<c:if test="${atab.display!='false'}">
		<c:set var="current" value="${atab.number == tab.number}"/>
	        	<c:choose>
	        		<c:when test="${atab.showLink=='false'}">
	        			<li1 class="tab ${current ? 'current' : ''}">
	        				<img src="<tags:imageUrl name="tab2${current ? '_h' : ''}_L.gif"/>" width="1" height="16" align="absmiddle"><a href="javascript:doNothing();" class="tab${atab.number}"><%=loopCount %>. ${atab.shortTitle}</a><img src="<tags:imageUrl name="tab2${current ? '_h' : ''}_R.gif"/>" width="6" height="16" align="absmiddle">
	        			</li1>
	        		</c:when>
	        		<c:otherwise>
	       			    <li class="tab ${current ? 'current' : ''}">
			        		<img src="<tags:imageUrl name="tab2${current ? '_h' : ''}_L.gif"/>" width="1" height="16" align="absmiddle"><a href="#" class="tab${atab.number}"><%=loopCount %>. ${atab.shortTitle}</a><img src="<tags:imageUrl name="tab2${current ? '_h' : ''}_R.gif"/>" width="6" height="16" align="absmiddle">
			        	</li>
			        </c:otherwise>
	        	</c:choose>
	    <%loopCount++; %>
	</c:if>
</c:forEach>
</ul>
<div id="level2-spacer"></div>
