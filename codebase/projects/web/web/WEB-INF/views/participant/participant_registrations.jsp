<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="participanttags" tagdir="/WEB-INF/tags/participant"%>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>

<html>
<head>
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
<chrome:box title="Subject Summary">
		<chrome:division title="Registration Summary">
			<registrationTags:searchResults registrations="${participantAssignments }"/>
		</chrome:division>
</chrome:box>
</form:form>
</body>
</html>



