<%@tag%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute name="basename" required="true"%>
<%@attribute name="name" required="true"%>
<%@attribute name="value" required="true"%>
<%@attribute name="displayValue" required="true"%>
<%@attribute name="hintKey" %>
<%@attribute name="hintId" %>
<%@attribute name="cssClass" %>
<%@attribute name="size" %>
<c:set var="size" value="${empty size ? '33' : size}"/>
<input id="${basename}-input" size="${size}" type="text" name="aaaxxx" value="${displayValue}" class="autocomplete ${cssClass}" />
<input type="hidden" id="${basename}-hidden" name="${name}" value="${value}" />

<!-- use the id attribute if you have multiple instances of the same field on the jsp e.g: added by row manager -->
<c:choose>
	<c:when test="${not empty hintId}"> 
		<a id="${hintId}-help-control" 
		onmouseover="$('${hintId}-help-content').style.display='block'" onmouseout="$('${hintId}-help-content').style.display='none'">
	    <span valign="center"><img src="<tags:imageUrl name='q.png'/>" alt="Help" height="13"/></span>
		</a>&nbsp;&nbsp;&nbsp;
		<span id="${hintId}-help-content" class="hint"><spring:message code="${hintKey}.hint.text" text="No help available" /><span class="hint-pointer">&nbsp;</span></span>
	</c:when>	
	<c:when test="${not empty hintKey}">
		<a id="${hintKey}-help-control" 
		onmouseover="$('${hintKey}-help-content').style.display='block'" onmouseout="$('${hintKey}-help-content').style.display='none'">
	    <img src="<tags:imageUrl name='q.png'/>" alt="Help" height="13"/>
		</a>&nbsp;&nbsp;&nbsp;
		<span id="${hintKey}-help-content" class="hint"><spring:message code="${hintKey}.hint.text" text="No help available" /><span class="hint-pointer">&nbsp;</span></span>
	</c:when>
</c:choose>

<img id="${basename}-indicator" class="indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator"/>
<div id="${basename}-choices" class="autocomplete" style="display: none;"></div>
<c:if test="${!empty displayValue && fn:trim(displayValue) != ''}">
	<script>
		if($("${basename}-input").hasClassName("required")){
			Element.removeClassName("${basename}-input", "required");
			Element.addClassName("${basename}-input", "valueOK");
		}
	</script>
</c:if>