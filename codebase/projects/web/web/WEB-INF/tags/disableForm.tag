<script type="text/javascript">
new Element.descendants("main${hasSummary ? '' : '-no-summary'}-pane").each(function(e){
			e.disabled="true";
			e.style.opacity='0.95';
			(e.type=="button"||e.type=="submit"||e.type=="reset")?e.style.backgroundColor="grey":null;
			if(e.tagName == 'A'){
				e.href="#";
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
