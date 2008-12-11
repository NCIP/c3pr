<%@ include file="taglibs.jsp"%>
<script>
function submitForm(){
		$("create").submit();
}
</script>

<form action="../registration/createRegistration?decorator=noheaderDecorator" method="post" id="create">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target0" id="_target0" value="0"/>
	<input type="hidden" name="registrationId" value=""/>
	<input type="hidden" name="epoch" id="create_epoch"/>
	<input type="hidden" name="studySubject.participant" id="participant"  value="${participant}"/>
	<input type="hidden" name="parentRegistrationId" id="parentRegistrationId"  value="${parentRegistrationId}"/>
</form>
</head>
<body>
	<chrome:box title="Selected Participant ${participant.fullName}"></chrome:box>
	<chrome:box title="Select Study Site">
		<table id="studySites" class="tablecontent" border="0" cellspacing="0" cellpadding="0"	>
               <tr>
               		<th></th>
                   <th><b>Organization</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                   <th><b>NCI Identifier</b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.nciInstituteCode"/></th>
               </tr>
               <c:forEach items="${studySites}" var="studySite" varStatus="status">
               	 <tr>
					<td>
						<input class="studySiteSelection" type="radio" value="${studySite.healthcareSite.nciInstituteCode}"/>
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
	</chrome:box>
	<chrome:box title="Select Epoch">
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
						<input class="epochSelection" type="radio" value="${epoch.id}"/>
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
	</chrome:box>
</body>
</html>