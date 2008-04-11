<script>
if(${alertForCeiling}){
	alert("Accrual Ceiling for this Epoch alreay reached. Cannot enroll any more subjects on this Epoch.");
	displayEpochMessage("Select an epoch", false);
}else{
	afterCheckEpochAccrual();
}
</script>