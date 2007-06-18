<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head>
<!--empty head-->
</head>

<body>
    <p class="instructions">Study Succesfully Created.</p>

        <chrome:division title="Confirmation" id="confirm">
            <div class="content">
                <div class="row">
                    <div class="label">Short Title:</div>
                    <div class="value">${command.trimmedShortTitleText}</div>
                </div>
                <div class="row">
                    <div class="label">Primary Identifier:</div>
                    <div class="value">${command.primaryIdentifier}</div>
                </div>
            </div>

        </chrome:division>
</body>
</html>
