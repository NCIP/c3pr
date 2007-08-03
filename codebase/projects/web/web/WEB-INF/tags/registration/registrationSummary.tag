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
	<tags:tdNameValue name="Gender" value="${studySubject.id }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Short Title" value="${studySubject.studySite.study.shortTitleText}" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Epoch" value="${studySubject.scheduledEpoch.epoch.name}" columnAttrName="class='labelR'"/>	
	<c:choose>
		<c:when test="${studySubject.ifTreatmentScheduledEpoch}">
			<tags:tdNameValue name="Epoch Type" value="Treatment" columnAttrName="class='labelR'"/>	
		</c:when>
		<c:otherwise>
			<tags:tdNameValue name="Epoch Type" value="Non Treatment" columnAttrName="class='labelR'"/>	
		</c:otherwise>
	</c:choose>
	<tags:tdNameValue name="Status" value="${studySubject.studySite.study.status }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Study Site" value="${studySubject.studySite.healthcareSite.name }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Site IRB Approval Date" value="${studySubject.studySite.irbApprovalDateStr }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Informed Consent Signed Date" value="${studySubject.informedConsentSignedDateStr }" columnAttrName="class='labelR'"/>
	<tags:tdNameValue name="Informed Consent Version" value="${studySubject.informedConsentVersion }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Treating Physician" value="${studySubject.treatingPhysicianFullName }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Disease" value="${studySubject.diseaseHistory.primaryDiseaseStr }" columnAttrName="class='labelR'"/>		
	<tags:tdNameValue name="Disease Site" value="${studySubject.diseaseHistory.primaryDiseaseSiteStr }" columnAttrName="class='labelR'"/>
	<c:if test="${studySubject.ifTreatmentScheduledEpoch}"><tags:tdNameValue name="Eligibility Indicator" value="${studySubject.scheduledEpoch.eligibilityIndicator }" columnAttrName="class='labelR'"/></c:if>
	<tags:tdNameValue name="Registration Status" value="${studySubject.registrationStatus }" columnAttrName="class='labelR'"/>		
	<tr>
		<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"	height="1" class="heightControl"></td>
	</tr>
</table>