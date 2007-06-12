<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="tab" required="true" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" %>
<%@attribute name="flow" required="true" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" %>
<%@attribute name="willSave"%>
<%@attribute name="title"%>
<%@attribute name="formName"%>
<%@attribute name="enctype"%>
<%@attribute name="boxId"%>
<%@attribute name="boxClass" %>
<%@attribute name="localButtons" fragment="true" %>

<form:form name="${formName}" enctype="${enctype}">
<c:if test="${empty willSave}"><c:set var="willSave" value="${true}"/></c:if>
<chrome:box title="${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}">
    <chrome:flashMessage/>
        <tags:tabFields tab="${tab}"/>
        <chrome:division id="single-fields">

            <tags:hasErrorsMessage/>
            <jsp:doBody/>
        </chrome:division>
        <tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</chrome:box>
</form:form>