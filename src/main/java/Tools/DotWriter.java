package Tools;

import logicComponents.TransactionLogic;

public class DotWriter extends Thread {

    public static void dropDotter() {
        int i = 0;
        StandardOutputChanger.openOutput();
        // System.out.println("Trying to BUY BTC with price: "+minBuyPrice);
        System.out.print(".");
        sleepASecond();
        i++;
        if (i % 80 == 0) {
            System.out.println(" ");
            if (i % 800 == 0)
                System.out.println("Trying to BUY BTC with price: " + TransactionLogic.getMinBuyPrice());
        }
        StandardOutputChanger.closeOutput();
    }

    public static void riseDotter() {
        int i = 0;
        StandardOutputChanger.openOutput();
        System.out.print(".");
        sleepASecond();
        i++;
        if (i % 80 == 0) {
            System.out.println(" ");
            if (i % 800 == 0) {
                System.out.println("Trying to SELL BTC with price: " + TransactionLogic.getMinSellPrice());
            }
        }
        StandardOutputChanger.closeOutput();
    }

    private static void sleepASecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
