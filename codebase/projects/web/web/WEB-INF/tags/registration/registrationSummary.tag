<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<style type="text/css">
        .labelL { text-align: left; padding: 4px; font-weight: bold;}
</style>
<style type="text/css">
        .labelC { text-align: center; padding: 4px; font-weight: bold;}
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0"id="table1">
	<tags:tdNameValue name="Full Name" value="${studySubject.studySubject.participant.firstName} ${studySubject.studySubject.participant.lastName }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Subject Primary Identifier" value="${studySubject.studySubject.participant.primaryIdentifier }" columnAttrName="width='50%' class='labelR'"/>
	<tags:tdNameValue name="Gender" value="${studySubject.studySubject.participant.administrativeGenderCode }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Short Title" value="${studySubject.studySubject.studySite.study.shortTitleText}" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Current Epoch" value="${studySubject.studySubject.scheduledEpoch.epoch.name}" columnAttrName="class='labelR'"/>	
	<tags:tdNameValue name="Enrolling Epoch" value="${studySubject.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Current Epoch Status" value="${studySubject.studySubject.scheduledEpoch.scEpochWorkflowStatus.code}" columnAttrName="class='labelR'"/>				
	<tags:tdNameValue name="Study Status" value="${studySubject.studySubject.studySite.study.coordinatingCenterStudyStatus.code }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Study Site" value="${studySubject.studySubject.studySite.healthcareSite.name }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Site IRB Approval Date" value="${studySubject.studySubject.studySite.irbApprovalDateStr }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Informed Consent Signed Date" value="${studySubject.studySubject.informedConsentSignedDateStr }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Informed Consent Version" value="${studySubject.studySubject.informedConsentVersion }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Registration Start Date" value="${studySubject.studySubject.startDateStr }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Treating Physician" value="${studySubject.studySubject.treatingPhysicianFullName }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Disease" value="${studySubject.studySubject.diseaseHistory.primaryDiseaseStr }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Disease Site" value="${studySubject.studySubject.diseaseHistory.primaryDiseaseSiteStr }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Eligibility Indicator" value="${studySubject.studySubject.scheduledEpoch.eligibilityIndicator?'Yes':'No' }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Data Entry Status" value="${studySubject.studySubject.dataEntryStatusString }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Registration Status" value="${studySubject.studySubject.regWorkflowStatus.code }" columnAttrName="class='labelR'"/>
	<tr>
		<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"	height="1" class="heightControl"></td>
	</tr>
</table>