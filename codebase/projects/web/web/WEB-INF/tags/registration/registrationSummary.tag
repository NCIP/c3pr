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
				<tags:tdNameValue name="Name" value="${studySubject.studySubject.participant.firstName} ${studySubject.studySubject.participant.lastName }" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Study" value="(${studySubject.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.study.primaryIdentifier}) ${ studySubject.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.shortTitleText}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Epoch" value="${studySubject.studySubject.scheduledEpoch.epoch.name} (${studySubject.studySubject.scheduledEpoch.epoch.id}) (${studySubject.studySubject.scheduledEpoch.id})" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Registration status" value="${studySubject.studySubject.regWorkflowStatus.displayName} (${studySubject.studySubject.studySubjectStudyVersion.id})" columnAttrName="class='labelR'"/>
			</table>
		</td>
		<td valign="top">
			<table>
				<tags:tdNameValue name="Subject MRN" value="${command.participant.primaryIdentifierValue }" columnAttrName="width='50%' class='labelR'"/>
				<tags:tdNameValue name="Study version" value="${studySubject.studySubject.studySiteVersion.studyVersion.name}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Enrolling site" value="(${studySubject.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studySite.healthcareSite.primaryIdentifier}) ${studySubject.studySubject.studySite.healthcareSite.name }" columnAttrName="class='labelR'"/>
			</table>
		</td>
	</tr>
</table>