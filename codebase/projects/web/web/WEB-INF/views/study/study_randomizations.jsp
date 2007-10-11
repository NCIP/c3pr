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
		var parameterMap;
		if(form != ""){
			parameterMap = getParameterMap(form);
		}
		
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
	
	function uploadFile(index){
		<tags:tabMethod method="parseFile" divElement="'bookRandomizationsDisplay-'+index" javaScriptParam="'index='+index"  formName="'epochForm_'+index"/>        
	}
	
	ValidationManager.submitPostProcess= function(formElement, continueSubmission){
		if(formElement.id.indexOf("epochForm_") != -1){
			formElement._target.name = "temp";
		}
		return continueSubmission;
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
		 </div>
		 <hr />
		 	<form:form method="post" id="epochForm_${epochCount.index}" enctype="multipart/form-data">
		    	<input type="hidden" name="index" value="${epochCount.index}"/>
		    	<tags:tabFields tab="${tab}"/>
			        <div class="content">
			            <div class="row">
			                <div class="label">Select file to Import:</div>
			                <div class="value">
			                    <div class="fileinputs"><input type="file" name="file" /></div>
			                </div>
			            </div>
			        </div>
			        <div id="bookButton" align="center">    
			         	<input type='submit' value='Upload Randomization File'/>   
					</div><br/> 		        
		    </form:form>
	    </chrome:box>	    

		<chrome:box title="${epoch.name}" id="book_results_${epochCount.index}" cssClass="paired">
		     <div id="bookRandomizationsDisplay-${epochCount.index}">	
		     	<c:out value="${bookRandomizationEntries[epochCount.index]}" escapeXml="false"/>	    	
		    </div>
		</chrome:box>
		</div>
		
<%--	will call this to get display onload in edit mode. but currently gives a hibernate exception
		<script>uploadBook("", "${epochCount.index}");</script>		--%>		
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
                <td><b>Call-Out URL:</b></td>
				<td>
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
                <td><b>Phone Number:</b></td>
				<td><form:input path="treatmentEpochs[${epochCount.index}].randomization.phoneNumber" size="20" cssClass="validate-notEmpty"/>
				</td>				
             </tr>
	     </table>
	     <br/>
	    </tags:minimizablePanelBox>
	</c:forEach>
</c:if>
<!--PHONECALL RANDOMIZATION SECTION-->


<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>
	     
</body>
</html>