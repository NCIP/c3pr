<%@attribute name="rowNumber" required="true" %>
<%@attribute name="bgcolor" required="true" %>
<%@attribute name="bgcolorSelected" required="true" %>

<tr
    bgcolor="${bgcolor}"
    onmouseover="setPointer(this, '${rowNumber}', 'over', '${bgcolor}', '${bgcolorSelected}', '${bgcolorSelected}');"
    onmouseout="setPointer(this, '${rowNumber}', 'out', '${bgcolor}', '${bgcolorSelected}', '${bgcolorSelected}');"
    onmousedown="setPointer(this, '${rowNumber}', 'click', '${bgcolor}', '${bgcolorSelected}', '${bgcolorSelected}');">
    <jsp:doBody/>
</tr>
