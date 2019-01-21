package messages;

public class PolishMessages implements Messages {

    public  String getAskForSecretKey() {
        return askForSecretKey;
    }
    public  String getWelcome() {
        return welcome;
    }
    public  String getBtcPrice() {
        return btcPrice;
    }
    public  String getFirstMoveQuesion() {
        return firstMoveQuesion;
    }

    public  String getTradingRules() {
        return tradingRules;
    }

    public  String getYourFreeBitcoins() {
        return yourFreeBitcoins;
    }

    private   final String yourFreeBitcoins= "To jest ilość twoich wolnych Bitcoinów. Pamiętaj, że bot będzie używał ich wszystkich do tradingu!!!:";

    private   final String btcPrice = "To jest aktualna cena Bitcoina:";

    public  String getYourFreeUSDT() {
        return yourFreeUSDT;
    }

    private   final String yourFreeUSDT = "To jest ilość twoich wolnych dolarów (USDT). Pamiętaj, że bot będzie używał ich wszystkich do tradingu!!!:";



    private   final String tradingRules = "Zasady gry: \n " +
            "1. Bot wykona pierwszy ruch na giełdzie używając do tego BTC - wymieni go na USDT i tak dalej w kółko. \n " +
            "2. Określasz jednorazowo na początku, czy bot ma w tym momencie czekać na spadek czy wzrost (pierwszy ruch) \n " +
            "3. Bot dokonuje zamiany w momencie, gdy cena zmieniła się o X% w od ostatniej zajętej przez niego pozycji. \n" +
            "4. Domyślny próg procentowy wynosi 3%.\n" +
            "5. Bot nie kupuje drożej, niż sprzedał i nie sprzedaje taniej, niż kupił - jest cierpliwy. \n" +
            "6. Używanie bota wiąże się z ryzykiem. Używając go, sam jesteś odpowiedzialny za konsekwencje!!! ";


    private   final String firstMoveQuesion = "Powiedz, na jaką (pierwszą) reakcję rynku ma czekać Bot: \n" +
            "R - wprowadź R jeśli ma czekać na wzrost \n" +
            "D - wprowadź D jeśli bot ma sprzedać i czekać na spadek (po czym kupić, gdy spadek nastąpi).";

    private   final String welcome = "Witamy. Bot do tradingu w parze  USDT - BTC został uruchomiony.";

    private   final String askForSecretKey = "Wprowadź swój secret key:";


}
