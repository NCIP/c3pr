<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold; width: 12em}
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr>
		<td valign="top" width="40%">
			<table>
				<tags:tdNameValue name="Short title" value="${studyCommand.study.trimmedShortTitleText}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Primary identifier" value="${studyCommand.study.primaryIdentifier} (${studyCommand.primaryIdentifierAssigningAuthority})" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Study status" value="${studyCommand.study.coordinatingCenterStudyStatus.code}" columnAttrName="class='labelR'"/>
			</table>
		</td>
		<td valign="top">
			<table>
				<tags:tdNameValue name="Coordinating center" value="${studyCommand.study.coordinatingCenterAssignedIdentifier.healthcareSite.name}" columnAttrName="class='labelR'"/>				
				<tags:tdNameValue name="Principal investigator" value="${studyCommand.study.principalInvestigatorFullName} (${studyCommand.study.principalInvestigatorStudyOrganization.healthcareSite.name })" columnAttrName="width='50%' class='labelR'"/>
				<c:if test="${fn:length(studyCommand.study.parentStudyAssociations) == 1}">
					<tags:tdNameValue name="Parent study" value="${studyCommand.study.parentStudy.trimmedShortTitleText}" columnAttrName="class='labelR'"/>
				</c:if>
				<c:if test="${!studyCommand.study.standaloneIndicator && fn:length(studyCommand.study.parentStudyAssociations) == 1}">
					<c:choose>
						<c:when test="${!empty param.parentStudyFlow}">
							<a href="#" onclick="javascript:returnToParent('${param.parentStudyFlow}', '${studyCommand.study.parentStudyAssociations[0].parentStudy.id}')">Return to parent study</a>
						</c:when>
						<c:when test="${flowType != 'VIEW_STUDY'}">
							<a href="#" onclick="javascript:viewParent('${studyCommand.study.parentStudyAssociations[0].parentStudy.id}')">Return to parent study</a>
						</c:when>
					</c:choose>
				</c:if>
				
			</table>
		</td>
	</tr>
</table>