/**
 * 
 */
package de.whisperedshouts.util;

import java.util.Arrays;

/**
 * Zur Kodierung werden die Zeichen A–Z, a–z, 0–9, + und / verwendet sowie = am
 * Ende. Da diese Zeichen auch im Extended Binary Coded Decimals Interchange
 * Code (EBCDIC) vorkommen (wenn auch an anderen Codepositionen), ist damit auch
 * ein Datenaustausch zwischen Nicht-ASCII-Plattformen möglich. Zur Kodierung
 * werden jeweils drei Byte des Bytestroms (= 24 Bit) in vier 6-Bit-Blöcke
 * aufgeteilt. Jeder dieser 6-Bit-Blöcke bildet eine Zahl von 0 bis 63. Diese
 * Zahlen werden anhand der nachfolgenden Umsetzungstabelle in „druckbare
 * ASCII-Zeichen“ umgewandelt und ausgegeben.
 * 
 * Falls die Gesamtanzahl der Eingabebytes nicht durch drei teilbar ist, wird
 * der zu kodierende Text am Ende mit aus Nullbits bestehenden Füllbytes
 * aufgefüllt, so dass sich eine durch drei teilbare Anzahl an Bytes ergibt. Um
 * dem Dekodierer mitzuteilen, wie viele Füllbytes angefügt wurden, werden die
 * 6-Bit-Blöcke, die vollständig aus Füllbytes entstanden sind, mit = kodiert.
 * Somit können am Ende einer Base64-kodierten Datei null, ein oder zwei
 * =-Zeichen auftreten. Anders gesagt, es werden so viele =-Zeichen angehängt,
 * wie Füllbytes angefügt worden sind.
 * 
 * @author mario.ragucci
 *
 */
public class Base64Util extends AbstractBitUtil {
  /**
   * the base64 characterset that is used to encode and decode base64
   */
  public static String CHARACTERSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" 
                                    + "abcdefghijklmnopqrstuvwxyz"
                                    + "0123456789+/";

  /**
   * decodes a base64 encoded byte array
   * 
   * @param byteArray
   *          the base64 array to decode
   * @return a base64 decoded byte array
   */
  public static byte[] decode(byte[] byteArray) {
    
    return decode(byteArray, Base64Util.CHARACTERSET);
  }
  
  /**
   * decodes a base64 encoded byte array by the supplied character set
   * @param byteArray the byte array to decode
   * @param characterSet the character set to use
   * @return the decoded byte array
   */
  public static byte[] decode(byte[] byteArray, String characterSet) {
    int originalLength = byteArray.length;
    byte[] sanitizedArray = sanitizeArray(byteArray, 4);
    byte[] tempArray = new byte[originalLength];
    int position = 0;
    int resolvedPosition = 0;
    while (position < sanitizedArray.length) {

      int[] resolvedBase64Chars = new int[4];
      int paddingCharacters = 0;
      for (int i = 0; i < 4; i++) {
        byte b = sanitizedArray[position++];
        resolvedBase64Chars[i] = characterSet.indexOf(b);
        if ((char) b == '=') {
          paddingCharacters++;
        }
      }

      // make a 24bit number out of the 4 characters
      long number = 0;
      // take the first character, shift 18 bit to the left
      number = ((resolvedBase64Chars[0] & 0xFF) << 18);
      // shift the first 2 bit, as those are not our payload
      // add the payload, shift 10 bits to the left
      number += ((resolvedBase64Chars[1] << 2 & 0xFF) << 10);
      // shift the first 2 bit, as those are not our payload
      // add the payload, shift 4 bits to the left
      number += ((resolvedBase64Chars[2] << 2 & 0xFF) << 4);
      // add the last 6 bits of the last character as payload
      number += ((resolvedBase64Chars[3] & 0x3F));

      // create the 8 original bytes out of the 24bit number
      long b1 = (long) (number >> 16) & 0xFF;
      long b2 = (long) (number >> 8) & 0xFF;
      long b3 = (long) (number >> 0) & 0xFF;

      switch (paddingCharacters) {
        case 0:
          tempArray[resolvedPosition++] = (byte) b1;
          tempArray[resolvedPosition++] = (byte) b2;
          tempArray[resolvedPosition++] = (byte) b3;
          break;
        case 1:
          tempArray[resolvedPosition++] = (byte) b1;
          tempArray[resolvedPosition++] = (byte) b2;
          break;
        case 2:
          tempArray[resolvedPosition++] = (byte) b1;
          break;
      }
    }

    sanitizedArray = Arrays.copyOf(tempArray, resolvedPosition);

    return sanitizedArray;
  }
  /**
   * decodes a string from base 64 using the standard base64 character set
   * @param base64Encoded the base64 encoded string
   * @return the decoded byte array
   */
  public static byte[] decode(String base64Encoded) {
    
    return decode(base64Encoded.getBytes(), Base64Util.CHARACTERSET);
  }
  
  /**
   * decodes a base64 encoded string using the supplied character set
   * @param base64Encoded the base64 encoded string
   * @param characterSet the character set to use
   * @return the decoded byte array
   */
  public static byte[] decode(String base64Encoded, String characterSet) {
    
    return decode(base64Encoded.getBytes(), characterSet);
  }

  /**
   * decode a base64 encoded byte array to its String representation
   * 
   * @param byteArray
   *          the base64 encoded byte array
   * @return the decoded string
   */
  public static String decodeToString(byte[] byteArray) {
    byte[] decoded = decode(byteArray);

    return new String(decoded);
  }

  /**
   * decode a base64 encoded string to its original representation
   * 
   * @param base64Encoded
   *          the base64 encoded string
   * @return the decoded string
   */
  public static String decodeToString(String base64Encoded) {
    return decodeToString(base64Encoded.getBytes());
  }

  /**
   * encodes a byte array to base64 using the standard base64 character set
   * 
   * @param byteArray
   *          the byte array to encode
   * @return the base64 encoded array
   */
  public static byte[] encode(byte[] byteArray) {

    return encode(byteArray, Base64Util.CHARACTERSET);
  }

  /**
   * encodes a byte array to base64
   * 
   * @param byteArray
   *          the array to encode
   * @param characterSet
   *          the characterSet to use
   * @return a base64 encoded byte array
   */
  public static byte[] encode(byte[] byteArray, String characterSet) {
    // how much do we overflow
    int overflow = byteArray.length % 3;
    // create proper padded input array
    byte[] paddedInput = Arrays.copyOf(byteArray, byteArray.length + (3 - overflow));
    // calculate size of destination byte array
    int resultSize = (4 * (paddedInput.length / 3));
    byte[] result = new byte[resultSize];

    // this is the current position of the resulting byte array
    int resultPosition = 0;
    for (int position = 0; position < paddedInput.length; position += 3) {

      // these three 8-bit (ASCII) characters become one 24-bit number
      long n = ((paddedInput[position + 0] & 0xFF) << 16) + ((paddedInput[position + 1] & 0xFF) << 8)
          + ((paddedInput[position + 2] & 0xFF) << 0);

      // this 24-bit number gets separated into four 6-bit numbers
      int n1 = (int) (n >> 18) & 0x3F;
      int n2 = (int) (n >> 12) & 0x3F;
      int n3 = (int) (n >> 6) & 0x3F;
      int n4 = (int) (n >> 0) & 0x3F;
      // get the proper base64 representation
      result[resultPosition++] = (byte) characterSet.charAt(n1);
      result[resultPosition++] = (byte) characterSet.charAt(n2);
      result[resultPosition++] = (byte) characterSet.charAt(n3);
      result[resultPosition++] = (byte) characterSet.charAt(n4);
    }

    // properly pad the last bytes
    if (overflow > 0) {
      int padWidth = 3 - overflow;
      for (int i = 1; i <= padWidth; i++) {
        result[result.length - i] = (byte) '=';
      }
    } else {
      // get rid of the last 4 bytes
      result = Arrays.copyOf(result, result.length - 4);
    }

    return result;
  }

  /**
   * base64 encode a byte array and return the base64 string
   * 
   * @param byteArray the byteArray to be encoded
   * @return the base64 string
   */
  public static String encodeToString(byte[] byteArray) {
    return encodeToString(byteArray, Base64Util.CHARACTERSET, false);
  }

  /**
   * returns the base64 encoded representation of a byte array. Optionally
   * includes newlines according to MIME spec
   * 
   * @param byteArray
   *          the byte array to encode
   * @param includeLinebreaks
   *          whether to include newlines every 76th character
   * @return the base64 string
   */
  public static String encodeToString(byte[] byteArray, boolean includeLinebreaks) {

    return encodeToString(byteArray, Base64Util.CHARACTERSET, includeLinebreaks);
  }

  /**
   * returns the base64 encoded representation of a byte array, according to the
   * supplied character set. Optionally includes newlines according to MIME spec
   * 
   * @param byteArray
   *          the byte array to encode
   * @param characterSet
   *          the character set to use
   * @param includeLinebreaks
   *          whether to include newlines every 76th character
   * @return the base64 string
   */
  public static String encodeToString(byte[] byteArray, String characterSet, boolean includeLinebreaks) {
    byte[] encodedResult = null;
    byte[] encodedArray = encode(byteArray, characterSet);

    // include newlines according to mime spec?
    if (includeLinebreaks) {
      // how many newlines to include
      int neededLinebreaks = encodedArray.length / 76;
      encodedResult = new byte[encodedArray.length + neededLinebreaks];

      int encodedResultPosition = 0;
      for (int i = 0; i < encodedArray.length; i++) {
        // if we hit the 76th character of this line, include a line separator
        if (i > 0 && i % 76 == 0) {
          encodedResult[encodedResultPosition++] = System.lineSeparator().getBytes()[0];
        }

        encodedResult[encodedResultPosition++] = encodedArray[i];
      }
    } else {
      encodedResult = encode(byteArray);
    }

    return new String(encodedResult);
  }

  /**
   * base64 encodes a string
   * 
   * @param toEncode
   *          the string to encode
   * @return the base64 encoded string
   */
  public static String encodeToString(String toEncode) {
    return encodeToString(toEncode.getBytes(), Base64Util.CHARACTERSET, false);
  }

  /**
   * base64 encodes a string. optionally includes newlines according to MIME
   * spec
   * 
   * @param toEncode
   *          the string to encode
   * @param includeLinebreaks
   *          whether to include newlines every 76th character
   * @return the base64 encoded string
   */
  public static String encodeToString(String toEncode, boolean includeLinebreaks) {
    return encodeToString(toEncode.getBytes(), Base64Util.CHARACTERSET, includeLinebreaks);
  }

  /**
   * * base64 encodes a string. optionally includes newlines according to MIME
   * spec
   * 
   * @param toEncode
   *          the string to encode
   * @param characterSet
   *          the character set to use
   * @param includeLinebreaks
   *          whether to include newlines every 76th character
   * @return the base64 encoded string
   */
  public static String encodeToString(String toEncode, String characterSet, boolean includeLinebreaks) {
    return encodeToString(toEncode.getBytes(), characterSet, includeLinebreaks);
  }
}