class CreateAuditEventValues extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        addColumn('Study_Subjects', 'stratum_group_number', 'integer');
    }

    void down() {
        dropColumn('Study_Subjects', 'stratum_group_number');
    }
}