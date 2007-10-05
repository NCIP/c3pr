<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@attribute name="path"%>
<%@attribute name="cssClass"%>
<form:input path="${path}" cssClass="date validate-DATE ${cssClass}"/>
<a href="#" id="${path}-calbutton">
    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
</a>