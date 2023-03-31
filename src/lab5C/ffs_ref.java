// compile with javac -g ffs_ref.java (-g generates all debug information)
public class ffs_ref {
   static int ffs_ref(int word) {
      int i = 0;
      if (word == 0) return 0;
      for(int cnt = 0; cnt < 32; cnt++) if (((1 << i++) & word) != 0) return i;
      return 0;
   }
   
   public static void main (String args[]) {
      int k = Integer.parseInt(args[0]);
      System.out.println("First 1 is at "+ffs_ref(k));
   }
}
