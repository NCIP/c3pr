<%@attribute name="registration" type="edu.duke.cabig.c3pr.domain.StudySubject" required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Registration<c:if test="${!empty registration && !empty registration.id}"><c:out value=": ${registration.participant.fullName} (${registration.participant.primaryIdentifierValue }) -${registration.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.shortTitleText} (${registration.studySite.study.primaryIdentifier})" />
</c:if>