<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="value" type="java.lang.String" required="true"%>
<%@attribute name="workflow" type="java.lang.String" required="true"%>
<c:choose>
	<c:when test="${value==null||value==''}">
		<font color="Red"><i>* Required Field to successfully complete ${workflow}</i></font>
	</c:when>
	<c:otherwise>
		${value }
	</c:otherwise>
</c:choose>
