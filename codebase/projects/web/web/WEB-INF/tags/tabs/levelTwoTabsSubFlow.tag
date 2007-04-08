<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="tab" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab" required="true" %>
<%@attribute name="flow" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow" required="true" %>
<%--<table width="100%" border="0" cellspacing="0" cellpadding="0" class="division">
    <tr>
        <td>
			<table width="40%" border="0" cellspacing="0" cellpadding="0" class="tabs">
			   <tr>
				   <c:forEach items="${flow.tabs}" var="atab">
				       <td class="tabDisplay"><img src="<tags:imageUrl name="tab3_h_L.gif"/>" width="1" height="16" align="absmiddle"><span class="current">
				           <a href="#" class="tab${atab.number}">${atab.number + 1}. ${atab.shortTitle}</a>
				           </span><img src="<tags:imageUrl name="tab3_h_R.gif"/>" width="7" height="16" align="absmiddle">
				       </td>
					</c:forEach>       
			   </tr>
			</table>
        </td>
    </tr>
</table>
--%>
<ul id="level2SubFlow" class="tabs autoclear">
<c:forEach items="${flow.tabs}" var="atab">
    <c:set var="current" value="${atab.number == tab.number}"/>
    <li class="tab ${current ? 'current' : ''}">
        <img src="<tags:imageUrl name="tab2${current ? '_h' : ''}_L.gif"/>" width="1" height="16" align="absmiddle"><a href="#" class="tab${atab.number}">${atab.number + 1}. ${atab.shortTitle}</a><img src="<tags:imageUrl name="tab2${current ? '_h' : ''}_R.gif"/>" width="6" height="16" align="absmiddle">
    </li>
</c:forEach>
</ul>
<div id="level2-spacer"></div>


