package tools;

import java.io.OutputStream;
import java.io.PrintStream;

public class StandardOutputChanger {

    private static PrintStream originalPrintStream = System.out;

    public static void closeOutput(){
        originalPrintStream = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                // NO-OP
            }
        }));
    }

    public static void openOutput(){
        System.setOut(originalPrintStream);
    }
}
