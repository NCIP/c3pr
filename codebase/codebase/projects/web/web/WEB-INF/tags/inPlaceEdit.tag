<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="path" required="true"%>
<%@attribute name="value" required="true"%>
<%@attribute name="pathToGet"%>
<%@attribute name="validations"%>
<c:set var="required" value="${! empty required?required:'false'}"></c:set>
<span id="${path}-id">${value}</span>
<script type="text/javascript">
	 var editor_${path}=new Ajax.InPlaceEditor('${path}-id', document.URL, { validations:'${validations}', cancelLink:false, cancelButton:true, okText:'ok', cancelText:'cancel',
	 														 callback: function(form, value) {
	 														 		return '_asynchronous=true&_asyncMethodName=doInPlaceEdit&_ajaxInPlaceEditParam=${path}&_pathToGet=${pathToGet}&${path}=' + escape(value);
	 														  	}
	 														  });
</script>