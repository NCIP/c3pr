<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Subject Created</title>
<!--empty head-->
<script type="text/javascript">
function editParticipant(){
	document.location="editParticipant?<tags:identifierParameterString identifier='${command.organizationAssignedIdentifiers[0] }'/>";
	}
</script>
</head>
<body>
<div id="controlPanel">
			<tags:controlPanel>
				<tags:oneControlPanelItem linkhref="javascript:editParticipant();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
				<tags:oneControlPanelItem linkhref="javascript:document.location='createParticipant';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_searchParticipantController.png" linktext="Create subject" />
			</tags:controlPanel>
		</div>
		<div id="flash-message" class="info"><img src="<tags:imageUrl name='check.png'/>" alt="" style="vertical-align:top;" /> 
                	 	Subject successfully created.
		</div>
    <chrome:box title="Confirmation" autopad="true">
            	<div class="label" >
                    <h2><font color="green">Subject successfully created.</font></h2>
                </div>
                <br>
                 <div class="row" >
				     <div class="label" ><fmt:message key="participant.fullName"/>:</div>
				     <div class="value" >${command.fullName}</div>
				 </div>
				 <div class="row" >
				     <div class="label" ><fmt:message key="c3pr.common.primaryIdentifier"/>:</div>
				     <div class="value" >${command.primaryIdentifier}</div>
				 </div>
		<br/>
    </chrome:box>
</body>
</html>
