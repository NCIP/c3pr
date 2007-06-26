<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head>
<!--empty head-->
</head>

<body>
    <chrome:box title="Confirmation">

          <table width="100%" border="0">
              <tr><td>
            <div class="content">
                <div class="row">
                    <div<h1>Study Succesfully Created</h1></div>
                </div>
                <div class="row">
                    <div class="label">Short Title:</div>
                    <div class="value">${command.trimmedShortTitleText}</div>
                </div>
                <div class="row">
                    <div class="label">Primary Identifier:</div>
                    <div class="value">${command.primaryIdentifier}</div>
                </div>
            </div>
            </td></tr>
         </table>

    </chrome:box>

</body>
</html>
