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
// Credit Card      | VISA,etc           | <[input].... type=.... class=validate-VISA[etc]
// Zip code         | ZIPCODE            | <[input].... type=.... class=validate-ZIPCODE
// Zip or Postal    | ZIP_POSTAL_CODE    | <[input].... type=.... class=validate-ZIP_POSTAL_CODE
// US Phone No.     | US_PHONE_NO        | <[input].... type=.... class=validate-US_PHONE_NO
// Alphanumeric     | ALPHANUMERIC       | <[input].... type=.... class=validate-ALPHANUMERIC
// Numeric          | NUMERIC            | <[input].... type=.... class=validate-NUMERIC
// Alphabetic       | ALPHABETIC         | <[input].... type=.... class=validate-ALPHABETIC
// date             | DATE               | <[input].... type=.... class=validate-DATE[(<format>)]
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
// --> submitPreProcess(formElement)                        :   Any page that provides and implementation of submitPreProcess(formElement)
//                                                              recieve a call back on the function. If the function return
// 																true then the form will be validated else the form will be submitted.
// --> submitPostProcess(formElement, continurSubmission)   :   Any page that provides and implementation of submitPostProcess(formElement, continurSubmission)
//                                                              recieve a call back on the function. If the function return
// 																true then the form will be submitted else the submit will be ignored.
//
Event.observe(window, "load", function(){
	$$('form').each(function(formVar){
									formVar._submit= formVar.submit
									formVar.submit = validateForm
									Event.observe(formVar, "submit", validateForm)
								})
})
validateForm=function(submit){
	formVar=submit?Event.element(submit):this
	submit?Event.stop(submit):null
	if(!(submitPreProcess?submitPreProcess(formVar):true)){
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
	checkFields.each(prepareField)
	flag=validateFields(checkFields)
	ret=(submitPostProcess?submitPostProcess(formVar, flag):flag)
	ret?formVar._submit():null
}
var submitPostProcess
var submitPreProcess

var ERROR_STRATEGY
var ERROR_HIGHTLIGHT_COLOR
var	DEFAULT_ERROR_STRATEGY="text"
var DEFAULT_ERROR_HIGHTLIGHT_COLOR="red"

var ERROR_MSG_REQUIRED
var ERROR_MSG_PATTERN
var ERROR_MSG_MINLENGTH
var ERROR_MSG_MAXLENGTH
var ERROR_MSG_PHONE
var	DEFAULT_ERROR_MSG_REQUIRED="required"
var	DEFAULT_ERROR_MSG_PATTERN="Incorrect format"
var	DEFAULT_ERROR_MSG_MINLENGTH="too short"
var	DEFAULT_ERROR_MSG_MAXLENGTH="too long"	
var	DEFAULT_ERROR_MSG_PHONE="The second phone number is not valid"	

prepareField=function(element){
	ERROR_MSG_REQUIRED=ERROR_MSG_REQUIRED?ERROR_MSG_REQUIRED:DEFAULT_ERROR_MSG_REQUIRED
	ERROR_MSG_PATTERN=ERROR_MSG_PATTERN?ERROR_MSG_PATTERN:DEFAULT_ERROR_MSG_PATTERN
	ERROR_MSG_MINLENGTH=ERROR_MSG_MINLENGTH?ERROR_MSG_REQUIRED:DEFAULT_ERROR_MSG_MINLENGTH
	ERROR_MSG_MAXLENGTH=ERROR_MSG_MAXLENGTH?ERROR_MSG_MAXLENGTH:DEFAULT_ERROR_MSG_MAXLENGTH
	ERROR_MSG_PHONE=ERROR_MSG_PHONE?ERROR_MSG_PHONE:DEFAULT_ERROR_MSG_PHONE
	ERROR_STRATEGY=ERROR_STRATEGY?ERROR_STRATEGY:DEFAULT_ERROR_STRATEGY
	ERROR_HIGHTLIGHT_COLOR=ERROR_HIGHTLIGHT_COLOR?ERROR_HIGHTLIGHT_COLOR:DEFAULT_ERROR_HIGHTLIGHT_COLOR		
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
			element.requiredError = ERROR_MSG_REQUIRED
		}else if(validationType.toUpperCase().indexOf('MINLENGTH')==0){
			element.minlength = parseInt(validationType.substr(9))
			element.minlengthError = ERROR_MSG_MINLENGTH
		}else if(validationType.toUpperCase().indexOf('MAXLENGTH')==0){
			element.maxlength = parseInt(validationType.substr(9))
			element.maxlengthError = ERROR_MSG_MAXLENGTH
									
		}else {
			element.pattern = validationType
			element.patternError = ERROR_MSG_PATTERN
		}
	}
}
function showError(element,msg){
	strategies=ERROR_STRATEGY.split("&&")
	for(i=0 ; i<strategies.length ; i++){
	errorStrategy1=strategies[i]
		if(errorStrategy1=="text"){
			new Insertion.After(element, " <span id='"+element.name+"-msg'style='color:#EE3324'>*"+msg+"</span>")
		}
		if(errorStrategy1=="highlight") {
			element.style._backgroundColor=element.style._backgroundColor?element.style._backgroundColor:element.style.backgroundColor
			element.style.backgroundColor=ERROR_HIGHTLIGHT_COLOR
		}
	}
}
function removeError(element){
	strategies=ERROR_STRATEGY.split("&&")
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
}