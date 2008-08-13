class updateEpochReservationIndicatorTreatmentIndicator extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    if (databaseMatches('postgresql')){
	       execute("update epochs set reservation_indicator='false' where treatment_indicator='true'");
	       execute("update epochs set randomized_indicator='false' where randomized_indicator=null;");
        }
        
    if (databaseMatches('oracle')){
	       execute("update epochs set reservation_indicator='no' where treatment_indicator='yes'");
	       execute("update epochs set randomized_indicator='no' where randomized_indicator='';");
        }
	      
    }

    void down() {
       
    }
}