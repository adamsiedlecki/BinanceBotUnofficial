package Messages;
import LogicComponents.SettingsLogic;

public class CurrentLanguageFabric {

    public static   Messages getCurrentLanguage(){
        //Default language - in case of error, to protect from null object reference
        Messages messagesObject = new EnglishMessages();
        if(SettingsLogic.language.equals("Polish"))
            messagesObject = new PolishMessages();
        else if(SettingsLogic.language.equals("English"))
            messagesObject = new EnglishMessages();

        return messagesObject;
    }

}
