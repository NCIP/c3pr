class AddColumnsDtypeUniqueIdentifierToResearchStaff extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {

       	addColumn("research_staff","dtype","string");
        addColumn("research_staff","unique_identifier","string");
    }
  
    void down() {
    	dropColumn("research_staff","dtype");
        dropColumn("research_staff","unique_identifier");
    }
}

