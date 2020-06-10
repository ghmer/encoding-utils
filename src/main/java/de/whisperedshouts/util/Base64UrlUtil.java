/**
 * 
 */
package de.whisperedshouts.util;


/**
 * Dies ist eine Alternativ Implementierung des base64 Standards. Prinzipiell ist sie
 * identisch zur herkömmlichen base64 Implementierung, jedoch werden Anstelle der Zeichen
 * '+' und '/' die Zeichen '-' (Minus) und '_' (Unterstrich) verwendet.
 * 
 * Zur Vermeidung von Verwechslungen sollte diese Implementierung stets als "base64url" 
 * referenziert werden. Gleichsam sollte die herkömmliche "base64" Implementierung nicht
 * fälschlicherweise als "base64url" referenziert werden.
 * @author mario.ragucci
 *
 */
public class Base64UrlUtil extends Base64Util {
    public static String CHARACTERSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" 
                                      + "abcdefghijklmnopqrstuvwxyz"
                                      + "0123456789-_";
    
    
    /**
     * decodes a base64url encoded byte array
     * 
     * @param byteArray
     *          the base64url array to decode
     * @return a base64url decoded byte array
     */
    public static byte[] decode(byte[] byteArray) {
      
      return decode(byteArray, Base64UrlUtil.CHARACTERSET);
    }
    
    /**
     * decodes a string from base 64 using the standard base64url character set
     * @param base64urlEncoded the base64url encoded string
     * @return the decoded byte array
     */
    public static byte[] decode(String base64urlEncoded) {
      
      return decode(base64urlEncoded.getBytes(), Base64UrlUtil.CHARACTERSET);
    }
    
    /**
     * encodes a byte array to base64url using the standard base64url character set
     * 
     * @param byteArray
     *          the byte array to encode
     * @return the base64url encoded array
     */
    public static byte[] encode(byte[] byteArray) {

      return encode(byteArray, Base64UrlUtil.CHARACTERSET);
    }
    
    /**
     * base64url encode a byte array and return the base64url string
     * 
     * @param byteArray the byteArray to be encoded
     * @return the base64url string
     */
    public static String encodeToString(byte[] byteArray) {
      return encodeToString(byteArray, Base64UrlUtil.CHARACTERSET, false);
    }
    
    /**
     * returns the base64url encoded representation of a byte array. Optionally
     * includes newlines according to MIME spec
     * 
     * @param byteArray
     *          the byte array to encode
     * @param includeLinebreaks
     *          whether to include newlines every 76th character
     * @return the base64url string
     */
    public static String encodeToString(byte[] byteArray, boolean includeLinebreaks) {

      return encodeToString(byteArray, Base64UrlUtil.CHARACTERSET, includeLinebreaks);
    }
    
    /**
     * base64url encodes a string
     * 
     * @param toEncode
     *          the string to encode
     * @return the base64url encoded string
     */
    public static String encodeToString(String toEncode) {
      return encodeToString(toEncode.getBytes(), Base64UrlUtil.CHARACTERSET, false);
    }

    /**
     * base64url encodes a string. optionally includes newlines according to MIME
     * spec
     * 
     * @param toEncode
     *          the string to encode
     * @param includeLinebreaks
     *          whether to include newlines every 76th character
     * @return the base64url encoded string
     */
    public static String encodeToString(String toEncode, boolean includeLinebreaks) {
      return encodeToString(toEncode.getBytes(), Base64UrlUtil.CHARACTERSET, includeLinebreaks);
    }
    
    

}
