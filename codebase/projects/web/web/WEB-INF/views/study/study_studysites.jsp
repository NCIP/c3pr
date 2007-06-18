<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <script type="text/javascript">
        function fireAction(action, selected) {
            document.getElementById('command')._target.name = '_noname';
            document.studySiteForm._action.value = action;
            document.studySiteForm._selected.value = selected;

            // need to disable validations while removing
//            name = 'studySites[' + selected + '].site';
//            $(name).className = 'none';
//            status = 'studySites[' + selected + '].statusCode';
//            $(status).className = 'none';
//            date = 'studySites[' + selected + '].irbApprovalDate';
//            $(date).className = 'none';

            document.studySiteForm.submit();
        }
    </script>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}" formName="studySiteForm">
<jsp:attribute name="singleFields">
   <div>
       <input type="hidden" name="_action" value="">
       <input type="hidden" name="_selected" value="">
   </div>

<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <p id="instructions">
                Add StudySites associated with the Study
                <a href="javascript:fireAction('addSite','0');"><img
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
                    <tr>
                        <td class="alt">
                            <form:select path="studySites[${status.index}].site" cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                                <form:options items="${healthCareSitesRefData}" itemLabel="name" itemValue="id"/>
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
                        <td class="specalt" class="specalt"><a
                                href="javascript:fireAction('removeSite',${status.index});"><img
                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>

                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
    	</jsp:attribute>
</tags:tabForm>
<!-- MAIN CONTENT ENDS HERE -->

</body>
</html>