<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Confirmation</title>
</head>
<body>
<chrome:box title="Organization Succesfully Created" autopad="true">

    <table width="100%" border="0">
        <tr>
            <td>
                <div class="content">
                    <div class="row">
                        <div class="label">Name:</div>
                        <div class="value">
                            <c:out value="${command.name}"/>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>

</chrome:box>

</body>
</html>
