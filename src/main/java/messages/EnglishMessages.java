package messages;

public class EnglishMessages implements Messages {


    public  String getAskForSecretKey() {
        return askForSecretKey;
    }
    public  String getWelcome() {
        return welcome;
    }
    public  String getYourFreeBitcoins() {
        return yourFreeBitcoins;
    }

    public  String getBtcPrice() {
        return btcPrice;
    }
    public  String getYourFreeUSDT() {
        return yourFreeUSDT;
    }
    public  String getTradingRules() {
        return tradingRules;
    }

    public  String getFirstMoveQuesion() {
        return firstMoveQuesion;
    }

    private   final String btcPrice = "This is actual BTC price:";
    private   final String yourFreeUSDT = "This is amount of dolars (USDT) that are on your account. Remember, bot will use all free btc for trading!!!:";
    private   final String yourFreeBitcoins= "This is amount of Bitcoins that are on your account. Remember, bot will use all free btc for trading!!!:";
    private   final String welcome = "Hi! Trading bot has been started.";
    private   final String askForSecretKey = "Please input your secret key:";

    private   final String firstMoveQuesion = "Say, for what (first) market reaction bot must wait: \n" +
            "R - input R if bot must wait for BTC rise \n" +
            "D - input D if bot must sell BTC and wait for BTC drop (after that he will buy).";

    private   final String tradingRules = "Trading rules: \n " +
            "1. Bot makes first market move using BTC - he will change it to USD and so on, over and over again. \n " +
            "2. You decide on beginning, if bot must wait for BTC drop or rise (first move).  \n " +
            "3. Bot exchanges when price changed X% prom his position. \n" +
            "4. Default percent threshold is 3%.\n" +
            "5. Bot dont't buy highter than he sold and don't sell lower than he bought - he is patient. \n" +
            "6. You use bot on your own risk!!! ";



}
