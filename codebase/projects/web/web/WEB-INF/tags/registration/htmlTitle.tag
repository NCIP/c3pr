<%@attribute name="registration" type="edu.duke.cabig.c3pr.domain.StudySubject" required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
    <c:when test="${!empty registration && !empty registration.id}"><c:out value="${registration.participant.fullName} (${registration.participant.primaryIdentifier })" /></c:when>
    <c:otherwise>Create Registration</c:otherwise>
</c:choose>
