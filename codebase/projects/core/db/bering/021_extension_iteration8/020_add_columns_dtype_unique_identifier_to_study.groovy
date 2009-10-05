class AddColumnsDtypeUniqueIdentifierToResearchStaff extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {

       	addColumn("studies","dtype","string");
        addColumn("studies","external_id","string");
    }
  
    void down() {
    	dropColumn("studies","dtype");
        dropColumn("studies","external_id");
    }
}