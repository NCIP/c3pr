class UpdateStudiesStatusCode extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    execute("update studies set status='OPEN' where status like 'AMENDMENT_PENDING'");
    }

    void down() {
    }
}
