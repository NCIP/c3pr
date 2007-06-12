<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="title"%>
<%@attribute name="boxId"%>
<%@attribute name="boxClass" %>

<chrome:box title="${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}">
    <chrome:flashMessage/>
        <chrome:division id="single-fields">
            <jsp:doBody/>
        </chrome:division>
</chrome:box>
