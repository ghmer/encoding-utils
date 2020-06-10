/**
 * 
 */
package de.whisperedshouts.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.apache.commons.codec.binary.Base32;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author mario_000
 *
 */
public class Base32UtilTest {
  private final static String ORIGINALSTRING = "Polyfon zwitschernd aßen Mäxchens Vögel Rüben, Joghurt und Quark";
  private final static String COMPARESTRING  = "KBXWY6LGN5XCA6TXNF2HGY3IMVZG4ZBAMHBZ6ZLOEBG4HJDYMNUGK3TTEBLMHNTHMVWCAUWDXRRGK3RMEBFG6Z3IOVZHIIDVNZSCAULVMFZGW===";
  private final static String COMPAREHEXSTR  = "A1NMOUB6DTN20UJND5Q76OR8CLP6SP10C71PUPBE416S793OCDK6ARJJ41BC7DJ7CLM20KM3NHH6ARHC4156UPR8ELP7883LDPI20KBLC5P6M===";
  private static String BASE32_ENCODED       = null;
  private static String BASE32_HEXENC        = null;

  public static Boolean setup = true;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    Base32UtilTest.BASE32_ENCODED = new Base32().encodeAsString(Base32UtilTest.ORIGINALSTRING.getBytes());
    Base32UtilTest.BASE32_HEXENC = new Base32(true).encodeAsString(Base32UtilTest.ORIGINALSTRING.getBytes());
    assertEquals(Base32UtilTest.COMPARESTRING, Base32UtilTest.BASE32_ENCODED);
    assertEquals(Base32UtilTest.COMPAREHEXSTR, Base32UtilTest.BASE32_HEXENC);
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base32Util#encode(byte[] byteArray)}.
   */
  @Test
  public final void testEncode() {
    if (setup) {
      try {
        byte[] base32Encoded = Base32Util.encode(Base32UtilTest.ORIGINALSTRING.getBytes());
        String base32String = new String(base32Encoded);

        assertEquals(base32String, Base32UtilTest.COMPARESTRING);
        assertEquals(base32String, Base32UtilTest.BASE32_ENCODED);

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base32Util#encode(byte[] byteArray, String characterSet)}.
   */
  @Test
  public final void testEncodeHex() {
    if (setup) {
      try {
        byte[] base32Encoded = Base32Util.encode(Base32UtilTest.ORIGINALSTRING.getBytes(),
            Base32Util.BASE32_HEX_CHARSET);
        String base32String = new String(base32Encoded);

        assertEquals(base32String, Base32UtilTest.COMPAREHEXSTR);
        assertEquals(base32String, Base32UtilTest.BASE32_HEXENC);

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base32Util#decode(byte[] byteArray)}.
   */
  @Test
  public final void testDecode() {
    if (setup) {
      try {
        byte[] decodedByUs = Base32Util.decode(Base32UtilTest.BASE32_ENCODED.getBytes());
        String decodedByUsString = new String(decodedByUs);

        assertEquals(decodedByUsString, Base32UtilTest.ORIGINALSTRING);
      } catch (Exception e) {

        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base32Util#decode(byte[] byteArray, String characterSet)}.
   */
  @Test
  public final void testDecodeHex() {
    if (setup) {
      try {
        byte[] decodedByUs = Base32Util.decode(Base32UtilTest.BASE32_HEXENC.getBytes(), Base32Util.BASE32_HEX_CHARSET);
        String decodedByUsString = new String(decodedByUs);

        assertEquals(decodedByUsString, Base32UtilTest.ORIGINALSTRING);
      } catch (Exception e) {

        fail(e.getMessage());
      }
    }
  }

  /**
   * Test method for
   * {@link de.whisperedshouts.util.Base32Util#decode(byte[] byteArray)}.
   */
  @Test
  public final void testRandomByteArray() {
    if (setup) {
      Base32 base32 = new Base32();
      try {
        byte[] b = new byte[2048];
        new Random().nextBytes(b);
        byte[] encodedByUs = Base32Util.encode(b);
        byte[] encodedByThem = base32.encode(b);

        String encodedByUsString = new String(encodedByUs);
        String encodedByThemString = new String(encodedByThem);

        byte[] decodedByUs = Base32Util.decode(encodedByUs);
        byte[] decodedByThem = base32.decode(encodedByThem);

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
   * Test method
   */
  @Test
  public final void testRandomByteArrayHex() {
    if (setup) {
      Base32 base32 = new Base32(true);
      try {
        byte[] b = new byte[2048];
        new Random().nextBytes(b);
        byte[] encodedByUs = Base32Util.encode(b, Base32Util.BASE32_HEX_CHARSET);
        byte[] encodedByThem = base32.encode(b);

        String encodedByUsString = new String(encodedByUs);
        String encodedByThemString = new String(encodedByThem);

        byte[] decodedByUs = Base32Util.decode(encodedByUs, Base32Util.BASE32_HEX_CHARSET);
        byte[] decodedByThem = base32.decode(encodedByThem);

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
   * Test method
   */
  @Test
  public final void testRandomByteArrayThousandTwentyFourTimes() {
    if (setup) {
      for (int i = 0; i < 1024; i++) {
        testRandomByteArray();
      }
    }
  }

  /**
   * Test method
   */
  @Test
  public final void testRandomByteArrayThousandTwentyFourTimesHex() {
    if (setup) {
      for (int i = 0; i < 1024; i++) {
        testRandomByteArrayHex();
      }
    }
  }
}
