class PopulateSummary3DiseaseSites extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('oracle')){
    		execute("alter table summ3_rep_disease_sites modify version number default 0")
    	}
    	if (databaseMatches('postgres')){
    		execute("alter table summ3_rep_disease_sites alter column version set default 0")
    	}
        insert('summ3_rep_disease_sites', [ id: 1, name: "Lip, Oral Cavity and Pharynx"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 2, name: "Esophagus"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 3, name: "Stomach"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 4, name: "Small Intestine"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 5, name: "Colon"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 6, name: "Rectum"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 7, name: "Anus"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 8, name: "Liver"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 9, name: "Pancreas"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 10, name: "Other Digestive Organ"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 11, name: "Larynx"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 12, name: "Lung"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 13, name: "Other Respiratory and Intrathoracic Organs"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 14, name: "Bones and Joints"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 15, name: "Soft Tissue"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 16, name: "Melanoma, Skin"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 17, name: "Kaposi's Sarcoma"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 18, name: "Mycosis Fungoides"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 19, name: "Other Skin"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 20, name: "Breast-Female"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 21, name: "Breast-Male"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 22, name: "Cervix"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 23, name: "Corpus Uteri"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 24, name: "Ovary"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 25, name: "Other Female Genital"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 26, name: "Prostate"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 27, name: "Other Male Genital"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 28, name: "Urinary Bladder"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 29, name: "Kidney"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 30, name: "Other Urinary"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 31, name: "Eye and Orbit"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 32, name: "Brain and Nervous System"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 33, name: "Thyroid"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 34, name: "Other Endocrine System"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 35, name: "Non-Hodgkin’s Lymphoma"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 36, name: "Hodgkin’s Lymphoma"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 37, name: "Multiple Myeloma"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 38, name: "Lymphoid Leukemia"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 39, name: "Myeloid and Monocytic Leukemia"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 40, name: "Leukemia, other"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 41, name: "Leukemia, not otherwise specified"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 42, name: "Other Hematopoietic"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 43, name: "Unknown Sites"], primaryKey: false)
        insert('summ3_rep_disease_sites', [ id: 44, name: "Ill-Defined Sites"], primaryKey: false)
    }

    void down() {
        execute("DELETE FROM summ3_rep_disease_sites")
    }
}