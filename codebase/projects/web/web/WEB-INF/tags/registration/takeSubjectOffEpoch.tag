<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="studysiteTags" tagdir="/WEB-INF/tags/studysite" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table border="0" id="table1" cellspacing="10" width="100%">
	<tr>
	<td valign="top" width="45%">
		Select reason(s)
		<div id="offStudySection-reasons" style="display:none;">
		<select multiple size="20" style="width:400px">
		    <option disabled="disabled">-------------------------- Off Study Reasons --------------------------</option>
		    <option>Patient Noncompliance</option>
		    <option>Alternative treatment</option>
		    <option>Disease progression before treatment</option>
		    <option>Study complete</option>
		    <option>Death on study</option>
		    <option>Late adverse event / side effect</option>
		    <option>Cytogenetic resistance</option>
		    <option>Followup period complete</option>
		    <option>Ineligible</option>
		    <option>Lost to followup</option>
		    <option>Death during followup period</option>
		    <option>Not treated</option>
		    <option>Other</option>
		    <option>Disease progression</option>
		    <option>Refused further treatment</option>
		    <option>Complicating disease</option>
		    <option>Toxicity</option>
		    <option>Not treated - other reasons</option>
		    <option>Protocol violation</option>
		    <option>Refused further followup</option>
		    <%--<option disabled="disabled">-------------------------- Off Treatment Reasons ----------------------</option>
		    <option>Alternative treatment</option>
		    <option>Disease progression before treatment</option>
		    <option>Study complete</option>
		    <option>Death on study</option>
		    <option>Began Protocol-Specified Follow-up</option>
		    <option>Cytogenetic resistance</option>
		    <option>Unable to schedule visit </option>
		    <option>Ineligible</option>
		    <option>Lost to followup</option>
		    <option>PI Discretion</option>
		    <option>Other</option>
		    <option>Disease progression</option>
		    <option>Treatment period completed</option>
		    <option>Refused further treatment</option>
		    <option>Complicating disease</option>
		    <option>Toxicity</option>
		    <option>Not treated - other reasons</option>
		    <option>Protocol violation</option>
		    <option>Declined to participate</option>
		    <option>Treatment complete but patient refused followup</option> --%>
		    <option disabled="disabled">-------------------------- Off Screening Reasons -----------------------</option>
		    <option>Alternative treatment</option>
		    <option>Cytogenetic resistance</option>
		    <option>Unable to schedule visit </option>
		    <option>Ineligible</option>
		    <option>PI Discretion</option>
		    <option>Other</option>
		    <option>Disease progression</option>
		    <option>Complicating disease</option>
		    <option>Toxicity</option>
		    <option>Protocol violation</option>
		    <option>Declined to participate</option>
		    <option>Treatment complete but patient refused followup</option>
		    <%-- <option disabled="disabled">-------------------------- Other Reasons ------------------------------</option>
		    <option>Reservation slot unavailable</option> --%>
		</select>
		</div>
		<div id="offEpochSection-reasons" style="display:none;">
		<select multiple size="20" style="width:400px">
		    <%--<option disabled="disabled">-------------------------- Off Study Reasons --------------------------</option>
		    <option>Patient Noncompliance</option>
		    <option>Alternative treatment</option>
		    <option>Disease progression before treatment</option>
		    <option>Study complete</option>
		    <option>Death on study</option>
		    <option>Late adverse event / side effect</option>
		    <option>Cytogenetic resistance</option>
		    <option>Followup period complete</option>
		    <option>Ineligible</option>
		    <option>Lost to followup</option>
		    <option>Death during followup period</option>
		    <option>Not treated</option>
		    <option>Other</option>
		    <option>Disease progression</option>
		    <option>Refused further treatment</option>
		    <option>Complicating disease</option>
		    <option>Toxicity</option>
		    <option>Not treated - other reasons</option>
		    <option>Protocol violation</option>
		    <option>Refused further followup</option>
		    <option disabled="disabled">-------------------------- Off Treatment Reasons ----------------------</option>
		    <option>Alternative treatment</option>
		    <option>Disease progression before treatment</option>
		    <option>Study complete</option>
		    <option>Death on study</option>
		    <option>Began Protocol-Specified Follow-up</option>
		    <option>Cytogenetic resistance</option>
		    <option>Unable to schedule visit </option>
		    <option>Ineligible</option>
		    <option>Lost to followup</option>
		    <option>PI Discretion</option>
		    <option>Other</option>
		    <option>Disease progression</option>
		    <option>Treatment period completed</option>
		    <option>Refused further treatment</option>
		    <option>Complicating disease</option>
		    <option>Toxicity</option>
		    <option>Not treated - other reasons</option>
		    <option>Protocol violation</option>
		    <option>Declined to participate</option>
		    <option>Treatment complete but patient refused followup</option> --%>
		    <option disabled="disabled">-------------------------- Off Screening Reasons -----------------------</option>
		    <option>Alternative treatment</option>
		    <option>Cytogenetic resistance</option>
		    <option>Unable to schedule visit </option>
		    <option>Ineligible</option>
		    <option>PI Discretion</option>
		    <option>Other</option>
		    <option>Disease progression</option>
		    <option>Complicating disease</option>
		    <option>Toxicity</option>
		    <option>Protocol violation</option>
		    <option>Declined to participate</option>
		    <option>Treatment complete but patient refused followup</option>
		    <%-- <option disabled="disabled">-------------------------- Other Reasons ------------------------------</option>
		    <option>Reservation slot unavailable</option> --%>
		</select>
		</div>
	</td>
	<td valign="middle">
		<tags:button type="button" icon="continue" size="small" color="blue" value="Add" id="add" onclick="document.location='changeEpoch?'+document.URL.split('?')[1].split('=')[0]+'=resultReasonTable'"/>
    </td>
    <td width="45%" valign="top">
    	Selected reason(s)
    	<div id="defReasonTable" style="display:none;">
   		<table border="1" class="tablecontent" width="100%">
            <tr>
                <th width="30%">Reason</th>
                <th width="65%">Description</th>
                <th width="5%"></th>
            </tr>
            <tr><td colspan="3">No reasons selected</td></tr>
        </table>
        </div>
        <div id="resultReasonTable" style="display:none;">
   		<table border="1" class="tablecontent" width="100%">
            <tr>
                <th width="30%">Reason</th>
                <th width="65%">Description</th>
                <th width="5%"></th>
            </tr>
            <tr>
            	<td>Ineligible</td>
                <td><textarea rows="3" cols="30"></textarea></td>
                <td class="alt"><img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="remove"></td>
            </tr>
            <tr>
            	<td>Disease progression</td>
                <td><textarea rows="3" cols="30"></textarea></td>
                <td class="alt"><img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="remove"></td>
            </tr>
            <tr>
            	<td>Complicating disease</td>
                <td><textarea rows="3" cols="30"></textarea></td>
                <td class="alt"><img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="remove"></td>
            </tr>
            <tr>
            	<td>Other</td>
                <td><textarea rows="3" cols="30"></textarea></td>
                <td class="alt"><img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="remove"></td>
            </tr>
            </tr>
        </table>
        </div>
    </td>
    </tr>
</table>
