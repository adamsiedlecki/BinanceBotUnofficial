import Messages.EnglishMessages;
import Messages.PolishMessages;
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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class TransactionLogic {
    private  BigDecimal percentOfDropToBuyBTC;
    private BigDecimal percentOfUpToSellBTC;
    private BinanceApi binanceApi;
    private Scanner input;
    private String language  = SettingsLogic.language;
    private BigDecimal lastBTCprice;
    private boolean toDrop;
    private BigDecimal minSellPrice;
    private BigDecimal minBuyPrice;
    private File textFile;
    private PrintWriter writer;
    private JsonArray balances;
    //private BigDecimal lastMovePrice;


    public TransactionLogic(BigDecimal percentOfDropToBuyBTC,BigDecimal percentOfUpToSellBTC){
        this.percentOfDropToBuyBTC = percentOfDropToBuyBTC;
        this.percentOfUpToSellBTC = percentOfUpToSellBTC;
        try {
            textFile = new File("TransactionHistory"+ new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) +".txt");
            writer = new PrintWriter(textFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

             binanceApi = new BinanceApi();

             //binanceApi.secretKey = BinanceBot.sl.secretKey;


            binanceApi.apiKey = BinanceBot.myApiKey;

            //Tymczasowe - wywal po ukończeniu testów!!!
            binanceApi.secretKey = "Erpd0BxHlqKG01SxAGpDi3NL2xCHkx7ytQdfDmvbHbgemh33fwgxHbjDK5Mdry6i";

            balances = binanceApi.account().getAsJsonArray("balances");


            //Language part:
            if(language.equals("Polish")){

                //Prints BTC amount
                System.out.println(PolishMessages.getYourFreeBitcoins());
                System.out.println(balances.get(0).getAsJsonObject().get("free"));

                //Prints USDT amount
                System.out.println(PolishMessages.getYourFreeUSDT());
                System.out.println(balances.get(11).getAsJsonObject().get("free"));

                //Trading logic
                System.out.println(PolishMessages.getFirstMoveQuesion());
                lastBTCprice = binanceApi.pricesMap().get("BTCUSDT");
                System.out.println(PolishMessages.getBtcPrice()+" "+lastBTCprice);
                input = new Scanner(System.in);
                String in = input.next();
                while(!in.equals("W")&&!in.equals("w")&&!in.equals("S")&&!in.equals("s")){
                    in = input.next();
                }
                if(in.equals("W")||in.equals("w")){
                    //czekamy
                    toDrop = false;

                    while(true){
                        tradeAction();
                    }
                }else if(in.equals("S")||in.equals("s")){
                    //sprzedajemy
                    toDrop = true;
                    while(true){
                        tradeAction();
                    }
                }

            }else if(language.equals("English")){

                //Prints BTC amount
                System.out.println(EnglishMessages.getYourFreeBitcoins());
                System.out.println(balances.get(0).getAsJsonObject().get("free"));

                //Prints USDT amount
                System.out.println(EnglishMessages.getYourFreeUSDT());
                System.out.println(balances.get(11).getAsJsonObject().get("free"));

                //Trading logic
                System.out.println(EnglishMessages.getFirstMoveQuesion());
                lastBTCprice = binanceApi.pricesMap().get("BTCUSDT");
                System.out.println(EnglishMessages.getBtcPrice()+" "+lastBTCprice);
                Scanner input = new Scanner(System.in);
                String in = input.next();
                while(!in.equals("R")&&!in.equals("r")&&!in.equals("D")&&!in.equals("d")){
                    in = input.next();
                }

                if(in.equals("R")||in.equals("r")){
                    //czekamy
                    toDrop = false;

                    while(true){
                        tradeAction();
                    }

                    //System.out.println(minSellPrice);
                }else if(in.equals("D")||in.equals("d")){
                    //sprzedajemy
                    toDrop = true;
                    while(true){
                        tradeAction();
                    }
                }

            }else{
                System.out.println("Language ERROR");
            }



           // System.out.println("Cena Bitcoina w USDT: "+binanceApi.pricesMap().get("BTCUSDT"));

        } catch (BinanceApiException e) {
            e.printStackTrace();
            System.out.println("Nieoczekiwany błąd: "+e);
        }
    }

    private void tradeAction(){
        if(toDrop==true){
            try {
                lastBTCprice = binanceApi.pricesMap().get("BTCUSDT");
            } catch (BinanceApiException e) {
                e.printStackTrace();
            }
            minSellPrice = lastBTCprice.multiply((percentOfUpToSellBTC.add(new BigDecimal(100)).divide(new BigDecimal(100))));
            while(true){
                if(lastBTCprice.compareTo(minSellPrice)==1||lastBTCprice.compareTo(minSellPrice)==0){
                    //sprzedajemy
                    try {
                        writer.println("You sold "+balances.get(0).getAsJsonObject().get("free")+ " bitcoins with price: "+lastBTCprice);
                        binanceApi.createOrder(new BinanceOrderPlacement(BinanceSymbol.BTC("USDT"), BinanceOrderSide.SELL));

                        break;
                    } catch (BinanceApiException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    lastBTCprice = binanceApi.pricesMap().get("BTCUSDT");
                } catch (BinanceApiException e) {
                    e.printStackTrace();
                }

            }
            toDrop = false;
        }else if(toDrop==false){
            try {
                lastBTCprice = binanceApi.pricesMap().get("BTCUSDT");
            } catch (BinanceApiException e) {
                e.printStackTrace();
            }
            minBuyPrice = lastBTCprice.multiply((new BigDecimal(100).subtract(percentOfDropToBuyBTC)).divide(new BigDecimal(100)));
            while(true){
                if(lastBTCprice.compareTo(minBuyPrice)==-1||lastBTCprice.compareTo(minBuyPrice)==0){
                    //kupujemy
                    try {
                        binanceApi.createOrder(new BinanceOrderPlacement(BinanceSymbol.BTC("USDT"), BinanceOrderSide.BUY));
                        writer.println("You bought "+balances.get(0).getAsJsonObject().get("free")+ " bitcoins with price: "+lastBTCprice);
                        break;
                    } catch (BinanceApiException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    lastBTCprice = binanceApi.pricesMap().get("BTCUSDT");
                } catch (BinanceApiException e) {
                    e.printStackTrace();
                }

            }
            toDrop = true;

        }

    }


}
