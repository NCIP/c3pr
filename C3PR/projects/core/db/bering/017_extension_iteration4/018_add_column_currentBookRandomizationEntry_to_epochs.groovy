class AddColumnCurrentBookRandomizationEntryToEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    if (databaseMatches('postgres')){
	       	addColumn("epochs","current_book_randomization_entry","integer");
	     }
    }
  
    void down() {
	    if (databaseMatches('postgres')){
	        dropColumn("epochs","current_book_randomization_entry");
	     }
    }
}

