<%@ include file="taglibs.jsp"%>
<script>
var studySiteId="" ;
var epochId="";

function startRegistartion(){
	$("create").submit();
}

function manageStudySiteSelection(element){
	$$('.studySiteSelection').each(function(e){
				e.checked=false;
			}
		);
	element.checked=true;
	$('studySite').value = element.value ;
}

function manageEpochSelection(element){
	$$('.epochSelection').each(function(e){
				e.checked=false;
			}
	);
	element.checked=true;
	$('create_epoch').value = element.value ;
}

</script>
<style>
	#mybox div.hr{
		display : none ;
	}
</style>

<form action="../registration/createEmbeddedCompanionRegistration?decorator=noheaderRegistrationflow" method="post" id="create">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target0" id="_target0" value="0"/>
	<input type="hidden" name="registrationId" value=""/>
	<input type="hidden" name="epoch" id="create_epoch" value="" />
	<input type="hidden" name="studySubject.studySite" id="studySite" value="" />
	<input type="hidden" name="studySubject.participant" id="participant"  value="${participant.id}"/>
	<input type="hidden" name="parentRegistrationId" id="parentRegistrationId"  value="${parentRegistrationId}"/>
</form>
</head>
<body>
	<br>
	<tags:panelBox boxId="mybox" title="Selected Subject: ${participant.fullName}"></tags:panelBox>
	<tags:panelBox boxId="mybox" title="Selected Study: ${companionStudy.shortTitleText} (${companionStudy.coordinatingCenterAssignedIdentifier.value})"></tags:panelBox>
	<tags:panelBox>
	<chrome:division title="Select Study Site">
		<table id="studySites" class="tablecontent" border="0" cellspacing="0" cellpadding="0"	>
               <tr>
               		<th></th>
                   <th><b><fmt:message key="c3pr.common.organization"/></b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                   <th><b><fmt:message key="c3pr.common.NCIIdentifier"/></b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.nciInstituteCode"/></th>
               </tr>
               <c:forEach items="${studySites}" var="studySite" varStatus="status">
               	 <tr>
					<td>
						<input class="studySiteSelection" type="radio" value="${studySite.id}" onclick="manageStudySiteSelection(this);" />
					</td>
               		<td>
               			${studySite.healthcareSite.name}
               		</td>
               		<td>
               			${studySite.healthcareSite.nciInstituteCode}
               		</td>
               	 </tr>
               </c:forEach>
            </table>
	</chrome:division>
	<chrome:division title="Select Epoch">
		<table id="epochs" class="tablecontent" border="0" cellspacing="0" cellpadding="0"	>
              <tr>
              		<th></th>
                  <th><b><fmt:message key="c3pr.common.name"/></b></th>
                  <th><b><fmt:message key="c3pr.common.description"/></b></th>
                  <th><b><fmt:message key="c3pr.common.enrolling"/></b></th>
              </tr>
              <c:forEach items="${companionStudy.epochs}" var="epoch" varStatus="epochStatus">
              	 <tr>
					<td>
						<input class="epochSelection" type="radio" value="${epoch.id}" onclick="manageEpochSelection(this);"/>
					</td>
             		<td>
             			${epoch.name}
             		</td>
             		<td>
             			${epoch.descriptionText}
             		</td>
             		<td>
             			${(epoch.enrollmentIndicator)?'Yes':'No'}
             		</td>
             	 </tr>
              </c:forEach>
           </table>
	</chrome:division>
</tags:panelBox>
	<div class="flow-buttons">
        <span class="next">
        	<tags:button type="button" color="red" icon="x" value="Cancel" onclick="parent.closePopup();" />
			<tags:button type="button" color="green" icon="continue" value="Continue" onclick="startRegistartion();"  />
        </span>
	</div>
	
</body>
</html>