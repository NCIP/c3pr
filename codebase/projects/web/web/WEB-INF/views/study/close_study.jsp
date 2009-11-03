<%@ include file="taglibs.jsp"%>
<script>
	function takeCloseAction(){
		closeRadios=$$('.studyStatus');
		closeRadioChecked=false;
		for(radioIndex=0 ; radioIndex<closeRadios.length ; radioIndex++){
			if(closeRadios[radioIndex].checked==true){
				closeRadioChecked= true;
				break;
			}
		}
		if(closeRadioChecked){
			parent.closeStudy();
		}else{
			Element.show('invalidSave');
		}
	}
</script>
<chrome:box title="Close Study" id="closeStudyClass">
	<tags:instructions code="close_study" />
	<table>
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Closed_To_Accrual_And_Treatment" name="closeStatusRadioBtn" onclick="javascript:parent.setCloseStatus('Closed_To_Accrual_And_Treatment');"/>
			</td>
			<td align="left">Closed To Accrual And Treatment</td>
		</tr>		
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Closed_To_Accrual" name="closeStatusRadioBtn" onclick="javascript:parent.setCloseStatus('Closed_To_Accrual');"/>
			</td>
			<td align="left">Closed To Accrual</td>
		</tr>
		<c:if test="${closed == 'Temporarily_Close'}">
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Temporarily_Closed_To_Accrual_And_Treatment" name="closeStatusRadioBtn" onclick="javascript:parent.setCloseStatus('Temporarily_Closed_To_Accrual_And_Treatment');"/>
			</td>
			<td align="left">Temporarily Closed To Accrual And Treatment</td>
		</tr>
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Temporarily_Closed_To_Accrual" name="closeStatusRadioBtn" onclick="javascript:parent.setCloseStatus('Temporarily_Closed_To_Accrual');"/>
			</td>
			<td align="left">Temporarily Closed To Accrual</td>
		</tr>
		</c:if>
	</table>
	<div id="invalidSave" style="display: none">
		<img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="STUDY.INVALIDCLOSE.WARNING"/>
	</div>
</chrome:box>
<div class="flow-buttons">
   	<span class="next">
   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="Element.hide('invalidSave');closeWin.close();" />
		<tags:button type="button" color="green" icon="save" onclick="takeCloseAction();" value="Close Study" />
	</span>
</div>
