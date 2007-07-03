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
var RowManager = Class.create();
var RowManager = {
	addRow: function(inserter){this.execute(true,inserter,-1)},
	deleteRow: function(inserter,delIndex){this.execute(false,inserter,delIndex)},
	execute: function(isAddRow, inserter, deletionIndex){
					if(isAddRow){
					    inserter.preProcessRowInsertion()
						inserter.insertRow()
						inserter.localIndex++
						inserter.postProcessRowInsertion()
					}else{
						inserter.preProcessRowDeletion()
						inserter.deleteRow(deletionIndex)
						inserter.postProcessRowDeletion()
					}
				},
}

var rowInserters=new Array()
Event.observe(window, "load", function() {
	for(i=0 ; i<rowInserters.length ; i++){
		registerRowInserter(rowInserters[i])
	}
})
function registerRowInserter(rowInserter){
	clone=Object.clone(rowInserter)
	Object.extend(rowInserter,AbstractRowInserterProps)
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
    generateRowHtml: function() {
    				rowHtml=this.getRowHtml()
    				rowHtml= rowHtml.gsub(this.row_index_indicator,this.localIndex)    
					rowHtml=this.addDivision(rowHtml)
					return rowHtml
				 },
	generateRowElement: function() {
					localTable=$($(this.skeleton_row_division_id)).getElementsByTagName("table")[0]
					//rows=localTable.getElementsByTagName("tr")
					rows=localTable.rows
					retRows=new Array()
					for(i=0 ; i<rows.length ; i++){
						rows[i].id=this.getColumnDivisionID(this.localIndex)
						columns=rows[i].getElementsByTagName("td")
						for(j=0 ; j<columns.length ; j++){
							columns[j].innerHTML=columns[j].innerHTML.gsub(this.row_index_indicator,this.localIndex)
						}
					}
					return rows
				 },
	getRowHtml: function(){
							return $(this.skeleton_row_division_id).innerHTML
						},
    insertRow: function() {
    						if(this.row_addition_startegy.toUpperCase()!="TABLE")
	    						new Insertion.Bottom(this.add_row_division_id ,this.generateRowHtml())	
	    					else{
									localHtml=this.getRowHtml()
	    							rows=this.generateRowElement()
	    							for(i=0 ; i<rows.length ; i++)
	    								$(this.add_row_division_id).getElementsByTagName("tbody")[0].appendChild(rows[i])
	    							$(this.skeleton_row_division_id).innerHTML=localHtml
	    						}
    					},
	preProcessRowInsertion: function(){},
	postProcessRowInsertion: function(){},
	preProcessRowDeletion: function(){},
	postProcessRowDeletion: function(){},
    deleteRow: function(index){	
    						temp=index
							new Insertion.Bottom(this.getColumnDivisionID(index),"<input type='hidden' name='_deletedRow-"+this.path+"-"+index+"'/>")
    						new Element.hide(this.getColumnDivisionID(index))
    						new Element.update(this.getColumnDivisionID(index),this.suppressValidation($(this.getColumnDivisionID(index)).innerHTML))
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
    					this.localIndex=this.initialIndex
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