package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 12:51:05 PM To change this
 * template use File | Settings | File Templates.
 */
public class RegistrationIdentifiersTab<C extends StudySubject> extends RegistrationTab<C> {

    public RegistrationIdentifiersTab() {
        super("Identifier", "Identifiers", "registration/reg_identifiers");
    }

}
