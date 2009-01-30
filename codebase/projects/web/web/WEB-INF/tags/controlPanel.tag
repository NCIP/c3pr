<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="controlPanel">
    <table style="border-collapse:collapse; display:inline;">
        <tr>
            <td id="header" colspan="3">
                <div id="title"></div>
                <div id="right"></div>
            </td>
        </tr>
        <tr>
            <td id="bodyleft"></td>
            <td id="body">
                <jsp:doBody/>
            </td>
            <td id="bodyright"></td>
        </tr>
        <tr>
            <td id="footer" colspan="3">
                <div id="left"></div>
                <div id="right"></div>
            </td>
        </tr>
    </table>
</div>
