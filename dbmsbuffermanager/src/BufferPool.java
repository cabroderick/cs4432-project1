import java.nio.Buffer;

public class BufferPool {
    private int frames;
    private Frame buffers[];
    public BufferPool (int frames) {
        this.frames = frames;
    }
    public void initialize () {
        buffers = new Frame[frames];
        for (int i = 0; i < frames; i++) {
            buffers[i] = new Frame();
            buffers[i].initialize();
        }
    }
}
