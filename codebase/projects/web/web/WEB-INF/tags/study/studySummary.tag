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
<script>
	function returnToParent(parentStudyFlow, parentStudyId){
		if(parentStudyFlow == 'Amend Study'){
			$('parentStudyForm').action = "/c3pr/pages/study/amendStudy?studyId="+parentStudyId ;
		}else{
			$('parentStudyForm').action = "/c3pr/pages/study/editStudy?studyId="+parentStudyId ;
		}
		$('parentStudyForm').submit();
	}

	function viewParent(parentStudyId){
		$('parentStudyViewForm').action = "/c3pr/pages/study/viewStudy?studyId="+parentStudyId ;
		$('parentStudyViewForm').submit();
	}
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr>
		<td valign="top" width="40%">
			<table>
				<tags:tdNameValue name="Short title" value="${studyCommand.study.trimmedShortTitleText}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Primary identifier" value="${studyCommand.study.primaryIdentifier}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Study status" value="${studyCommand.study.coordinatingCenterStudyStatus.code}" columnAttrName="class='labelR'"/>
			</table>
		</td>
		<td valign="top">
			<table>
				<tags:tdNameValue name="Coordinating center" value="${studyCommand.study.coordinatingCenterAssignedIdentifier.healthcareSite.name}" columnAttrName="class='labelR'"/>				
				<tags:tdNameValue name="Principal investigator" value="${studyCommand.study.principalInvestigatorFullName }" columnAttrName="width='50%' class='labelR'"/>
				<c:if test="${fn:length(studyCommand.study.parentStudyAssociations) == 1}">
					<tags:tdNameValue name="Parent study" value="${studyCommand.study.parentStudy.trimmedShortTitleText}" columnAttrName="class='labelR'"/>
				</c:if>
				<c:if test="${!studyCommand.study.standaloneIndicator && fn:length(studyCommand.study.parentStudyAssociations) > 0}">
					<c:choose>
						<c:when test="${!empty param.parentStudyFlow}">
							<a href="#" onclick="javascript:returnToParent('${param.parentStudyFlow}', '${studyCommand.study.parentStudyAssociations[0].parentStudy.id}')">Return to parent study</a>
						</c:when>
						<c:otherwise>
							<a href="#" onclick="javascript:viewParent('${studyCommand.study.parentStudyAssociations[0].parentStudy.id}')">Return to parent study</a>
						</c:otherwise>
					</c:choose>
				</c:if>
				
			</table>
		</td>
	</tr>
</table>
<form id="parentStudyForm" action="" method="post">
	<input type="hidden" name="_page" id="_page7" value="7"/>
	<input type="hidden" name="_target7" id="_target7" value="7"/>
	<input type="hidden" name="refreshCommandObject" id="refreshCommandObject" value="true"/>
</form>
<form id="parentStudyViewForm" action="" method="post">	
</form>