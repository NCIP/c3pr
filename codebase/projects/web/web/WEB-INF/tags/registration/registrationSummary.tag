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
	<tags:tdNameValue name="Full Name" value="${studySubject.participant.firstName} ${studySubject.participant.lastName }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Subject Primary Identifier" value="${studySubject.participant.primaryIdentifier }" columnAttrName="width='50%' class='labelR'"/>
	<tags:tdNameValue name="Gender" value="${studySubject.participant.administrativeGenderCode }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Short Title" value="${studySubject.studySite.study.shortTitleText}" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Current Epoch" value="${studySubject.scheduledEpoch.epoch.name}" columnAttrName="class='labelR'"/>	
	<tags:tdNameValue name="Current Epoch Type" value="${studySubject.ifTreatmentScheduledEpoch?'Treatment':'Non Treatment'}" columnAttrName="class='labelR'"/>	
	<tags:tdNameValue name="Enrolling Epoch" value="${!studySubject.ifTreatmentScheduledEpoch?studySubject.scheduledEpoch.epoch.enrollmentIndicator:'True'}" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Current Epoch Status" value="${studySubject.scheduledEpoch.scEpochWorkflowStatus.code}" columnAttrName="class='labelR'"/>				
	<tags:tdNameValue name="Study Status" value="${studySubject.studySite.study.coordinatingCenterStudyStatus.code }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Study Site" value="${studySubject.studySite.healthcareSite.name }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Site IRB Approval Date" value="${studySubject.studySite.irbApprovalDateStr }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Informed Consent Signed Date" value="${studySubject.informedConsentSignedDateStr }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Informed Consent Version" value="${studySubject.informedConsentVersion }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Treating Physician" value="${studySubject.treatingPhysicianFullName }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Disease" value="${studySubject.diseaseHistory.primaryDiseaseStr }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Disease Site" value="${studySubject.diseaseHistory.primaryDiseaseSiteStr }" columnAttrName="class='labelR'"/>
	<c:if test="${studySubject.ifTreatmentScheduledEpoch}"><tags:tdNameValue name="Eligibility Indicator" value="${studySubject.scheduledEpoch.eligibilityIndicator?'True':'False' }" columnAttrName="class='labelR'"/></c:if>
	<tags:tdNameValue name="Data Entry Status" value="${studySubject.dataEntryStatusString }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Registration Status" value="${studySubject.regWorkflowStatus.code }" columnAttrName="class='labelR'"/>
	<tr>
		<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"	height="1" class="heightControl"></td>
	</tr>
</table>