<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <script language="JavaScript" type="text/JavaScript">
        function fireAction(action, selected) {
            document.getElementById('command')._target.name = '_noname';
            document.studyIdentifiersForm._action.value = action;
            document.studyIdentifiersForm._selected.value = selected;
            document.studyIdentifiersForm.submit();
        }
        function clearField(field) {
            field.value = "";
        }
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
                <a href="javascript:fireAction('addIdentifier','0');"><img
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
                <c:forEach items="${command.localIdentifiers}" varStatus="status">
                    <tr>
                        <td class="alt"><form:select path="localIdentifiers[${status.index}].source"
                                                     cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <form:options items="${identifiersSourceRefData}" itemLabel="name"
                                          itemValue="name"/></form:select></td>
                        <td class="alt"><form:select path="localIdentifiers[${status.index}].type"
                                                     cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <form:options items="${identifiersTypeRefData}" itemLabel="desc"
                                          itemValue="desc"/></form:select></td>
                        <td class="alt"><form:input path="localIdentifiers[${status.index}].value"
                                                    cssClass="validate-notEmpty"/></td>
                        <td class="alt"><form:radiobutton path="localIdentifiers[${status.index}].primaryIndicator"
                                                          value="true"/></td>
                        <td class="alt"><a href="javascript:fireAction('removeIdentifier',${status.index});"><img
                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
</jsp:attribute>
</tags:tabForm>
</body>
</html>