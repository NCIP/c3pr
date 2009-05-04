<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
	<c:choose>
		<c:when test="${not empty exception}">
			<c:choose>
				<c:when test="${type == 'testEmailServer'}">
					<a href="javascript:showErrorTrace('${errorTrace}' )" class="error"> Email send failure</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:showErrorTrace('${errorTrace}' )" class="error"> Test Failed</a>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${type == 'testEmailServer'}">
					Email successfully sent to ${email}
				</c:when>
				<c:otherwise>
					Test Passed
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>	
<script>
	Element.hide('testIndicator[${id}]');
	new Effect.Pulsate('connectionTestResult[${id}]')
</script>