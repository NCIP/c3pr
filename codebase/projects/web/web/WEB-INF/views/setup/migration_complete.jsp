<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>    
<c:url value="/pages/dashboard" var="login"/>
<div id="flash-message" class="${imageAndMessageList[0]}">
	<img src="<tags:imageUrl name="info" />" style="vertical-align:top;" /> 
	Migration complete, you will now be redirected to the <a href="<c:url value="/pages/dashboard"/>">dashboard</a> page in <span id="seconds"></span> seconds.	
</div>
<script>
	count = 5;
	function delayer(){
		Element.update('seconds',count);
		if(count==0){
			window.location = "<c:url value="/"/>";
		}else{
			count--;
		}
		setTimeout('delayer()', 1000);
	}
	delayer();
</script>
