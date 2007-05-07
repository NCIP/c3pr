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
// This library assists in building auto completers
// The class names are case sensitive
// example usage
// Build your own autocompleter object
// var diseaseSiteAutocompleterProps = {
//	  basename: "<some base name>",
//    
//    /*This function populates the dropp downs*/
//	  populator: function(autocompleter, text) {
//			        <Some DWR Bean>.<Some Method>([[arg1],[arg2]...], function(returnedData) {
//																    	autocompleter.setChoices(returnedData)
//																	   })
//			    },
//    /*This function returns a string from each object withing the returned objects*/
//    valueSelector: function(obj) {
//						return obj.name
//			    	},
//    /*This function is called after user select a element from the auto completer*/
//    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
//    								hiddenField=diseaseSiteAutocompleterProps.basename+"-hidden"
//	    							$(hiddenField).value=selectedChoice.id;
//	    							ajaxDiseaseSite=selectedChoice.name;
//								},
// }
//
// After writing your class for autocompleter add this class to the autocompleters array. for e.g
//    autoCompleters.push(<My Auto Completer Object>);
//
// Thats all you need to do. The rest will be handled by the library.
// Note: Make sure that that the input fields match the <basename>-<option> syntax
//       for autocompleter to work properly


var autoCompleters=new Array()
Event.observe(window, "load", function() {
	autoCompleters.length>0?registerAutoCompleters() : null
})
function registerAutoCompleters(){
	for(i=0 ; i<autoCompleters.length ; i++){
		var autoCompleterObject=AbstractAutocompleterProps
		Object.extend(autoCompleterObject,autoCompleters[i])
					acCreate(autoCompleterObject)
	}
}
function acCreate(mode) {
    new Autocompleter.DWR(mode.inputElement(), mode.displayChoices(),
							mode.populator, {valueSelector: mode.valueSelector,
											 	afterUpdateElement: mode.afterUpdateElement,
											 	indicator: mode.indicator()
    										 })
    Event.observe(mode.basename + "-clear", "click", function() {
														$(mode.basename)?$(mode.basename).value = "":null
														$(mode.inputElement()).value = ""
												    })
}

/* Abstract Implementation of an autocompleter object*/
var AbstractAutocompleterProps = Class.create();
var AbstractAutocompleterProps = {
	basename: "autoCompleter",
    populator: function(autocompleter, text) {    },
    valueSelector: function(obj) {
						return obj
			    	},
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {	},
    inputElement: function(){ return this.basename+"-input"},
    displayChoices: function(){ return this.basename+"-choices"}, 
    indicator: function(){ return this.basename+"-indicator"}, 
}