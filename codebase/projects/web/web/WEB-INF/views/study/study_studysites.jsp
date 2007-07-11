<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>



<html>
<head>
    <script language="JavaScript" type="text/JavaScript">

        var instanceRowInserterProps = {

            add_row_division_id: "mytable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row",
            initialIndex: ${fn:length(command.studySites)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "studySites",                               /* this is the path of the collection that holds the rows  */
            postProcessRowInsertion: function(object){
                inputDateElementLocal="studySites["+object.localIndex+"].startDate";
                inputDateElementLink="studySites["+object.localIndex+"].startDate-calbutton";
                Calendar.setup(
                {
                    inputField  : inputDateElementLocal,         // ID of the input field
                    ifFormat    : "%m/%d/%Y",    // the date format
                    button      : inputDateElementLink       // ID of the button
                }
                        );
                inputDateElementLocal="studySites["+object.localIndex+"].irbApprovalDate";
                inputDateElementLink="studySites["+object.localIndex+"].irbApprovalDate-calbutton";
                Calendar.setup(
                {
                    inputField  : inputDateElementLocal,         // ID of the input field
                    ifFormat    : "%m %d, %Y",    // the date format
                    button      : inputDateElementLink       // ID of the button
                }
                        );
            },
        };
        rowInserters.push(instanceRowInserterProps);

    </script>
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="studySiteForm">



    <jsp:attribute name="singleFields">
<tags:errors path="*" />

<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>

            <br>
            <table id="mytable" border="0" cellspacing="0" cellpadding="0">
                <tr class="label">
                    <th scope="col"><b>HealthCare Site</b></th>
                    <th scope="col"><b>Status</b></th>
                    <th scope="col"><b>Activation Date</b></th>
                    <th scope="col"><b>IRB Approval Date</b></th>
                    <th scope="col" class="specalt"></th>
                </tr>
                <c:forEach items="${command.studySites}" varStatus="status">
                    <tr id="mytable-${status.index}">
                        <td class="alt">
                            <form:select id="studySites[${status.index}].site"
                                         path="studySites[${status.index}].site.id" cssClass="validate-notEmpty">
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

                        <td class="alt">
                            <tags:dateInput path="studySites[${status.index}].startDate"/>
                        </td>
                        <td class="alt">
                            <tags:dateInput path="studySites[${status.index}].irbApprovalDate"/>
                        </td>
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

    	 <jsp:attribute name="localButtons">
        <input id="addEpoch" type="button"
               value="Add Study Site"
               onclick="RowManager.addRow(instanceRowInserterProps);"/>
    </jsp:attribute>

</tags:tabForm>
<div id="dummy-row" style="display:none;">
    <table>
        <tr id="mytable-PAGE.ROW.INDEX">
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
                <input id="studySites[PAGE.ROW.INDEX].startDate"
                       name="studySites[PAGE.ROW.INDEX].startDate"
                       type="text"
                       class="date" />
                <a href="#" id="studySites[PAGE.ROW.INDEX].startDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
                </a>
            </td>
            <td class="alt">
                <input id="studySites[PAGE.ROW.INDEX].irbApprovalDate"
                       name="studySites[PAGE.ROW.INDEX].irbApprovalDate"
                       type="text"
                       class="date" />
                <a href="#" id="studySites[PAGE.ROW.INDEX].irbApprovalDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
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