var RowManager = Class.create();
var RowManager = {
	addRow: function(inserter){this.execute(true,inserter,-1)},
	deleteRow: function(inserter,delIndex){this.execute(false,inserter,delIndex)},
	execute: function(isAddRow, inserter, deletionIndex){
					if(inserter.methodExecOrder.toUpperCase()=="Before")
						inserter.methodsToInvoke()
					if(isAddRow){
						inserter.insertRow()
						inserter.localIndex++
					}else{
						inserter.deleteRow(deletionIndex)
					}
					
				},
}

var rowInserters=new Array()
Event.observe(window, "load", function() {
	extendRowInserters()

})
function extendRowInserters(){
	for(i=0 ; i<rowInserters.length ; i++){
		clone=Object.clone(rowInserters[i])
		Object.extend(rowInserters[i],AbstractRowInserterProps)
		Object.extend(rowInserters[i],clone)
		rowInserters[i].init()
	}
}
/* Abstract Implementation of an row-inseter object*/
var AbstractRowInserterProps = Class.create()
temp=-1
var AbstractRowInserterProps = {
	add_row_division_id: "row-table",
	skeleton_row_division_id: "dummy-row",
	initialIndex: -1,
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
					rows=localTable.getElementsByTagName("tr")
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
    methodExecOrder: "After",
    methodsToInvoke: function(){		},
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
    					this.localIndex=this.initialIndex+1
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