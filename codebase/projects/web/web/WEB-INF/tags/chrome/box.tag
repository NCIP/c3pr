<%-- TODO: support for inner tabs (needs uniform controller support first) --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@attribute name="title"%>
<%@attribute name="id"%>
<%@attribute name="cssClass"%>
<%@attribute name="style"%>
<%@attribute name="helpUrl"%>
<%@attribute name="htmlContent"%>
<%-- If this attribute is true, the provided contents will be wrapped in a .content div.
     Use it if the box will only need one content div -- i.e., it doesn't contain any
     chrome:divisions with titles. --%>
<%@attribute name="autopad" required="false" %>
<div class="box ${cssClass}" <tags:attribute name="id" value="${id}"/> <tags:attribute name="style" value="${style}"/>>
    <!-- header -->
    <div class="header"><div class="background-L"><div class="background-R">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td align="left"><h2>${title}</h2></td>
                <td style="padding:6px 8px 4px 8px;"></td>
                <td align="right">${htmlContent}&nbsp;&nbsp;&nbsp;&nbsp;</td>
            </tr>
        </table>
    </div></div></div>
    <!-- end header -->

    <!-- inner border -->
    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-BL"><div class="border-BR">
        <div class="interior">
            <c:if test="${autopad}"><div class="content"></c:if>
            <jsp:doBody/>
            <c:if test="${autopad}"></div></c:if>
        </div>
    </div></div></div></div></div></div>
    <!-- end inner border -->
</div>
<!-- end box -->