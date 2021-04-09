import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

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

    // updates the record and marks it dirty
    public void updateRecord (int record, String value) {
        int minIndex = (record - 1)*40;
        byte[] newVal = value.getBytes(); // converts the bytes to a string
        for (int i = 0; i < 40; i++) { // go through array manually update values
            if (i < newVal.length) { // if there is data to overwrite
                content[minIndex + i] = newVal[i];
            }
        }
        markDirty(); // marks as dirty so I/O is performed whenever the frame is evicted
        System.out.println("Write successful");
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

    public void pin () {
        pinned = true;
    }

    public void unpin() {
        pinned = false;
    }

    public void loadFrame (int frame) { // loads the frame into the content array
        try {
            if (dirty) { // make sure to write to file if dirty
                String oldFrame = System.getProperty("user.dir")+"\\src\\Project1\\F"+blockId+".txt"; // get the old frame file to override
                FileWriter writer = new FileWriter(oldFrame);
                String strToWrite = new String(content);
                writer.write(strToWrite);
                writer.close();
                dirty = false; // mark as not dirty once completed
                System.out.println("Record contents were written to disk");
            }
            blockId = frame;
            String filename = System.getProperty("user.dir")+"\\src\\Project1\\F"+frame+".txt"; // get the filename based on the frame number
            File frameFile = new File(filename);
            Scanner scanner = new Scanner(frameFile);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                content = data.getBytes(); // encode string data into bytes
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File failed to load");
        } catch (IOException e) {
            System.out.println("File failed to write");
        }
        
    }
}
