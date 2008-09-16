class CreateScheduledEpochsAndAssociations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
 
         createTable("scheduled_epochs") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("spa_id", "integer", nullable: false)
            t.addColumn("eph_id", "integer", nullable: false)
            t.addColumn("type", "string")
            t.addColumn("start_date", "date")
            t.addColumn("eligibility_indicator", "boolean")
            t.addColumn("eligibility_waiver_reason_text", "string")
            t.addColumn("registration_status", "string")                        
        }
        execute("ALTER TABLE SCHEDULED_EPOCHS ADD CONSTRAINT FK_SCEPH_SS FOREIGN KEY (SPA_ID) REFERENCES STUDY_SUBJECTS (ID)");
        execute("ALTER TABLE SCHEDULED_EPOCHS ADD CONSTRAINT FK_SCEPH_EPH	FOREIGN KEY (EPH_ID) REFERENCES EPOCHS (ID)");
        
        dropColumn('scheduled_arms','spa_id');
        // ensure that the new constraint will be applicable
        execute("DELETE FROM scheduled_arms");
        addColumn('scheduled_arms','sceph_id','integer',nullable:false);
        execute("ALTER TABLE SCHEDULED_ARMS ADD CONSTRAINT FK_SCA_SCEPH FOREIGN KEY (SCEPH_ID) REFERENCES SCHEDULED_EPOCHS (ID)");
        
        dropColumn('subject_strat_ans','SPA_ID');
        dropColumn('subject_strat_ans','STR_CRI_ID');        
        // ensure that the new constraint will be applicable
        execute("DELETE FROM subject_strat_ans");
        addColumn('subject_strat_ans','sceph_id','integer', nullable:false);
        addColumn('subject_strat_ans','STR_CRI_ID', 'integer', nullable: false);                
        execute("ALTER TABLE subject_strat_ans ADD CONSTRAINT FK_PSA_SCEPH	FOREIGN KEY (SCEPH_ID) REFERENCES SCHEDULED_EPOCHS (ID)");
        execute("ALTER TABLE subject_strat_ans ADD CONSTRAINT FK_PSA_SC FOREIGN KEY (STR_CRI_ID) REFERENCES strat_criteria (ID)");
        
        dropColumn('SUBJECT_ELIGIBILITY_ANS','SPA_ID');
        // ensure that the new constraint will be applicable
        execute("DELETE FROM SUBJECT_ELIGIBILITY_ANS");
        addColumn('SUBJECT_ELIGIBILITY_ANS','sceph_id', 'integer', nullable:false);
        execute("ALTER TABLE SUBJECT_ELIGIBILITY_ANS ADD CONSTRAINT FK_SEA_SS FOREIGN KEY (SCEPH_ID) REFERENCES SCHEDULED_EPOCHS (ID)");
    }

    void down() {
        dropTable('scheduled_epochs')
        
        execute("DELETE FROM scheduled_arms");
		addColumn('scheduled_arms','spa_id');
        // ensure that the new constraint will be applicable
        dropColumn('scheduled_arms','sceph_id');
        execute("ALTER TABLE SCHEDULED_ARMS ADD CONSTRAINT FK_SCA_SS FOREIGN KEY (SPA_ID) REFERENCES STUDY_SUBJECTS (ID)");        
        
        addColumn('subject_strat_ans','SPA_ID');
        dropColumn('subject_strat_ans','STR_CRI_ID');        
        // ensure that the new constraint will be applicable
        dropColumn('scheduled_arms','sceph_id');
        addColumn('subject_strat_ans','STR_CRI_ID');
		execute("ALTER TABLE subject_strat_ans ADD CONSTRAINT FK_PSA_SC FOREIGN KEY (STR_CRI_ID) REFERENCES strat_criteria (ID)");        

        addColumn('SUBJECT_ELIGIBILITY_ANS','SPA_ID');
        // ensure that the new constraint will be applicable
        dropColumn('scheduled_arms','sceph_id');
    }
}

