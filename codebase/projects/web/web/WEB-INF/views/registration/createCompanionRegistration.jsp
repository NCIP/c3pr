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

<form action="../registration/createEmbeddedCompanionRegistration?decorator=noheaderDecorator" method="post" id="create">
	<input type="hidden" name="_page" id="_page1" value="1"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="registrationId" value=""/>
	<input type="hidden" name="epoch" id="create_epoch" value="" />
	<input type="hidden" name="studySubject.studySite" id="studySite" value="" />
	<input type="hidden" name="studySubject.participant" id="participant"  value="${participant.id}"/>
	<input type="hidden" name="parentRegistrationId" id="parentRegistrationId"  value="${parentRegistrationId}"/>
</form>
</head>
<body>
	<tags:panelBox>
		<chrome:division title="Select Study Site">
		<table id="studySites" class="tablecontent" border="0" cellspacing="0" cellpadding="0"	>
               <tr>
               		<th></th>
                   <th><b>Organization</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                   <th><b>NCI Identifier</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.nciInstituteCode"/></th>
               </tr>
               <c:forEach items="${studySites}" var="studySite" varStatus="status">
               	 <tr>
					<td>
						<input class="studySiteSelection" type="radio" value="${studySite.id}" onclick="manageStudySiteSelection(this);" />
					</td>
               		<td>
               			<input size="40" type="text" value="${studySite.healthcareSite.name}" disabled="disabled" />
               		</td>
               		<td>
               			<input size="20"  type="text" value="${studySite.healthcareSite.nciInstituteCode}" disabled="disabled" />
               		</td>
               	 </tr>
               </c:forEach>
            </table>
	</chrome:division>
	<chrome:division title="Select Epoch">
		<table id="epochs" class="tablecontent" border="0" cellspacing="0" cellpadding="0"	>
              <tr>
              		<th></th>
                  <th><b>Name</b></th>
                  <th><b>Description</b></th>
                  <th><b>Enrolling</b></th>
              </tr>
              <c:forEach items="${epochs}" var="epoch" varStatus="epochStatus">
              	 <tr>
					<td>
						<input class="epochSelection" type="radio" value="${epoch.id}" onclick="manageEpochSelection(this);"/>
					</td>
             		<td>
             			<input size="30" type="text" value="${epoch.name}" disabled="disabled" />
             		</td>
             		<td>
             			<input size="30"  type="text" value="${epoch.descriptionText}" disabled="disabled" />
             		</td>
             		<td>
             			<input size="15"  type="text" value="${epoch.enrollmentIndicator}" disabled="disabled" />
             		</td>
             	 </tr>
              </c:forEach>
           </table>
	</chrome:division>
	<br>
	<div class="flow-buttons">
        <span class="next">
             <input type="button" value="Continue" onclick="startRegistartion();"/>
        </span>
	</div>
	<br>
	<br>
	</tags:panelBox>
	
</body>
</html>