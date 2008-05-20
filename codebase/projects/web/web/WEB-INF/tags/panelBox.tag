<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="title"%>
<%@attribute name="boxId"%>
<%@attribute name="boxClass" %>
<%@attribute name="helpIconUrl" %>

<chrome:box title="${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}" helpIconUrl="${helpIconUrl}">
    <chrome:flashMessage/>
        <chrome:division id="single-fields">
            <jsp:doBody/>
        </chrome:division>
</chrome:box>
