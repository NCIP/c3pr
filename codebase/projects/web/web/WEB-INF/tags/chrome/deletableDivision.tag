<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title"%>
<%@attribute name="id"%>
<%@attribute name="cssClass"%>
<%@attribute name="style"%>
<%@attribute name="onclick" required="true"%>
<%@attribute name="divTitle" required="true"%>
<%@attribute name="minimize" %>
<%@attribute name="divIdToBeMinimized" %>

<div class="division ${cssClass}"
	<tags:attribute name="id" value="${id}"/>
	<tags:attribute name="style" value="${style}"/>>
<h3>
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
    	<td>
			<c:if test="${not empty title}">
			<c:choose>	
				<c:when test="${not empty minimize && minimize == 'true' && not empty divIdToBeMinimized}">
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
				<a style='cursor:pointer' onclick='toggleCriteria("${divIdToBeMinimized}", "minmax_${divIdToBeMinimized}")'><img id="minmax_${divIdToBeMinimized}" src="<chrome:imageUrl name="../../templates/mocha/images/maximize.png" />" alt="Maximize" style="vertical-align:middle" /></a>
				<span id="${divTitle}">${title}</span>
				</c:when>
				<c:otherwise>
					<span id="${divTitle}">${title}</span>
				</c:otherwise>
			</c:choose>
			</c:if>
		</td>
		<td>
        <div id="${id}-image-div" align="right">
        	<a href="javascript:${onclick};"><img id="${id }-image" src="<tags:imageUrl name="checkno.gif"/>" border="1" alt="Remove">
        </a></div>
	</td>
	</tr>
	</table></h3>
    <div class="content"><jsp:doBody /></div>
</div>