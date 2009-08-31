class ChangeICD9DiseaseSitesCodeToStringNotNullable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("alter table icd9_disease_sites alter column code type text")
    	execute("alter table icd9_disease_sites alter column code set not null")
    }

    void down() {
    }
}