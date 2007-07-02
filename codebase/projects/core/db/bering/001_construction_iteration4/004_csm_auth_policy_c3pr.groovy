class CreateCSMAuthPolicy extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
            external("../../oracle/csm/3_C3PRV2_auth_policy.sql")
        } else if (databaseMatches('postgresql')){
            external("../../PostGreSQL/csm/3_C3PRV2_auth_policy.sql")
        }
    }

    void down() {
        execute("DELETE FROM csm_user_group");
        execute("DELETE FROM csm_protection_element");
        execute("DELETE FROM csm_group");
        execute("DELETE FROM csm_user_group_role_pg");
        execute("DELETE FROM csm_role_privilege");
        execute("DELETE FROM csm_role");
        execute("DELELTE FROM csm_pg_pe");
        execute("DELETE FROM csm_protection_group")
    }
}

