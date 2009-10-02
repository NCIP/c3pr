<%@ include file="taglibs.jsp"%>
<chrome:box title="Close Study" id="closeStudyClass">
	<tags:instructions code="close_study" />
	<table>
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Closed_To_Accrual_And_Treatment" name="closeStatus" onclick="javascript:parent.setCloseStatus('Closed_To_Accrual_And_Treatment');"/>
			</td>
			<td align="left">Closed To Accrual And Treatment</td>
		</tr>		
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Closed_To_Accrual" name="closeStatus" onclick="javascript:parent.setCloseStatus('Closed_To_Accrual');"/>
			</td>
			<td align="left">Closed To Accrual</td>
		</tr>
		<c:if test="${closed == 'Temporarily_Close'}">
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Temporarily_Closed_To_Accrual_And_Treatment" name="closeStatus" onclick="javascript:parent.setCloseStatus('Temporarily_Closed_To_Accrual_And_Treatment');"/>
			</td>
			<td align="left">Temporarily Closed To Accrual And Treatment</td>
		</tr>
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Temporarily_Closed_To_Accrual" name="closeStatus" onclick="javascript:parent.setCloseStatus('Temporarily_Closed_To_Accrual');"/>
			</td>
			<td align="left">Temporarily Closed To Accrual</td>
		</tr>
		</c:if>
	</table>
</chrome:box>
<div class="flow-buttons">
   	<span class="next">
   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closeWin.close();" />
		<tags:button type="button" color="green" icon="save" onclick="parent.closeStudy();" value="Close Study" />
	</span>
</div>
