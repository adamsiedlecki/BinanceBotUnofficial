package Messages;

public class PolishMessages extends Messages {





    private static  final String welcome = "Witamy. Bot do tradingu w parze  USDT - BTC został uruchomiony.";
    private static  final String askForSecretKey = "Wprowadź swój secret key:";

    public static String getYourFreeBitcoins() {
        return yourFreeBitcoins;
    }

    private static  final String yourFreeBitcoins= "To jest ilość twoich wolnych Bitcoinów. Pamiętaj, że bot będzie używał ich wszystkich do tradingu!!!:";

    public static String getBtcPrice() {
        return btcPrice;
    }

    private static  final String btcPrice = "To jest aktualna cena Bitcoina:";

    public static String getYourFreeUSDT() {
        return yourFreeUSDT;
    }

    private static  final String yourFreeUSDT = "To jest ilość twoich wolnych dolarów (USDT). Pamiętaj, że bot będzie używał ich wszystkich do tradingu!!!:";

    public static String getTradingRules() {
        return tradingRules;
    }

    private static  final String tradingRules = "Zasady gry: \n " +
            "1. Bot wykona pierwszy ruch na giełdzie używając do tego BTC - wymieni go na USDT i tak dalej w kółko. \n " +
            "2. Określasz jednorazowo na początku, czy bot ma w tym momencie czekać na spadek czy wzrost (pierwszy ruch) \n " +
            "3. Bot dokonuje zamiany w momencie, gdy cena zmieniła się o X% w od ostatniej zajętej przez niego pozycji. \n" +
            "4. Domyślny próg procentowy wynosi 3%.\n" +
            "5. Bot nie kupuje drożej, niż sprzedał i nie sprzedaje taniej, niż kupił - jest cierpliwy. \n" +
            "6. Używanie bota wiąże się z ryzykiem. Używając go, sam jesteś odpowiedzialny za konsekwencje!!! ";

    public static String getFirstMoveQuesion() {
        return firstMoveQuesion;
    }

    private static  final String firstMoveQuesion = "Powiedz, na jaką (pierwszą) reakcję rynku ma czekać Bot: \n" +
            "W - wprowadź W jeśli ma czekać na wzrost \n" +
            "S - wprowadź S jeśli bot ma sprzedać i czekać na spadek (po czym kupić, gdy spadek nastąpi).";

    public static String getAskForSecretKey() {
        return askForSecretKey;
    }
    public static String getWelcome() {
        return welcome;
    }
}
