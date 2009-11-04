<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="value" type="java.lang.String" required="true"%>
<%@attribute name="workflow" type="java.lang.String" required="true"%>
<%@attribute name="compareWith" type="java.lang.String" required="true"%>
${value }
<c:choose>
	<c:when test="${value!=compareWith}">
		<font color="Red"><i> * Should be '${compareWith}' to successfully complete ${workflow}</i></font>
	</c:when>
</c:choose>
