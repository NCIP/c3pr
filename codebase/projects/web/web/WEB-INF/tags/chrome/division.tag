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
<%@attribute name="condition"%>
<%@attribute name="inPlaceLinkId"%>
<!-- set this attribute to true if you wish to display the help icon next to the division title -->
<%@attribute name="insertHelp" description="true or false"%>
<!-- the keyProp for the help icon text. only used if insertHelp is set to true-->
<%@attribute name="insertHelpKeyProp"%>

<c:set var="condition" value="${empty condition ? true : condition}"/>
<c:set var="insertHtml" value="${empty insertHtml ? false : insertHtml}"/>

<div class="division ${cssClass}"<tags:attribute name="id" value="${id}"/><tags:attribute name="style" value="${style}"/>>
<c:choose>
    <c:when test="${not empty minimize && not empty divIdToBeMinimized}">
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
			<c:choose>
				<c:when test="${minimize}">
					<c:set var="imageVar" value="maximize"></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="imageVar" value="minimize"></c:set>				
				</c:otherwise>
			</c:choose>
            <h3><a style='cursor:pointer' onclick='toggleCriteria("${divIdToBeMinimized}", "minmax_${divIdToBeMinimized}")'><img id="minmax_${divIdToBeMinimized}" src="<chrome:imageUrl name="../../templates/mocha/images/${imageVar}.png" />" alt="${imageVar}" style="vertical-align:middle" /></a> 
	            <c:choose>
	            	<c:when test="${!empty link && condition}">
	            		<a href="${link}">
	          			 ${title} <c:if test="${insertHelp}"><font size="2pt" color="black"><tags:hoverHint keyProp="${insertHelpKeyProp}" /> </font></c:if>
	          		 </a>
	            	</c:when>
	            	<c:otherwise>
	            		${title} <c:if test="${insertHelp}"><font size="2pt" color="black"><tags:hoverHint keyProp="${insertHelpKeyProp}" /> </font></c:if>
	            	</c:otherwise>
	            </c:choose>
	             <c:if test="${!empty link}">
	            	<a href="${link}"> <img src="<chrome:imageUrl name="../../templates/mocha/images/controlPanel/controlPanel_pencil.png" />"></a>
	            </c:if>
            </h3>
        </c:if>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty title}">
            <h3>
	             <c:choose>
	            	<c:when test="${!empty link && condition}">
	            		<a href="${link}">
	          			 ${title} <c:if test="${insertHelp}"><font size="2pt" color="black"><tags:hoverHint keyProp="${insertHelpKeyProp}" /> </font></c:if>
	          		 </a>
	            	</c:when>
	            	<c:otherwise>
	            		${title} <c:if test="${insertHelp}"><font size="2pt" color="black"><tags:hoverHint keyProp="${insertHelpKeyProp}" /> </font></c:if>
	            	</c:otherwise>
	            </c:choose>
	           	<c:if test="${!empty link && condition}">
	            	<a href="${link}"> <img src="<chrome:imageUrl name="../../templates/mocha/images/controlPanel/controlPanel_pencil.png" />"></a>
	            </c:if>
	            <c:if test="${empty link &&  !empty inPlaceLinkId && condition}">
	            	<a href="#"> <img  id="${inPlaceLinkId}" src="<chrome:imageUrl name="../../templates/mocha/images/controlPanel/controlPanel_pencil.png" />"></a>
	            </c:if>
            </h3>
        </c:if>
    </c:otherwise>
</c:choose>
<div class="content" id="${id}-interior">
    <jsp:doBody/>
</div>
</div>