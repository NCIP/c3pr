<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!--  Ajax loading indicator -->
<div id="ajax-loading-indictor" style="z-index:200;">
    <div id="spinning-logo">
        <img src="<tags:imageUrl name="big-rotating-SB-logo.gif"/>" alt="ajax activity indicator" style="margin:25px auto 0;"/>
    </div>
</div>
<script>
Insertion.Top($$('body')[0],'<div id="overlay_modal" class="overlay_mac_os_x" style="position: absolute; top: 0pt; left: 0pt; z-index: 101; width: 100%; height: 1075px; opacity: 0.6;"/>');
</script>
<!--  Ajax loading indicator -->
