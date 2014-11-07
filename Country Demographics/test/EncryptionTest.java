
import country.demographics.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class EncryptionTest {
    
    @Test
    public void test_encrypt() {
        
        String origA = "testtesttest";
        String origB = "thisasdff";
        String origC = "oooohyeah&^^%";
        String origD = "!!!!!!!!!!!!!!";
        String origE = "++++++++++++";
        String origF = "ah herro egroll";
        String origG = "lolasdfjfkjdf";
        String origH = "***&&)@#$)(";
        
        String encrypA = null;
        String decrypA = null;
        String encrypB = null;
        String decrypB = null; 
        String encrypC = null;
        String decrypC = null; 
        String encrypD = null;
        String decrypD = null; 
        String encrypE = null;
        String decrypE = null; 
        String encrypF = null;
        String decrypF = null; 
        String encrypG = null;
        String decrypG = null;
        String encrypH = null;
        String decrypH = null;
 
        try {
            //Util.decrypt("baGnf/P7Blke2X3gRT1vZQ==");
            encrypA= Util.encrypt(origA);
            decrypA = Util.decrypt(encrypA);
            encrypB= Util.encrypt(origB);
            decrypB = Util.decrypt(encrypB);
            encrypC= Util.encrypt(origC);
            decrypC = Util.decrypt(encrypC);
            encrypD= Util.encrypt(origD);
            decrypD = Util.decrypt(encrypD);
            encrypE= Util.encrypt(origE);
            decrypE = Util.decrypt(encrypE);
            encrypF= Util.encrypt(origF);
            encrypG= Util.encrypt(origG);
            decrypG = Util.decrypt(encrypG);
            encrypH= Util.encrypt(origH);
            decrypH = Util.decrypt(encrypH);
                        decrypF = Util.decrypt(encrypF);


        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(origA, decrypA);
        assertEquals(origB, decrypB);
        assertEquals(origC, decrypC);
        assertEquals(origD, decrypD);
        assertEquals(origE, decrypE);
        assertEquals(origF, decrypF);
        assertEquals(origG, decrypG);
        assertEquals(origH, decrypH);
    }
}
