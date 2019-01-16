import Messages.PolishMessages;
import com.google.gson.JsonArray;
import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;

import java.math.BigDecimal;
import java.util.Scanner;

// My name is Adam Siedlecki. If you didn't buy this program, please donate me here (BTC address):  1LuR1RcCuXCgfjeUKEpaNjusmEaembnUXT
public class BinanceBot {

    public static SettingsLogic sl;
    public static  final String myApiKey = "qKHuQy4exRqiulmq1xbcQCfxwTAGFre3cBVPpZSjvvinOpISgGBbYqcKotYFLyiW";
    public static void main(String [] args){

        //Language and secret key settings
        sl = new SettingsLogic();
        sl.startSettings();

        //Transaction and API connection logic
        TransactionLogic tl = new TransactionLogic(new BigDecimal(3),new BigDecimal(3));





    }

}
