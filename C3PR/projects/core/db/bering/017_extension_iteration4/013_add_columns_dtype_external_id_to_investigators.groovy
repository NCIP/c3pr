class AddColumnsDtypeExternalIdToInvestigators extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       	addColumn("investigators","dtype","string");
        addColumn("investigators","unique_identifier","string");
    }
  
    void down() {
        dropColumn("investigators","unique_identifier");
        dropColumn("investigators","dtype");
    }
}

