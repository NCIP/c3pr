<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<!-- MAIN BODY STARTS HERE -->
<body>
<form:form name="form" method="post">
<tabs:tabFields tab="${tab}" />
<div>
	<tabs:division id="Summary">
	<registrationTags:searchResults registrations="${participantAssignments }" />
	</tabs:division>
</div>
</form:form>
</body>
<!-- MAIN BODY ENDS HERE -->
</html>