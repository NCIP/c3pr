class AddCCTSDemoUser extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		    insert('csm_user',[user_id: -1001, LOGIN_NAME: 'cctsdemo1@nci.nih.gov',FIRST_NAME: 'ccts', LAST_NAME: 'demo1', PASSWORD: 't2HgEhc2LJA=',email_id: 'cctsdemo1@nci.nih.gov'],primaryKey: false)
		    insert('csm_user_group',[user_group_id: -1001, user_id: -1001, group_id: 1],primaryKey: false)

    }

    void down() {

	        execute("delete from CSM_USER where user_id IN (-1001)");
	        execute("delete from CSM_USER_GROUP where user_id IN (-1001)");
    }
}