<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<script>
if(${alertForCeiling}){
	alert("Accrual Ceiling for this Epoch alreay reached. Cannot enroll any more subjects on this Epoch.");
	displayEpochMessage("Select an epoch", false);
}else{
	afterCheckEpochAccrual();
}
</script>
