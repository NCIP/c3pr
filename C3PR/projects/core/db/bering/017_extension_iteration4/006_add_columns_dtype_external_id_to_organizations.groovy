class AddColumnsDtypeExternalIdToOrganizations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {

       	addColumn("organizations","dtype","string");
        addColumn("organizations","external_id","string");
    }
  
    void down() {
        dropColumn("organizations","external_id");
        dropColumn("organizations","dtype");
    }
}

