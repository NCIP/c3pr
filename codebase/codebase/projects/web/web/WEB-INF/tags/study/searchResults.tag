<%@tag%><%@attribute name="url" required="true"%>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>

<!-- STUDY SEARCH RESULTS START HERE -->
<div class="eXtremeTable" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		<table class="tableRegion" width="100%" cellspacing="0" cellpadding="0" border="0">
			<thead>

            <tr>
				<td class="tableHeader">Primary Identifier</td>
				<td class="tableHeader">Short Title</td>
				<td class="tableHeader">Status</td>
				<td class="tableHeader">Sponsor</td>
				<td class="tableHeader">Phase</td>
				<td class="tableHeader">Target Accrual</td>
			</tr>
			 </thead>
            <tbody class="tableBody">
            <c:if test="${studyResults!=null && fn:length(studyResults)==0}">
				<tr>
                    <td>
                    Sorry, no matches were found
                    </td>
                </tr>
			</c:if>

			<%int i=0; %>
			<c:forEach items="${studyResults}" var="study">
                <% String currClass=i%2==0? "odd":"even"; %>

            <tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
				onMouseOut="this.className='<%= currClass %>'"
				onClick="
					<c:choose>
						<c:when test="${!empty subjectId}">
							document.location='${url}?studySite=${study.studySites[0].id}&participant=${subjectId }&resumeFlow=true&_page=1&_target3=3'
						</c:when>
						<c:when test="${!empty inRegistration}">
							document.location='${url}?studySiteId=${study.studySites[0].id}'
						</c:when>
						<c:otherwise>
							document.location='${url}?studyId=${study.id}'
						</c:otherwise>
					</c:choose>
				">
				<td><a href="${url}?studyId=${study.id}">${study.primaryIdentifier}</a></td>
				<td>${study.trimmedShortTitleText}</td>
				<td>${study.status}</td>
				<td>${study.identifiers[0].value}</td>
				<td>${study.phaseCode}</td>
				<td>${study.targetAccrualNumber}</td>
			 
			</tr>
			</c:forEach>
             </tbody>
        </table>
	</td>
</tr>
</table>
    </div>
<!-- STUDY SEARCH RESULTS END HERE -->