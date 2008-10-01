<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title"%>
<%@attribute name="id"%>
<%@attribute name="cssClass"%>
<%@attribute name="style"%>
<%@attribute name="onclick" required="true"%>
<%@attribute name="divTitle" required="true"%>

<div class="division ${cssClass}"
	<tags:attribute name="id" value="${id}"/>
	<tags:attribute name="style" value="${style}"/>>
<h3>
    <table width="100%" cellpadding="0" cellspacing="0" border="0"><tr>
	<c:if test="${not empty title}">
	<td>
		<span id="${divTitle}">${title}</span></c:if></td><td>
        <div id="${id}-image-div" align="right">
        <a href="javascript:${onclick};"><img id="${id }-image" src="<tags:imageUrl name="checkno.gif"/>" border="1" alt="Remove">
        </a></div>
	</td>
	</tr>
	</table></h3>
    <div class="content"><jsp:doBody /></div>
    
</div>
