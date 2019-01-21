package BinanceBot;

import logicComponents.SettingsLogic;
import logicComponents.TransactionLogic;

import java.math.BigDecimal;

// Made by Adam Siedlecki. If you didn't buy this program, please donate me here (BTC address):  1LuR1RcCuXCgfjeUKEpaNjusmEaembnUXT
public class BinanceBot {


    public static  final String myApiKey = "qKHuQy4exRqiulmq1xbcQCfxwTAGFre3cBVPpZSjvvinOpISgGBbYqcKotYFLyiW";

    public static void main(String [] args){



        //Language and secret key settings
        SettingsLogic sl = new SettingsLogic();
        sl.startSettings();

        //Transaction and API connection logic
        TransactionLogic tl = new TransactionLogic(new BigDecimal(3),new BigDecimal(3));





    }

}
