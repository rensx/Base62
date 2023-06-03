//package util;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Objects;

public class Base62 {

  private static String[] alphabet =
    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
  // private static char[]DIGITS =
  // "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  private static HashMap<String, Integer> hex2Ten = createMapHex2Ten();

  private static int BASE = 62;
  

  //test
  public static void main(String[] args) {
    Base62 b = new Base62()
    .set("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
    .set(62);


    System.out.println(b.encode(12345.12345608900043d));
    System.out.println(b.decode("5YpXsLkbJrc", 1d));


  }


  public Base62() {}

  public Base62(int b) {
    this.BASE = b;
  }

  public Base62(String a) {
    this.alphabet = a.split("");
    this.hex2Ten = createMapHex2Ten();
  }

  public Base62(String a, int b) {
    this.alphabet = a.split("");
    this.hex2Ten = createMapHex2Ten();
    this.BASE = b;
  }

  public Base62 set(int b) {
    return setRadix(b);
  }

  public Base62 set(String b) {
    return setAlphabet(b);
  }

  public Base62 setRadix(int b) {
    this.BASE = b;
    return this;
  }

  public Base62 setAlphabet(String a) {
    this.alphabet = a.split("");
    this.hex2Ten = createMapHex2Ten();
    return this;
  }

  public static String encode(int n) {
    return encode(n, BASE);
  }

  public static String encode(long n) {
    return encode(n, BASE);
  }

  public static String encode(double n) {
    return encode(n, BASE);
  }

  public static String encode(float n) {
    return encode(n, BASE);
  }

  public static long decode(String s) {
    return hexDecodeLong(s, BASE);
  }

  public static long decode(String s, long b) {
    return hexDecodeLong(s, BASE);
  }

  public static double decode(String s, double b) {

    return decode(s, b, BASE);
  }

  public static int decode(String s, int b) {
    return hexDecodeInt(s, BASE);
  }

  public static float decode(String s, float b) {
    return decode(s, b, BASE);
  }

  public static long decode(String s, long b, int radix) {
    return hexDecodeLong(s, radix);
  }

  public static double decode(String s, double b, int radix) {
    boolean negative = false;
    if (s.charAt(0) == '-') {
      negative = true;
      s = s.substring(1);
    }
    long a = hexDecodeLong(s, radix);
    if (negative) return -fromBytes(toByteArray(a), b);

    return fromBytes(toByteArray(a), b);
  }

  public static int decode(String s, int b, int radix) {
    return hexDecodeInt(s, radix);
  }

  public static float decode(String s, float b, int radix) {
    boolean negative = false;
    if (s.charAt(0) == '-') {
      negative = true;
      s = s.substring(1);
    }
    long a = hexDecodeLong(s, radix);
    if (negative) return -fromBytes(toByteArray(a), b);
    return fromBytes(toByteArray(a), b);
  }

  public static int hexDecodeInt(String s, int radix) {
    if (s == "") return 0;
    if (Objects.equals(s, "0")) return 0;

    boolean negative = false;
    if (s.charAt(0) == '-') {
      negative = true;
      s = s.substring(1);
    }

    int deciaml = 0;

    String[] keys = s.split("");
    for (int i = 0; i < s.length(); i++) {

      int value = hex2Ten.get(keys[i]);
      deciaml = deciaml * radix + value;
    }
    if (negative) deciaml = -deciaml;
    return deciaml;
  }

  public static long hexDecodeLong(String s, int radix) {
    if (s == "") return 0l;
    if (Objects.equals(s, "0")) return 0l;
    boolean negative = false;
    if (s.charAt(0) == '-') {
      negative = true;
      s = s.substring(1);
    }

    long deciaml = 0l;

    String[] keys = s.split("");
    for (int i = 0; i < s.length(); i++) {

      int value = hex2Ten.get(keys[i]);
      deciaml = deciaml * radix + value;
    }
    if (negative) deciaml = -deciaml;
    return deciaml;
  }

  public static String encode(int n, int radix) {
    // if (radix < 0) {return encode_negbase(n, radix);}

    if (n == 0L) return alphabet[0];
    boolean negative = n < 0;
    StringBuilder out = new StringBuilder();

    if (negative && radix > 0) {
      n = -n;
    }
    if (n == 0) return "0";
    int nn = n;
    while (nn != 0) {
      int rem = (int) (nn % radix);
      nn /= radix;
      if (rem < 0) {
        nn++;
        rem -= radix;
      }
      out.append(alphabet[rem]);
    }
    if (negative) out.append("-");
    out.reverse();
    return out.toString();
  }

  public static String encode(long n, int radix) {
    // if (radix < 0) {return encode_negbase(n, radix);}
    if (n == 0L) return alphabet[0];
    boolean negative = n < 0L;
    StringBuilder out = new StringBuilder();
    if (negative && radix > 0) {
      n = -n;
    }

    long nn = n;
    while (nn != 0) {
      int rem = (int) (nn % radix);
      nn /= radix;
      if (rem < 0) {
        nn++;
        rem -= radix;
      }
      out.append(alphabet[rem]);
    }
    if (negative) out.append('-');
    out.reverse();
    return out.toString();
  }

  public static String encode(double b, int radix) {
    boolean negative = b < 0d;
    if (negative) {
      b = -b;
    }
    long n = fromBytes(toByteArray(b), 1l);
    if (negative) return "-" + encode(n, radix);
    return encode(n, radix);
  }

  public static String encode(float b, int radix) {
    boolean negative = b < 0f;
    if (negative) {
      b = -b;
    }
    int n = fromBytes(toByteArray(b), 1);
    if (negative) return "-" + encode(n, radix);

    return encode(n, radix);
  }

  public static byte[] toByteArray(int n) {
    return ByteBuffer.allocate(4).putInt(n).array();
    // return  new BigInteger(Integer.toBinaryString(n), 2).toByteArray();
  }

  public static byte[] toByteArray(long n) {
    return ByteBuffer.allocate(8).putLong(n).array();
    // return  new BigInteger(Long.toBinaryString(n), 2).toByteArray();
  }

  public static byte[] toByteArray(double n) {
    return ByteBuffer.allocate(8).putDouble(n).array();
    // return  new BigInteger(Long.toBinaryString(Double.doubleToRawLongBits(n)), 2).toByteArray();
  }

  public static byte[] toByteArray(float n) {

    // return new byte[] {(byte) (intBits >> 24), (byte) (intBits >> 16), (byte) (intBits >> 8),
    // (byte) (intBits)};
    return ByteBuffer.allocate(4).putFloat(n).array();
    // return  new BigInteger(Integer.toBinaryString(Float.floatToIntBits(n)), 2).toByteArray();

  }

  public static byte[] toByteArray(short n) {

    // return  new BigInteger(Integer.toBinaryString(n & 0xffff), 2).toByteArray();
    return new byte[] {(byte) (n >> 8), (byte) n};
  }

  /*

      public static byte[] toByteArray(long value) {


          byte[] result = new byte[8];
          for (int i = 7; i >= 0; i--) {
              result[i] = (byte) (value & 0xffL);
              value >>= 8;
          }
          return result;
      }

      public static byte [] toByteArray (double value) {
          return ByteBuffer.allocate(8).putDouble(value).array();
      }

      public static byte [] toByteArray (float value) {
          return ByteBuffer.allocate(4).putFloat(value).array();
      }


      public static byte[] toByteArray(int value) {
          return new byte[] {
                     (byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value
                 };
      }
      public static byte[] toByteArray(short value) {
          return new byte[] {
                     (byte) (value >> 8), (byte) value
                 };
      }
  */
  public static float fromBytes(byte[] bytes, float b) {
    return ByteBuffer.wrap(bytes).getFloat();
  }

  public static double fromBytes(byte[] bytes, double b) {
    return ByteBuffer.wrap(bytes).getDouble();
  }

  public static long fromBytes(byte[] bytes, long b) {
    return ByteBuffer.wrap(bytes).getLong();
  }

  public static int fromBytes(byte[] bytes, int b) {
    return ByteBuffer.wrap(bytes).getInt();
  }

  public static short fromBytes(byte[] bytes, short b) {
    return ByteBuffer.wrap(bytes).getShort();
  }

  private static HashMap<String, Integer> createMapHex2Ten() {
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    for (int i = 0; i < alphabet.length; i++) {

      map.put(alphabet[i], i);
    }
    return map;
  }
  /*
  private static HashMap<Long, String> createMapTen2Hex() {
      HashMap<Long, String> map = new HashMap<Long, String>();
      for (int i = 0; i < alphabet.length; i++) {

          map.put((long)i, alphabet[i]);
      }
      return map;
  }*/
  public static int log(int x) {
    return (int) (Math.log(x) / Math.log(2) + 1e-10);
  }

  public static char digit(int c) {

    return ((c) >= 0 && (c) < 10 ? (char)((c) + '0') : ((c) >= 10 && (c) < 36 ? (char)((c) + 'a' - 10) : (char)((c) + 'A' - 36)));
  }
  /*
  public enum NumberType {
      FLOAT(0),
      LONG(1),
      INT(2),
      DOUBLE(3);
      private final int number;
      private  NumberType(int number) {
          this.number = number;
      }
      public int getNumberType() {
          return this.number;
      }
  }

  */
}
