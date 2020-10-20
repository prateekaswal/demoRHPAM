package com.myspace.nsp_registration;

import java.math.BigInteger;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



public class encript_passwoard {
   private String rand_satl;
    private static final String ALGO = "AES";
    private final static String HEX = "0123456789ABCDEF";
    private static final byte[] keyValue =
    new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
    'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };


    public encript_passwoard() {
    }
    
    public  String encript1(String pass) 
    {
       String hash="";
       try {
                 
         hash = byteArrayToHexString(computeHash(pass));
             
       }
       catch (Exception e)
       {
         e.printStackTrace();
       }
      return hash;
     }
  
    private static void appendHex(StringBuffer sb, byte b) {
    sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }

    public static String toHex(byte[] buf) {
    if (buf == null)
    return "";
    StringBuffer result = new StringBuffer(2*buf.length);
    for (int i = 0; i < buf.length; i++) {
    appendHex(result, buf[i]);
    }
    return result.toString();
    }


    public static byte[] toByte(String hexString) {
    int len = hexString.length()/2;
    byte[] result = new byte[len];
    for (int i = 0; i < len; i++)
    result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
    return result;
    }


    
    public  byte[] computeHash(String x)   
    throws Exception  
    {
       java.security.MessageDigest d =null;
       d = java.security.MessageDigest.getInstance("SHA-1");
       d.reset();
       d.update(x.getBytes());
       return  d.digest();
    }
    
    public String byteArrayToHexString(byte[] b){
       StringBuffer sb = new StringBuffer(b.length * 2);
       for (int i = 0; i < b.length; i++){
         int v = b[i] & 0xff;
         if (v < 16) {
           sb.append('0');
         }
         sb.append(Integer.toHexString(v));
       }
       return sb.toString().toUpperCase();
    }
    public String salt() 
    {
        
        Random rng=new Random();
         String characters="0123456789abcdefg";
         int length=16;
         char[] text = new char[length];
         for (int i = 0; i < length; i++)
         {
           text[i] = characters.charAt(rng.nextInt(characters.length()));
         }
        
        return new String(text);

    }


    public void setRand_satl(String rand_satl) {
        this.rand_satl = rand_satl;
    }

    public String getRand_satl() {
        return rand_satl;
    }
    public String md5rand() {
        String md5="";
           try
           {
           Random rng=new Random();
           String characters="0123456789abcdefg";
           int length=8;
           char[] text = new char[length];
           for (int i = 0; i < length; i++)
           {
               text[i] = characters.charAt(rng.nextInt(characters.length()));
           }
           MessageDigest md = MessageDigest.getInstance("MD5");
           String str=text.toString();
           byte[] md5sum = md.digest(str.getBytes());
           BigInteger number = new BigInteger(1,md5sum);
            md5 =number.toString(16);
           }
           catch(Exception e) {
               System.out.println(e.toString());
           }
           return md5;
    }

    public String getRandomNumber(){
               int length = 32;
                       String chars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRST";
                       int numberOfCodes = 0;//controls the length of alpha numberic string
                       String code = "";
                       while (numberOfCodes < length) {
                       char c = chars.charAt((int) (Math.random() * chars.length()));
                       code += c;
                       numberOfCodes++;
                       }
               return code;
           }  
       
        public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        //String encryptedValue = new BASE64Encoder().encode(encVal);
        String encryptedValue = toHex(encVal);
        return encryptedValue;
        }


         public static String decrypt(String encryptedData){
         try
         {
         Key key = generateKey();
         Cipher c = Cipher.getInstance(ALGO);
         c.init(Cipher.DECRYPT_MODE, key);
         byte[] decordedValue = toByte(encryptedData);
         byte[] decValue = c.doFinal(decordedValue);
         String decryptedValue = new String(decValue);
         return decryptedValue;
         }
        catch( Exception e )
        {
        return "false";
        }
      }

        
      private static Key generateKey() throws Exception {
      Key key = new SecretKeySpec(keyValue, ALGO);
      return key;
      }
    
    
    public  String randomString( int len ) 
        {
             String AB = "012389abcdefghijkl4567mnopqrstuvwxyz";
             Random rnd = new Random();
             StringBuilder sb = new StringBuilder( len );
             for( int i = 0; i < len; i++ ) 
              sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
             return sb.toString();
        } 
    
        public static String decodemd5(String input) {
		
		String md5 = null;
		
		if(null == input) return null;
		
		try {
			
		//Create MessageDigest object for MD5
		MessageDigest digest = MessageDigest.getInstance("MD5");
		
		//Update input string in message digest
		digest.update(input.getBytes(), 0, input.length());

		//Converts message digest value in base 16 (hex) 
		md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return md5;
	}
        
        
        public static String SHA512(String message) throws NoSuchAlgorithmException {
        MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
        byte[] array = sha512.digest(message.getBytes());
        return arrayToString(array);
    }
  public static String MD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
 
  
    private static String arrayToString(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
    
    
       public static String SHA512_MD5(String message) throws NoSuchAlgorithmException {
           String msg=SHA512(MD5(message));
          // System.out.println(msg);
                   
        return msg;
    
    }
    
}
