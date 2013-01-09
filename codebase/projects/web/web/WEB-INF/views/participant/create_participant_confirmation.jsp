<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Subject Created</title>
<!--empty head-->
<script type="text/javascript">
function manageParticipant(){
	document.location="viewParticipant?<tags:identifierParameterString identifier='${command.participant.organizationAssignedIdentifiers[0] }'/>";
	}
</script>
</head>
<body>
<div id="controlPanel">
			<tags:controlPanel>
				<tags:oneControlPanelItem linkhref="javascript:manageParticipant();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Manage" />
				<tags:oneControlPanelItem linkhref="javascript:document.location='createParticipant';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_createParticipantController.png" linktext="Create subject" />
			</tags:controlPanel>
		</div>
		<div id="flash-message" class="info"><img src="<tags:imageUrl name='check.png'/>" alt="" style="vertical-align:top;" /> 
                	 	Subject successfully created.
		</div>
    <chrome:box title="Confirmation" autopad="true">
                 <div class="row" >
				     <div class="label" ><fmt:message key="participant.fullName"/>:</div>
				     <div class="value" >${command.participant.fullName}</div>
				 </div>
				 <div class="row" >
				     <div class="label" ><fmt:message key="c3pr.common.primaryIdentifier"/>:</div>
				     <div class="value" >${command.participant.primaryIdentifierValue}</div>
				 </div>
				 <div class="row" >
				     <div class="label" ><fmt:message key="participant.birthDate"/>:</div>
				     <div class="value" >${command.participant.birthDateStr}</div>
				 </div>
				 <div class="row">
				 	 <div class="label"><fmt:message key="c3pr.common.streetAddress"/>:</div>
				 	 <tags:value value="${command.participant.address.streetAddress}" ></tags:value>
				 </div>
				 <div class="row">
					 <div class="label"><fmt:message key="c3pr.common.city"/>:</div>
					 <tags:value value="${command.participant.address.city}" ></tags:value>
				 </div>
				 <div class="row">
					 <div class="label"><fmt:message key="c3pr.common.state"/>:</div>
					 <tags:value value="${command.participant.address.stateCode}" ></tags:value>
				 </div>
				 <div class="row">
					 <div class="label"><fmt:message key="c3pr.common.country"/>:</div>
					 <tags:value value="${command.participant.address.countryCode}" ></tags:value>
				 </div>
				 <div class="row">
					 <div class="label"><fmt:message key="c3pr.common.zip"/>:</div>
					 <tags:value value="${command.participant.address.postalCode}" ></tags:value>
				 </div>
		<br/>
    </chrome:box>
</body>
</html>
