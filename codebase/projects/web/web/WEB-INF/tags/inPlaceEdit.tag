<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="path" required="true"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@attribute name="id" required="true"%>
<%@attribute name="value" required="true"%>
<%@attribute name="pathToGet"%>
<%@attribute name="validations"%>
<%@attribute name="onComplete"%>
<c:set var="callbackOpts" value=""></c:set>
<c:if test="${! empty onComplete}">
	<c:set var="callbackOpts" value="${callbackOpts}onComplete:${onComplete },"></c:set>
</c:if>
<%@attribute name="autocompleterJSVar"%>
<c:if test="${!empty autocompleterJSVar}">
	<c:set var="autocompleterJS" value="autocompleter:${autocompleterJSVar}"/>
</c:if>
<%@attribute name="disable" type="java.lang.Boolean"%>
<%@attribute name="displayValueIfSuccessful"%>

<c:set var="required" value="${! empty required?required:'false'}"></c:set>
<span id="${path}-id">${value}</span>
<a href="#" style="${disable?'display:none':''}"> <img  id="img-${id}" src="<chrome:imageUrl name='../../templates/mocha/images/controlPanel/controlPanel_pencil.png' />"></a>
<script type="text/javascript">

var editor_${id}=new Ajax.InPlaceEditor('${path}-id', 
										document.URL, { ${autocompleterJS}${!empty autocompleterJS?',':''} 
														validations:'${validations}' ,
														${callbackOpts}
														externalControlOnly:true, externalControl:'img-${id}',
														cancelLink:false, cancelButton:true, okText:'ok', cancelText:'cancel',
 														callback: function(form, value) {
													 		return '_asynchronous=true&_asyncMethodName=doInPlaceEdit&_ajaxInPlaceEditParam=${path}&_pathToGet=${pathToGet}&_displayValue=${displayValueIfSuccessful}&${path}=' + escape(value);
													  	}
										  });
</script>

