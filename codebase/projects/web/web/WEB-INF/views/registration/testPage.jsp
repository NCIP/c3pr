<%@ include file="taglibs.jsp"%>
<script>
function submitForm(){
		$("create").submit();
}
</script>

<form action="../registration/createRegistration?decorator=noheaderDecorator&customButton=true&create_companion=true" method="post" id="create">
	<input type="hidden" name="_page" id="_page1" value="1"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="registrationId" value=""/>
	<input type="hidden" name="epoch" id="create_epoch"/>
	<input type="hidden" name="studySubject.studySite" id="studySite" value="${studySite}"/>
	<input type="hidden" name="studySubject.participant" id="participant"  value="${participant}"/>
	<input type="hidden" name="studySubject.parentRegistrationId" id="parentRegistrationId"  value="${parentRegistrationId}"/>
</form>
</head>
<body>
<script>submitForm()</script>

</body>
</html>