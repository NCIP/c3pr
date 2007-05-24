<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title" required="true"%>
<%@attribute name="id"%>
<%@attribute name="url"%>
<%@attribute name="display"%>
<%@attribute name="isAlwaysDisplay"%>
<script>
function displayDiv(id,flag){
	if(flag=='true'){
		document.getElementById(id).style.display='block';
	}else
		document.getElementById(id).style.display='none';	
}
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="body">
    <tr>
    	<td id=current <c:if test="${empty isAlwaysDisplay}">onMouseOver="displayDiv('${id }-image-div','true')" onMouseOut="displayDiv('${id }-image-div','false')"</c:if>>
	       <table width="100%"><tr>
	       		<td>${title}</td>
	       		<c:if test="${!empty id || !empty url}">
	       			<td align=right>
	       				<div id="${id }-image-div" <c:if test="${empty isAlwaysDisplay}">style="display: none;"</c:if>>
	       				<a href="javascript:
				    		<c:choose>
				    			<c:when test="${!empty url}">document.location='${url}'</c:when>
				    			<c:otherwise>PanelCombo('${id }');</c:otherwise>
				    		</c:choose>
       					"><img id="${id }-image"
							src="<tags:imageUrl name="${display=='false'||!empty url?'maximize':'minimize' }.gif"/>" border="0" alt="toggle button"></a>
	       				</div>
		       		</td>
	       		</c:if>
	       	</tr></table>
       </td>
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