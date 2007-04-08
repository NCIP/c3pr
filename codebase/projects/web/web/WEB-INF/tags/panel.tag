<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title"%>
<%@attribute name="id"%>
<%@attribute name="url"%>
<%@attribute name="display"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="body">
    <tr onClick="
    		<c:choose>
    			<c:when test="${!empty url}">document.location='${url}'</c:when>
    			<c:otherwise>Effect.Combo('${id}');</c:otherwise>
    		</c:choose>
    			
    ">
       <td id="current">${title}</td>
    </tr>
    <tr><td class="display"><div id="${id }" 
    							<c:if test="${display=='false'}">style="display: none;"</c:if>
    						>
        <jsp:doBody/></div>
    </td></tr>
    <tr>
        <td class="display_B"><img src="<tags:imageUrl name="display_BL.gif"/>" align="left" hspace="0"><img src="<tags:imageUrl name="display_BR.gif"/>" align="right" hspace="0"></td>
    </tr>
</table>