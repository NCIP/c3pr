<%@tag%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@attribute name="path" required="true" %>
<a id="${path}-help-control" href="javascript:Effect.Combo('${path}-help-content')">
    <img src="<tags:imageUrl name='q.gif'/>" alt="Help" title="Show help for this field">
</a>
<div id="${path}-help-content" class="help-content" style="display: none">
    <spring:message code="${path}.help.text" text="No help available" />
</div>
