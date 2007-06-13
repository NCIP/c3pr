<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head/>

<body>
<chrome:box title="Confirmation">
    <form:form>

        <chrome:division id="single-fields">

            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right" colspan="2">
                        <h3><font color="green">Study Created Successfully</font></h3>
                    </td>
                </tr>
                <tr>
                    <td align="right"><b>Short Title:&nbsp;</b></td>
                    <td>${command.trimmedShortTitleText}</td>
                </tr>

                <tr>
                    <td align="right"><b>Sponsor Study Identifier:&nbsp;</b></td>
                    <td>${command.primaryIdentifier}</td>
                </tr>

            </table>

        </chrome:division>
    </form:form>
</chrome:box>
</body>
</html>
