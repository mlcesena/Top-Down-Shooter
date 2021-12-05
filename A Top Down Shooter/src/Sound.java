import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    
    Clip clip;

    public Sound (String name) {
        try {
            String fullPath = System.getProperty("user.dir") + "/src/sounds/" + name;
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(fullPath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }
    public void start() {
        clip.start();
        while (clip.isRunning()) {
            if (!clip.isRunning())
			    clip.close();
        }
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void reset() {
        clip.stop();
        clip.setMicrosecondPosition(0);
    }
}
