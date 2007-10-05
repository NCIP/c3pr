Ajax.InPlaceCollectionEditor.prototype.__createEditField = Ajax.InPlaceCollectionEditor.prototype.createEditField;
Ajax.InPlaceCollectionEditor.prototype.__onSubmit = Ajax.InPlaceCollectionEditor.prototype.onSubmit;
Ajax.InPlaceCollectionEditor.prototype = Object.extend(Ajax.InPlaceCollectionEditor.prototype, {
    createEditField: function() {
        if (this.options.callback) { var callbackSet = this.options.callback };
        this.__createEditField();
        if (callbackSet) { this.options.callback = callbackSet;    };
    },
    onSubmit: function(){
		if(this.editField.value=="" && this.options.requiredIndicator!=null && this.options.requiredIndicator==true){
			alert("Its a required field. Please enter a value.");
			if (arguments.length > 1) {
		      Event.stop(arguments[0]);
		    }
		    return false;
		}else{
        	this.__onSubmit();
        }
    },
    
});

//InPlaceEditor extension that adds a 'click to edit' text when the field is 
//empty.
Ajax.InPlaceEditor.prototype.__onSubmit = Ajax.InPlaceEditor.prototype.onSubmit;
Ajax.InPlaceEditor.prototype = Object.extend(Ajax.InPlaceEditor.prototype, {
    onSubmit: function(){
		if(this.editField.value=="" && this.options.requiredIndicator!=null && this.options.requiredIndicator==true){
			alert("Its a required field. Please enter a value.");
			if (arguments.length > 1) {
		      Event.stop(arguments[0]);
		    }
		    return false;
		}else{
        	this.__onSubmit();
        }
    },
});