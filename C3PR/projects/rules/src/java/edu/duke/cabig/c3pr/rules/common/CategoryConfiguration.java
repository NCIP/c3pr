/**
 * 
 */
package edu.duke.cabig.c3pr.rules.common;

/**
 * @author vinaykumar
 * 
 */
public enum CategoryConfiguration {

    CAAERS_BASE("CAAERS_BASE", "CAAERS_BASE", "edu.duke.cabig.c3pr.rules",
                    "The rule base for all caaers rules"), SPONSOR_BASE("SPONSOR",
                    "/CAAERS_BASE/SPONSOR", "edu.duke.cabig.c3pr.rules.sponsor", ""), INSTITUTION_BASE(
                    "INSTITUTION", "/CAAERS_BASE/INSTITUTION",
                    "edu.duke.cabig.c3pr.rules.institution", ""), SPONSOR_DEFINED_STUDY_BASE(
                    "SPONSOR_DEFINED_STUDY", "/CAAERS_BASE/SPONSOR_DEFINED_STUDY",
                    "edu.duke.cabig.c3pr.rules.sponsor.study", ""), INSTITUTION_DEFINED_STUDY_BASE(
                    "INSTITUTION_DEFINED_STUDY", "/CAAERS_BASE/INSTITUTION_DEFINED_STUDY",
                    "edu.duke.cabig.c3pr.rules.institution.study", "");
    // STUDY_BASE("STUDY","/CAAERS_BASE/STUDY","edu.duke.cabig.c3pr.rules","");

    private String name;

    private String path;

    private String packagePrefix;

    private String desc;

    CategoryConfiguration(String name, String path, String packagePrefix, String desc) {
        this.name = name;
        this.path = path;
        this.packagePrefix = packagePrefix;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getPackagePrefix() {
        return packagePrefix;
    }

    public String getDescription() {
        return desc;
    }

}
