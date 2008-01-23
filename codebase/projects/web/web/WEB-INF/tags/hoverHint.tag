<%@tag%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@attribute name="keyProp" required="true" %>
<a id="${keyProp}-help-control" 
	onmouseover="$('${keyProp}-help-content').style.display='inline'" onmouseout="$('${keyProp}-help-content').style.display='none'">
    <span valign="center"><img src="<tags:imageUrl name='q.gif'/>" alt="Help"/></span>
</a>&nbsp;&nbsp;&nbsp;
<span id="${keyProp}-help-content" class="hint"><spring:message code="${keyProp}.hint.text" text="No help available" /><span class="hint-pointer">&nbsp;</span></span>
