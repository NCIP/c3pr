<%@ include file="taglibs.jsp"%>    
<c:url value="/pages/dashboard" var="login"/>
<div id="flash-message" class="${imageAndMessageList[0]}">
	<img src="<tags:imageUrl name="info" />" style="vertical-align:top;" /> 
	Setup complete, you should now be able to <a href="${login}">login</a>.	
</div>
