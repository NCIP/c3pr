class UpdateRolePrivilegeDataImporter extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(nextval('role_privileges_ID_SEQ'),0,'data_importer','edu.duke.cabig.c3pr.domain.Study','READ')");
       	}
     	if (databaseMatches('oracle')){
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(role_privileges_ID_SEQ.NEXTVAL,0,'data_importer','edu.duke.cabig.c3pr.domain.Study','READ')");
       	}
	}
	
	void down() {
    }
}



