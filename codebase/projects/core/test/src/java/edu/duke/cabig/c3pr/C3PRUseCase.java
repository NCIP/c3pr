package edu.duke.cabig.c3pr;

/**
 * @author Rhett Sutphin
 */
public enum C3PRUseCase implements UseCase {
    CREATE_PARTICIPANT(3, 1, "Create Subject"),
    CREATE_INVESTIGATOR(3, 2, "Create Investigator"),
    CREATE_RESEARCH_STAFF(3, 3, "Create Research Staff"),
    CREATE_ORGANIZATION(3, 4, "Create Organization"),
    CREATE_STUDY_INVESTIGATOR(3, 5, "Associate Investigator to Study"),
    CREATE_STUDY_PERSONNEL(3, 6, "Associate Study Personnel to Study"),
    ADD_STRATIFICATION(3, 7, "Add Startification"),
    ADD_DISEASE(3, 8, "Add Diseases"),
    UPDATE_SUBJECT(3, 9, "Update Subject"),
    UPDATE_STUDY_PERSONNEL(3, 10, "Update Study Personnel"),
    VERIFY_SUBJECT(3, 11, "Verify Subject"),
    VERIFY_STUDY_PERSONNEL(3, 12, "Verify Study Personnel"),
    DELETE_SUBJECT(3, 13, "Delete Subject"),
    DELETE_STUDY_PERSONNEL(3, 14, "Delete Study Personnel"),
    MANAGE_SUBJECT_ADDRESS(3, 15, "Manage Subject Address"),
    MANAGE_SUBJECT_CONTACT(3, 16, "Manage Subject Contact"),
    SEARCH_SUBJECT(3, 18, "Search for a Subject"),
    VIEW_SUBJECT(3, 20, "View a Subject"),
    ADD_DISEASE_SUBJECT(3, 22, "Associate Disease to Subject"),

    CREATE_STUDY(4, 3, "Create Study Definition Manually"),
    EXPORT_STUDY(4, 4, "Export Study definition to a File"),
    IMPORT_STUDY(4, 5, "Import Study definition to a File"),
    SEARCH_STUDY(4, 7, "Search for Studies"),
    VIEW_STUDY(4, 8, "View Studies"),
    UPDATE_STUDY(4, 9, "Update Study"),
    
    ASSIGN_NEW_PARTICIPANT(5, 1, "Register a New Subject to a Study"),
    ASSIGN_EXISTING_PARTICIPANT(5, 2, "Register an Existing Unregistered Subject to a Study"),
    ASSIGN_REGISTERED_PARTICIPANT(5, 3, "Register an Existing Registered Subject to a Study"),
    CREATE_INCOMPLETE_REGISTERATION(5, 4, "Save a registration in an incomplete status"),
    CREATE_LOCAL_REGISTERATION(5, 5, "Register Subject to Local Study"),
    CREATE_RESERVED_REGISTERATION(5, 7, "Register Subject to a Reservation-enabled Study"),
    SEARCH_REGISTERATION(5, 8, "Search for Registrations"),
    VIEW_SUBJECT_REGISTRATIONS(5, 9, "View Subject Registration History"),
    UPDATE_SUBJECT_ELIGIBILITY(5, 10, "Update Eligibility Status on a Pre-Registration/Registration"),
    ASSIGN_ARM(5, 11, "5.11	Assign Subject to a Randomization Arm"),
    UPDATE_REGISTERATION_STATUS(5, 12, "Update Registration Status of a Subject on a given Study"),
    CONFIRM_REGISTERATION(5, 16, "Confirm Submitted Registration Information"),
    REPORT_REGISTERATION(12, 1, "Advanced Registration Search"),
   
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
