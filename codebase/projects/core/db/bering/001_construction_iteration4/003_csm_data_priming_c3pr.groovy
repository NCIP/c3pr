class CreateCSMDataPriming extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
            external("../../oracle/csm/2_DataPriming.sql")
        } else if (databaseMatches('postgresql')){
            external("../../PostGreSQL/csm/2_DataPriming.sql")
        }
    }

    void down() {
        execute("DELETE FROM csm_user_group");
        execute("DELETE FROM csm_protection_element");
        execute("DELETE FROM csm_privilege");
        execute("DELETE FROM csm_group");
        execute("DELELTE FROM csm_user_pe");
        execute("DELETE FROM csm_application");
    }
}

