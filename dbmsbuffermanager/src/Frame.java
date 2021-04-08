import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Frame {
    private byte content[];
    private boolean dirty;
    private boolean pinned;
    private int blockId;

    public void initialize () {
        content = new byte[4000];
        dirty = false;
        pinned = false;
        blockId = -1;
    }

    public String getRecord (int record) {
        return null;
    }

    public void updateRecord (int record, byte[] newValue) {
        markDirty();
    }

    public void markDirty () {
        dirty = true;
    }

    public int getBlockId () {
        return blockId;
    }

    public void loadFrame (int frame) { // loads the frame into the content array
        try {
            blockId = frame;
            String filename = "F"+frame; // get the filename based on the frame number
            File frameFile = new File(filename);
            Scanner scanner = new Scanner(frameFile);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                content = data.getBytes(); // encode string data into bytes
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Why did you get here this wasn't supposed to happen");
        }
        
    }
}
