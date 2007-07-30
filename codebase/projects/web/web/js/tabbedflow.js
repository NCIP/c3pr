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
    var targetPage = tabclass.slice(3)
    $('_target').name = "_target" + targetPage
    if ($('command')._finish) $('command')._finish.disable()
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
     $$("li.tab.mandatory a").each(function(a) {
         Event.stopObserving(a, "click", C3PR.tabbedFlowSelectPage)
        Event.observe(a, "click", C3PR.tabbedFlowMandatoryPage)
    })
    if ($("flow-prev")) Event.observe("flow-prev", "click", C3PR.tabbedFlowSelectPage)
    if ($("flow-next")) Event.observe("flow-next", "click", function(click) {
        Event.stop(click)
        $("command").submit();
    })
    if ($("flow-update")) Event.observe("flow-update", "click", function(click) {
        Event.stop(click);
        $("command")._action.value='update';
        // $("targetPage").name='_noname';
        document.getElementById('_target').name='_noname';
        $("command").submit(); // command is the default ID for a form created with form:form
    })
})