package messages;
import logicComponents.SettingsLogic;

public class CurrentLanguageFactory {

    public  Messages getCurrentLanguage(){
        //Default language - in case of error, to protect from null object reference
        Messages messagesObject ;
        if("Polish".equals(SettingsLogic.getLanguageParameter()))
            messagesObject = new PolishMessages();
        else if("English".equals(SettingsLogic.getLanguageParameter())) {
            messagesObject = new EnglishMessages();
        }else
            messagesObject = new EnglishMessages();

        return messagesObject;
    }

}
