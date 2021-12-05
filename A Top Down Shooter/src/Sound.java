import java.io.File;
import javax.sound.sampled.*;

/**
 * Sound class enables us to create music and sound effects for our game through Java's imported libraries.
 * 
 * @author Tyler Battershell
 */
public class Sound {
    
    Clip clip;

    /**
     * Overloading constructor to create a sound clip from the given file name.
     * Uses AudioInputStream and CLip from Java library.
     * 
     * @param name - File name (.wav)
     */
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

    /**
     * start() method starts a clip and closes it after it is done running.
     */
    public void start() {
        clip.start();
        while (clip.isRunning()) {
            if (!clip.isRunning())
			    clip.close();
        }
    }

    /**
     * loop() method starts a clip and continues to loop that clip indefinitely.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * reset() method stops a playing clip and sets that clip back to the beginning.
     */
    public void reset() {
        clip.stop();
        clip.setMicrosecondPosition(0);
    }
}
