import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import java.util.jar.*;

public class PlaySound extends Thread {
   String toplay;

   public PlaySound (String s) { toplay = s; }

   public void run () {
      try {
         URL url = getClass().getResource("sounds/"+toplay);
         AudioInputStream ais = AudioSystem.getAudioInputStream(url);
         Clip clip = AudioSystem.getClip();
         clip.open(ais);
         
         clip.addLineListener(new LineListener(){
               public void update(LineEvent e){
                  if (e.getType() == LineEvent.Type.STOP) {
                     e.getLine().close();
                  }
               }
            });

         clip.start();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
