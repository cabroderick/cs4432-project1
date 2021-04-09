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

    private int getBlock (int k) {
        return (int) Math.floor((k - 1) / 100) + 1; // k - 1 is used instead of k because block #1 ranges from 1-100, #2 101-200, etc. // indicates which file the record is present in
    }

    private int getFrameRecord (int k, int block) { // the record number within the loaded buffer
        return k + 100 - (block * 100);
    }

    // update a buffer in the table
    public void set (int k, String value) {
        int block = getBlock(k);
        int selectedFrame = getFrameNum(block);
        if (selectedFrame == -1) { // the frame was not found loaded into any of the buffers, need to load it into first available frame
            selectedFrame = loadFrame(block); // load the given block into the first available frame
            if (selectedFrame == -1) { // all frames are pinned
                System.out.println("The corresponding block #"+block+" cannot be accessed from disk because the memory buffers are full");
                return;
            }
            System.out.println("I/O was performed to load the block from memory");
        } else {
            System.out.println("No I/O was performed");
        }
        int frameRecord = getFrameRecord(k, block);
        Frame frame = buffers[selectedFrame - 1]; // the frame being accessed, -1 to offset the 1-indexing from getFrameNum
        frame.updateRecord(frameRecord, value); // update the record
    }

    public String get (int k) { // gets a record k from the buffer pool
        int block = getBlock(k);
        // if (block > frames) { // if the block # is out of range
        //     return "The corresponding block # "+block+"cannot be accessed from disk because the memory buffers are all full.";
        // }

        // now iterate through each frame in the buffers array. If the block is present in any of those frames, then fetch it. Otherwise it needs to be fetched into the first available frame.
        int selectedFrame = getFrameNum(block);
        if (selectedFrame == -1) { // the frame was not found loaded into any of the buffers, need to load it into first available frame
            selectedFrame = loadFrame(block); // load the given block into the first available frame
            if (selectedFrame == -1) { // all frames are pinned
                return("The corresponding block #"+block+" cannot be accessed from disk because the memory buffers are full");
            }
            System.out.println("I/O was performed to load the block from memory");
        } else {
            System.out.println("No I/O was performed");
        }
        int frameRecord = getFrameRecord(k, block);
        Frame frame = buffers[selectedFrame - 1]; // the frame being accessed, -1 to offset the 1-indexing from getFrameNum
        String contents = frame.getRecord(frameRecord);
        System.out.println("The block was contained in frame #"+selectedFrame);
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


    // loads the block number into the first available frame. If there are no available frames then eviction will occur. Returns the frame that is filled
    private int loadFrame(int block) {
        int frame = getAvailableFrame();
        if (frame == -1) { // there are no available frames, eviction must occur
            int startFrame = 0;
            if (lastEvicted != -1) { // if there is a frame that was last evicted
                startFrame = lastEvicted + 1; // start looking at the last evicted frame
                if (startFrame >= frames) { // to avoid out of bounds errors
                    startFrame = 0; // restart at 0
                }
            }
            for (int i = startFrame; i < frames; i++) {
                if (!buffers[i].isPinned()) {
                    buffers[i].loadFrame(block);
                    lastEvicted = i; // mark this frame as the last evicted
                    System.out.println("Frame "+(i+1)+" was evicted");
                    frame = i + 1;
                    return frame;
                }
            }
            return -1; // in this case nothing can be done
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
