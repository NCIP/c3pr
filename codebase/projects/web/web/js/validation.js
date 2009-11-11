// ===================================================================
// Author: Kruttik Aggarwal <kruttikagarwal@@gmail.com>
// 
//
// NOTICE: You may use this code for any purpose, commercial or
// private, without any further permission from the author. You may
// remove this notice from your final code if you wish.
//
// You may *NOT* re-distribute this code in any way except through its
// use. That means, you can include it in your product, or your web
// site, or any other form where the code is actually being used. You
// may not put the plain javascript up on your site for download or
// include it in your javascript libraries for download. 
// If you wish to share this code with others, please just point them
// to the URL instead.
// Please DO NOT link directly to my .js files from your site. Copy
// the files to your server and use them there. Thank you.
// ===================================================================

// ------------------------------------------------------------------
// The css class names are case insensitive and define the validations
// an input should go thorgh before its validated and submitted.
// The following validations are provided out of box using commons-validations:
// 
// Validation Type  | CSS Class Name     | usage
// -----------------+--------------------+-----------------------
// Required         | NOTEMPTY           | <[input][select][textarea] type=[text][password] class=validate-NOTEMPTY
// Minimum Length   | MINLENGTH          | <[input].... type=.... class=validate-MINLENGTH<length>
// Maximum Length   | MAXLENGTH          | <[input].... type=.... class=validate-MAXLENGTH<length>
// Email            | EMAIL              | <[input].... type=.... class=validate-EMAIL
// URL              | URL                | <[input].... type=.... class=validate-URL
// Credit Card      | VISA,etc           | <[input].... type=.... class=validate-VISA[etc]
// Zip code         | ZIPCODE            | <[input].... type=.... class=validate-ZIPCODE
// Zip or Postal    | ZIP_POSTAL_CODE    | <[input].... type=.... class=validate-ZIP_POSTAL_CODE
// US Phone No.     | US_PHONE_NO        | <[input].... type=.... class=validate-US_PHONE_NO
// Alphanumeric     | ALPHANUMERIC       | <[input].... type=.... class=validate-ALPHANUMERIC
// Non Zero Numeric | NONZERO_NUMERIC    | <[input].... type=.... class=validate-NONZERO_NUMERIC
// Numeric          | NUMERIC            | <[input].... type=.... class=validate-NUMERIC
// Alphabetic       | ALPHABETIC         | <[input].... type=.... class=validate-ALPHABETIC
// date             | DATE               | <[input].... type=.... class=validate-DATE[(<format>)]
// HTML characters  | HTML_SPECIAL_CHARS | <[input].... type=.... class=validate-HTML_SPECIAL_CHARS
//
// NOTE : For Multiple validation use JAVA 'and' notation ('&&').
// Example:
//  <[input].... type=.... class=validate-NOTEMPTY&&MINLENGTH7&&EMAIL
// ------------------------------------------------------------------

// VALIDATION ERROR DISPLAY:
// This javascript library provides two strategies of indicating validation error on the html page.
// These strategies can be handled by setting the ERROR_STRATEGY global variable.
// --> TEXT Strategy: this is the default strategy (ERROR_STRATEGY="text")
//     For each type of validation there is a default error message which can be overridden
//     by each html page by setting the global error message variables. e.g setting the ERROR_MSG_REQUIRED global
//     variable, a page can customize the Required Feild Error Message.
// --> Highlighting Strategy: this can be activated by setting Error strategy varibale to "highlight"
//     Defalt color is red. though this can be customized setting the global variable ERROR_HIGHTLIGHT_COLOR
//
// Callback Hooks:
// The validation framework provides a callback hook for html pages to pre process and post process validations.
// --> ValidationManager.submitPreProcess=function(formElement)                        :   Any page that provides and implementation of submitPreProcess(formElement)
//                                                              		 recieve a call back on the function. If the function return
// 																		 true then the form will be validated else the form will be submitted.
// --> ValidationManager.submitPostProcess= function(formElement, continurSubmission)   :   Any page that provides and implementation of submitPostProcess(formElement, continurSubmission)
//                                                              		  recieve a call back on the function. If the function return
// 																		  true then the form will be submitted else the submit will be ignored.
//

var ValidationManager = Class.create()
var ValidationManager = {
	debug: false,
	currentFormVar:"",
	registeredInvokes: new Array(),
	ERROR_STRATEGY:"text",
	ERROR_HIGHTLIGHT_COLOR:"red",
	ERROR_MSG_REQUIRED:"Missing field",
	ERROR_MSG_PATTERN:"Incorrect format",
	ERROR_MSG_MINLENGTH:"Too short",
	ERROR_MSG_MAXLENGTH:"Too long",
	ERROR_MSG_PHONE:"Invalid phone number",
	ERROR_MSG_NONZERO_VALUE:"Enter value more then zero",
	ERROR_MSG_HTML_SPECIAL_CHARS:"Incorrect format. (*|,\":<>[]{}`\'()@&$#%) not allowed",

	validateForm: function(submit){
		formVar=submit?Event.element(submit):this
		submit?Event.stop(submit):null
		if(!ValidationManager.submitPreProcess(formVar)){
			formVar._submit()
			return
		}
		var fields=Form.getElements(formVar)
		var checkFields=fields.findAll(function(field){
							className=Element.classNames(field).detect(function(cls) {
																var v=cls.indexOf('validate') == 0
																return cls.indexOf('validate') == 0
															})
							return className==null?false:className.length>0
						})
		checkFields.each(ValidationManager.prepareField)
		if(ValidationManager.afterPrepareFeilds(formVar)){
			flag=validateFields(checkFields, true)
			if(ValidationManager.submitPostProcess(formVar, flag)){
				ValidationManager.invokeAll(formVar)
				if(ValidationManager.debug){
					ValidationManager.currentFormVar=formVar
					vLogString="------------------<br>"
					vLogString+="Form not submitted...<br>"
					vLogString+="Form submit params:<br>"
					vSubmitParams= Form.serialize(formVar).split("&")
					for(vI=0 ; vI<vSubmitParams.length ; vI++)
						vLogString+=vSubmitParams[vI]+"<br>"
					vLogString+="------------------<br>"
					ValidationManager.log(vLogString)
					ValidationManager.log("<input type='button' value='submit this form' onClick='ValidationManager.resumeSubmit()'/>")
				}else
					formVar._submit()
					openInfoDialog()
			}
		}
	},
	submitPostProcess: function(formElement, validationFlag){return validationFlag},
	submitPreProcess: function(formElement){return true},
	afterPrepareFeilds: function(formElement){return true},
	
	prepareField: function(element){
		validationTypeStr=Element.classNames(element).detect(function(cls) {
																var v=cls.indexOf('validate') == 0
																return cls.indexOf('validate') == 0
															})
		validationTypeStr=validationTypeStr.substr(9)
		validations=validationTypeStr.split("&&")
		for(i=0 ; i<validations.length ; i++){
			validationType=validations[i]
			if(validationType.toUpperCase()=='NOTEMPTY'||validationType==''){
				element.required = true
				element.requiredError = ValidationManager.ERROR_MSG_REQUIRED
			}else if(validationType.toUpperCase().indexOf('MINLENGTH')==0){
				element.minlength = parseInt(validationType.substr(9))
				element.minlengthError = ValidationManager.ERROR_MSG_MINLENGTH
			}else if(validationType.toUpperCase().indexOf('MAXLENGTH')==0){
				element.maxlength = parseInt(validationType.substr(9))
				element.maxlengthError = ValidationManager.ERROR_MSG_MAXLENGTH
			}else if(validationType.toUpperCase().indexOf('NONZERO_NUMERIC')==0){
				element.nonzero = true 
				element.nonzeroError = ValidationManager.ERROR_MSG_NONZERO_VALUE
			}else if(validationType.toUpperCase().indexOf('HTML_SPECIAL_CHARS')==0){
				element.htmlescape = true 
				element.htmlescapeError = ValidationManager.ERROR_MSG_HTML_SPECIAL_CHARS
			}else {
				element.pattern = validationType
				element.patternError = ValidationManager.ERROR_MSG_PATTERN
			}
		}
	},
	showError: function(element,msg){
		strategies=ValidationManager.ERROR_STRATEGY.split("&&")
		for(i=0 ; i<strategies.length ; i++){
		errorStrategy1=strategies[i]
			if(errorStrategy1=="text"){
				//new Insertion.After(element, " <span id='"+element.name+"-msg'style='color:#EE3324'><img src='/c3pr/images/error-red.png' />"+msg+"</span>")
				new Insertion.Bottom(element.parentNode, "<ul id='"+element.name+"-msg' class='errors'><li>"+msg+"</li></ul>")
			}
			if(errorStrategy1=="highlight") {
				element.style._backgroundColor=element.style._backgroundColor?element.style._backgroundColor:element.style.backgroundColor
				element.style.backgroundColor=ValidationManager.ERROR_HIGHTLIGHT_COLOR
			}
		}
	},
	removeError: function(element){
		strategies=ValidationManager.ERROR_STRATEGY.split("&&")
		for(i=0 ; i<strategies.length ; i++){
		errorStrategy2=strategies[i]
			if(errorStrategy2=="text"){
				msgId=element.name+"-msg"
			   	$(msgId)!=null?new Element.remove(msgId):null
			}
			if(errorStrategy2=="highlight") {
				element.style.backgroundColor=element.style._backgroundColor?element.style._backgroundColor:element.style.backgroundColor
			}
		}
	},
	registerToInvoke: function(obj){
							this.registeredInvokes.push(obj)
						},
	invokeAll: function(formElement){
					for(vJ=0 ; vJ<this.registeredInvokes.length ; vJ++){
						try{
							this.registeredInvokes[vJ].onValidateSubmit(formElement)
						}catch(error){
							ValidationManager.log(error.message)
						}
					}
				},
	resumeSubmit: function(){
						this.currentFormVar._submit()
						openInfoDialog()
					},
	loggerDiv: "ValidationManagerLog",
	log: function(string){
						if(ValidationManager.debug)
							document.getElementById(ValidationManager.loggerDiv)!=null?new Insertion.Bottom(ValidationManager.loggerDiv,string):null
					},
	clearLog: function(){
						document.getElementById(ValidationManager.loggerDiv)!=null?new Element.update(ValidationManager.loggerDiv,""):null
					},
	errorLog: function(str){
					this.log("<br><span style='color:redfont-weight:bolder'>----------------------<br>"+str+"<br>-------------------------</span><br>")
				},
	registerForm: function(formVariable){
					formVariable._submit= formVariable.submit
					formVariable.submit = ValidationManager.validateForm
					Event.observe(formVariable, "submit", ValidationManager.validateForm)
					
					Event.observe(formVariable, "click", ValidationManager.formClick)
			������� Event.observe(formVariable, "domfocusout", ValidationManager.formBlur)
			������� Event.observe(formVariable, "keyup", ValidationManager.formKeyup)
			������� Event.observe(formVariable, "change", ValidationManager.formChange)
			������� Event.observe(formVariable, "focus", ValidationManager.formFocus)
				},
	setState: function(inputField, isValid) {
������� if (isValid) {
����������� ValidationManager.setValidState(inputField)
������� } else {
����������� ValidationManager.setInvalidState(inputField)
������� }
��� },

	setStateOnLoad: function(inputField, isValid) {
������� if (isValid) {
����������� ValidationManager.setNormalState(inputField)
������� } else {
����������� ValidationManager.setInvalidState(inputField)
������� }
��� },

��� setNormalState: function(inputField) {
����������� Element.removeClassName(inputField, "required")
����������� Element.removeClassName(inputField, "mandatory")
����������� Element.removeClassName(inputField, "validField")
����������� Element.addClassName(inputField, "valueOK")
��� },

	doFieldValidation: function(inputField) {
		if (!inputField) return
������� if (inputField.hasClassName("autocomplete")) return
������� 
������� ValidationManager.prepareField(inputField)
������� isValid = (validateFields(new Array(inputField), false) && trimWhitespace(inputField.value) != "")
������� ValidationManager.setState(inputField, isValid)
��� },

	doFieldValidationOnLoad: function(inputField) {
		if (!inputField) return
������� ValidationManager.prepareField(inputField)
������� isValid = (validateFields(new Array(inputField), false) && trimWhitespace(inputField.value) != "")
������� ValidationManager.setStateOnLoad(inputField, isValid)
��� },

��� registerFields: function() {
������� alert('registerFields')
������� return
������� $$('input.required', 'input.mandatory', 'input.valueOK').each(function(inputField) {
����������� Event.stopObserving(inputField, "keyup")
����������� Event.observe(inputField, "keyup", function() {
��������������� ValidationManager.doFieldValidation(inputField)
����������� })
������� })

������� $$('select.required', 'select.mandatory', 'select.valueOK').each(function(inputField) {
����������� Event.observe(inputField, "change", function() {
��������������� ValidationManager.doFieldValidation(inputField)
����������� })
������� })

������� $$('textarea.required', 'textarea.mandatory', 'textarea.valueOK').each(function(inputField) {
����������� Event.observe(inputField, "keyup", function() {
��������������� ValidationManager.doFieldValidation(inputField)
����������� })
������� })

������� $$('textarea.required', 'textarea.mandatory',� 'textarea.valueOK', 'select.required', 'select.mandatory', 'select.valueOK', 'input.required', 'input.mandatory', 'input.valueOK').each(function(inputField) {
����������� Event.observe(inputField, "blur", function() {
��������������� if (inputField.hasClassName("validField")) {
������������������� Element.removeClassName(inputField, "validField")
������������������� Element.removeClassName(inputField, "required")
������������������� Element.removeClassName(inputField, "mandatory")
������������������� Element.addClassName(inputField, "valueOK")
��������������� }
����������� })
������� })

��� },

��� getElement: function(event) {
������� var inputField = Event.findElement(event, 'INPUT')
������� if (inputField) return inputField

������� var inputField = Event.findElement(event, 'TEXTAREA')
������� if (inputField) return inputField

������� var inputField = Event.findElement(event, 'SELECT')
������� if (inputField) return inputField

������� return null
��� },

��� formClick: function(event) {
������� // var inputField = ValidationManager.getElement(event)
��� },

��� clearFieldCss: function(inputField) {
������� if (Element.hasClassName(inputField, "autocomplete")) return
������� if (Element.hasClassName(inputField, "validField") || Element.hasClassName(inputField, "valueOK")) {
����������� Element.removeClassName(inputField, "validField")
����������� Element.removeClassName(inputField, "required")
����������� Element.removeClassName(inputField, "mandatory")
����������� Element.addClassName(inputField, "valueOK")
������� }
��� },

��� setInvalidState: function(inputField) {
������� if (Element.hasClassName(inputField, "mandatory") || Element.hasClassName(inputField, "required") || Element.hasClassName(inputField, "validField") || Element.hasClassName(inputField, "valueOK")) {
����������� Element.removeClassName(inputField, "validField")
����������� Element.removeClassName(inputField, "valueOK")
����������� Element.removeClassName(inputField, "mandatory")
����������� Element.addClassName(inputField, "required")
������� }
��� },

��� setValidState: function(inputField) {
������� if (Element.hasClassName(inputField, "mandatory") || Element.hasClassName(inputField, "required") || Element.hasClassName(inputField, "validField") || Element.hasClassName(inputField, "valueOK")) {
����������� Element.removeClassName(inputField, "required")
����������� Element.removeClassName(inputField, "mandatory")
����������� Element.removeClassName(inputField, "valueOK")
����������� Element.addClassName(inputField, "validField")
������� }
��� },

��� formChange: function(event) {
������� var inputField = ValidationManager.getElement(event)
������� if (inputField == null) return
������� if (inputField.hasClassName("required") || inputField.hasClassName("mandatory") || inputField.hasClassName("valueOK") || inputField.hasClassName("validField")) {
����������� if (inputField.type == "select-one" || inputField.type == "select-multiple") {
��������������� if (inputField) ValidationManager.doFieldValidation(inputField)
����������� }
����������� inputField.onblur = function() {
��������������� ValidationManager.clearFieldCss(inputField)
����������� }
������� }
��� },

��� formBlur: function(event) {
��� },

��� formFocus: function(event) {
��� },

��� formKeyup: function(event) {
������� // Insertion.Before(document.body, "<font color='white'>KeyUp.</font><br>")
������� var inputField = ValidationManager.getElement(event)
������� if (inputField == null) return
������� if (inputField.hasClassName("required") || inputField.hasClassName("mandatory") || inputField.hasClassName("valueOK") || inputField.hasClassName("validField")) {
����������� if (inputField) {
��������������� ValidationManager.doFieldValidation(inputField)
��������������� inputField.onblur = function() {
������������������� ValidationManager.clearFieldCss(inputField)
��������������� }
����������� }
������� }
��� }
}
Event.observe(window, "load", function(){
	$$('form').each(function(formVar){
									ValidationManager.registerForm(formVar)
								})
})
