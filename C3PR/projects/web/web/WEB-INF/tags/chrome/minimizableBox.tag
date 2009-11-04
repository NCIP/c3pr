<%-- TODO: support for inner tabs (needs uniform controller support first) --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@attribute name="title" required="true"%>
<%@attribute name="id" required="true"%>
<%@attribute name="url"%>
<%@attribute name="helpUrl"%>
<%@attribute name="display"%>
<%@attribute name="cssClass"%>
<%@attribute name="style"%>
<%@attribute name="isDeletable" description="Is the Box deletable" %>
<%@attribute name="onDelete" description="Action to call on Delete" %>

<script>
    function PanelCombo(element) {
        panelDiv = $(element+"-interior");
        imageId= element+'-image';
        imageSource=document.getElementById(imageId).src;
		if (panelDiv.style.display == 'none') {
            new Effect.OpenUp(panelDiv, arguments[1] || {});
            document.getElementById(imageId).src=imageSource.replace('minimize','maximize');
			document.getElementById(imageId).alt = ("Minimize");
			document.getElementById(element + "hr").style.display = "block";

        } else {
            new Effect.CloseDown(panelDiv, arguments[1] || {});
            document.getElementById(imageId).src=imageSource.replace('maximize','minimize');
			document.getElementById(imageId).alt = ("Maximize");
			if (!is_ie) {
				window.setTimeout(killHRonMin, 1000, element);
			} else {
				document.getElementById(element + "hr").style.display = "none"
				}
        }
    }
	function killHRonMin(element) {
		document.getElementById(element + "hr").style.display = "none"
	}
    Effect.OpenUp = function(element) {
        element = $(element);
        new Effect.BlindDown(element, arguments[1] || {});
    }

    Effect.CloseDown = function(element) {
        element = $(element);
        new Effect.BlindUp(element, arguments[1] || {});
    }

    Effect.Combo = function(element) {
        element = $(element);
        if (element.style.display == 'none') {
            new Effect.OpenUp(element, arguments[1] || {});
        } else {
            new Effect.CloseDown(element, arguments[1] || {});
        }
    }

    function onDelete(element) {
        panelDiv = $(element);
        new Effect.Squish(panelDiv, arguments[1] || {});
    }


</script>
<%-- If this attribute is true, the provided contents will be wrapped in a .content div.
     Use it if the box will only need one content div -- i.e., it doesn't contain any
     chrome:divisions with titles. --%>
<%@attribute name="autopad" required="false" %>
<div class="box ${cssClass}"
        <tags:attribute name="id" value="${id}"/> <tags:attribute name="style" value="${style}"/>>

    <!-- header -->
    <div class="header"><div class="background-L"><div class="background-R">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"><tr>
            <td>
                <h2>${title}</h2>
            </td>
            <td align="right" style="padding-right: 5px;">
                <div id="${id }-image-div">
                    <a href="javascript:
		<c:choose>
			<c:when test="${!empty url}">document.location='${url}'</c:when>
			<c:otherwise>PanelCombo('${id }');</c:otherwise>
		</c:choose>
				"><img id="${id }-image" src="<tags:imageUrl name="${display=='false'||!empty url?'minimize':'maximize' }.gif"/>" border="0" alt="Minimize" style="margin-right:10px; margin-top:7px;"></a>
                    <c:if test="${isDeletable}">
                    	<c:if test="${empty onDelete}"><c:set var="onDelete" value="onDelete('${id }')"></c:set></c:if>
                        <a href="javascript:${onDelete}"><img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Close"></a>&nbsp;&nbsp;
                    </c:if>
                </div>
            </td>
        </tr>
        <tr>
		  <td colspan="3">
			<div id="${id}hr" class="hr" style="display:block;"/>
		  </td>
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