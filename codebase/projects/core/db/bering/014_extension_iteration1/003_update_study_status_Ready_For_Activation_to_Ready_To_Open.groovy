class StudyOpenToActiveStatusMigrator extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    		execute("update studies set status='READY_TO_OPEN' where status = 'READY_FOR_ACTIVATION'")
    }

    void down() {
    		execute("update studies set status='READY_FOR_ACTIVATION' where status = 'READY_TO_OPEN'")
    }
}