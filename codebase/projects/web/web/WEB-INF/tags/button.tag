<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="id" required="false" %>
<%@ attribute name="onclick" required="false" %>
<%@ attribute name="icon" required="false" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="href" required="false" %>
<%@ attribute name="cssClass" required="false" %>
<%@ attribute name="markupWithTag" required="true" %>
<%@ attribute name="color" required="true" %>
<%@ attribute name="size" required="false" %>
<%@ attribute name="type" required="false" %>

<<c:if test="${markupWithTag=='anchor'}">a</c:if><c:if test="${markupWithTag=='button'}">button</c:if>
	class="c3pr-button ${color} ${size} ${cssClass}<c:if test="${empty icon}"> no-icon</c:if>"
	<c:if test="${not empty type && markupWithTag=='button'}">
		type="${type}"
	</c:if>
	<c:if test="${not empty href}">
    	href="${href}"
	</c:if>
	<c:if test="${not empty id}">
		id="${id}" 
	</c:if>
	<c:if test="${not empty onclick}">
    	onclick="${onclick}"
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
		</c:if>
		<c:if test="${icon=='check'}">
    		<img src="<tags:imageUrl name="icons/check_icon.png"/>" alt="" />
		</c:if>
		<c:if test="${icon=='subject'}">
    		<img src="<tags:imageUrl name="icons/subject_icon.png"/>" alt="" />
		</c:if>
		<c:if test="${icon=='x'}">
    		<img src="<tags:imageUrl name="icons/x_icon.png"/>" alt="" />
		</c:if>
		<c:if test="${icon=='search'}">
			<c:choose>
				<c:when test="${size=='small'}">
    				<img src="<tags:imageUrl name="icons/search_small_icon.png"/>" alt="" />
				</c:when>
				<c:otherwise>
					<img src="<tags:imageUrl name="icons/search.png"/>" alt="" />
				</c:otherwise>
			</c:choose>
		</c:if>
		
		${value}
		
		<c:if test="${icon=='Save &amp; Continue'}">
    		<img src="<tags:imageUrl name="icons/savecontinue_icon.png"/>" alt="" />
		</c:if>
		<c:if test="${icon=='Continue'}">
    		<img src="<tags:imageUrl name="icons/continue_icon.png"/>" alt="" />
		</c:if>

		<div class="right"></div>

</<c:if test="${markupWithTag=='anchor'}">a</c:if><c:if test="${markupWithTag=='button'}">button</c:if>>