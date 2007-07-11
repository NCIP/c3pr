<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title"%>
<%@attribute name="id"%>
<%@attribute name="cssClass"%>
<%@attribute name="style"%>
<%@attribute name="onclick" required="true"%>
<div class="division ${cssClass}"
	<tags:attribute name="id" value="${id}"/>
	<tags:attribute name="style" value="${style}"/>>
	<table width="100%" cellpadding="0" cellspacing="0"><tr>
	<c:if test="${not empty title}">
	<td>
	<h3>${title}</h3>
	</td>
	</c:if>
	<td align="right"><h3><div id="${id }-image-div"><a
		href="javascript:${onclick};"><img
		id="${id }-image"
		src="<tags:imageUrl name="checkno.gif"/>"
		border="0" alt="Remove"></a></div></h3>
	</td>
	</tr>
	</table>
<div class="content"><jsp:doBody /></div>
</div>
