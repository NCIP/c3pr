<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<fmt:message key="${pageContext.request.servletPath}${pageContext.request.pathInfo}" />--%>
<c:if test="${htmlTitle != null}">${htmlTitle}</c:if>
<c:if test="${htmlTitle == null}">C3PR</c:if>

