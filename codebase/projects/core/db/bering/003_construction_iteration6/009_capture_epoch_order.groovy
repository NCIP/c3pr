class CaptureEpochOrder extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
    
    	addColumn('EPOCHS', "epoch_order", "integer")
   		
   		 		
   		
   	 }

    void down() {
    
   		dropColumn('EPOCHS','epoch_order');
   	
   			}
	
}