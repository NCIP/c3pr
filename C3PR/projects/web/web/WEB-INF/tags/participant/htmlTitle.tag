<%@attribute name="subject" type="edu.duke.cabig.c3pr.domain.Participant" required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Subject
<c:if test="${!empty subject && !empty subject.firstName}"><c:out value=" : ${subject.firstName} ${subject.lastName}" /></c:if>
