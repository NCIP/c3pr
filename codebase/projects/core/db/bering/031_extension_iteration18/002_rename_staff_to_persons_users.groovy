class RenameStaffToPersonsUsers extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('oracle')) {
	    	execute("rename research_staff_id_seq to seq_research_staff_id")
	 	}
        renameTable('research_staff', 'persons_users')
        if (databaseMatches('oracle')) {
	    	    execute("rename seq_persons_users_ID to persons_users_ID_SEQ")
 	    }
 	    addColumn('persons_users','person_user_type','string');
 	    
 	    execute('ALTER TABLE persons_users drop CONSTRAINT uq_assignedid_rs');
 	    execute("ALTER TABLE study_personnel rename column research_staff_id to persons_users_id");
 	    
    }

    void down() {
		if (databaseMatches('postgres')) {
	    	 execute("alter table persons_users_id_seq RENAME TO SEQUENCE research_staff_ID_seq")	 		 
	 	}
	 	renameTable('persons_users', 'research_staff')
    	if (databaseMatches('oracle')) {
	    	 execute("rename persons_users_ID_SEQ TO SEQUENCE research_staff_id_seq")	 		 
	 	}
    }
}

