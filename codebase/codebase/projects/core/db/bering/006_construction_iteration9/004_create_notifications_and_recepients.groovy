class CreateNotificationsAndRecepients extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	createTable('notifications') { t ->
    		t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn('retired_indicator', 'string' ); 
	        t.addColumn('threshold','integer', nullable:false) 
   	        t.addColumn('stu_id','integer', nullable:false) 	            	
        } 				
 		execute('alter table notifications add constraint fk_stu_id foreign key (stu_id) references studies(id)') 		
 		
 		createTable('recepients') { t ->
 			t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("dtype", "string") 
            t.addColumn('retired_indicator', 'string' ); 
	        t.addColumn('email_address','string') 
   	        t.addColumn('role','string') 
   	        t.addColumn('notifications_id','integer', nullable:false) 	            	
        } 				
 		execute('alter table recepients add constraint fk_notifications_id foreign key (notifications_id) references notifications(id)') 	
 		
 		if (databaseMatches('oracle')) {
    	    execute("rename seq_notifications_ID to notifications_ID_SEQ")
    	    execute("rename seq_recepients_ID to recepients_ID_SEQ")
 	    }	
    }

    void down() {
        dropTable('notifications')
        dropTable('recepients');
    }
}