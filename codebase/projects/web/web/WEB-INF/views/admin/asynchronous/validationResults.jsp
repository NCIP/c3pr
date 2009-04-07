<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
	<c:choose>
		<c:when test="${not empty exception}">
			<a href="javascript:showErrorTrace('${errorTrace}' )" class="error"> Test Failed</a>
		</c:when>
		<c:otherwise>
			Test Passed 
		</c:otherwise>
	</c:choose>	
<script>
	Element.hide('testIndicator[${id}]');
	new Effect.Pulsate('connectionTestResult[${id}]')
</script>