<%--
Copyright Duke Comprehensive Cancer Center and SemanticBits
 
  Distributed under the OSI-approved BSD 3-Clause License.
  See  https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<chrome:deletableDivision divTitle="armTitle-${statusArms.index}" id="armBox-${statusArms.index}" title="Arm: ${treatmentEpoch.arms[statusArms.index].name}" onclick="RowManager.deleteRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,${index}),NESTED.${index},-1);">
	<div class="leftpanel">																																			  			
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="study.epoch.arm"/></div>
			<div class="value">
				<input type="text" size="43" name="study.epochs[${index}].arms[NESTED.${index}].name" class="required validate-notEmpty" value="Arm A" />
				<tags:hoverHint	id="study.arm.name-${index}" keyProp="study.arm.name" />
			</div>
		</div>
		<div class="row">
			<div class="label"><fmt:message key="study.accrualCeiling"/></div>
			<div class="value">
				<input type="text" name="study.epochs[${index}].arms[NESTED.${index}].targetAccrualNumber" size="6" maxlength="6" class="validate-numeric" />
				<tags:hoverHint id="study.arm.targetAccrualNumber-${index}" keyProp="study.arm.targetAccrualNumber" />
			</div>
		</div>
	</div>
	<div class="rightpanel">
		<div class="row">
			<div class="label"><fmt:message key="c3pr.common.description"/></div>
			<div class="value">
				<textarea  name="study.epochs[${index}].arms[NESTED.${index}].descriptionText" rows="2" cols="33"></textarea>
				<tags:hoverHint id="study.arm.description-${index}" keyProp="study.arm.description" />
			</div>
		</div>
	</div>
</chrome:deletableDivision>
