<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="../taglibs.jsp"%>
	<c:choose>
		<c:when test="${not empty exception}">
			<script>
				errorTrace = '<spring:message code="Test-failed-${exception.class.name}" text="${exception.message}"/>';
				<%--errorTrace = '<fmt:message key="Test-failed-${exception.class.name}"/>';--%>
			</script>
			<c:choose>
				<c:when test="${type == 'testEmailServer'}">
					<a href="javascript:showErrorTrace(errorTrace)" class="error"> Email send failure</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:showErrorTrace(errorTrace)" class="error"> Test Failed</a>
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
