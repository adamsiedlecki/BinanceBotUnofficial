package BinanceBot;

import logicComponents.SettingsLogic;
import logicComponents.TransactionLogic;

import java.math.BigDecimal;

// Made by Adam Siedlecki. If you didn't buy this program, please donate me here (BTC address):  1LuR1RcCuXCgfjeUKEpaNjusmEaembnUXT
public class BinanceBot {


    public static  final String myApiKey = "VsXBkB0yOTC62N35kg7jS5z2PtueMbauX9EIVDBlEwq00QP6WEROjSWkqSSYcXus";
    public static SettingsLogic sl;
    public static void main(String [] args){



        //Language and secret key settings
        sl = new SettingsLogic();
        sl.startSettings();

        //Transaction and API connection logic
        TransactionLogic tl = new TransactionLogic(new BigDecimal(0.5), new BigDecimal(0.5));





    }

}
