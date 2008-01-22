<%@tag%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@attribute name="path" required="true" %>
<a id="${path}-help-control" 
	onmouseover="$('${path}-help-content').style.display='inline'" onmouseout="$('${path}-help-content').style.display='none'">
    <img src="<tags:imageUrl name='q.gif'/>" alt="Help">
</a>&nbsp;&nbsp;&nbsp;
<span id="${path}-help-content" class="hint"><spring:message code="${path}.hint.text" text="No help available" /><span class="hint-pointer">&nbsp;</span></span>
