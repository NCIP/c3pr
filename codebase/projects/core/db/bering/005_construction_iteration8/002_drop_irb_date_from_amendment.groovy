class DropIrbDateFromAmendment extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		dropColumn('study_amendments','irb_approval_date');   			
   	 }

    void down() {
    	addColumn('study_amendments', 'irb_approval_date','date', nullable:false)
    	
	}
	
}