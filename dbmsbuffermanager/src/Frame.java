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

    public String getRecord (int record) { // get the record from the byte array and return
        // in value will be 1-indexed so adjust accordingly
        int minIndex = (record - 1)*40;
        int maxIndex = (minIndex + 40); // each record is 40 bytes
        byte[] byteArr = Arrays.copyOfRange(content, minIndex, maxIndex); // the array of bytes to convert to a string
        String stringVal = new String(byteArr); // convert bytes to a string
        return stringVal;
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

    public boolean isPinned () {
        return pinned;
    }

    public void loadFrame (int frame) { // loads the frame into the content array
        try {
            blockId = frame;
            String filename = System.getProperty("user.dir")+"\\src\\Project1\\F"+frame+".txt"; // get the filename based on the frame number
            System.out.println(filename);
            File frameFile = new File(filename);
            Scanner scanner = new Scanner(frameFile);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                content = data.getBytes(); // encode string data into bytes
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File failed to load");
        }
        
    }
}
