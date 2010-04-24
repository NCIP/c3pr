class AlterStuSubDemographicsDropEthnicGroupCodeNotNullConstraint extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    
        if (databaseMatches('postgres')){
       		execute("alter table stu_sub_demographics alter column ethnic_group_code drop not null");
       	}
     	if (databaseMatches('oracle')){
       		execute("alter table stu_sub_demographics modify ethnic_group_code null");
       	}
	 	
	}
	
	void down() {
    }
}



