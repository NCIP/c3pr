C3PR.tabbedFlowSelectPage = function(click) {
    Event.stop(click)
    var a = Event.element(click)
    var tabclass = Element.classNames(a).detect(function(cls) { return cls.slice(0, 3) == "tab" })
    var targetPage = tabclass.slice(3)
    $('flowredirect-target').name = "_target" + targetPage
    $('flowredirect').submit()
}

C3PR.tabbedFlowUpdateTarget = function(evt) {
    var a = Event.element(evt)
    var tabclass = Element.classNames(a).detect(function(cls) { return cls.slice(0, 3) == "tab" })
    if (tabclass!=null) {
    	var targetPage = tabclass.slice(3)
    	$('_target').name = "_target" + targetPage 
    	if ($('command')!=null && $('command')._finish){
    		$('command')._finish.disabled = true 
    	}
    }
}

C3PR.tabbedFlowUpdateFinishTarget = function(evt) {
	$('command').submit()
}

C3PR.tabbedFlowMandatoryPage = function(click) {
    Event.stop(click)
    C3PR.tabbedFlowUpdateTarget(click)
    $('command').submit() // command is the default ID for a form created with form:form
}


Event.observe(window, "load", function() {

    $$("li.tab a").each(function(a) {
        Event.observe(a, "click", C3PR.tabbedFlowSelectPage)
    })

    $$("div.ssmItems a").each(function(a) {
        Event.observe(a, "click", C3PR.tabbedFlowSelectPage)
    })

    $$("div.ssmItems.mandatory a").each(function(a) {
        Event.stopObserving(a, "click", C3PR.tabbedFlowSelectPage)
        Event.observe(a, "click", C3PR.tabbedFlowMandatoryPage)
    })


    $$("li.tab.mandatory a").each(function(a) {
        Event.stopObserving(a, "click", C3PR.tabbedFlowSelectPage)
        Event.observe(a, "click", C3PR.tabbedFlowMandatoryPage)
    })


    if ($("flow-prev")) Event.observe("flow-prev", "click", C3PR.tabbedFlowUpdateTarget)
    if ($("flow-update")) Event.observe("flow-update", "click", C3PR.tabbedFlowUpdateTarget)
    if ($("flow-next")) Event.observe("flow-next", "click", C3PR.tabbedFlowUpdateTarget)
    if ($("flow-finish")) Event.observe("flow-finish", "click", C3PR.tabbedFlowUpdateFinishTarget)
})
