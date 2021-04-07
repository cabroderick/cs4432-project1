import java.nio.Buffer;

public class App {
    public static void main(String[] args) throws Exception {
        int frames = Integer.parseInt(args[0]);
        BufferPool pool = new BufferPool(frames);
    }
}
