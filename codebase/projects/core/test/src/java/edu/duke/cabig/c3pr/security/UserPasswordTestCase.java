package edu.duke.cabig.c3pr.security;

import gov.nih.nci.security.util.StringEncrypter;
import junit.framework.TestCase;

public class UserPasswordTestCase extends TestCase {
    
    public void testEncryptPassword() throws Exception{
        String password="Ccts123!";
        String encryptPassword=new StringEncrypter().encrypt(password);
        assertEquals("Wrong encrypted value", "56nit3oKigG8j2uyHEABIQ==", encryptPassword);
    }
    
    public void testDecryptPassword() throws Exception{
        String encryptPassword="56nit3oKigG8j2uyHEABIQ==!";
        String password=new StringEncrypter().decrypt(encryptPassword);
        assertEquals("Wrong encrypted value", "Ccts123!", password);
    }
    
    public void testPassword() throws Exception{
        System.out.println(new StringEncrypter().encrypt("p@ssw0rd"));
    }
}
