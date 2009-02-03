<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" %>
<%@attribute name="minimize" %>
<%@attribute name="divIdToBeMinimized" %>
<%@attribute name="id" %>
<%@attribute name="cssClass" %>
<%@attribute name="style" %>
<%@attribute name="link" %>


<div class="division ${cssClass}"<tags:attribute name="id" value="${id}"/><tags:attribute name="style" value="${style}"/>>
<c:choose>
    <c:when test="${not empty minimize && minimize == 'true' && not empty divIdToBeMinimized}">
        <c:if test="${not empty title}">
        	<script>
    function toggleCriteria(divIdToBeMinimized, id){
        var el = document.getElementById(divIdToBeMinimized);
		var elimg =document.getElementById(id);
        if (el == null) {
            alert("division.tag - Could not find div Element to minimize.");
        }
        if (el.style.display != 'none') {
            new Effect.BlindUp(el);
            elimg.src = '<chrome:imageUrl name="../../templates/mocha/images/maximize.png" />';
			elimg.alt = 'Maximize';
            
        }
        else {
            new Effect.BlindDown(el);
            elimg.src = '<chrome:imageUrl name="../../templates/mocha/images/minimize.png" />';
			elimg.alt = 'Minimize';
            
        }
    }
</script>
            <h3><a style='cursor:pointer' onclick='toggleCriteria("${divIdToBeMinimized}", "minmax_${divIdToBeMinimized}")'><img id="minmax_${divIdToBeMinimized}" src="<chrome:imageUrl name="../../templates/mocha/images/maximize.png" />" alt="Maximize" style="vertical-align:middle" /></a> 
            
            <c:if test="${!empty link}">
            	<a href="${link}">
          			 ${title} 
          		 </a>
           	</c:if>
           	<c:if test="${empty link}">
            	${title} 
           	</c:if>
             <c:if test="${!empty link}">
            	<a href="${link}"><img src="<chrome:imageUrl name="../../images/controlPanel/controlPanel_pencil.png" />"></a>
            </c:if>
            </h3>
           
        </c:if>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty title}">
            <h3><c:if test="${!empty link}">
            	<a href="${link}">
          			 ${title} 
          		 </a>
           	</c:if>
           	<c:if test="${empty link}">
            	${title} 
           	</c:if>
           	 <c:if test="${!empty link}">
            	<a href="${link}"><img src="<chrome:imageUrl name="../../images/controlPanel/controlPanel_pencil.png" />"></a>
            </c:if>
            </h3>
            
        </c:if>
    </c:otherwise>
</c:choose>
<div style="padding:2px 1px 3px 1px;" class="content" id="${id}-interior">
    <jsp:doBody/>
</div>
</div>