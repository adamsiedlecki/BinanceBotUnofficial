package Tools;

import logicComponents.TransactionLogic;

public class DotWriter extends Thread {
    private int i = 0;

    public void resetDotter() {
        i = 0;
    }

    public void dropDotter() {

        StandardOutputChanger.openOutput();
        System.out.print(".");
        sleepASecond();
        i++;
        if (i % 80 == 0) {
            System.out.println(" ");
            if (i % 800 == 0) {
                System.out.println("Trying to BUY BTC with price: " + TransactionLogic.getMinBuyPrice());
                System.out.println("Current BTC price: " + TransactionLogic.getCurrentPrice());
            }
        }
        StandardOutputChanger.closeOutput();
    }

    public void riseDotter() {

        StandardOutputChanger.openOutput();
        System.out.print(".");
        sleepASecond();
        i++;
        if (i % 80 == 0) {
            System.out.println(" ");
            if (i % 800 == 0) {
                System.out.println("Trying to SELL BTC with price: " + TransactionLogic.getMinSellPrice());
                System.out.println("Current BTC price: " + TransactionLogic.getCurrentPrice());
            }
        }
        StandardOutputChanger.closeOutput();
    }

    private void sleepASecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
