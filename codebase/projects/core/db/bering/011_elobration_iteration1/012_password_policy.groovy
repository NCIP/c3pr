class CreatePasswordPolicyTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable("password_policy") { t ->
            t.addColumn("hash_algorithm", "string")
            t.addColumn("ln_allowed_attempts", "integer")
            t.addColumn("ln_lockout_duration", "integer")
            t.addColumn("ln_max_age", "integer")
            t.addColumn("cn_min_age", "integer")
            t.addColumn("cn_history_size", "integer")
            t.addColumn("cn_min_length", "integer")
            t.addColumn("cn_cb_min_required", "integer")
            t.addColumn("cn_cb_is_upper_case_alphabet", "boolean")
            t.addColumn("cn_cb_is_lower_case_alphabet", "boolean")
            t.addColumn("cn_cb_is_non_alpha_numeric", "boolean")
            t.addColumn("cn_cb_is_base_ten_digit", "boolean")
            t.addColumn("cn_cb_max_substring_length", "integer")
            t.addColumn("grid_id", "string")
            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
        }
        createTable("password_history") { t ->
            t.includePrimaryKey = false
            t.addColumn("user_id", "integer", nullable: false)
            t.addColumn("password", "string")
            t.addColumn("list_index", "integer", nullable: false, defaultValue: 0)
        }
        execute("ALTER TABLE password_history ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES research_staff")
        
        addColumn("research_staff", "email_address", "string")
        addColumn("research_staff", "fax_number", "string")
        addColumn("research_staff", "phone_number", "string")
        addColumn("research_staff", "salt", "string", limit: 127)
        addColumn("research_staff", "token", "string", limit: 127)
        addColumn("research_staff", "token_time", "timestamp")
        addColumn("research_staff", "password_last_set", "timestamp")
        addColumn("research_staff", "num_failed_logins", "integer", nullable: false, defaultValue: 0)
        
         insert('password_policy', [id: 1, ln_allowed_attempts: 5, ln_lockout_duration: 86400, 
                   ln_max_age: 7776000, cn_min_age: 3600, cn_history_size: 3,
                   cn_min_length: 7, cn_cb_min_required: 3, cn_cb_is_upper_case_alphabet: '0',
                   cn_cb_is_lower_case_alphabet: '1', cn_cb_is_non_alpha_numeric: '1',
                   cn_cb_is_base_ten_digit: '1', cn_cb_max_substring_length: 3],  primaryKey: false)
    }

    void down() {
    	execute("delete from password_policy where id=1");
        dropTable("password_policy")
        dropTable("password_history")
        dropColumn("research_staff", "salt")
        dropColumn("research_staff", "token")
        dropColumn("research_staff", "token_time")
        dropColumn("research_staff", "password_last_set")
        dropColumn("research_staff", "password_history")
        dropColumn("research_staff", "num_failed_logins")
        dropColumn("research_staff", "email_address", "string")
        dropColumn("research_staff", "fax_number", "string")
        dropColumn("research_staff", "phone_number", "string")
        
    }
}
