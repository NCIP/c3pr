<script type="text/javascript">
new Element.descendants("main${hasSummary ? '' : '-no-summary'}-pane").each(function(e){
																				e.disabled="true";
																				(e.type=="button"||e.type=="submit"||e.type=="reset")?e.style.backgroundColor="grey":null;
																			}
																			);
</script>
