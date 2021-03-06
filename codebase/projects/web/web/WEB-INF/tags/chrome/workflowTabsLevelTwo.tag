<!--BEGIN workflowTabsLevelTwo.tag-->
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="tab" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" required="true" %>
<%@attribute name="flow" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" required="true" %>
<ul id="tabs3w" class="tabs autoclear">
<c:forEach items="${flow.tabs}" var="atab" varStatus="status">
    <c:set var="selected" value="${atab.number == tab.number}"/>
    <li class="tab ${selected ? 'selectedtab' : ''} ${status.last ? 'last' : ''}"><div>
        <a href="#" class="tab${atab.number}">${atab.number + 1}. ${atab.shortTitle}</a>
    </div></li>
</c:forEach>
</ul>
<!--END workflowTabsLevelTwo.tag-->