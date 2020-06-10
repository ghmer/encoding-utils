/**
 * 
 */
package de.whisperedshouts.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author mario_000
 *
 */
public class Base64UrlUtilTest {
  private final static String ORIGINALSTRING = "Polyfon zwitschernd aßen Mäxchens Vögel Rüben, Joghurt und Quark";
  private final static String COMPARESTRING  = "UG9seWZvbiB6d2l0c2NoZXJuZCBhw59lbiBNw6R4Y2hlbnMgVsO2Z2VsIFLDvGJlbiwgSm9naHVydCB1bmQgUXVhcms=";
  
  private static String BASE64Encoded        = null;

  public static Boolean setup = true;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    Base64UrlUtilTest.BASE64Encoded = java.util.Base64.getUrlEncoder().encodeToString(ORIGINALSTRING.getBytes());
    assertEquals(COMPARESTRING, BASE64Encoded);
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base64UrlUtil#encode(byte[] byteArray)}.
   */
  @Test
  public final void testEncode() {
    if (setup) {
      try {
        byte[] base64Encoded = Base64UrlUtil.encode(Base64UrlUtilTest.ORIGINALSTRING.getBytes());
        String base64String = new String(base64Encoded);

        assertEquals(base64String, Base64UrlUtilTest.COMPARESTRING);
        assertEquals(base64String, Base64UrlUtilTest.BASE64Encoded);

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base64UrlUtil#decode(byte[] byteArray)}.
   */
  @Test
  public final void testDecode() {
    if (setup) {
      try {
        byte[] decodedByJava = java.util.Base64.getUrlDecoder().decode(Base64UrlUtilTest.BASE64Encoded);
        String decodedJavaString = new String(decodedByJava);

        byte[] decodedByUs = Base64UrlUtil.decode(Base64UrlUtilTest.BASE64Encoded.getBytes());
        String decodedByUsString = new String(decodedByUs);

        assertEquals(decodedJavaString, Base64UrlUtilTest.ORIGINALSTRING);
        assertEquals(decodedJavaString, decodedByUsString);

      } catch (Exception e) {

        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base64UrlUtil#decode(byte[] byteArray)}.
   */
  @Test
  public final void testRandomByteArray() {
    if (setup) {
      try {
        byte[] b = new byte[2048];
        new Random().nextBytes(b);
        byte[] encodedByUs = Base64UrlUtil.encode(b);
        byte[] encodedByThem = java.util.Base64.getUrlEncoder().encode(b);

        String encodedByUsString = new String(encodedByUs);
        String encodedByThemString = new String(encodedByThem);

        byte[] decodedByUs = Base64UrlUtil.decode(encodedByUs);
        byte[] decodedByThem = java.util.Base64.getUrlDecoder().decode(encodedByThem);

        assertEquals(encodedByThemString, encodedByUsString);
        assertTrue(encodedByUs.length   == encodedByThem.length);
        assertTrue(decodedByThem.length == b.length);
        assertTrue(decodedByUs.length   == b.length);

      } catch (Exception e) {

        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base64UrlUtil#decode(byte[] byteArray)}.
   */
  @Test
  public final void testRandomByteArrayThousandTwentyFourTimes() {
    if (setup) {
      for (int i = 0; i < 1024; i++) {
        testRandomByteArray();
      }
    }
  }
}
