<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="path"%>
<form:errors path="${path}">
<div class="errors">
    <c:if test="${not empty messages}">
    	<p><fmt:message key="submision.errors"/></p>
        <ul class="errors">
            <c:forEach items="${messages}" var="message"><li>${message}</li></c:forEach>
        </ul>
    </c:if>
</div>
</form:errors>