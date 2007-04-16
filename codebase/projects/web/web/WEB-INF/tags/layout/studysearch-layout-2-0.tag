<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study"%>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="body">
    <tr>
        <td>
			<tags:panel id="SearchStudy" title="Search Study">		        
        	<c:choose>
        		<c:when test="${!empty subjectId}">
	        		<tags:search action="searchStudy?inRegistration=true&subjectId=${subjectId}"/>
	           	</c:when>
        		<c:otherwise>
	        		<tags:search action="searchStudy?inRegistration=true"/>
        		</c:otherwise>
        	</c:choose>
	        <c:if test="${actionReturnType=='SearchResults' }">
		        <c:choose>
					<c:when test="${!empty subjectId}">
						<%-- <studyTags:searchResults url="createRegistration"/>--%>
						<registrationTags:searchStudyResults url="createRegistration"/>						
					</c:when>
					<c:otherwise>
						<%--<studyTags:searchResults url="searchParticipant" />--%>
						<registrationTags:searchStudyResults url="searchParticipant"/>						
					</c:otherwise>
				</c:choose>
			</c:if>
	        </tags:panel>
        </td>
	</tr>        
</table>