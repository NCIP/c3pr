 <%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head>
<!--empty head-->
</head>


<body>
<table width="100%">
<tr><td>

<chrome:box title="Study Summary">

<chrome:division id="study-details" title="Study Details">
    <table class="tablecontent">
        <tr>
            <td class="alt" align="left"><b>Short Title:</b></td>
            <td class="alt" align="left">${command.shortTitleText}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Sponsor Study Identifier:</b></td>
            <td class="alt" align="left">${command.primaryIdentifier}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Target Accrual Number:</b></td>
            <td class="alt" align="left">${command.targetAccrualNumber}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Status:</b></td>
            <td class="alt" align="left">${command.status}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Phase:</b></td>
            <td class="alt" align="left">${command.phaseCode}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Type:</b></td>
            <td class="alt" align="left">${command.type}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Phase:</b></td>
            <td class="alt" align="left">${command.phaseCode}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Multi Institution:</b></td>
            <td class="alt" align="left">${command.multiInstitutionIndicator}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Blinded:</b></td>
            <td class="alt" align="left"> ${command.randomizedIndicator}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Randomized:</b></td>
            <td class="alt" align="left">${command.blindedIndicator}</td>
        </tr>
    </table>
</chrome:division>

<chrome:division title="Identifiers">
    <table class="tablecontent">
        <tr>
            <th scope="col" align="left">Source</th>
            <th scope="col" align="left">Type</th>
            <th scope="col" align="left">Identifier</th>
        </tr>
        <c:forEach items="${command.identifiers}" var="identifier">
            <tr class="results">
                <td class="alt" align="left">${identifier.source}</td>
                <td class="alt" align="left">${identifier.type}</td>
                <td class="alt" align="left">${identifier.value}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Sites">
    <table class="tablecontent">
        <tr>
            <th scope="col" align="left">Study Site</th>
            <th scope="col" align="left">Status</th>
            <th scope="col" align="left">Role</th>
            <th scope="col" align="left">Start Date</th>
            <th scope="col" align="left">IRB Approval Date</th>
        </tr>
        <c:forEach items="${command.studySites}" var="studySite">
            <tr class="results">
                <td class="alt" align="left">${studySite.site.name}</td>
                <td class="alt" align="left">${studySite.statusCode}</td>
                <td class="alt" align="left">${studySite.roleCode}</td>
                <td class="alt" align="left">${studySite.startDateStr}</td>
                <td class="alt" align="left">${studySite.irbApprovalDateStr}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Investigators">
    <table class="tablecontent">
        <tr>
            <th scope="col" align="left">Investigator</th>
            <th scope="col" align="left">Role</th>
            <th scope="col" align="left">Status</th>
        </tr>
        <c:forEach items="${command.studySites}" var="studySite" varStatus="status">
            <c:forEach items="${studySite.studyInvestigators}" var="studyInvestigator" varStatus="status">
                <tr class="results">
                    <td class="alt"
                        align="left">${studyInvestigator.healthcareSiteInvestigator.investigator.fullName}</td>
                    <td class="alt" align="left">${studyInvestigator.roleCode}</td>
                    <td class="alt" align="left">${studyInvestigator.statusCode}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Personnel">
    <table class="tablecontent">
        <tr>
            <th scope="col" align="left">Name</th>
            <th scope="col" align="left">Role</th>
            <th scope="col" align="left">Status</th>
        </tr>
        <c:forEach items="${command.studySites}" var="studySite" varStatus="status">
            <c:forEach items="${studySite.studyPersonnels}" var="studyPersonnel" varStatus="status">
                <tr class="results">
                    <td class="alt">${studyPersonnel.researchStaff.fullName}</td>
                    <td class="alt">${studyPersonnel.roleCode}</td>
                    <td class="alt">${studyPersonnel.statusCode}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Stratifications">
    <table class="tablecontent">
        <tr>
            <th scope="col" align="left"><b>Strata</b></th>
            <b></td>
        </tr>
        <c:forEach items="${command.stratificationCriteria}" var="strat">
            <tr>
                <td class="alt">${strat.questionText}</td>
                <td class="alt">
                    <table border="0" cellspacing="0" cellpadding="0" id="mytable">
                        <c:forEach items="${strat.permissibleAnswers}" var="ans">
                            <tr>
                                <td class="alt" align="left">${ans.permissibleAnswer}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Diseases">
    <table class="tablecontent">
        <tr>
            <th scope="col" align="left"><b>Disease Term</b></th>
            <th scope="col" align="left"><b>Primary</b></th>
        </tr>
        <c:forEach items="${command.studyDiseases}" var="studyDisease" varStatus="status">
            <tr class="results">
                <td class="alt">${studyDisease.diseaseTerm.ctepTerm}</td>
                <td class="alt">${studyDiseases[status.index].leadDisease}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Study Design">
    <table class="tablecontent">
        <tr>
            <th scope="col" align="left"><b>Epochs</b></th>
            <th scope="col" align="left"><b>Arms</b>
                <b>&nbsp;&nbsp;&nbsp;&nbsp;Target Accrual No</b></th>
        </tr>
        <c:forEach items="${command.epochs}" var="epoch">
            <tr>
                <td class="alt">${epoch.name}</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0" id="mytable">
                        <tr>
                            <c:forEach items="${epoch.arms}" var="arm">
                        <tr>
                            <td class="alt" align="left">${arm.name}</td>
                            <td class="alt" align="left">${arm.targetAccrualNumber}</td>
                        </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </c:forEach>
    </table>

</chrome:division>
   </chrome:box>

</td></tr>
</table>

</body>
</html>