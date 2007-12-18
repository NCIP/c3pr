<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title"%>
<%@attribute name="minimize"%>
<%@attribute name="divIdToBeMinimized"%>
<%@attribute name="id"%>
<%@attribute name="cssClass"%>
<%@attribute name="style"%>
<script>
	function toggleCriteria(divIdToBeMinimized){
       	var el = document.getElementById(divIdToBeMinimized);       	
		if ( el.style.display != 'none' ) {
			new Effect.BlindUp(el);
		}
		else {
			new Effect.BlindDown(el);
		}
    }     
</script>
<div class="division ${cssClass}"
    <tags:attribute name="id" value="${id}"/> <tags:attribute name="style" value="${style}"/>>
	<c:choose>
		<c:when test="${not empty minimize && minimize == 'true' && not empty divIdToBeMinimized}">
		    <c:if test="${not empty title}">
		        <h3><a style='cursor:pointer' onclick='toggleCriteria("${divIdToBeMinimized}")'>${title}</a></h3>
		    </c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${not empty title}">
		        <h3>${title}</h3>
		    </c:if>
		</c:otherwise>
	</c:choose>
    <div class="content">
        <jsp:doBody/>
    </div>
</div>
