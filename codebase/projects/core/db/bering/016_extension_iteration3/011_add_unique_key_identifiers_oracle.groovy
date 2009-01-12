class AddUniqueKeyIdentifiersOracle extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {

        if (databaseMatches('oracle')) {
	    	execute("alter table identifiers add constraint uk_type_source_target unique(stu_id,prt_id,spa_id,type,hcs_id,system_name)")
	 	}     
    }
  
    void down() {
    	if (databaseMatches('oracle')) {
	    	execute("alter table identifiers drop constraint uk_type_source_target")
	 	}   
    }
}
