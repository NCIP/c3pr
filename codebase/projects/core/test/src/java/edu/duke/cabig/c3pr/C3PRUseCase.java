package edu.duke.cabig.c3pr;

/**
 * @author kherm
 */
public enum C3PRUseCase implements UseCase {
    STUDY_EXPORT(1, 1, "Export Study"),
    STUDY_IMPORT(1, 2, "Export Study"),

    ;

    private int major;
    private int minor;
    private String title;

    C3PRUseCase(int major, int minor, String title) {
        this.major = major;
        this.minor = minor;
        this.title = title;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public String getTitle() {
        return title;
    }
}
