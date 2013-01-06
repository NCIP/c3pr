<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="studysiteTags" tagdir="/WEB-INF/tags/studysite" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="studyVersion" required="true" type="edu.duke.cabig.c3pr.domain.StudyVersion" %>
<chrome:division title="Amendment Summary" id="studyVersionSummary-${studyVersion.id }">
	<div class="row">
		<div class="label"><fmt:message key="study.versionNameNumber" />:</div>
		<div class="value">${studyVersion.name }</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.amendmentType" />:</div>
		<div class="value">${studyVersion.amendmentType.displayName }</div>
	</div>
	<c:if test="${studyVersion.amendmentType == 'IMMEDIATE_AFTER_GRACE_PERIOD'}">
	<div class="row">
		<div class="label"><fmt:message key="study.gracePeriod" />:</div>
		<div class="value">${studyVersion.gracePeriod }</div>
	</div>
	</c:if>
	<div class="row">
		<div class="label"><fmt:message key="study.amendmentDate" />:</div>
		<div class="value">${studyVersion.versionDateStr }</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.comments" />:</div>
		<div class="value">${studyVersion.comments == ''? 'NA' : studyVersion.comments}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.amendmentReasons" />:</div>
		<div class="value">
			<c:forEach items="${studyVersion.amendmentReasons}" var="amendmentReason">
				${amendmentReason.displayName }<br>
			</c:forEach>
		</div>
	</div>
</chrome:division>
