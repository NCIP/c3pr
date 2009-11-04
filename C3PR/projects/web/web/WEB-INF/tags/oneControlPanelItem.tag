<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="linktext" required="true" %>
<%@attribute name="linkhref" required="true" %>
<%@attribute name="imgsrc" required="true" %>
<%@attribute name="id"%>
<%@attribute name="onclick"%>
<div class="oneControlPanelItem">
	<a href="${linkhref}" <c:if test="${id}">id="${id}"</c:if> <c:if test="${not empty onclick}">onclick="${onclick}"</c:if>><img src="${imgsrc}" alt="" /> ${linktext}</a>
</div>
