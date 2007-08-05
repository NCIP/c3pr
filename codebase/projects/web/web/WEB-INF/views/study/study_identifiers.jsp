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

            add_row_division_id: "identifierTable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row",
            initialIndex: ${fn:length(command.identifiers)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "identifiers",                               /* this is the path of the collection that holds the rows  */
        };
        rowInserters.push(instanceRowInserterProps);

    </script>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="studyIdentifiersForm">

    <jsp:attribute name="singleFields">
          <table>
              <tr>
                  <td>

                      <br>
                      <table id="identifierTable" class="tablecontent">
                          <tr>
                              <th>Assigning Authority<span class="required-indicator">*</span></th>
                              <th>Identifier Type<span class="required-indicator">*</span></th>
                              <th>Identifier<span class="required-indicator">*</span></th>
                              <th>Primary&nbsp;Indicator</th>
                              <th></th>
                          </tr>
                          <c:forEach items="${command.identifiers}" begin="2" varStatus="status">
                              <tr id="mytable-${status.index}">
                                  <td><form:select path="identifiers[${status.index}].source"
                                                               cssClass="validate-notEmpty">
                                      <option value="">--Please Select--</option>
                                      <form:options items="${identifiersSourceRefData}" itemLabel="name"
                                                    itemValue="name"/></form:select></td>
                                  <td><form:select path="identifiers[${status.index}].type"
                                                               cssClass="validate-notEmpty">
                                      <option value="">--Please Select--</option>
                                      <form:options items="${identifiersTypeRefData}" itemLabel="desc"
                                                    itemValue="desc"/></form:select></td>
                                  <td><form:input path="identifiers[${status.index}].value"
                                                              onfocus="clearField(this)" cssClass="validate-notEmpty"/></td>
                                  <td><form:radiobutton path="identifiers[${status.index}].primaryIndicator"
                                                                    value="true"/></td>
                                  <td><a href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index});"><img
                                          src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                              </tr>
                          </c:forEach>
                      </table>
                  </td>
              </tr>
          </table>

</jsp:attribute>

     <jsp:attribute name="localButtons">
        <input id="addEpoch" type="button"
               value="Add Identifier"
               onclick="RowManager.addRow(instanceRowInserterProps);"/>
    </jsp:attribute>

</tags:tabForm>

<div id="dummy-row" style="display:none;">
    <table>
        <tr id="identifierTable-PAGE.ROW.INDEX">
            <td><select id="identifiers[PAGE.ROW.INDEX].source"
                                    name="identifiers[PAGE.ROW.INDEX].source"
                                    class="validate-notEmpty">
                <option value="">--Please Select--</option>
                <c:forEach items="${identifiersSourceRefData}" var="id">
                    <option value="${id.name}">${id.name}</option>
                </c:forEach>
            </select>
            </td>
            <td><select id="identifiers[PAGE.ROW.INDEX].type"
                                    name="identifiers[PAGE.ROW.INDEX].type"
                                    class="validate-notEmpty">
                <option value="">--Please Select--</option>
                <c:forEach items="${identifiersTypeRefData}" var="id">
                    <option value="${id.desc}">${id.desc}</option>
                </c:forEach>
            </select>
            </td>
            <td><input id="identifiers[PAGE.ROW.INDEX].value" name="identifiers[PAGE.ROW.INDEX].value"
                                   onfocus="javascript:clearField(this)"
                                   type="text" class="validate-notEmpty"/></td>
            <td><input type="radio" id="identifiers[PAGE.ROW.INDEX].primaryIndicator" name="identifiers[PAGE.ROW.INDEX].primaryIndicator"
                                   value="true"/></td>
            <td><a href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
        </tr>
    </table>
</div>

</body>
</html>