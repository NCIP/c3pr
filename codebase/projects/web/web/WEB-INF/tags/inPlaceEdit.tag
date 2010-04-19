<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="path" required="true"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@attribute name="id" required="true"%>
<%@attribute name="value" required="true"%>
<%@attribute name="pathToGet"%>
<%@attribute name="validations"%>
<%@attribute name="autocompleterJSVar"%>
<c:if test="${!empty autocompleterJSVar}">
	<c:set var="autocompleterJS" value="autocompleter:${autocompleterJSVar}"/>
</c:if>
<c:set var="required" value="${! empty required?required:'false'}"></c:set>
<span id="${path}-id">${value}</span>
<a href="#"> <img  id="img-${id}" src="<chrome:imageUrl name='../../templates/mocha/images/controlPanel/controlPanel_pencil.png' />"></a>
<script type="text/javascript">

var editor_${id}=new Ajax.InPlaceEditor('${path}-id', 
										document.URL, { ${autocompleterJS}${!empty autocompleterJS?',':''} 
														validations:'${validations}' , 
														externalControlsOnly:true, externalControl:'img-${id}',
														cancelLink:false, cancelButton:true, okText:'ok', cancelText:'cancel',
 														callback: function(form, value) {
													 		return '_asynchronous=true&_asyncMethodName=doInPlaceEdit&_ajaxInPlaceEditParam=${path}&_pathToGet=${pathToGet}&${path}=' + escape(value);
													  	}
										  });
</script>

