<script>
function accessApp(url, app, targetWindow){
	if(url=="")
		document.form.action="/"+app;
	else
		document.form.action=url+"/"+app;
		
	document.form.target=targetWindow;
	document.form.submit();
}
</script>
<html>
<table width="60%" border="0" cellspacing="0" cellpadding="0"
	id="details">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td class="label" align="left"><a
			href="javascript:accessApp('${adverseEventUrl}','{adverseEventApp}','_caaers');">
		<b>Adverse Event Reporting</a> </b></td>
	</tr>
	<tr>
		<td class="label" align="left"><a
			href="javascript:accessApp('${studyCalendarUrl}','${studyCalendarApp}','_psc');">
		<b>Study Calendar</a></b></td>
	</tr>
	<tr>
		<td class="label" align="left"><a
			href="javascript:accessApp('${c3dUrl}','${c3dApp}','_c3d');">
		<b>Clinical Database</a></b></td>
	</tr>
</table>
</html>