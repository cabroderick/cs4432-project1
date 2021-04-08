import java.nio.Buffer;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        int frames = Integer.parseInt(args[0]);
        BufferPool pool = new BufferPool(frames);
        getInput();
    }

    private static void getInput () {
        System.out.println("The program is ready for the next command");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String[] sSplit = s.split(" ");
        switch (sSplit[0]) {
            case ("GET"):
                break;
            case ("SET"):
                break;
            case ("PIN"):
                break;
            case ("UNPIN"):
                break;
            default:
                System.out.println("The entered command is not valid: please use GET, SET, PIN, OR UNPIN");
                break;
        }
        getInput();
    }
}
