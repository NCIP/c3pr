class RenameRecepients extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		renameTable('RECEPIENTS', 'RECIPIENTS')
		
		if (databaseMatches('oracle')) {
	   		execute("rename recepients_ID_SEQ to recipients_ID_SEQ")
 	    }
 	    if (databaseMatches('postgres')) {
    	    //execute("alter table seq_recepients_ID rename to seq_recipients_ID")
 	    }
    }

    void down() {
    	renameTable('RECIPIENTS', 'RECEPIENTS')
    	if (databaseMatches('oracle')) {
	   		execute("rename recipients_ID_SEQ to recepients_ID_SEQ")
 	    }
 	    if (databaseMatches('postgres')) {
    	    //execute("rename seq_recipients_ID to seq_recepients_ID")
 	    }
    }
}