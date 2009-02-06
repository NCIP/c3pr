<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="id" required="false" %>
<%@ attribute name="onClick" required="false" %>
<%@ attribute name="icon" required="false" %>
<%@ attribute name="value" %>
<%@ attribute name="href" required="false" %>
<%@ attribute name="cssClass" required="false" %>
<%@ attribute name="type" required="true" %>
<c:if test="${type=='anchor'}">
    <a class="c3pr-button ${cssClass}"
        <c:if test="${href}">
            href="${href}"
        </c:if>
        id="${id}" 
        <c:if test="${onClick}">
            href="${onClick}"
        </c:if>>
        <c:if test="${icon=='save'||icon=='Save'}">
            <img src="<tags:imageUrl name="icons/disk_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='Back'||icon=='back'}">
            <img src="<tags:imageUrl name="icons/back_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='Save &amp; Back'}">
            <img src="<tags:imageUrl name="icons/saveback_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='add'}">
            <img src="<tags:imageUrl name="icons/add_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='page'}">
            <img src="<tags:imageUrl name="icons/page_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='window'}">
            <img src="<tags:imageUrl name="icons/window_icon.png"/>" alt="" />
        </c:if>${value}
        <c:if test="${icon=='Save &amp; Continue'}">
            <img src="<tags:imageUrl name="icons/savecontinue_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='Continue'}">
            <img src="<tags:imageUrl name="icons/continue_icon.png"/>" alt="" />
        </c:if>
        <div class="right">
        </div>
    </a>
</c:if>
<c:if test="${type=='button'}">
    <button class="c3pr-button ${cssClass}" type="submit"
        <c:if test="${href}">
            href="${href}"
        </c:if>
        id="${id}" 
        <c:if test="${onClick}">
            href="${onClick}"
        </c:if>>
        <c:if test="${icon=='save'||icon=='Save'}">
            <img src="<tags:imageUrl name="icons/disk_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='Back'}">
            <img src="<tags:imageUrl name="icons/back_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='Save &amp; Back'}">
            <img src="<tags:imageUrl name="icons/saveback_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='add'}">
            <img src="<tags:imageUrl name="icons/add_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='page'}">
            <img src="<tags:imageUrl name="icons/page_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='window'}">
            <img src="<tags:imageUrl name="icons/window_icon.png"/>" alt="" />
        </c:if>${value}
        <c:if test="${icon=='Save &amp; Continue'}">
            <img src="<tags:imageUrl name="icons/savecontinue_icon.png"/>" alt="" />
        </c:if>
        <c:if test="${icon=='Continue'}">
            <img src="<tags:imageUrl name="icons/continue_icon.png"/>" alt="" />
        </c:if>
        <div class="right">
        </div>
    </button>
</c:if>
