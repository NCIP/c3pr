<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
                    <div><h1>Subject Succesfully Created</h1></div>
                </div>
                <div class="row">
                    <div class="label">Full Name:</div>
                    <div class="value">${param.lastName} ${param.middleName} ${param.firstName} </div>
                </div>
                <div class="row">
                    <div class="label">Primary Identifier:</div>
                    <div class="value">${param.primaryIdentifier}</div>
                </div>
            </div>
            </td></tr>
         </table>

    </chrome:box>

</body>
</html>
