<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="path"%>
<form:input path="${path}" cssClass="date"/>
<a href="#" id="${path}-calbutton">
    <img src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
</a>