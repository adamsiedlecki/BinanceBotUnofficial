package logicComponents;

import BinanceBot.BinanceBot;
import com.webcerebrium.binance.datatype.*;
import messages.CurrentLanguageFactory;
import messages.Messages;
import com.google.gson.JsonArray;
import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    private BigDecimal lastUSDTprice;
    private boolean firstSellMove;
    //private BigDecimal lastMovePrice;


    public TransactionLogic(BigDecimal percentOfDropToBuyBTC, BigDecimal percentOfUpToSellBTC) {
        this.percentOfDropToBuyBTC = percentOfDropToBuyBTC;
        this.percentOfUpToSellBTC = percentOfUpToSellBTC;

        apiConfiguration();
        logsFileCreation();
        firstMessagesAndSettings();
        writer.println("TRADING_BOT LOGS");

        if ("R".equals(in) || "r".equals(in)) {
            //czekamy
            toDrop = false;

            while (true) {
                try {
                    tradeAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if ("D".equals(in) || "d".equals(in)) {
            //sprzedajemy
            toDrop = true;
            firstSellMove = true;
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
            writer.flush();
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
            lastBTCpriceUpdate();
        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
        System.out.println(messages.getBtcPrice() + " " + lastBTCprice);
        input = new Scanner(System.in);
        in = input.next();
        while (!"R".equals(in) && !"r".equals(in) && !"D".equals(in) && !"d".equals(in)) {
            System.out.println(messages.getIncorrectValue());;
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

    private void lastUSDTpriceUpdate() {
        try {
            lastUSDTprice = binanceApi.pricesMap().get("USDTBTC");
        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
    }

    private BigDecimal getFreeBitcoins() {
        return balances.get(0).getAsJsonObject().get("free").getAsBigDecimal();
    }

    private BigDecimal getFreeUsdt() {

           return balances.get(11).getAsJsonObject().get("free").getAsBigDecimal();

    }

    private void bitcoinBuyOrder() {
        try {
            BinanceSymbol symbol = BinanceSymbol.BTC("USDT");
            BinanceOrderPlacement placement = new BinanceOrderPlacement(symbol, BinanceOrderSide.BUY);
            placement.setType(BinanceOrderType.MARKET);
            placement.setPrice(lastBTCprice);
            BigDecimal amountToBuy = getFreeUsdt().divide(lastBTCprice,5) ;
            placement.setQuantity(amountToBuy.round(new MathContext(6, RoundingMode.HALF_UP))); // buy 10000 of asset for 0.00001 BTC
            BinanceOrder order = binanceApi.getOrderById(symbol, binanceApi.createOrder(placement).get("orderId").getAsLong());
            System.out.println(order.toString());
            writer.println("You made an order: " +order.toString());
            writer.flush();
        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
    }

    private void bitcoinSellOrder() {
        try {
            BinanceSymbol symbol = BinanceSymbol.USDT("BTC");
            BinanceOrderPlacement placement = new BinanceOrderPlacement(symbol, BinanceOrderSide.SELL);
            placement.setType(BinanceOrderType.MARKET);

//            BinanceExchangeInfo binanceExchangeInfo = binanceApi.exchangeInfo();
//            List<BinanceExchangeSymbol> symbols = binanceExchangeInfo.getSymbols();
//            BinanceExchangeSymbol BTC = symbols.stream().filter(a -> a.getQuoteAsset().equals("BTC")).findFirst().get();

//            System.out.println(BTC.getLotSize());


            placement.setPrice(lastBTCprice);
            BigDecimal amountToSell = getFreeBitcoins();


            placement.setQuantity(amountToSell.round(new MathContext(6, RoundingMode.HALF_UP))); // buy 10000 of asset for 0.00001 BTC
            BinanceOrder order = binanceApi.getOrderById(symbol, binanceApi.createOrder(placement).get("orderId").getAsLong());
            System.out.println(order.toString());
            writer.println("You made an order with BTC price: "+lastBTCprice+" " +order.toString());
            writer.flush();
            //writer.println("You sold " + balances.get(0).getAsJsonObject().get("free") + " bitcoins with price: " + lastBTCprice);

        } catch (BinanceApiException e) {
            e.printStackTrace();
        }
    }

    private void tradeAction() {
        if (toDrop) {

            minSellPrice = lastBTCprice.multiply((percentOfUpToSellBTC.add(new BigDecimal(100)).divide(new BigDecimal(100), new MathContext(5))));
            while (true) {
                if (lastBTCprice.compareTo(minSellPrice) > 0 || lastBTCprice.compareTo(minSellPrice) == 0||firstSellMove) {
                    //sprzedajemy
                    bitcoinSellOrder();
                    toDrop = false;
                    firstSellMove = false;
                    break;
                }
                lastBTCpriceUpdate();

            }

        } else if (!toDrop) {

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
