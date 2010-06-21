<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@attribute name="path"%>
<%@attribute name="cssClass"%>
<%@attribute name="validateDate"%>
<%@attribute name="size"%>
<form:input path="${path}" cssClass='date ${!empty validateDate || vaidateDate=="false"?"":"validate-DATE" } ${cssClass}' size='${!empty size? size:18}'/>
<a href="#" id="${path}-calbutton">
    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
</a>