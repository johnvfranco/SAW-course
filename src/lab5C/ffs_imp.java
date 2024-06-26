// compile with javac -g FFS.java (-g generates all debug information)
public class ffs_imp {
   static int ffs_imp(int i) {
      byte n = 1;
      if ((i & 0xffff) == 0) { n += 16; i >>= 16; }
      if ((i & 0x00ff) == 0) { n +=  8; i >>=  8; }
      if ((i & 0x000f) == 0) { n +=  4; i >>=  4; }
      if ((i & 0x0003) == 0) { n +=  2; i >>=  2; }
      if (i != 0) { return (n+((i+1) & 0x01)); } else { return 0; }
   }
   
   public static void main (String args[]) {
      int k = Integer.parseInt(args[0]);
      System.out.println("First 1 is at "+ffs_imp(k));		
   }
}
