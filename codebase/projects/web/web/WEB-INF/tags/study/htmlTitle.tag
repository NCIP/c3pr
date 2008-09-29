<%@attribute name="study" type="edu.duke.cabig.c3pr.domain.Study" required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
Study
<c:if test="${!empty study.shortTitleText}">
	<c:out value=" : ${study.shortTitleText} (${study.primaryIdentifier})" />
</c:if>
