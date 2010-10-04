<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr>
		<td valign="top" width="40%">
			<table>
				<tags:tdNameValue name="Name1" value="${command.studySubject.participant.firstName} ${studySubject.studySubject.participant.lastName }" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Study" value="(${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.study.primaryIdentifier}) ${ studySubject.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.shortTitleText}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Epoch" value="${command.studySubject.scheduledEpoch.epoch.name}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Registration status" value="${command.studySubject.regWorkflowStatus.displayName}" columnAttrName="class='labelR'"/>
			</table>
		</td>
		<td valign="top">
			<table>
				<tags:tdNameValue name="Subject MRN" value="${command.participant.primaryIdentifierValue }" columnAttrName="width='50%' class='labelR'"/>
				<tags:tdNameValue name="Study version" value="${command.studySubject.studySiteVersion.studyVersion.name}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Enrolling site1" value="(${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studySite.healthcareSite.primaryIdentifier}) ${studySubject.studySubject.studySite.healthcareSite.name }" columnAttrName="class='labelR'"/>
			</table>
		</td>
	</tr>
</table>