<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="title"%>
<%@attribute name="boxId"%>
<%@attribute name="boxClass" %>
<%@attribute name="url"%>
<%@attribute name="display"%>
<%@attribute name="helpIconUrl"%>

<chrome:minimizableBox title="${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}" url="${url}" display="${display}" helpIconUrl="${helpIconUrl}">
    <chrome:flashMessage/>
        <chrome:division id="single-fields">
            <jsp:doBody/>
        </chrome:division>
</chrome:minimizableBox>
