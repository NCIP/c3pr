class PopulateICD9DiseaseSites extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
    		execute("alter table icd9_disease_sites alter column version set default 0")
    	}
        insert('icd9_disease_sites', [ id: 1, name: "Malignant Neoplasm Of Lip, Oral Cavity, And Pharynx ", summ3_rep_disease_site_id:1, depth: "LEVEL1", selectable: false,code:"140-149"], primaryKey: false)
        insert('icd9_disease_sites', [ id: 2, name: "Malignant neoplasm of lip", parent_id:1, summ3_rep_disease_site_id:1, depth: "LEVEL2", selectable: false, code:"140"], primaryKey: false)
        insert('icd9_disease_sites', [ id: 3, name: "Malignant neoplasm of tongue", parent_id:1, summ3_rep_disease_site_id:1, depth: "LEVEL2", selectable: false, code:"141"], primaryKey: false)
        insert('icd9_disease_sites', [ id: 4, name: "Malignant neoplasm of upper lip vermilion border",parent_id:2, summ3_rep_disease_site_id:1,  depth: "LEVEL3", selectable: true, code:"140.0"], primaryKey: false)
        insert('icd9_disease_sites', [ id: 5, name: "Malignant neoplasm of other sites of lip", parent_id:2, summ3_rep_disease_site_id:1, depth: "LEVEL3", selectable: true, code:"140.8"], primaryKey: false)
        
        insert('icd9_disease_sites', [ id: 6, name: "Malignant Neoplasm Of Lymphatic And Hematopoietic Tissue", summ3_rep_disease_site_id:1, depth: "LEVEL1", selectable: false,code:"200-208"], primaryKey: false)
        
        insert('icd9_disease_sites', [ id: 7, name: "Lymphosarcoma and reticulosarcoma",parent_id:6, summ3_rep_disease_site_id:2, depth: "LEVEL2", selectable: false,code:"200"], primaryKey: false)
        insert('icd9_disease_sites', [ id: 8, name: "Reticulosarcoma", parent_id:7, summ3_rep_disease_site_id:2, depth: "LEVEL3", selectable: false, code:"200.0"], primaryKey: false)
        insert('icd9_disease_sites', [ id: 9, name: "Reticulosarcoma unspecified site",parent_id:8, summ3_rep_disease_site_id:2,  depth: "LEVEL3", selectable: true, code:"200.00"], primaryKey: false)
        insert('icd9_disease_sites', [ id: 10, name: "Reticulosarcoma involving lymph nodes of head face and neck",parent_id:8, summ3_rep_disease_site_id:2,  depth: "LEVEL4", selectable: true, code:"200.01"], primaryKey: false)
    }

    void down() {
    }
}