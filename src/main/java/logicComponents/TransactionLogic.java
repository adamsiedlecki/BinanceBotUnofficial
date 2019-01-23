package logicComponents;

import BinanceBot.BinanceBot;
import messages.CurrentLanguageFactory;
import messages.Messages;
import com.google.gson.JsonArray;
import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;
import com.webcerebrium.binance.datatype.BinanceOrderPlacement;
import com.webcerebrium.binance.datatype.BinanceOrderSide;
import com.webcerebrium.binance.datatype.BinanceSymbol;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TransactionLogic {
    private BigDecimal percentOfDropToBuyBTC;
    private BigDecimal percentOfUpToSellBTC;
    private BinanceApi binanceApi;
    private Scanner input;
    private BigDecimal lastBTCprice;
    private boolean toDrop;
    private BigDecimal minSellPrice;
    private BigDecimal minBuyPrice;
    private File textFile;
    private PrintWriter writer;
    private JsonArray balances;
    private CurrentLanguageFactory languageFabric;
    private Messages messages;
    private String in;
    //private BigDecimal lastMovePrice;


    public TransactionLogic(BigDecimal percentOfDropToBuyBTC, BigDecimal percentOfUpToSellBTC) {
        this.percentOfDropToBuyBTC = percentOfDropToBuyBTC;
        this.percentOfUpToSellBTC = percentOfUpToSellBTC;

        apiConfiguration();
        logsFileCreation();
        firstMessagesAndSettings();

        if ("W".equals(in) || "w".equals(in)) {
            //czekamy
            toDrop = false;

            while (true) {
                try {
                    tradeAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if ("S".equals(in) || "s".equals(in)) {
            //sprzedajemy
            toDrop = true;
            while (true) {
                try {
                    tradeAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void logsFileCreation() {
        try {
            textFile = new File("TransactionHistory" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".txt");
            writer = new PrintWriter(textFile);
            writer.println("Logs of BinanceBot:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void apiConfiguration() {
        binanceApi = new BinanceApi();

        //binanceApi.secretKey = BinanceBot.BinanceBot.sl.secretKey;

        binanceApi.apiKey = BinanceBot.myApiKey;

        //Tymczasowe - wywal po ukończeniu testów!!!
        binanceApi.secretKey = "Erpd0BxHlqKG01SxAGpDi3NL2xCHkx7ytQdfDmvbHbgemh33fwgxHbjDK5Mdry6i";

        try {
            balances = binanceApi.account().getAsJsonArray("balances");
        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
    }

    private void firstMessagesAndSettings() {
        //Language part:
        languageFabric = new CurrentLanguageFactory();
        messages = languageFabric.getCurrentLanguage();
        //Prints BTC amount
        System.out.println(messages.getYourFreeBitcoins());
        System.out.println(balances.get(0).getAsJsonObject().get("free"));

        //Prints USDT amount
        System.out.println(messages.getYourFreeUSDT());
        System.out.println(balances.get(11).getAsJsonObject().get("free"));

        //Trading logic
        System.out.println(messages.getFirstMoveQuesion());
        try {
            lastBTCprice = binanceApi.pricesMap().get("BTCUSDT");
        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
        System.out.println(messages.getBtcPrice() + " " + lastBTCprice);
        input = new Scanner(System.in);
        in = input.next();
        while (!"W".equals(in) && !"w".equals(in) && !"S".equals(in) && !"s".equals(in)) {
            in = input.next();
        }
    }

    private void lastBTCpriceUpdate() {
        try {
            lastBTCprice = binanceApi.pricesMap().get("BTCUSDT");
        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
    }

    private void bitcoinBuyOrder() {
        try {
            binanceApi.createOrder(new BinanceOrderPlacement(BinanceSymbol.BTC("USDT"), BinanceOrderSide.BUY));
            writer.println("You bought " + balances.get(0).getAsJsonObject().get("free") + " bitcoins with price: " + lastBTCprice);
        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
    }

    private void bitcoinSellOrder() {
        try {
            writer.println("You sold " + balances.get(0).getAsJsonObject().get("free") + " bitcoins with price: " + lastBTCprice);
            binanceApi.createOrder(new BinanceOrderPlacement(BinanceSymbol.BTC("USDT"), BinanceOrderSide.SELL));
        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
    }

    private void tradeAction() {
        if (toDrop) {
            lastBTCpriceUpdate();
            minSellPrice = lastBTCprice.multiply((percentOfUpToSellBTC.add(new BigDecimal(100)).divide(new BigDecimal(100), new MathContext(5))));
            while (true) {
                if (lastBTCprice.compareTo(minSellPrice) > 0 || lastBTCprice.compareTo(minSellPrice) == 0) {
                    //sprzedajemy
                    bitcoinSellOrder();
                    toDrop = false;
                    break;
                }
                lastBTCpriceUpdate();

            }

        } else if (!toDrop) {
            lastBTCpriceUpdate();
            minBuyPrice = lastBTCprice.multiply((new BigDecimal(100).subtract(percentOfDropToBuyBTC)).divide(new BigDecimal(100), new MathContext(5)));
            while (true) {
                if (lastBTCprice.compareTo(minBuyPrice) <= 0) {
                    //kupujemy
                    bitcoinBuyOrder();
                    toDrop = true;
                    break;
                }
                lastBTCpriceUpdate();
            }


        }

    }


}
