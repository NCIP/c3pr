<%@attribute name="study" type="edu.duke.cabig.c3pr.domain.Study" required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:out value="Study: ${study.shortTitleText}" />
