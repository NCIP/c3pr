<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
    <script language="JavaScript" type="text/JavaScript">

        var instanceRowInserterProps = {

            add_row_division_id: "mytable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row",
            initialIndex: ${fn:length(command.studySites)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "studySites",                               /* this is the path of the collection that holds the rows  */
        };
        rowInserters.push(instanceRowInserterProps);

    </script>
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" formName="studySiteForm">
<jsp:attribute name="singleFields">


<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <p id="instructions">
                Add StudySites associated with the Study
                <a href="javascript:RowManager.addRow(instanceRowInserterProps);"><img
                        src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Study Site"></a><br>
            </p>
            <br>
            <table id="mytable" border="0" cellspacing="0" cellpadding="0">
                <tr align="left" class="label">
                    <th scope="col" align="left"><b>HealthCare Site</b><span class="red">*</span></th>
                    <th scope="col" align="left"><b>Status<span class="red">*</span></b></th>
                    <th scope="col" align="left"><b>Activation&nbsp;Date&nbsp;&nbsp;&nbsp;</b></th>
                    <th scope="col" align="left"><b>IRB&nbsp;Approval&nbsp;Date</b></th>
                    <th scope="col" class="specalt" align="left"></th>
                </tr>
                <c:forEach items="${command.studySites}" varStatus="status">
                    <tr id="mytable-${status.index}">
                        <td class="alt">
                            <form:select id="studySites[${status.index}].site"
                                    path="studySites[${status.index}].site" cssClass="validate-notEmpty">
                                <option value="">--Please Select--</option>
                                <form:options items="${healthCareSites}" itemLabel="name" itemValue="id"/>
                            </form:select></td>
                        <td class="alt"><form:select path="studySites[${status.index}].statusCode"
                                                     cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <form:options items="${studySiteStatusRefData}" itemLabel="desc"
                                          itemValue="desc"/>
                        </form:select>
                            <input type="hidden" name="studySites[${status.index}].roleCode" value="Affiliate Site"/>

                        </td>
                        <!--TODO:HACK Remove this once more roles are present -->

                        <td class="alt"><tags:dateInput path="studySites[${status.index}].startDate"/><span
                                class="red"><em></em></span></td>
                        <td class="alt"><tags:dateInput path="studySites[${status.index}].irbApprovalDate"/><span
                                class="red"><em></em></span></td>
                        <td class="specalt"><a
                                href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index});"><img
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
        <tr>
            <td class="alt">
                <select id="studySites[PAGE.ROW.INDEX].site"
                        name="studySites[PAGE.ROW.INDEX].site"
                        class="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <c:forEach items="${healthCareSites}" var="site">
                        <option value="${site.id}">${site.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td class="alt">
                <select id="studySites[PAGE.ROW.INDEX].statusCode"
                        name="studySites[PAGE.ROW.INDEX].statusCode"
                        class="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <c:forEach items="${studySiteStatusRefData}" var="studySite">
                        <option value="${studySite.desc}">${studySite.desc}</option>
                    </c:forEach>
                </select>
                <input type="hidden" id="studySites[PAGE.ROW.INDEX].roleCode"
                       name="studySites[PAGE.ROW.INDEX].roleCode" value="Affiliate Site"/>

            </td>
            <td class="alt">
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
                <input id="studySites[PAGE.ROW.INDEX].startDate"
                       name="studySites[PAGE.ROW.INDEX].startDate"
                       class="date,validate-notEmpty" size="12" onchange="checkDate(this, 'null');"/>
                <a href="#" id="studySites[PAGE.ROW.INDEX].startDate-calbutton">
                    <img src="<tags:imageUrl name="b-calendar.gif"/>" align="top" alt="Calendar" width="17" height="16" border="0"/>
                </a>
            </td>

            <td class="alt">
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
                <input id="studySites[PAGE.ROW.INDEX].irbApprovalDate"
                       name="studySites[PAGE.ROW.INDEX].irbApprovalDate"
                       class="date,validate-notEmpty"  size="12" onchange="checkDate(this, 'null');"/>
                <a href="#" id="studySites[PAGE.ROW.INDEX].startDate-calbutton">
                    <img src="<tags:imageUrl name="b-calendar.gif"/>" align="top" alt="Calendar" width="17" height="16" border="0"/>
                </a>
            </td>

            <td  class="specalt"><a
                    href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>

        </tr>
    </table>
</div>


</body>
</html>