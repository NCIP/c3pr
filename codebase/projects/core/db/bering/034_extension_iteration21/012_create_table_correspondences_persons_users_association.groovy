class CreateTableCorrespondencesPersonsUsersAssociation extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    
    	createTable("corr_per_users_assoc") { t ->
            t.addColumn("corr_id", "integer", nullable: false)
            t.addColumn("pers_user_id", "integer", nullable: false)
         }
         
         
		execute('ALTER TABLE corr_per_users_assoc ADD CONSTRAINT FK_CORR_PERS_USR_CORR FOREIGN KEY (corr_id) REFERENCES correspondences(ID)');
		execute('ALTER TABLE corr_per_users_assoc ADD CONSTRAINT FK_CORR_PERS_USR_PERS_USR FOREIGN KEY (pers_user_id) REFERENCES persons_users(ID)');
		dropColumn('corr_per_users_assoc','id');
		
		execute('alter table corr_per_users_assoc add PRIMARY KEY(corr_id,pers_user_id)');
		
	}
	void down() {
    }
}
