class PopulateAnatomicSite extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    if (databaseMatches('oracle')){
    execute("alter table anatomic_sites modify version number default 0")
    }
    
        // Have to break up the inserts so as not to exceed the java max method length
        m0()
        m1()
        m2()
        m3()
        m4()
    }

    void m0() {
        // all0 (25 terms)
        insert('anatomic_sites', [ id: 1, name: "Body site", category: "Body Category"], primaryKey: false)
        insert('anatomic_sites', [ id: 11, name: "Bone Marrow", category: "Bone Marrow"], primaryKey: false)
        insert('anatomic_sites', [ id: 12, name: "Duodenum", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 13, name: "Esophagus", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 14, name: "Ileum", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 15, name: "Jejunum", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 16, name: "Liver", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 17, name: "Pancreas", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 18, name: "Rectum", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 19, name: "Spleen", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 20, name: "Sigmoid Colon", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 21, name: "Stomach", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 22, name: "Bile Duct", category: "Gastrointestinal"], primaryKey: false)
        insert('anatomic_sites', [ id: 23, name: "Ear-External", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 24, name: "Ear-Inner", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 25, name: "Ear-Mid", category: "Head and Neck"], primaryKey: false)
    }

    void m1() {
        // all1 (25 terms)
        insert('anatomic_sites', [ id: 26, name: "Eye-Globe", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 27, name: "Eye-Orbit", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 28, name: "Mouth", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 29, name: "Pharnyx", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 30, name: "Sinuses", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 31, name: "Throat", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 32, name: "Thyroid", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 33, name: "Nose", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 34, name: "Nasopharynx", category: "Head and Neck"], primaryKey: false)
        insert('anatomic_sites', [ id: 35, name: "Bladder", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 36, name: "Cervix", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 37, name: "Fallopian Tube", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 38, name: "Kidney", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 39, name: "Ovary", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 40, name: "Pelvis", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 41, name: "Penis", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 42, name: "Testicle", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 43, name: "Ureter", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 44, name: "Uterus", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 45, name: "Vagina", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 46, name: "Prostate", category: "Genitourinary"], primaryKey: false)
        insert('anatomic_sites', [ id: 47, name: "Axilla", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 48, name: "Brachial", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 49, name: "Cervical", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 50, name: "Epitrochlear", category: "Lymph Node"], primaryKey: false)
    }

    void m2() {
        // all2 (25 terms)
        insert('anatomic_sites', [ id: 51, name: "Femoral", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 52, name: "Hilar", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 53, name: "Iliac", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 54, name: "Inguinal", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 55, name: "Internal Mammary", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 56, name: "Lymphnode", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 57, name: "Mediastinum", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 58, name: "Mesenteric", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 59, name: "Mid-Pelvis", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 60, name: "Neck", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 61, name: "Occipital", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 62, name: "Paraaortic", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 63, name: "Pelvis", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 64, name: "Periauricular", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 65, name: "Popliteal", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 66, name: "Retroperitoneal", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 67, name: "Subclavian", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 68, name: "Submental", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 69, name: "Supraclavicular", category: "Lymph Node"], primaryKey: false)
        insert('anatomic_sites', [ id: 70, name: "Back", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 71, name: "Feet", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 72, name: "Femur", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 73, name: "Fibula", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 74, name: "Hands", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 75, name: "Humerus", category: "Musculoskeletal"], primaryKey: false)
    }

    void m3() {
        // all3 (25 terms)
        insert('anatomic_sites', [ id: 76, name: "Iliac", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 77, name: "Mandible", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 78, name: "Muscle LE Distal", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 79, name: "Muscle LE Proximal", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 80, name: "Muscle UE Distal", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 81, name: "Muscle UE Proximal", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 82, name: "Radius", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 83, name: "Ribs", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 84, name: "Skull", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 85, name: "Spine-C", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 86, name: "Spine-L", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 87, name: "Spine-S", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 88, name: "Spine-T", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 89, name: "Sternum", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 90, name: "Tibia", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 91, name: "Ulna", category: "Musculoskeletal"], primaryKey: false)
        insert('anatomic_sites', [ id: 92, name: "Ant-LLL", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 93, name: "Ant-LUL", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 94, name: "Ant-RLL", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 95, name: "Ant-RML", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 96, name: "Ant-RUL", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 97, name: "Breast", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 98, name: "Chest", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 99, name: "Hilar", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 100, name: "Lung", category: "Thoracic (Pulmonary)"], primaryKey: false)
    }

    void m4() {
        // all4 (11 terms)
        insert('anatomic_sites', [ id: 101, name: "Mediastinum", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 102, name: "Pericardial", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 103, name: "Pleural", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 104, name: "Post-LLL", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 105, name: "Post-LUL", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 106, name: "Post-RLL", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 107, name: "Post-RML", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 108, name: "Post-RUL", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 109, name: "Retrocrural", category: "Thoracic (Pulmonary)"], primaryKey: false)
        insert('anatomic_sites', [ id: 110, name: "Other", category: "Other"], primaryKey: false)
        insert('anatomic_sites', [ id: 111, name: "Adrenal Gland", category: "Other"], primaryKey: false)
    }

    void down() {
        execute("DELETE FROM anatomic_sites")
    }
}
