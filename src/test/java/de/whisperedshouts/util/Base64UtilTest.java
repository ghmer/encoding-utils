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
public class Base64UtilTest {
  private final static String ORIGINALSTRING = "Polyfon zwitschernd aßen Mäxchens Vögel Rüben, Joghurt und Quark";
  private final static String COMPARESTRING  = "UG9seWZvbiB6d2l0c2NoZXJuZCBhw59lbiBNw6R4Y2hlbnMgVsO2Z2VsIFLDvGJlbiwgSm9naHVydCB1bmQgUXVhcms=";
  private static String BASE64Encoded        = null;

  public static Boolean setup = true;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    Base64UtilTest.BASE64Encoded = java.util.Base64.getEncoder().encodeToString(ORIGINALSTRING.getBytes());
    assertEquals(COMPARESTRING, BASE64Encoded);
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base64Util#encode(byte[] byteArray)}.
   */
  @Test
  public final void testEncode() {
    if (setup) {
      try {
        byte[] base64Encoded = Base64Util.encode(Base64UtilTest.ORIGINALSTRING.getBytes());
        String base64String = new String(base64Encoded);

        assertEquals(base64String, Base64UtilTest.COMPARESTRING);
        assertEquals(base64String, Base64UtilTest.BASE64Encoded);

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base64Util#decode(byte[] byteArray)}.
   */
  @Test
  public final void testDecode() {
    if (setup) {
      try {
        byte[] decodedByJava = java.util.Base64.getDecoder().decode(Base64UtilTest.BASE64Encoded);
        String decodedJavaString = new String(decodedByJava);

        byte[] decodedByUs = Base64Util.decode(Base64UtilTest.BASE64Encoded.getBytes());
        String decodedByUsString = new String(decodedByUs);

        assertEquals(decodedJavaString, Base64UtilTest.ORIGINALSTRING);
        assertEquals(decodedJavaString, decodedByUsString);

      } catch (Exception e) {

        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base64Util#decode(byte[] byteArray)}.
   */
  @Test
  public final void testRandomByteArray() {
    if (setup) {
      try {
        byte[] b = new byte[2048];
        new Random().nextBytes(b);
        byte[] encodedByUs = Base64Util.encode(b);
        byte[] encodedByThem = java.util.Base64.getEncoder().encode(b);

        String encodedByUsString = new String(encodedByUs);
        String encodedByThemString = new String(encodedByThem);

        byte[] decodedByUs = Base64Util.decode(encodedByUs);
        byte[] decodedByThem = java.util.Base64.getDecoder().decode(encodedByThem);

        assertEquals(encodedByUsString, encodedByThemString);
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
   * {@link de.whisperedshouts.util.Base64Util#decode(byte[] byteArray)}.
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
