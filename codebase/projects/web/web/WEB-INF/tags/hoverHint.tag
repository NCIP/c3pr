<%@tag%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- use the id attribute if you have multiple instances of the same field on the jsp 
e.g: added by row manager -->
<%@attribute name="id" required="false" %>
<%@attribute name="keyProp" required="true" %>

<c:choose>
	<c:when test="${not empty id}"> 
		<a id="${id}-help-control" 
		onmouseover="$('${id}-help-content').style.display='inline'" onmouseout="$('${id}-help-content').style.display='none'">
	    <span valign="center"><img src="<tags:imageUrl name='q.gif'/>" alt="Help" height="13"/></span>
		</a>&nbsp;&nbsp;&nbsp;
		<span id="${id}-help-content" class="hint"><spring:message code="${keyProp}.hint.text" text="No help available" /><span class="hint-pointer">&nbsp;</span></span>
	</c:when>	
	<c:otherwise>
		<a id="${keyProp}-help-control" 
		onmouseover="$('${keyProp}-help-content').style.display='inline'" onmouseout="$('${keyProp}-help-content').style.display='none'">
	    <img src="<tags:imageUrl name='q.gif'/>" alt="Help" height="13"/>
		</a>&nbsp;&nbsp;&nbsp;
		<span id="${keyProp}-help-content" class="hint"><spring:message code="${keyProp}.hint.text" text="No help available" /><span class="hint-pointer">&nbsp;</span></span>
	</c:otherwise>
</c:choose>
