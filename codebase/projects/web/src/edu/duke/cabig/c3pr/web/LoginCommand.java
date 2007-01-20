package edu.duke.cabig.c3pr.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Kruttik Aggarwal
 */
public class LoginCommand {
    private String username;
    private String password;


    
    //
    // BOUND PROPERTIES
    //
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}