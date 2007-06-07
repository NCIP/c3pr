package edu.duke.cabig.c3pr.utils.web.navigation;

import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.utils.web.spring.ControllerUrlResolver;

/**
 * Object representing a link in the section tasks in the caAERS chrome.
 * The <code>linkName</code> may be one of a couple of things:
 * <ul>
 *   <li>The name of a controller bean</li>
 *   <li>A context-relative URL (must start with <kbd>/</kbd>)</li>
 * </li>
 *
 * @author Rhett Sutphin
 */
public class Task {
    private String displayName;
    private String linkName;
    private ControllerUrlResolver urlResolver;

    ////// LOGIC

    public String getUrl() {
        if (linkName.startsWith("/")) {
            return linkName;
        } else {
            return urlResolver.resolve(linkName).getUrl(true);
        }
    }

    ////// BEAN PROPERTIES

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    @Required
    public void setUrlResolver(ControllerUrlResolver urlResolver) {
        this.urlResolver = urlResolver;
    }
}
