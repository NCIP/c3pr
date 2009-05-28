<script type="text/javascript">
new Element.descendants("main${hasSummary ? '' : '-no-summary'}-pane").each(function(e){
			e.disabled="true";
			el.style.opacity='0.99';
			(e.type=="button"||e.type=="submit"||e.type=="reset")?e.style.backgroundColor="grey":null;
			if(el.tagName == 'A'){
				el.href="#";
			}
			if(e.tagName=="IMG"){
				e.onclick="function nu(){return}";
				parentElement=e.parentNode;
				if(parentElement.tagName=="A"){
					parentElement.href="#";
				}
			}
		}
		);
//this tag is still not perfect, links and calendar are still clickable.
</script>
