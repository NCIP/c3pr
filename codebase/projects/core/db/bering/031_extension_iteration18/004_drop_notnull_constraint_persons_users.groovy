class DropNotNullConstraintPersonsUsers extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	setNullable('persons_users','assigned_identifier', true);
    }

    void down() {
		
    }
}


