<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="title"%>
<%@attribute name="boxId"%>
<%@attribute name="boxClass" %>
<%@attribute name="url"%>
<%@attribute name="display"%>
<%@attribute name="helpUrl"%>

<chrome:minimizableBox title="${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}" url="${url}" display="${display}" helpUrl="${helpUrl}">
    <chrome:flashMessage/>
        <chrome:division id="single-fields">
            <jsp:doBody/>
        </chrome:division>
</chrome:minimizableBox>
