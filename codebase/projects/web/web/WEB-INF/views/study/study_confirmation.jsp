<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head>
<!--empty head-->
</head>

<body>
    <chrome:box title="Study Succesfully Created" autopad="true">

          <table width="100%" border="0">
              <tr><td>
            <div class="content">
                <div class="row">
                    <div class="label">Short Title:</div>
                    <div class="value">${command.trimmedShortTitleText}</div>
                </div>
                <div class="row">
                    <div class="label">Coordinating Center Study Identifier:</div>
                    <div class="value">${command.organizationAssignedIdentifiers[0].value}</div>
                </div>
                <div class="row">
                    <div class="label">Coordinating Center Study Status:</div>
                    <div class="value">${command.coordinatingCenterStudyStatus.code}</div>
                </div>
            </div>
            </td></tr>
         </table>



    </chrome:box>

</body>
</html>
