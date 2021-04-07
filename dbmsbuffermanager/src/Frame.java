import java.util.Arrays;

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

    public byte[] getRecord (int record) {
        return Arrays.copyOfRange(content, record*40, (record*40)+40);
    }

    public void updateRecord (int record, byte[] newValue) {
        markDirty();
    }

    public void markDirty () {
        dirty = true;
    }
}
