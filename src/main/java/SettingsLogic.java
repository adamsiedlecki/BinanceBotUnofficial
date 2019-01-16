import Messages.PolishMessages;
import Messages.EnglishMessages;
import Messages.Messages;

import java.util.Scanner;

public class SettingsLogic {
    public String  secretKey;
    public static String language;
    public void startSettings(){



        System.out.println(Messages.getBtcLogo());
        System.out.println("If you bought this program, enjoy it. If not, here is my BTC address: 1LuR1RcCuXCgfjeUKEpaNjusmEaembnUXT");
        System.out.println("Please sent even a little part of Bitcoin. I will really appreciate that :)");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Please select your language: \n P - Polish \n E - English");
        Scanner input = new Scanner(System.in);
        String in = input.next();
        while(!in.equals("p")&&!in.equals("P")&&!in.equals("e")&&!in.equals("E")){
            System.out.println("Incorrect value! Please enter again such value as: e OR p");
            in = input.next();

        }



        // Actually I don't got any better idea to write it better; however it is not good way to call same methods two or more times in code.
        if(in.equals("P")||in.equals("p")){
            language="Polish";
             PolishMessages messages = new PolishMessages();
            System.out.println(messages.getWelcome());
            System.out.println(messages.getAskForSecretKey());
            secretKey = input.next();


        }else if(in.equals("E")||in.equals("e")){
            language = "English";
             EnglishMessages messages = new EnglishMessages();
            System.out.println(messages.getWelcome());
            System.out.println(messages.getAskForSecretKey());
            secretKey = input.next();

        }
        





    }

}
