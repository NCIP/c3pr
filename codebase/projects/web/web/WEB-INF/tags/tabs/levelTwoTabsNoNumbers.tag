<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="tab" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab" required="true" %>
<%@attribute name="flow" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow" required="true" %>
<%@attribute name="showNumber"  %>

<ul id="level2" class="tabs autoclear">
<c:forEach items="${flow.tabs}" var="atab">
    <c:set var="current" value="${atab.number == tab.number}"/>
    <li class="tab ${current ? 'current' : ''}">
        <img src="<tags:imageUrl name="tab2${current ? '_h' : ''}_L.gif"/>" width="1" height="16" align="absmiddle"><a href="#" class="tab${atab.number}">${atab.shortTitle}</a><img src="<tags:imageUrl name="tab2${current ? '_h' : ''}_R.gif"/>" width="6" height="16" align="absmiddle">
    </li>
</c:forEach>
</ul>
<div id="level2-spacer"></div>
