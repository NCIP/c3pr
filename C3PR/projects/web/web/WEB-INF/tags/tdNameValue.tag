<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="name" type="java.lang.String" required="true"%>
<%@attribute name="value" type="java.lang.String" required="true"%>
<%@attribute name="columnAttrName" type="java.lang.String"%>
<%@attribute name="columnAttrValue" type="java.lang.String"%>
<%@attribute name="isNullDisplay" type="java.lang.String"%>
<c:choose>
	<c:when test="${isNullDisplay!='true'&&(value==null||value=='')}">
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="label">${name }:</div>
			<div class="value">${value }</div>
		</div>
	</c:otherwise>
</c:choose>
