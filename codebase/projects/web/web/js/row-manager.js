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
// To enable logging, set RowManager.debug to true. Row Manager will look for a division with id 'workAreaLog' in the html document to log.
// However this can be overriden by setting the RowManager.loggerDiv property.
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
	isSubmitted: false,
	rowInserters: new Array(),
	rowInsertersForDeletion: new Array(),
	debug: false,
	addRow: function(inserter){!this.isSubmitted?this.execute(true,inserter,-1,-1):this.errorLog("Row Manager inactivated")},
	deleteRow: function(inserter,delIndex,hashCode){
					if(this.isSubmitted){
						this.errorLog("Row Manager inactivated")
						return
					}
					flagDelLocal=this.execute(false,inserter,delIndex,hashCode)
					if(flagDelLocal && !inserter.softDelete){
						inserter.deletedRows.push(delIndex)
						if(!this.rowInsertersForDeletion.include(inserter))
							this.rowInsertersForDeletion.push(inserter)
					}
				},
	execute: function(isAddRow, inserter, deletionIndex,hashCode){
					if(isAddRow){
					    inserter.preProcessRowInsertion(inserter)
						inserter.insertRow()
						inserter.postProcessRowInsertion(inserter)
						inserter.localIndex++						
					}else{
						flagDel=inserter.shouldDelete(inserter,deletionIndex)
						if(!flagDel)
							return false
						inserter.preProcessRowDeletion(inserter,deletionIndex)
						inserter.handleRowRemoval(deletionIndex,hashCode)
						inserter.postProcessRowDeletion(inserter,deletionIndex)
						return true
					}
				},
	getNestedRowInserter: function(inserter, index){
								return inserter.cloned_nested_row_inserters[index]
							},
	getSecondaryNestedRowInserter: function(inserter, index){
								return inserter.secondary_cloned_nested_row_inserters[index]
							},
	addRowInseter: function (inserter){
						this.rowInserters.push(inserter)
					},
	registerRowInserters: function(){
								for(x=0 ; x<this.rowInserters.length ; x++){
									if(this.rowInserters[x].isRegistered==null){
										this.registerRowInserter(this.rowInserters[x])
										//alert("registered "+rowInserters[x].add_row_division_id)
									}
								}
							},
	registerRowInserter: function(rowInserter){
								clone=Object.clone(AbstractRowInserterProps)
								Object.extend(clone,rowInserter)
								Object.extend(rowInserter,clone)
								rowInserter.init()
							},
	loggerDiv: "workAreaLog",
	log: function(string){
						if(RowManager.debug)
							document.getElementById(RowManager.loggerDiv)!=null?new Insertion.Bottom(RowManager.loggerDiv,string):null
					},
	clearLog: function(){
						document.getElementById(RowManager.loggerDiv)!=null?new Element.update(RowManager.loggerDiv,""):null
					},
	errorLog: function(str){
					this.log("<br><span style='color:red;font-weight:bolder;'>----------------------<br>"+str+"<br>-------------------------</span><br>")
				},
	updateIndexesOfRows: function(){
								if(this.isSubmitted){
									this.errorLog("Row Manager inactivated")
									return
								}
								for(k=0 ; k<this.rowInsertersForDeletion.length ; k++)
									this.rowInsertersForDeletion[k].updateSubmitParamsRows()
								this.isSubmitted=true
							},
	onValidateSubmit: function(formElement){
							this.log("Updating Indexes of Rows")
							this.updateIndexesOfRows()
						}
}
Event.observe(window, "load", function() {
	RowManager.registerRowInserters()
	for(x=0 ; x<RowManager.rowInserters.length ; x++){
		RowManager.rowInserters[x].onLoadInitialize(RowManager.rowInserters[x])
	}
	ValidationManager.registerToInvoke(RowManager)
})
/* Abstract Implementation of an row-inseter object*/
var AbstractRowInserterProps = Class.create()
var AbstractRowInserterProps = {
	add_row_division_id: "row-table",
	path: "",
	skeleton_row_division_id: "dummy-row",
	initialIndex: 0,
	localIndex: -1,
	validationCSSIndicator: "validate-",
	row_index_indicator: "PAGE.ROW.INDEX",
	row_addition_startegy: "table",
	cloned_nested_row_inserters: "",
	secondary_cloned_nested_row_inserters: "",
	deletedRows: "",
	nested_row_inserter: "",
	secondary_nested_row_inserter: "",
	parent_row_inserter: "",
	parent_row_index: -1,
	isRegistered: true,
	softDelete: false,
	isAdmin: false,
	alertOnSoftDelete: true,
	callRemoveFromCommand: false,
	deleteMsgPrefix:"",
	reset: function(index){
					this.initialIndex=index
					this.init()
				},
	updateIndex: function(index){
						this.localIndex=index
						this.initialIndex=index
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
	getRowDivisionString: function(index){
							elementString="'"+this.getColumnDivisionID(index)+"'"
							if(this.havingParentRowInserter()){
								elementString+=" in "+this.getTableString()
							}
							return elementString
						},
	getTableString: function(){
							tableString= "'"+this.add_row_division_id+"'"
							if(this.havingParentRowInserter()){
								tableString+=" in '"+this.parent_row_inserter.getColumnDivisionID(this.parent_row_index)+"'"
							}
							return tableString
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
    						if(this.havingSecondaryNestedRowInserter()){
								inNewInstance=Object.clone(AbstractRowInserterProps)
								Object.extend(inNewInstance,Object.clone(this.secondary_nested_row_inserter))
								inNewInstance.parent_row_index=this.localIndex
								inNewInstance.init()
								this.secondary_cloned_nested_row_inserters.push(inNewInstance)
								inNewInstance.parent_row_inserter=this
    						}
    					},
	preProcessRowInsertion: function(object){},
	postProcessRowInsertion: function(object){},
	preProcessRowDeletion: function(object,index){},
	postProcessRowDeletion: function(object,index){},
	//postProcessCommandUpdate: function(object,index,hashCode){},	
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
    					this.deletedRows=new Array()
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
    					this.secondary_cloned_nested_row_inserters= new Array()
    					if(this.havingSecondaryNestedRowInserter()){
	    					for(a=0 ; a<this.initialIndex ; a++){
								newInstance=Object.clone(AbstractRowInserterProps)
								Object.extend(newInstance,Object.clone(this.secondary_nested_row_inserter))
								newInstance.parent_row_inserter=this
								newInstance.parent_row_index=a
								this.secondary_cloned_nested_row_inserters.push(newInstance)
								newInstance.init()
    						}
    					}
    					for(initRow=0 ; initRow<this.localIndex ; initRow++){
    						this.onLoadRowInitialize(this,initRow)
    					}
    				},
    onLoadRowInitialize: function(object, currentRowIndex){
    						},
	onLoadInitialize: function(object){
							if(this.softDelete && !this.isAdmin){
								for(localInd=0 ; localInd<this.initialIndex ; localInd++)
									new Element.descendants(object.getColumnDivisionElement(localInd)).each(function(e){e.disabled="true"})
							}							
						},    						
    getNestedRowInserter: function(index){
    								return cloned_nested_row_inserters[index]
    							},
	getSecondaryNestedRowInserter: function(index){
    								return secondary_cloned_nested_row_inserters[index]
    							},    							
   	havingNestedRowInserter: function(){
   										return this.nested_row_inserter==""?false:true
   									},
   	havingSecondaryNestedRowInserter: function(){
   										return this.secondary_nested_row_inserter==""?false:true
   									},
   	havingParentRowInserter: function(){
   										return this.parent_row_inserter==""?false:true
   									},
   	handleRowRemoval: function(index,hashCode){ 
   	  						this.removeRowFromDisplay(index)	
   							if(index<this.initialIndex || this.callRemoveFromCommand){
   								if(hashCode != "-1"){
   									this.removeFromCommand(index,hashCode)   								
   								}
   							}
   						},
    disableRow: function(index){
    						RowManager.log("------------------------<br>")
							logString="Disabling Row in display: "+this.getRowDivisionString(index)+"<div style='padding-left:20px;'>"
							logString+="row inserter path:"+this.path+"<br>"
							deletionMessage=""
							if(RowManager.debug)
								deletionMessage="<b>(deleted upon request)</b>"
							//new Element.descendants(this.getColumnDivisionElement(index)).each(function(e){e.style.backgroundColor="red";})
							new Element.descendants(this.getColumnDivisionElement(index)).each(function(e){e.style.background="white url('../../images/redStripes.jpg') repeat"})
							//this.getColumnDivisionElement(index).style.background="url(/images/redStripes.jpg) repeat"
							RowManager.log(logString+"</div>")
    						RowManager.log("------------------------<br>")							
						},
    removeRowFromDisplay: function(index){
    						RowManager.log("------------------------<br>")
							logString="Removing Row from display: "+this.getRowDivisionString(index)+"<div style='padding-left:20px;'>"
							logString+="row inserter path:"+this.path+"<br>"
							deletionMessage=""
							if(RowManager.debug)
								deletionMessage="<b>(deleted upon request)</b>"
							new Element.update(this.getColumnDivisionElement(index),deletionMessage)
							idString=this.getColumnDivisionElement(index).id
							this.getColumnDivisionElement(index).id=idString.substring(0,idString.lastIndexOf("-"))+" "+idString.substring(idString.lastIndexOf("-")+1,idString.length)+" deleted"
							RowManager.log(logString+"</div>")
    						RowManager.log("------------------------<br>")							
						},
	removeFromCommand: function(index,hashCode){
							$('rowDeletionForm').collection.value=this.replaceParentIndexes(this.path)
							$('rowDeletionForm').deleteIndex.value=index
							$('rowDeletionForm').deleteHashCode.value=hashCode
							parameterString="decorator=nullDecorator&_asynchronous=true&_asyncMethodName=deleteRow&"+Form.serialize('rowDeletionForm')
							if(this.softDelete)
								parameterString="softDelete=true&"+parameterString
							new Ajax.Request($('rowDeletionForm').action, 
								{parameters:parameterString,
								onSuccess: this.onDeleteFromCommandSuccess, onFailure: this.onDeleteFromCommandError, asynchronous:true, evalScripts:true})
						},
	onDeleteFromCommandError: function(t){
						alert("Error Deleting Row. Please refresh the page to reload the deleted row.")
					},
	onDeleteFromCommandSuccess: function(t){
   						RowManager.log("Row deletion Message: "+t.responseText)
   						//alert("in onDeleteFromCommandSuccess")
   						rets=t.responseText.split("||")
   						//this.postProcessCommandUpdate(this,rets[0].split("=")[1],rets[1].split("=")[1])
					},					
	shouldDelete: function(inserter, index){
						if(index<this.initialIndex && this.alertOnSoftDelete){
							return confirm(this.deleteMsgPrefix+ " Are you sure you want to delete this row?")
						}
						return true
					},
	updateSubmitParamsRows: function(){
    						RowManager.log("------------------------<br>")
							RowManager.log("Deleting Rows from "+this.getTableString()+"<br>")
							elements=new Array()
							try{
								if(this.row_addition_startegy.toUpperCase()=="TABLE"){
									elements=this.getRowDivisionElement().rows
								}else{
									elements=new Element.immediateDescendants(this.add_row_division_id)
								}
							}catch(error){
								RowManager.errorLog(error.message+"<br>")
								return
							}
							RowManager.log("no of rows in containing table: "+elements.length+"<br>")
							for(l=0 ; l<this.deletedRows.length ; l++){
								RowManager.log("deleting row number "+this.deletedRows[l]+"<br>")
								this.updateSubmitParamRow(elements,this.deletedRows[l])
							}
    						RowManager.log("------------------------<br>")							
						},
	updateSubmitParamRow: function(rows,index){
						RowManager.log("Row to delete : '"+this.getColumnDivisionID(index)+"'<br>")
						logString="<div style='padding-left:20px;'>"
						for(i=0 ; i<rows.length ; i++){
							if(rows[i].id>this.getColumnDivisionID(index)){
								logString+="Row to update index:"
								logString+="{"+rows[i].id+"}"
								logString+="<br>"
							}else{
								logString+="Row not to update index:"
								logString+="{"+rows[i].id+"}"
								logString+="<br>"							
							}
						}
						logString+="updating indexes with regular expression: "+this.getRegEx()+"<br>"					
						for(i=0 ; i<rows.length ; i++){
							if(rows[i].id>this.getColumnDivisionID(index)){
								logString+=("elements in row: "+rows[i].id+"<div style='padding-left:20px;'>")
								subElements=new Element.descendants(rows[i])
								for(j=0 ; j<subElements.length ; j++)
									logString+=this.reduceIndexOfElement(subElements[j])
								logString+="</div>"
							}
						}
						logString+="</div>"
						RowManager.log(logString)
					},
	getRegEx: function(){
					props=this.path.split(".")
					property=props[props.length-1]
					return "("+property+")\\[(\\d)\\]"
				},
	reduceIndexOfElement: function(element, index){
									if(element==null){
										return
									}
									idLocal="undefined"
									nameLocal="undefined"
									if(element.id!=null){
										idLocal=element.id
										element.id=element.id.gsub(this.getRegEx(),function(matches){
																				return matches[1]+"["+parseInt(matches[2]-1)+"]"
																			}
																	)
									}
									if(element.name!=null){
										nameLocal=element.name
										element.name=element.name.gsub(this.getRegEx(),function(matches){
																				return matches[1]+"["+parseInt(matches[2]-1)+"]"
																			}
																	)
									}
									return "element: ["+idLocal+"] of type ["+element.type+"] and name ["+nameLocal+"]<br>"
								}
	}