import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
    public static void main(String[] argv) throws TelegramApiRequestException {
        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new myBot());
        }
        catch (TelegramApiRequestException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }


    }
}
