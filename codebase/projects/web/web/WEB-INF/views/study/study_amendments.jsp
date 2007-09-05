<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<head>
<tags:includeScriptaculous />
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
              
var studyAmendmentsRowInserterProps = {
    add_row_division_id: "studyAmendments", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row-studyAmendment",
    initialIndex: ${fn:length(command.studyAmendments)},                            /* this is the initial count of the rows when the page is loaded  */
    path: "studyAmendments" ,                              /* this is the path of the collection that holds the rows  */
    postProcessRowInsertion: function(object){
        inputDateElementLocal="studyAmendments["+object.localIndex+"].amendmentDate";
        inputDateElementLink="studyAmendments["+object.localIndex+"].amendmentDate-calbutton";
        Calendar.setup(
        {
            inputField  : inputDateElementLocal,         // ID of the input field
            ifFormat    : "%m/%d/%Y",    // the date format
            button      : inputDateElementLink       // ID of the button
        }
                );
        inputDateElementLocal="studyAmendments["+object.localIndex+"].irbApprovalDate";
        inputDateElementLink="studyAmendments["+object.localIndex+"].irbApprovalDate-calbutton";
        Calendar.setup(
        {
            inputField  : inputDateElementLocal,         // ID of the input field
            ifFormat    : "%m/%d/%Y",    // the date format
            button      : inputDateElementLink       // ID of the button
        }
                );
       
    },
};

RowManager.addRowInseter(studyAmendmentsRowInserterProps);
</script>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}"
	formName="studyAmendmentsForm">
	<jsp:attribute name="singleFields">
		<table width="100%">
			<tr>
				<td>
					<table id="studyAmendments" class="tablecontent">
						<tr>
							<th>Version #</th>
							<th>Date</th>
							<th><span class="required-indicator">IRB Approval Date</span></th>
							<th>Comments</th>
							<th></th>
						</tr>
						<c:forEach items="${command.studyAmendments}"
							varStatus="status">
							<tr id="studyAmendments-${status.index}">
								<td><form:input
									path="studyAmendments[${status.index}].amendmentVersion"
									cssClass="validate-notEmpty&&NUMERIC" size="5" /></td>
								<td><tags:dateInput path="studyAmendments[${status.index}].amendmentDate"/></td>
								<td><tags:dateInput path="studyAmendments[${status.index}].irbApprovalDate"/></td>
								<td><form:textarea path="studyAmendments[${status.index}].comments" rows="2" cols="40"/></td>
								<td><a href="javascript:RowManager.deleteRow(studyAmendmentsRowInserterProps,${status.index});"><img
									src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
							</tr>
						</c:forEach>
					</table>

					<br>
					<div align="right"><input id="addSystemIdentifier" type="button"
						value="Add Amendment"
						onclick="RowManager.addRow(studyAmendmentsRowInserterProps);" />
					</div>

				</td>
			</tr>
		</table>
	</jsp:attribute>

</tags:tabForm>

<div id="dummy-row-studyAmendment" style="display:none;">
<table>
	<tr>
		<td><input type="text" size="5" id="studyAmendments[PAGE.ROW.INDEX].amendmentVersion"
			name="studyAmendments[PAGE.ROW.INDEX].amendmentVersion" class="validate-notEmpty&&NUMERIC"
			 /></td>
		<td><input id="studyAmendments[PAGE.ROW.INDEX].amendmentDate"
                       name="studyAmendments[PAGE.ROW.INDEX].amendmentDate"
                       type="text"
                       class="date" />
                <a href="#" id="studyAmendments[PAGE.ROW.INDEX].amendmentDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
                </a></td>
		<td><input id="studyAmendments[PAGE.ROW.INDEX].irbApprovalDate"
                       name="studyAmendments[PAGE.ROW.INDEX].irbApprovalDate"
                       type="text"
                       class="validate-notEmpty date" />
                <a href="#" id="studyAmendments[PAGE.ROW.INDEX].irbApprovalDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
                </a></td>
		<td><textarea id="studyAmendments[PAGE.ROW.INDEX].comments"
			name="studyAmendments[PAGE.ROW.INDEX].comments" rows="2" cols="40"></textarea></td>
		<td><a
			href="javascript:RowManager.deleteRow(studyAmendmentsRowInserterProps,PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

</body>
</html>
