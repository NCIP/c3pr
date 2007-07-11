C3PR.tabbedFlowUpdateTarget = function(evt) {
    var a = Event.element(evt)
    var tabclass = Element.classNames(a).detect(function(cls) { return cls.slice(0, 3) == "tab" })
    var targetPage = tabclass.slice(3)
    $('_target').name = "_target" + targetPage
    if ($('command')._finish) $('command')._finish.disable()
}

C3PR.tabbedFlowSelectAndSubmit = function(click) {
    Event.stop(click)
    C3PR.tabbedFlowUpdateTarget(click)
    $('command').submit() // command is the default ID for a form created with form:form
}

Event.observe(window, "load", function() {
    $$("li.tab a").each(function(a) {
        Event.observe(a, "click", C3PR.tabbedFlowSelectAndSubmit)
    })
    if ($("flow-prev")) Event.observe("flow-prev", "click", C3PR.tabbedFlowUpdateTarget)
    if ($("flow-update")) Event.observe("flow-update", "click", C3PR.tabbedFlowUpdateTarget)
})