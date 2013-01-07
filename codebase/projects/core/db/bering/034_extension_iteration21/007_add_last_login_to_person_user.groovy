class ModifyPersonUsers extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        addColumn("persons_users","last_login", "timestamp")
		
    }

    void down() {
        dropColumn("persons_users", "last_login")
    }
}