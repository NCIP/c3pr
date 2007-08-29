class CreateRandomization extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
 
 		createTable("strat_criterion_answer") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")  
            t.addColumn("sc_id", "integer")
            t.addColumn("scpa_id", "integer")
            t.addColumn("str_grp_id", "integer")
        }
       execute("ALTER TABLE strat_criterion_answer ADD CONSTRAINT FK_SCAC_SC FOREIGN KEY (SC_ID) REFERENCES STRATIFICATION_CRITERION(ID)");
       execute("ALTER TABLE strat_criterion_answer ADD CONSTRAINT FK_SCAC_SCPA FOREIGN KEY (SCPA_ID) REFERENCES STRATIFICATION_CRI_PER_ANS(ID)");
                           

       createTable("stratum_group") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("current_position", "integer")
            t.addColumn("stratum_group_number", "integer")
            //book_randomization_entry id
            //t.addColumn("bre_id", "integer")
            //treatment epoch id
            t.addColumn("epochs_id", "integer")
        }       
       execute("ALTER TABLE stratum_group ADD CONSTRAINT FK_STR_GRP_EPOCHS FOREIGN KEY (epochs_id) REFERENCES EPOCHS(ID)");
		//adding fk contraint to strat_criterion_answer
       execute("ALTER TABLE strat_criterion_answer ADD CONSTRAINT FK_SCAC_STR_GRP FOREIGN KEY (STR_GRP_ID) REFERENCES STRATUM_GROUP(ID)");
        
        createTable("book_randomization_entry") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("position", "integer")
            //stratum group id
            t.addColumn("str_grp_id", "integer")
            //arm id
            t.addColumn("arms_id", "integer")
            //randomization id
            t.addColumn("rndm_id", "integer")            
        }
        execute("ALTER TABLE BOOK_RANDOMIZATION_ENTRY ADD CONSTRAINT FK_BRE_ARMS FOREIGN KEY (ARMS_ID) REFERENCES ARMS(ID)");
		execute("ALTER TABLE BOOK_RANDOMIZATION_ENTRY ADD CONSTRAINT FK_BRE_STR_GRP FOREIGN KEY (STR_GRP_ID) REFERENCES STRATUM_GROUP(ID)");
		
        //adding the f_key to arms that references book_randomization_entry		
        //addColumn('arms','bre_id','integer',nullable:true);
        //execute("ALTER TABLE ARMS ADD CONSTRAINT FK_ARMS_BRE FOREIGN KEY (BRE_ID) REFERENCES BOOK_RANDOMIZATION_ENTRY(ID)");
        //adding the f_key to stratum group 
        //execute("ALTER TABLE stratum_group ADD CONSTRAINT FK_STR_GRP_BRE FOREIGN KEY (bre_id) REFERENCES BOOK_RANDOMIZATION_ENTRY(ID)");
        
        createTable("randomizations") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("dtype", "string")
            t.addColumn("callout_url","string")
        }
        //adding the fk constraint to bre		
		execute("ALTER TABLE BOOK_RANDOMIZATION_ENTRY ADD CONSTRAINT FK_BRE_RNDM FOREIGN KEY (RNDM_ID) REFERENCES randomizations(ID)");
        
        addColumn('epochs','rndm_id','integer',nullable:true);
        execute("ALTER TABLE EPOCHS ADD CONSTRAINT FK_EPOCHS_RNDM FOREIGN KEY (RNDM_ID) REFERENCES RANDOMIZATIONS(ID)");
        
		//inserting dummy values for f_key fields before enforcing nullable=false
		//setNullable('RANDOMIZATION', 'BRE_ID', true)
		//setNullable('RANDOMIZATION', 'DTYPE', true)
		//execute("insert into randomizations(bre_id, dtype, version) values ('1','BK_RNDM','1')")
    	//execute("update randomizations set bre_id=1")
    	//execute("update randomizations set dtype='BK_RNDM'")
    	//setNullable('RANDOMIZATIONS', 'BRE_ID', false)
	   	//setNullable('RANDOMIZATIONS', 'DTYPE', false) 
	   	
		//setNullable('book_randomization_entry', 'arms_id', true)
		//execute("insert into arms('id') values ('100')")
		execute("insert into stratum_group(id) values ('101')")
		//execute("insert into book_randomization_entry('str_grp_id') values ('101')")
		execute("update book_randomization_entry set str_grp_id=101")
		//execute("update book_randomization_entry set arms_id=100")
	   	setNullable('book_randomization_entry', 'str_grp_id', false)
		//setNullable('book_randomization_entry', 'arms_id', false)  
	   	
	   	//Kruttik added this line
	   	setNullable('ARMS', 'EPH_ID', true) 
	   	//setNullable('stratum_group', 'epochs_id', true)
    }

    void down() {
        
        dropTable('strat_criterion_answer')
        dropTable('stratum_group')  
        dropColumn('epochs','rndm_id')
        dropTable('book_randomization_entry')
        dropTable('randomizations')
        
        
        //prev file's code       
        //execute("DELETE FROM scheduled_arms");
		//addColumn('scheduled_arms','spa_id');
        // ensure that the new constraint will be applicable
        //dropColumn('scheduled_arms','sceph_id');
        //execute("ALTER TABLE SCHEDULED_ARMS ADD CONSTRAINT FK_SCA_SS FOREIGN KEY (SPA_ID) REFERENCES STUDY_SUBJECTS (ID)");        
    }
}

