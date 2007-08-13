<%@attribute name="path" required="true"%>
<%@attribute name="value" required="true"%>
<span id="${path}-id">${value}</span>
<script type="text/javascript">
	 var editor_${path}=new Ajax.InPlaceEditor('${path}-id', document.URL, {
	 														 callback: function(form, value) {
	 														 		return '_asynchronous=true&_asyncMethodName=doInPlaceEdit&_ajaxInPlaceEditParam=${path}&${path}=' + escape(value);
	 														  	}
	 														  });
</script>