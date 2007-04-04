<%@attribute name="effectsArea" required="true"%>
<%@attribute name="imgExpandArea" required="true"%>

<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="title"%>
<%@attribute name="id"%>

<div id="abs">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="division" <c:if test="${not empty id}">id="${id}"</c:if>>
    <tr>
        <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabs">
            <div id="tabdivisionEffect">
            <tr onClick()="Effect.Combo('${effectsArea}','${imgExpandArea}','tabdivisionEffect')">
                <td width="100%" class="tabDisplay">
                    <c:if test="${not empty title}">
                        <img src="<tags:imageUrl name="tab3_h_L.gif"/>" width="1" height="16" align="absmiddle"><span class="current">
                        <img id="expandIncl"
					src="images/b-plus.gif">${title}
                        </span><img src="<tags:imageUrl name="tab3_h_R.gif"/>" width="7" height="16" align="absmiddle">
                    </c:if>
                    <img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="16" align="absmiddle">
                </td>
                <td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
            </tr>
            </div>
            <tr>
                <td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
                    width="1" height="7"></td>
            </tr>
            <tr>
                <td colspan="2" class="contentL division-content">
                    <jsp:doBody/>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="content_B"><img src="<tags:imageUrl name="content_BL.gif"/>" align="left" hspace="0"><img src="<tags:imageUrl name="content_BR.gif"/>" align="right" hspace="0"></td>
            </tr>
        </table>
        </td>
    </tr>
</table>
</div>