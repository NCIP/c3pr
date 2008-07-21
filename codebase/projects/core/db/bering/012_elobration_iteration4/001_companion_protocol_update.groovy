class companionProtocolUpdate extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	dropColumn("study_subjects", "comp_assoc_id")
    	dropColumn("study_subjects", "parent_stu_sub_id")
    	addColumn("study_subjects", "comp_assoc_id", "integer")
    	addColumn("study_subjects", "parent_stu_sub_id", "integer")
    	
    	
    }
  
    void down() {
    	addColumn("study_subjects", "comp_assoc_id", "integer", nullbale : true)
    	addColumn("study_subjects", "parent_stu_sub_id", "integer" , nullbale : true)
    }
}
