<%@attribute name="subject" type="edu.duke.cabig.c3pr.domain.Participant" required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
    <c:when test="${subject.id > 0}"><c:out value="Subject: ${subject.firstName} ${subject.lastName}" /></c:when>
    <c:otherwise>Create Subject</c:otherwise>
</c:choose>
