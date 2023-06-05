package base62;
//import base62.NativeUtils;
public class Base62JNI {  // Save as Base62JNI.java


   static {
      
      String os = System.getProperty("os.name").toLowerCase();
      try {
         if (os.indexOf("win") >= 0) {
            NativeUtils.loadLibraryFromJar("/resource/Base62.dll");
         } else if(os.indexOf("linux") >= 0) {
            NativeUtils.loadLibraryFromJar("/resource/libBase62.so");
         }
      } catch (Exception e) {
         System.loadLibrary("base62");
      }
         //System.loadLibrary("base62");
      

       // Load native library hello.dll (Windows) or libhello.so (Unixes)
      //  at runtime
      // This library contains a native method called sayHello()
   }

   // Declare an instance native method sayHello() which receives no parameter and returns void
   native  String encode(long n, int b);
   native  String encode(int n, int b);
   native  String encode(double n, int b);
   native  String encode(float n, int b);
   native long decode(String s, int b);
   native double decodeD(String s, int b);
   native float decodeF(String s, int b);

   // Test Driver
   public static void main(String[] args) {
      final Base62JNI bb=new base62.Base62JNI();
      System.out.println( bb.encode(120160524121052485L, 62) ); // Create an instance and invoke the native method
      System.out.println( bb.decode("8SkRptSbMV", 62) ); // Create an instance and invoke the native method
      System.out.println( bb.encode(12345.12345608900043F, 62) ); // Create an instance and invoke the native method
      System.out.println( bb.decodeF("1hLwLA", 62) ); // Create an instance and invoke the native method

      System.out.println( bb.encode(12345.12345608900043D, 62) ); // Create an instance and invoke the native method
      System.out.println( bb.decodeD("5yPxSlKBjRC", 62) ); // Create an instance and invoke the native method

   }
}
