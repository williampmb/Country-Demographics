/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country.demographics.util;


import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author admin
 */
public class Util {
    
    public static void log(final String str) {
        System.out.println(str);
    }
    
    private static String IV =            "AAAAAAAAAAAAAAAA";
    private static String encryptionKey = "V$D!%f|o!#zWnwé"; 
    private static int pwSize = 16;
    
    /**
     * Encrypts password. 
     * 
     * @param plainText
     * @return
     * @throws Exception 
     */
    public static String encrypt(String plainText) throws Exception {
        
        if(plainText.length() < pwSize) {
            plainText = fillPassword(plainText);
        }
        
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")));
    }
 
    /**
     * Decrypts a password
     * 
     * @param cipherString
     * @return
     * @throws Exception 
     */
    public static String decrypt(String cipherString) throws Exception{
        
        byte[] cipherBytes = Base64.getDecoder().decode(cipherString);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherBytes),"UTF-8").trim();
    }
    
    /**
     * This adds null characters to ends of strings to make them 16 bits
     * 
     * @param pw The password to pad
     * @return the String with padding
     */
    private static String fillPassword(final String pw) {
        StringBuilder sb = new StringBuilder();
        sb.append(pw);
        
        for(int i = pw.length(); i < pwSize; i++) {
            sb.append("\0");
        }
        
        return sb.toString();
    }
}
