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
		<tr>
			<td ${columnAttrName }>${name }:&nbsp;</td>
			<td ${columnAttrValue }>${value }</td>
		</tr>
	</c:otherwise>
</c:choose>
