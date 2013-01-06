<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@attribute name="id"%>
<%@attribute name="errors" type="java.util.List" required="true"%>
<script>
</script>
<div id="${id }">
	<c:choose>
		<c:when test="${fn:length(errors)>0}">
			<table class="tablecontent">
			<tr>
				<th>Date</th>
				<th>Code</th>
				<th>Message</th>
			</tr>
			<c:forEach items="${errors}" var="error">
				<tr>
				<td width="20%">
				<tags:formatDate value="${error.errorDate }"/>
				</td>
				<td width="10%">
					${error.errorCode }			
				</td>
				<td>
					${error.errorMessage }
				</td>
				</tr>
			</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			No error information available
		</c:otherwise>
	</c:choose>
</div>