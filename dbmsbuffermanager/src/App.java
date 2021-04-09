import java.nio.Buffer;

import java.util.Scanner;

/**
 * Important notes:
 * Indexing for files and records starts at 1
 */

public class App {
    public static void main(String[] args) throws Exception {
        int frames = Integer.parseInt(args[0]);
        BufferPool pool = new BufferPool(frames);
        pool.initialize();
        getInput(pool);
    }

    private static void getInput (BufferPool pool) {
        System.out.println("The program is ready for the next command");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String[] sSplit = s.split(" ");
        if (sSplit.length != 2) {
            System.out.println("Invalid number of arguments, two required");
        } else {
            switch (sSplit[0]) {
                case ("GET"):
                    int k = parseNumber(sSplit[1]);
                    if (k > 0) {
                        System.out.println(pool.get(k));
                    }
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
        }
        //in.close();
        getInput(pool);
    }

    private static int parseNumber (String num) {
        int i;
        try {
            i = Integer.parseInt(num);
            if (i > 0) {
                return i;
            }
        } catch (NumberFormatException e) {}
        System.out.println("The entered number is not valid");
        return 0;
    }
}
