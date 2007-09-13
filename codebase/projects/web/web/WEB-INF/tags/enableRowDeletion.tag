<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<form:form id="rowDeletionForm">
	<tags:tabFields tab="${tab}"/>
	<input type="hidden" id="collection" name="collection"/>
	<input type="hidden" id="deleteIndex" name="deleteIndex"/>
</form:form>