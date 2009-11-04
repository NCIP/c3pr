<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="rowNumber" required="true" %>
<%@attribute name="bgcolor" required="true" %>
<%@attribute name="bgcolorSelected" required="true" %>
<%@attribute name="_url" required="false" %>
<%@attribute name="_onclick" required="false" %>

<tr style='cursor:pointer;'
    bgcolor="${bgcolor}"
    onmouseover="setPointer(this, '${rowNumber}', 'over', '${bgcolor}', '${bgcolorSelected}', '${bgcolorSelected}');"
    onmouseout="setPointer(this, '${rowNumber}', 'out', '${bgcolor}', '${bgcolorSelected}', '${bgcolorSelected}');" 
	onclick="${_onclick}"
<c:if test="${not empty _url}">onmousedown=document.location='${_url}';</c:if>

>

    <jsp:doBody/>
</tr>
