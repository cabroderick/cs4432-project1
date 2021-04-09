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
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("The program is ready for the next command");
            String s = in.nextLine();
            String[] sSplit = s.split(" ");
            if (sSplit.length < 2) {
                System.out.println("Invalid number of arguments, at least two required");
            } else {
                int k = parseNumber(sSplit[1]);
                switch (sSplit[0]) {
                    case ("GET"):
                        if (k > 0) {
                            System.out.println(pool.get(k));
                        }
                        break;
                    case ("SET"):
                        if (sSplit.length < 3) {
                            System.out.println("3 arguments are required for SET");
                        } else {
                            if (k > 0) {
                                String val = sSplit[2];
                                pool.set(k, val);
                            }
                        }
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
        }
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
