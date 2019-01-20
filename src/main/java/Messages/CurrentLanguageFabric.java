package Messages;
import LogicComponents.SettingsLogic;

public class CurrentLanguageFabric {

    public  Messages getCurrentLanguage(){
        //Default language - in case of error, to protect from null object reference
        Messages messagesObject = new EnglishMessages();
        if(SettingsLogic.getLanguageParameter().equals("Polish"))
            messagesObject = new PolishMessages();
        else if(SettingsLogic.getLanguageParameter().equals("English"))
            messagesObject = new EnglishMessages();

        return messagesObject;
    }

}
