<%@attribute name="registration" type="edu.duke.cabig.c3pr.domain.StudySubject" required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
    <c:when test="${registration != null && registration.id > 0}"><c:out value="Registration: ${registration.name}" /></c:when>
    <c:otherwise>Create Registration</c:otherwise>
</c:choose>
