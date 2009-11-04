class StudyOpenToActiveStatusMigrator extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    		execute("update studies set status='OPEN' where status = 'ACTIVE'")
    }

    void down() {
    		execute("update studies set status='ACTIVE' where status = 'OPEN'")
    }
}