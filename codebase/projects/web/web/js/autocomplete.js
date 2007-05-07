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