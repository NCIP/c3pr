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
	if(flag){
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
	new Insertion.After(element, " <span id='"+element.name+"-msg'class='red'>*"+msg+"</span>")
}
function removeError(element){
	msgId=element.name+"-msg"
   	$(msgId)!=null?new Element.remove(msgId):null
}