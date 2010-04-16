Ajax.InPlaceCollectionEditor.prototype.__createEditField = Ajax.InPlaceCollectionEditor.prototype.createEditField;
Ajax.InPlaceCollectionEditor.prototype.__onSubmit = Ajax.InPlaceCollectionEditor.prototype.onSubmit;
Ajax.InPlaceCollectionEditor.prototype.__buildOptionList = Ajax.InPlaceCollectionEditor.prototype.buildOptionList;
Object.extend(Ajax.InPlaceCollectionEditor.prototype, {
    createEditField: function() {
        if (this.options.callback) { var callbackSet = this.options.callback };
        this.__createEditField();
        if(this.options.autocompleter){
        	this._controls.editor.id=this.options.autocompleter.basename-"input";
        	//create hidden feild for autocompleter
        	var inputfld = document.createElement('input');
		    inputfld.type = 'hidden';
		    inputfld.name=this._controls.editor.name;
		    this._controls.editor.name="";
		    var size = this.options.size || this.options.cols || 0;
		    if (0 < size) inputfld.size = size;
		    inputfld.className = 'autocomplete';
		    inputfld.id=this.options.autocompleter.basename-"hidden";
		    this._form.appendChild(inputfld);
		    //register autocompleter
        	AutocompleterManager.registerAutoCompleter(this.options.autocompleter);
        	
    	}
        if (callbackSet) { this.options.callback = callbackSet;    };
    },
    buildOptionList: function() {
        if(BrowserDetect.browser=='Explorer')
        	Element.extend(this._controls.editor); 
        this.__buildOptionList();
    },
    onSubmit: function(){
		if(this.options.validations!=null && this.options.validations!=''){
			this.editField.className=this.editField.className + " "+this.options.validations;
			ValidationManager.removeError(this.editField);
			var fields=new Array();
			fields.push(this.editField);
			ValidationManager.prepareField(fields[0]);
			if(validateFields(fields)){
				this.__onSubmit();
		    }else{
		    	arguments.length > 1?Event.stop(arguments[0]):null;
		    	return false;
		    }
		}else{
        	this.__onSubmit();
        }
    }
    
});

//InPlaceEditor extension that adds a 'click to edit' text when the field is 
//empty.
Ajax.InPlaceEditor.prototype.__onSubmit = Ajax.InPlaceEditor.prototype.onSubmit;
Ajax.InPlaceEditor.prototype = Object.extend(Ajax.InPlaceEditor.prototype, {
    onSubmit: function(){
		if(this.options.validations!=null && this.options.validations!=''){
			this.editField.className=this.editField.className + " "+this.options.validations;
			ValidationManager.removeError(this.editField);
			var fields=new Array();
			fields.push(this.editField);
			ValidationManager.prepareField(fields[0]);
			if(validateFields(fields)){
				this.__onSubmit();
		    }else{
		    	arguments.length > 1?Event.stop(arguments[0]):null;
		    	return false;
		    }
		}else{
        	this.__onSubmit();
        }
    }
});

Ajax.InPlaceAutocompleter = Class.create(Ajax.InPlaceEditor, {

  createEditField: function() {
    var list = document.createElement('select');
    list.name = this.options.paramName;
    list.size = 1;
    this._controls.editor = list;
    this._collection = this.options.collection || [];
    if (this.options.loadCollectionURL)
      this.loadCollection();
    else
      this.checkForExternalText();
    this._form.appendChild(this._controls.editor);
  },

  loadCollection: function() {
    this._form.addClassName(this.options.loadingClassName);
    this.showLoadingText(this.options.loadingCollectionText);
    var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
    Object.extend(options, {
      parameters: 'editorId=' + encodeURIComponent(this.element.id),
      onComplete: Prototype.emptyFunction,
      onSuccess: function(transport) {
        var js = transport.responseText.strip();
        if (!/^\[.*\]$/.test(js)) // TODO: improve sanity check
          throw 'Server returned an invalid collection representation.';
        this._collection = eval(js);
        this.checkForExternalText();
      }.bind(this),
      onFailure: this.onFailure
    });
    new Ajax.Request(this.options.loadCollectionURL, options);
  },

  showLoadingText: function(text) {
    this._controls.editor.disabled = true;
    var tempOption = this._controls.editor.firstChild;
    if (!tempOption) {
      tempOption = document.createElement('option');
      tempOption.value = '';
      this._controls.editor.appendChild(tempOption);
      tempOption.selected = true;
    }
    tempOption.update((text || '').stripScripts().stripTags());
  },

  checkForExternalText: function() {
    this._text = this.getText();
    if (this.options.loadTextURL)
      this.loadExternalText();
    else
      this.buildOptionList();
  },

  loadExternalText: function() {
    this.showLoadingText(this.options.loadingText);
    var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
    Object.extend(options, {
      parameters: 'editorId=' + encodeURIComponent(this.element.id),
      onComplete: Prototype.emptyFunction,
      onSuccess: function(transport) {
        this._text = transport.responseText.strip();
        this.buildOptionList();
      }.bind(this),
      onFailure: this.onFailure
    });
    new Ajax.Request(this.options.loadTextURL, options);
  },

  buildOptionList: function() {
    this._form.removeClassName(this.options.loadingClassName);
    this._collection = this._collection.map(function(entry) {
      return 2 === entry.length ? entry : [entry, entry].flatten();
    });
    var marker = ('value' in this.options) ? this.options.value : this._text;
    var textFound = this._collection.any(function(entry) {
      return entry[0] == marker;
    }.bind(this));
    this._controls.editor.update('');
    var option;
    this._collection.each(function(entry, index) {
      option = document.createElement('option');
      option.value = entry[0];
      option.selected = textFound ? entry[0] == marker : 0 == index;
      option.appendChild(document.createTextNode(entry[1]));
      this._controls.editor.appendChild(option);
    }.bind(this));
    this._controls.editor.disabled = false;
    Field.scrollFreeActivate(this._controls.editor);
  }
});
Autocompleter.Base.prototype.__onBlur = Autocompleter.Base.prototype.onBlur;
Autocompleter.Base.prototype = Object.extend(Autocompleter.Base.prototype, {
	onBlur: function(event){
		//getting the id of the elmt...replacing"-input" with "-hidden" to get the hidden var
		//and re-setting the entered text with "" if the hidden var and the input var's default value contains no value.
		//the hidden value will contain a val only if something is selected from the auto-completer drop-down.
		//and the input vars defaultValue will only contain a value if the auto-completer was displaying an existing value(CPR-1708)
		//so when the user enters junk literals in the auto-completer
		//and tries to submit or clicks elsewhere the onblur clears the entered text.
		if(!this.options.isFreeTextAllowed){
			var hiddenElmtId = this.element.id.substring(0, this.element.id.lastIndexOf('-input')) + "-hidden";	
			if($(hiddenElmtId) != null ){
				if($(hiddenElmtId).value == null || $(hiddenElmtId).value == ''){
					$(this.element.id).value="";
					Element.addClassName(this.element, "pending-search");
				}
				else{
					//setting the required field as valid (white background)
					if (this.element.hasClassName("required") || this.element.hasClassName("validField")){
						ValidationManager.setNormalState(this.element);
						ValidationManager.removeError(this.element);
					}
				}
			}
		}
		if(Prototype.Browser.IE){
			if (event.offsetX > 450) {     
			  //good may close    
			} else if (event.offsetY < 0) {       
			  //good - may close     
			} else {         
			  //prevent autocomplete close     
			  event.cancelBubble = true;         
			  return false;    
			}
		 }
		this.__onBlur(event);
	}
});

Autocompleter.Base.prototype.__onKeyPress = Autocompleter.Base.prototype.onKeyPress;
Autocompleter.Base.prototype = Object.extend(Autocompleter.Base.prototype, {
	onKeyPress: function(event) {
	    if(this.active)
	      switch(event.keyCode) {
	       case Event.KEY_TAB:
	       case Event.KEY_RETURN:
	         this.selectEntry();
	         Event.stop(event);
	       case Event.KEY_ESC:
	         this.hide();
	         this.active = false;
	         Event.stop(event);
	         return;
	       case Event.KEY_LEFT:
	       case Event.KEY_RIGHT:
	       case 35:
	       case 36:
	       case 224:
	       case 17:
	         return;
	       case Event.KEY_UP:
	         this.markPrevious();
	         this.render();
	         if(navigator.appVersion.indexOf('AppleWebKit')>0) Event.stop(event);
	         return;
	       case Event.KEY_DOWN:
	         this.markNext();
	         this.render();
	         if(navigator.appVersion.indexOf('AppleWebKit')>0) Event.stop(event);
	         return;
	      }
	     else
	       if(event.keyCode==Event.KEY_TAB || event.keyCode==Event.KEY_RETURN ||
	       	 event.ctrlKey==true || event.metaKey==true || event.altKey==true ||
	         (navigator.appVersion.indexOf('AppleWebKit') > 0 && event.keyCode == 0)) return;
	
	    this.changed = true;
	    this.hasFocus = true;
	    
	    //getting the id of the elmt...replacing"-input" with "-hidden" to get the hidden var
	    //and re-setting it to "" so that the user cannot select something from the autocompleter 
	    //drop down and then modify it and submit the changed text with the 
	    //previously selected id. 
	    switch(event.keyCode) {
	       case Event.KEY_TAB:
	       case Event.KEY_RETURN:
	         this.selectEntry();
	         Event.stop(event);
	       case Event.KEY_ESC:
	         this.hide();
	         this.active = false;
	         Event.stop(event);
	         return;
	       case Event.KEY_LEFT:
	       case Event.KEY_RIGHT:
	       case 35:
	       case 36:
	       case 224:
	       case 17:
	         return;
	       case Event.KEY_UP:
	         this.markPrevious();
	         this.render();
	         if(navigator.appVersion.indexOf('AppleWebKit')>0) Event.stop(event);
	         return;
	       case Event.KEY_DOWN:
	         this.markNext();
	         this.render();
	         if(navigator.appVersion.indexOf('AppleWebKit')>0) Event.stop(event);
	         return;
	       default:
	       	 if(this.options.isFreeTextAllowed != null && !this.options.isFreeTextAllowed){
				var hiddenElmtId = this.element.id.substring(0, this.element.id.lastIndexOf('-input')) + "-hidden";			
				if($(hiddenElmtId) != null ){
					$(hiddenElmtId).value="";
					//Setting the state as invalid if its a required field
					if (this.element.hasClassName("valueOK") || this.element.hasClassName("validField")){
						ValidationManager.setState(this.element, false);
						
					}
		    	}
		    }
	    }
	    if(this.observer) clearTimeout(this.observer);
	      this.observer = 
	        setTimeout(this.onObserverEvent.bind(this), this.options.frequency*1000);
	  }
});
Autocompleter.Base.prototype.__baseInitialize = Autocompleter.Base.prototype.baseInitialize;
Autocompleter.Base.prototype = Object.extend(Autocompleter.Base.prototype, {
	baseInitialize: function(element, update, options) {
		options.frequency    = 2.0;
    	options.minChars     = 3;
		this.__baseInitialize(element, update, options);
	}
});



