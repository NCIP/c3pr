<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="path" required="true"%>
<%@attribute name="id" required="true"%>
<%@attribute name="commanSepOptVal" required="true"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@attribute name="value" required="true"%>
<%@attribute name="pathToGet"%>
<%@attribute name="validations"%>
<%@attribute name="onComplete"%>
<c:set var="callbackOpts" value=""></c:set>
<c:if test="${! empty onComplete}">
	<c:set var="callbackOpts" value="${callbackOpts}onComplete:${onComplete },"></c:set>
</c:if>
<%@attribute name="disable" type="java.lang.Boolean"%>


<c:set var="required" value="${! empty required?required:'false'}"></c:set>

<span id="${path}-id">${value}</span>
<a href="#" style="${disable?'display:none':''}"> <img  id="img-${id}" src="<chrome:imageUrl name='../../templates/mocha/images/controlPanel/controlPanel_pencil.png' />"></a>
<script type="text/javascript">
	var cheatVar;
	var editor_${id}=new Ajax.InPlaceCollectionEditor('${path}-id', 
													document.URL, {collection: ${commanSepOptVal}, 
													validations:'${validations}', 
													ajaxOptions:{evalScripts:true}, 
													${callbackOpts}
													cancelLink:false, cancelButton:true, okText:'ok', cancelText:'cancel',
													externalControlOnly:true, externalControl:'img-${id}',
													callback: function(form, value) {
			  											cheatVar = value;
												 		return '_asynchronous=true&_asyncMethodName=doInPlaceEdit&_ajaxInPlaceEditParam=${path}&_pathToGet=${pathToGet}&${path}=' + escape(value);
												  	}
										} );    						
</script>