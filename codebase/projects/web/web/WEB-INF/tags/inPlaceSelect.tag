<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="path" required="true"%>
<%@attribute name="id" required="true"%>
<%@attribute name="commanSepOptVal" required="true"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@attribute name="value" required="true"%>
<%@attribute name="pathToGet"%>
<%@attribute name="validations"%>

<c:set var="required" value="${! empty required?required:'false'}"></c:set>

<span id="${path}-id">${value}</span>
<a href="#"> <img  id="img-${id}" src="<chrome:imageUrl name='../../templates/mocha/images/controlPanel/controlPanel_pencil.png' />"></a>
<script type="text/javascript">
	var cheatVar;
	var editor_${id}=new Ajax.InPlaceCollectionEditor('${path}-id', 
													document.URL, {collection: ${commanSepOptVal}, 
													validations:'${validations}', 
													ajaxOptions:{evalScripts:true}, 
													cancelLink:false, cancelButton:true, okText:'ok', cancelText:'cancel',
													externalControlsOnly:true, externalControl:'img-${id}',
													callback: function(form, value) {
			  											cheatVar = value;
												 		return '_asynchronous=true&_asyncMethodName=doInPlaceEdit&_ajaxInPlaceEditParam=${path}&_pathToGet=${pathToGet}&${path}=' + escape(value);
												  	}
										} );    						
</script>