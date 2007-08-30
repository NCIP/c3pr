class CreateStudyAmendments extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	        createTable('study_amendments') { t ->
	            	t.addColumn('amendment_version','integer', nullable:true) 
   	           	 	t.addColumn('amendment_date','date', nullable:true)
   	           	 	t.addColumn('comments', 'string', nullable:true)
   	           	 	t.addColumn('irb_approval_date', 'date', nullable:false)
   	           	 	t.addColumn('stu_id','integer', nullable:false) 
	            	t.addVersionColumn()
	            	t.addColumn('grid_id' , 'string' , nullable:true)
        		}
 				//now add the constraints
 				execute('alter table study_amendments add constraint fk_stuamnd_stu_id foreign key (stu_id) references studies (id)')
 				execute("ALTER TABLE study_amendments ALTER COLUMN id SET DEFAULT nextval('study_amendments_id_seq'::regclass)")
    }

    void down() {
        dropTable('study_amendments')
    }
}