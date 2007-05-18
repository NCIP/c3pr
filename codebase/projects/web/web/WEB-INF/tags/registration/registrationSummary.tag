<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<style type="text/css">
        .labelL { text-align: left; padding: 4px; font-weight: bold;}
</style>
<style type="text/css">
        .labelC { text-align: center; padding: 4px; font-weight: bold;}
</style>
<tabs:division id="Summary" title="Summary">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"id="table1">
		<tags:tdNameValue name="Full Name" value="${studyParticipantAssignment.participant.firstName} ${studyParticipantAssignment.participant.lastName }" columnAttrName="class='labelR'"/>
		<tags:tdNameValue name="Subject MRN" value="${studyParticipantAssignment.participant.primaryIdentifier }" columnAttrName="width='50%' class='labelR'"/>
		<tags:tdNameValue name="Gender" value="${studyParticipantAssignment.id }" columnAttrName="class='labelR'"/>
		<tags:tdNameValue name="Short Title" value="${studyParticipantAssignment.studySite.study.shortTitleText}" columnAttrName="class='labelR'"/>
		<tags:tdNameValue name="Status" value="${studyParticipantAssignment.studySite.study.status }" columnAttrName="class='labelR'"/>
		<tags:tdNameValue name="Study Site" value="${studyParticipantAssignment.studySite.site.name }" columnAttrName="class='labelR'"/>
		<tags:tdNameValue name="Site IRB Approval Date" value="${studyParticipantAssignment.studySite.irbApprovalDateStr }" columnAttrName="class='labelR'"/>		
		<tags:tdNameValue name="Informed Consent Signed Date" value="${studyParticipantAssignment.informedConsentSignedDateStr }" columnAttrName="class='labelR'"/>
		<tags:tdNameValue name="Informed Consent Version" value="${studyParticipantAssignment.informedConsentVersion }" columnAttrName="class='labelR'"/>		
		<tags:tdNameValue name="Treating Physician" value="${studyParticipantAssignment.treatingPhysician.healthcareSiteInvestigator.investigator.fullName }" columnAttrName="class='labelR'"/>		
		<tags:tdNameValue name="Disease" value="${studyParticipantAssignment.diseaseHistory.primaryDiseaseStr }" columnAttrName="class='labelR'"/>		
		<tags:tdNameValue name="Disease Site" value="${studyParticipantAssignment.diseaseHistory.primaryDiseaseSiteStr }" columnAttrName="class='labelR'"/>
		<tags:tdNameValue name="Eligibility Indicator" value="${studyParticipantAssignment.eligibilityIndicator }" columnAttrName="class='labelR'"/>		
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"	height="1" class="heightControl"></td>
		</tr>
	</table>
</tabs:division>
