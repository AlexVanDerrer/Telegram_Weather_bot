import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.ApiContextInitializer;


public class MainClass {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi tba = new TelegramBotsApi();
        try {
            tba.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
