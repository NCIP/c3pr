class rollbackResearchStaffTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		dropColumn("research_staff", "email_address")
        dropColumn("research_staff", "fax_number")
        dropColumn("research_staff", "phone_number")
    }

    void down() {
    	addColumn("research_staff", "email_address", "string")
        addColumn("research_staff", "fax_number", "string")
        addColumn("research_staff", "phone_number", "string")
    }
}
