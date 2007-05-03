//Event.observe(window, "load", registerForms)
Event.observe(window, "load", function(){
	//alert("registering on load function")
	$$('form').each(function(formVar){
									//alert(formVar.name)
									formVar._submit= formVar.submit
									formVar.submit = validateForm
									Event.observe(formVar, "submit", validateForm)
								})
})
validateForm=function(submit){
	//alert("0.")
	formVar=submit?Event.element(submit):this
	submit?Event.stop(submit):null
	//alert("1."+formVar.name)
	var fields=Form.getElements(formVar)
	//alert('2.'+fields.length)
	var checkFields=fields.findAll(function(field){
						className=Element.classNames(field).detect(function(cls) {
															var v=cls.indexOf('validate') == 0
															//alert("2.1."+cls+" 2.2"+v)
															return cls.indexOf('validate') == 0
														})
						return className==null?false:className.length>0
					})
	//alert("2.3"+checkFields)
	checkFields.each(prepareField)
	//alert("10")
	flag=validateFields(checkFields)
	//alert("10.5")
	if(flag){
		//alert("13")
		formVar._submit()
	}
}
prepareField=function(element){
	ERROR_MSG_REQUIRED="required"
	ERROR_MSG_PATTERN="Incorrect fromat"
	ERROR_MSG_MINLENGTH="too short"
	ERROR_MSG_MAXLENGTH="too long"	
	ERROR_MSG_PHONE="The second phone number is not valid"	
	validationTypeStr=Element.classNames(element).detect(function(cls) {
															var v=cls.indexOf('validate') == 0
															//alert("2.5.["+cls+"] 2.6"+v)
															return cls.indexOf('validate') == 0
														})
	//alert("3."+validationTypeStr)
	validationTypeStr=validationTypeStr.substr(9)
	//alert("4."+validationTypeStr)
	validations=validationTypeStr.split("&&")
	for(i=0 ; i<validations.length ; i++){
		validationType=validations[i]
		if(validationType.toUpperCase()=='NOTEMPTY'||validationType==''){
			//alert("5.")
			element.required = true
			element.requiredError = ERROR_MSG_REQUIRED
		}else if(validationType.toUpperCase().indexOf('MINLENGTH')==0){
			//alert("7.length="+parseInt(validationType.substr(9)))
			element.minlength = parseInt(validationType.substr(9))
			element.minlengthError = ERROR_MSG_MINLENGTH
		}else if(validationType.toUpperCase().indexOf('MAXLENGTH')==0){
			//alert("8.length="+parseInt(validationType.substr(9)))
			element.maxlength = parseInt(validationType.substr(9))
			element.maxlengthError = ERROR_MSG_MAXLENGTH
									
		}else {
			//alert("6.")
			element.pattern = validationType
			element.patternError = ERROR_MSG_PATTERN
		}
	}
}
function showError(element,msg){
	//alert("9")
	new Insertion.After(element, " <span id='"+element.name+"-msg'class='red'>*"+msg+"</span>")
}
function removeError(element){
	//alert("hiding")
	msgId=element.name+"-msg"
   	$(msgId)!=null?new Element.remove(msgId):null
}