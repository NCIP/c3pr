class AddSubjectStratAnsIdSeq extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('oracle')){
    		execute("CREATE SEQUENCE subject_strat_ans_id_seq INCREMENT BY 1 START WITH 1 NOMAXVALUE MINVALUE 1 NOCYCLE NOCACHE NOORDER;");
    	}
    }

    void down() {
        if (databaseMatches('oracle')){
    	}
    }
}