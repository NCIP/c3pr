<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="tab" required="true" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" %>
<%@attribute name="flow" required="true" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" %>
<%@attribute name="title"%>
<%@attribute name="formName"%>
<%@attribute name="enctype"%>
<%@attribute name="boxId"%>
<%@attribute name="boxClass" %>
<%@attribute name="action" %>
<%@attribute name="localButtons" fragment="true" %>
<%@attribute name="continueLabel"%>
<c:if test="${not empty action}"><c:set var="actionString" value="action='${action}'"></c:set></c:if>
<form:form name="${formName}" action="${action}" enctype="${enctype}">
<tags:tabFields tab="${tab}"/>
<c:if test="${empty willSave}"><c:set var="willSave" value="${true}"/></c:if>
<chrome:box title="${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}">
    <chrome:flashMessage/>
        <chrome:division id="single-fields">

            <tags:hasErrorsMessage/>
            <jsp:doBody/>
        </chrome:division>
        <tags:tabControls continueLabel="${continueLabel}" tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</chrome:box>
</form:form>