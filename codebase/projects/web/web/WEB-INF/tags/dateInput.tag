<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="path" required="true" %>
<%@attribute name="format"%>
<script>
DEFAULT_FORMAT='mm/dd/yyyy'
function checkDate(field,format){
	removeError(field);
	format = (format==null||format=='')?DEFAULT_FORMAT:format;
	if(isDate(field.value,format)==false){
		showError(field,'invalid format(mm/dd/yyyy')
	}
}
</script>
<form:input path="${path}" cssClass="date" size="12" onchange="checkDate(this,'${format }');"/>
<a href="#" id="${path}-calbutton">
    <img src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0"/>
</a>