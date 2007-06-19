<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head/>

<body>
<chrome:box title="Confirmation">
    <form:form>

        <chrome:division id="single-fields">
            <div class="content">
                <div class="row">
                    <div>Study Succesfully Created</div>
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

        </chrome:division>
    </form:form>
</chrome:box>
</body>
</html>
