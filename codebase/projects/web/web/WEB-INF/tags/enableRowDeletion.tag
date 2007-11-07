<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<form:form id="rowDeletionForm">
	<input type="hidden" id="collection" name="collection"/>
	<input type="hidden" id="deleteIndex" name="deleteIndex"/>
	<input type="hidden" id="deleteHashCode" name="deleteHashCode"/>
	<input type="hidden" name="_target${tab.number}" id="_target"/>
	<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
</form:form>