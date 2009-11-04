<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><participanttags:htmlTitle subject="${command}" /></title>
<style type="text/css">
        .labelR { width: 12em; text-align: right; padding: 4px; }
</style>
<style type="text/css">
        .label { width: 12em; text-align: left; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function updateTargetPage(target){
	document.formName._target0.value=s;
	document.formName.submit();
}

</script>
</head>
<body>
<form:form>
<tags:tabFields tab="${tab}"/>
</form:form>
<chrome:box title="Subject Summary">
		<chrome:division title="Registration Summary">
			<registrationTags:searchResults registrations="${participantAssignments }"/>
		</chrome:division>
</chrome:box>

</body>
</html>



