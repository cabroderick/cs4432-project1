import java.nio.Buffer;

public class BufferPool {
    private int frames;
    private Frame buffers[];
    private int lastEvicted; // indicates the last evicted frame
    public BufferPool (int frames) {
        this.frames = frames;
    }
    public void initialize () {
        buffers = new Frame[frames];
        lastEvicted = -1;
        for (int i = 0; i < frames; i++) {
            buffers[i] = new Frame();
            buffers[i].initialize();
        }
    }

    public String get (int k) { // gets a record k from the buffer pool
        int block = (int) Math.floor((k - 1) / 100) + 1; // k - 1 is used instead of k because block #1 ranges from 1-100, #2 101-200, etc. // indicates which file the record is present in
        if (block > frames) { // if the block # is out of range
            return "INVALID";
        }

        String contents = ""; // the contents of the record, the value to return
        // now iterate through each frame in the buffers array. If the block is present in any of those frames, then fetch it. Otherwise it needs to be fetched into the first available frame.
        int selectedFrame = getFrameNum(block);
        if (selectedFrame == -1) { // the frame was not found loaded into any of the buffers, need to load it into first available frame
            selectedFrame = loadFrame(block); // load the given block into the first available frame
            Frame frame 
            // todo condense and refactor code right here
        } else { // frame was found loaded, simply return value
            int frameRecord = k - (block * 100); // the record number within the loaded buffer
            Frame frame = buffers[selectedFrame - 1]; // the frame being accessed, -1 to offset the 1-indexing from getFrameNum
            
        }
        contents = frame.getRecord(frameRecord);
        System.out.println("Frame # "+selectedFrame+" accessed");
        return contents;
    }

    // gets the frame number that contains the given block, or -1 if it is not found in memory
    private int getFrameNum (int block) {
        for (int i = 0; i < frames; i++) {
            Frame frame = buffers[i];
            if (frame.getBlockId() == block) {
                return i + 1; // block found in frame, +1 due to 1-indexing
            }
        }
        return -1; // block not found in frame, return -1
    }


    // loads the block number into the first available frame. If there are no available frames then eviction will occur
    private int loadFrame(int block) {
        int frame = getAvailableFrame();
        if (frame == -1) { // there are no available frames, eviction must occur
            return 1; // placeholder
        } else {
            // read into frame here
            buffers[frame - 1].loadFrame(block); // load the given block number into the frame
            return frame;
        }
    }

    // returns the first available frame to load the file into; -1 if no available frames
    private int getAvailableFrame () {
        for (int i = 0; i < frames; i++) {
            Frame frame = buffers[i];
            if (frame.getBlockId() == -1) { // indicating the frame is available
                return i + 1; // +1 for 1-indexing
            }
        }
        return -1;
    }
}
