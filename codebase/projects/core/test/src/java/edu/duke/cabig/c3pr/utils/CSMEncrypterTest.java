package edu.duke.cabig.c3pr.utils;

import gov.nih.nci.security.util.StringEncrypter;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Apr 16, 2007
 * Time: 1:22:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSMEncrypterTest extends TestCase {

    private String Password = "c3pr_admin";
    private String EncryptedPassword = "Ie0InPvp8oOgmHldOE8ejA==";

      public void encryptionUtility(){
        try {
            StringEncrypter stringEncrypter = new StringEncrypter();

            String encryptedPassword =
                    stringEncrypter.encrypt(Password);

            System.out.println(encryptedPassword);

        } catch (StringEncrypter.EncryptionException e) {
            fail(e.getMessage());
        }


    }
    
    public void testEncryption(){
        try {
            StringEncrypter stringEncrypter = new StringEncrypter();

            String encryptedPassword =
            stringEncrypter.encrypt(Password);

            String password = stringEncrypter.decrypt(EncryptedPassword);
            
            System.out.println(password);
            assertEquals(password,Password);

            System.out.println(encryptedPassword);
           assertEquals(EncryptedPassword,encryptedPassword);




        } catch (StringEncrypter.EncryptionException e) {
            fail(e.getMessage());
        }

    }
}
