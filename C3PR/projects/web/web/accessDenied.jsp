<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head>

</head>
<body>

<chrome:box title="Access Denied" autopad="true">

    <div class="row">
        <div class="label">
             <ul class="errors">
            Access Denied:
                 </ul>
            </div>
        <div class="value">
                You do not have sufficient privileges to access this resource.
        </div>
    </div>
     <div class="row">
    <div class="value">
        <a href="<c:url value="/"/>">Return Home</a>
    </div>
         </div>

</chrome:box>

</body>

</html>