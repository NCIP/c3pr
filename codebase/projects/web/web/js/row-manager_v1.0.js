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
// This javascript library helps in adding add deleting rows on the html page
// and it automatically manages the input type and spring path binding. For deletion
// it works in accordance with the server side utility RowManager.java. Please read its reference for
// more information
// 
//
// ROW TYPES:
// This javascript library provides two strategies of adding row to on the html page.
// These strategies can be handled by setting the row_addition_startegy property of row inserter object.
// --> TABLE Strategy: this is the default strategy (row_addition_startegy="table")
//					   This expects a table type strucyure for the dummy row and it picks up all 
//					   the rows from the dummy table and adds it to the specified table in the end.
// --> DIVISION Strategy: this can be set (row_addition_startegy="division")
//					   This extracts the innerHtml from the dummy division row 
//					   adds it to the specified element in the end.
//
// Callback Hooks:
// The validation framework provides a callback hook for html pages to pre process and post process validations.
//	preProcessRowInsertion()   :	this call back function is called before the row is inserted
//	postProcessRowInsertion()  :	this call back function is called after the row is inserted
//	preProcessRowDeletion()    :	this call back function is called before the row is deleted
//	postProcessRowDeletion()   :	this call back function is called after the row is deleted
// 																
//
var RowManager = Class.create()
var RowManager = {
	addRow: function(inserter){this.execute(true,inserter,-1)},
	deleteRow: function(inserter,delIndex){this.execute(false,inserter,delIndex)},
	execute: function(isAddRow, inserter, deletionIndex){
					if(isAddRow){
					    inserter.preProcessRowInsertion(inserter)
						inserter.insertRow()
						inserter.postProcessRowInsertion(inserter)
						inserter.localIndex++						
					}else{
						inserter.preProcessRowDeletion(inserter,deletionIndex)
						inserter.deleteRow(deletionIndex)
						inserter.postProcessRowDeletion(inserter,deletionIndex)
					}
				},
	getNestedRowInserter: function(inserter, index){
								return inserter.cloned_nested_row_inserters[index]
							}
}

var rowInserters=new Array()
Event.observe(window, "load", function() {
	registerRowInserters();
})
function registerRowInserters(){
	for(x=0 ; x<rowInserters.length ; x++){
		if(rowInserters[x].isRegistered==null){
			registerRowInserter(rowInserters[x])
//			alert("registered "+rowInserters[x].add_row_division_id)
		}
	}
}
function registerRowInserter(rowInserter){
	clone=Object.clone(AbstractRowInserterProps);
	Object.extend(clone,rowInserter)
	Object.extend(rowInserter,clone)
	rowInserter.init()
}
/* Abstract Implementation of an row-inseter object*/
var AbstractRowInserterProps = Class.create()
temp=-1
var AbstractRowInserterProps = {
	add_row_division_id: "row-table",
	skeleton_row_division_id: "dummy-row",
	initialIndex: 0,
	localIndex: -1,
	validationCSSIndicator: "validate-",
	row_index_indicator: "PAGE.ROW.INDEX",
	row_addition_startegy: "table",
	cloned_nested_row_inserters: "",
	nested_row_inserter: "",
	parent_row_inserter: "",
	parent_row_index: -1,
	isRegistered: true,
	updateIndex: function(index){
						this.localIndex=index
					},
	getRowDivisionElement: function(){
							element=$(this.add_row_division_id)
							if(this.havingParentRowInserter()){
								classString="#"+this.parent_row_inserter.getColumnDivisionID(this.parent_row_index)+" #"+this.add_row_division_id
								element=$$(classString)[0]
							}
							return element
						},
	getColumnDivisionElement: function(index){
							element=$(this.getColumnDivisionID(index))
							if(this.havingParentRowInserter()){
								classString="#"+this.parent_row_inserter.getColumnDivisionID(this.parent_row_index)+" #"+this.getColumnDivisionID(index)
								element=$$(classString)[0]
							}
							return element
						},
    generateRowHtml: function() {
    				rowHtml=this.getRowHtml()
    				rowHtml= this.replaceIndexes(rwoHtml)    
					rowHtml=this.addDivision(rowHtml)
					return rowHtml
				 },
	generateRowElement: function() {
					localTable=$($(this.skeleton_row_division_id)).getElementsByTagName("table")[0]
					rows=localTable.rows
					retRows=new Array()
					for(i=0 ; i<rows.length ; i++){
						rows[i].id=this.getColumnDivisionID(this.localIndex)
						columns=rows[i].cells
						for(j=0 ; j<columns.length ; j++){
							columns[j].innerHTML=this.replaceIndexes(columns[j].innerHTML)
						}
					}
					return rows
				 },
	replaceIndexes: function(htmlString){
							htmlString=htmlString.gsub(this.row_index_indicator,this.localIndex)
							return this.replaceParentIndexes(htmlString)
						},
	replaceParentIndexes: function(htmlString){
							currentObject=this
							while(currentObject.havingParentRowInserter()){
								localTempIndex=currentObject.parent_row_index
								currentObject=currentObject.parent_row_inserter
								htmlString=htmlString.gsub(currentObject.row_index_indicator,localTempIndex)
							}
							return htmlString
						},
	getRowHtml: function(){
							return $(this.skeleton_row_division_id).innerHTML
						},
    insertRow: function() {
    						if(this.row_addition_startegy.toUpperCase()!="TABLE")
	    						new Insertion.Bottom(this.getRowDivisionElement() ,this.generateRowHtml())	
	    					else{
								localHtml=this.getRowHtml()
    							rows=this.generateRowElement()
    							for(k=0 ; k<rows.length ; k++){
    								new Element.hide(rows[k])
    								this.getRowDivisionElement().getElementsByTagName("tbody")[0].appendChild(rows[k])
    								tableRows=this.getRowDivisionElement().getElementsByTagName("tbody")[0].rows
    								new Effect.Appear(this.getRowDivisionElement().getElementsByTagName("tbody")[0].rows[tableRows.length-1], arguments[1] || {});
    							}
    							$(this.skeleton_row_division_id).innerHTML=localHtml
    						}
    						if(this.havingNestedRowInserter()){
								inNewInstance=Object.clone(AbstractRowInserterProps)
								Object.extend(inNewInstance,Object.clone(this.nested_row_inserter))
								inNewInstance.parent_row_index=this.localIndex
								inNewInstance.init()
								this.cloned_nested_row_inserters.push(inNewInstance)
								inNewInstance.parent_row_inserter=this
    						}
    					},
	preProcessRowInsertion: function(object){},
	postProcessRowInsertion: function(object){},
	preProcessRowDeletion: function(object,index){},
	postProcessRowDeletion: function(object,index){},
    deleteRow: function(index){	
    						temp=index
							new Insertion.Bottom(this.getColumnDivisionElement(index),"<input type='hidden' name='_deletedRow-"+this.replaceParentIndexes(this.path)+"-"+index+"'/>")
    						try{
    							new Effect.Puff(this.getColumnDivisionElement(index))
    						}catch(e){
    							new Element.hide(this.getColumnDivisionElement(index))
    						}
    						new Element.update(this.getColumnDivisionElement(index),this.suppressValidation($(this.getColumnDivisionID(index)).innerHTML))
    						rowHtml=this.getRowsDivisionHtml()
    					},
    addDivision: function(htmlStr){
   									return "<div id='"+this.getColumnDivisionID(this.localIndex)+"'>"+htmlStr+"</div>"
    							},
    getColumnDivisionID: function(index){
    							return this.add_row_division_id+"-"+index
    						},
    getRowsDivisionHtml: function(){
    							return $(this.add_row_division_id).innerHTML
    						},
    init: function(){
    					this.updateIndex(this.initialIndex)
    					this.cloned_nested_row_inserters= new Array()
    					if(this.havingNestedRowInserter()){
	    					for(a=0 ; a<this.initialIndex ; a++){
								newInstance=Object.clone(AbstractRowInserterProps)
								Object.extend(newInstance,Object.clone(this.nested_row_inserter))
								newInstance.parent_row_inserter=this
								newInstance.parent_row_index=a
								this.cloned_nested_row_inserters.push(newInstance)
								newInstance.init()
    						}
    					}
    					for(initRow=0 ; initRow<this.localIndex ; initRow++){
    						this.onLoadRowInitialize(this,initRow)
    					}
    				},
    onLoadRowInitialize: function(object, currentRowIndex){},
    getNestedRowInserter: function(index){
    								return cloned_nested_row_inserters[index]
    							},
   	havingNestedRowInserter: function(){
   										return this.nested_row_inserter==""?false:true
   									},
   	havingParentRowInserter: function(){
   										return this.parent_row_inserter==""?false:true
   									},
    suppressValidation: function(htmlString){
    								return htmlString.gsub(this.getRegExValidationStr(),function(match){
    																									return ""
    																								})
    							},
    getRegExValidationStr: function(){
    									//regEx="("+String.fromCharCode(34)+"|"+String.fromCharCode(39)+")"+this.validationCSSIndicator+"."+"("+String.fromCharCode(34)+"|"+String.fromCharCode(38)+")"
    									regEx=this.validationCSSIndicator
    									return regEx
    								}
}