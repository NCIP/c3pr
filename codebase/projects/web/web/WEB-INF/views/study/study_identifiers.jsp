<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <script language="JavaScript" type="text/JavaScript">

        function clearField(field) {
            field.value = "";
        }

        var instanceRowInserterProps = {

            add_row_division_id: "mytable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row",
            initialIndex: ${fn:length(command.identifiers)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "identifiers",                               /* this is the path of the collection that holds the rows  */
        };
        rowInserters.push(instanceRowInserterProps);

    </script>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}" formName="studyIdentifiersForm">
<jsp:attribute name="singleFields">
<div>
    <input type="hidden" name="_action" value="">
    <input type="hidden" name="_selected" value="">
</div>

<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <p id="instructions">
                Add Identifiers associated with the Study
                <a href="javascript:RowManager.addRow(instanceRowInserterProps);"><img
                        src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Identifier"></a><br>
            </p>
            <table id="mytable" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th scope="col" align="left"><b>Assigning Authority<span class="red">*</span></b></th>
                    <th scope="col" align="left"><b>Identifier Type<span class="red">*</span></b></th>
                    <th scope="col" align="left"><b>Identifier<span class="red">*</span></b></th>
                    <th scope="col" align="left"><b>Primary&nbsp;Indicator</b></th>
                    <th class="specalt" scope="col" align="left"></th>
                </tr>
                <c:forEach items="${command.identifiers}" begin="2" varStatus="status">
                    <tr id="mytable-${status.index}">
                        <td class="alt"><form:select path="identifiers[${status.index}].source"
                                                     cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <form:options items="${identifiersSourceRefData}" itemLabel="name"
                                          itemValue="name"/></form:select></td>
                        <td class="alt"><form:select path="identifiers[${status.index}].type"
                                                     cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <form:options items="${identifiersTypeRefData}" itemLabel="desc"
                                          itemValue="desc"/></form:select></td>
                        <td class="alt"><form:input path="identifiers[${status.index}].value"
                                                    onfocus="clearField(this)" cssClass="validate-notEmpty"/></td>
                        <td class="alt"><form:radiobutton path="identifiers[${status.index}].primaryIndicator"
                                                          value="true"/></td>
                            <td class="alt"><a href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index});"><img
                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

</jsp:attribute>
</tags:tabForm>
    <div id="dummy-row" style="display:none;">
        <table>
            <tr id="myTable-PAGE.ROW.INDEX">
                <td class="alt"><select id="identifiers[PAGE.ROW.INDEX].source"
                                        name="identifiers[PAGE.ROW.INDEX].source"
                                        class="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <c:forEach items="${identifiersSourceRefData}" var="id">
                        <option value="${id.name}">${id.name}</option>
                    </c:forEach>
                </select>
                </td>
                <td class="alt"><select id="identifiers[PAGE.ROW.INDEX].type"
                                        name="identifiers[PAGE.ROW.INDEX].type"
                                        class="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <c:forEach items="${identifiersTypeRefData}" var="id">
                        <option value="${id.desc}">${id.desc}</option>
                    </c:forEach>
                </select>
                </td>
                <td class="alt"><input id="identifiers[PAGE.ROW.INDEX].value" name="identifiers[PAGE.ROW.INDEX].value"
                                       onfocus="javascript:clearField(this)"
                                       type="text" class="validate-notEmpty"/></td>
                <td class="alt"><input type="radio" id="identifiers[PAGE.ROW.INDEX].primaryIndicator" name="identifiers[PAGE.ROW.INDEX].primaryIndicator"
                                       value="true"/></td>
                <td class="alt"><a href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX);"><img
                        src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
            </tr>
        </table>
    </div>

</body>
</html>