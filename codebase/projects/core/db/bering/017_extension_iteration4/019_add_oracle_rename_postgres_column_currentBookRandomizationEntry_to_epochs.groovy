class AddColumnOracleRenamePostgresCurrentBookRandomizationEntryToEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    if (databaseMatches('postgres')){
	       	renameColumn("epochs","current_book_randomization_entry","current_bk_rand_entry");
	     }
	    if (databaseMatches('oracle')){
	       	addColumn("epochs","current_bk_rand_entry","integer");
	     }
    }
  
    void down() {
	    if (databaseMatches('postgres')){
	        renameColumn("epochs","current_bk_rand_entry","current_book_randomization_entry");
	     }
	    if (databaseMatches('oracle')){
	        dropColumn("epochs","current_bk_rand_entry");
	     }
    }
}

