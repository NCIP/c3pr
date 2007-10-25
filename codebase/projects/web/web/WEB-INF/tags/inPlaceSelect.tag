<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="path" required="true"%>
<%@attribute name="commanSepOptVal" required="true"%>
<%@attribute name="value" required="true"%>
<%@attribute name="pathToGet"%>
<%@attribute name="required"%>

<c:set var="required" value="${! empty required?required:'false'}"></c:set>

<span id="${path}-id">${value}</span>
<script type="text/javascript">
	var cheatVar;
	var editor_${path}=new Ajax.InPlaceCollectionEditor('${path}-id', document.URL,
								{collection: ${commanSepOptVal}, requiredIndicator:${required},
			  						callback: function(form, value) {
			  									cheatVar = value;
	 														 		return '_asynchronous=true&_asyncMethodName=doInPlaceEdit&_ajaxInPlaceEditParam=${path}&_pathToGet=${pathToGet}&${path}=' + escape(value);
	 														  	},
	 								onComplete: function(){window.location.reload( true );}
								}
							);    						
</script>