<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<html>
<head>
<style type="text/css">
        .test { width: 100%;}
</style>
<tags:dwrJavascriptLink objects="BookRandomizationAjaxFacade"/>
<script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>
<script>
	var globalIndex;

	function uploadBook(form, index){		
		var parameterMap = getParameterMap(form);
		if(index == ""){
			index = globalIndex;
		} else {			
			globalIndex = index;
		}
		var str = "bookRandomizations-" + index;
		var content = "";
		if(form == "command"){
		//alert("command it is");
			content = document.getElementById(str).value;
		}
		BookRandomizationAjaxFacade.getTable(parameterMap, content, index, uploadBookCallback);
	}
	
	function uploadBookCallback(table){
		var str = "bookRandomizationsDisplay-"+ globalIndex; 
		new Element.update(str, table)
		//document.getElementById(str).innerHTML=table;
	}	
</script>
</head>

<body>

<!--BOOK RANDOMIZATION SECTION-->
<c:if test="${command.randomizationType.name == 'BOOK'}">	

	<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
		<div id="book_container_${epochCount.index}" class="test">
		<chrome:box title="${epoch.name}" id="book_${epochCount.index}" cssClass="paired"> 
		<br/>
	     <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }">         
             <tr>
                <td><b> Randomization Book:</b>
                	<br/> eg: Stratum Group Number, Position, Arm Name</td>
				<td>
					<TEXTAREA name="bookRandomizations-${epochCount.index}" id="bookRandomizations-${epochCount.index}" cols=25 rows=12 class="validate-notEmpty&&maxlength500"></TEXTAREA>
				</td>				
             </tr>
	     </table>
	     <br/>
	     <div id="bookButton" align="center">    
         	<input type='button' onclick='uploadBook("command", "${epochCount.index}")' value='Upload Randomization Book'/>   
		 </div><br/> 
	    </chrome:box>	    

		<chrome:box title="${epoch.name}" id="book_results_${epochCount.index}" cssClass="paired">
		    <div id="bookRandomizationsDisplay-${epochCount.index}">		    	
		    </div>
		</chrome:box>

		</div>
		<script>$('book_container_${epochCount.index}').style.height=new String((50+$('book_${epochCount.index}').offsetHeight)+"px")</script>	
			
	</c:forEach>	

</c:if>
<!--BOOK RANDOMIZATION SECTION-->

<form:form method="post">
<input type="hidden" id="_action" name="_action" value="doNotSave">
<tags:tabFields tab="${tab}"/>

<!--CALLOUT RANDOMIZATION SECTION-->
<c:if test="${command.randomizationType.name == 'CALL_OUT'}">
	<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
		<br/>
	     <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }">         
             <tr>
                <td><b>Enter Call-Out Randomization URL:</b></td>
				<td>
				<!-- <input type="text" 
				id="treatmentEpochs[${epochCountIndex}].stratumGroups[${statusStratumGroup.index}].stratumGroupNumber"
				name="${command.treatmentEpochs[epochCount.index].randomization.calloutUrl}" 
				value="${command.treatmentEpochs[epochCount.index].randomization.calloutUrl}" size="40"/> -->
				<form:input path="treatmentEpochs[${epochCount.index}].randomization.calloutUrl" size="30" cssClass="validate-notEmpty"/>
				</td>				
             </tr>
	     </table>
	     <br/>
	    </tags:minimizablePanelBox>
	</c:forEach>
</c:if>
<!--CALLOUT RANDOMIZATION SECTION-->

<!--PHONECALL RANDOMIZATION SECTION-->
<c:if test="${command.randomizationType.name == 'PHONE_CALL'}">
	<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
		<br/>
	     <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }">         
             <tr>
                <td><b>Enter Randomization PhoneNumber:</b></td>
				<td>

				<form:input path="treatmentEpochs[${epochCount.index}].randomization.phoneNumber" size="20" cssClass="validate-notEmpty"/>
				</td>				
             </tr>
	     </table>
	     <br/>
	    </tags:minimizablePanelBox>
	</c:forEach>
</c:if>
<!--PHONECALL RANDOMIZATION SECTION-->

<!--CALLING FOR AJAX ONLOAD : BOOK RANDOMIZATION SECTION-->
<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
<script>uploadBook("command", "${epochCount.index}")</script>	
</c:forEach>
<!--BOOK RANDOMIZATION SECTION-->

<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>
	     
</body>
</html>