import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {


    public void onUpdateReceived(Update update) {
        Model model = new Model();
        SendMessage outMessage = new SendMessage();
        outMessage.enableMarkdown(true);
        Message inMessage;
        inMessage = update.getMessage();
        System.out.println("Сообщение: " + inMessage.getText() +
                " от пользователя: " + inMessage.getFrom().getFirstName() + " " + inMessage.getFrom().getLastName());
        if (inMessage.hasText()){
            switch (inMessage.getText()) {
                case "/start":
                    outMessage.setText("Привет! Этот чат-бот сообщает погоду. Введите название города:");
                    break;
                case "/settings":
                    outMessage.setText("Что будем настраивать?");
                    break;
                case "/subscribe": {

                    Sub subscribe = new Sub(true);
                    subscribe.Subscribe();
                    outMessage.setText("Уважаемый " + inMessage.getFrom().getFirstName() +
                                "! Вы оформили подписку на ежедневную рассылку погоды! " + subscribe.Subscribe());
                    try {
                        DB.Conn();
                        DB.CreateTable();
                        DB.WriteDB("INSERT INTO 'Users' ('Name', 'LastName', 'Chat_id', 'Subscribe') " +
                                "VALUES ('" + inMessage.getFrom().getFirstName() + "', '" +
                                inMessage.getFrom().getLastName() + "', " + inMessage.getChatId() + ", '" + subscribe.Subscribe() + "'); ");
                        DB.ReadDB();
                        DB.CloseDB();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "/unsubscribe": {
                    Sub unsubscribe = new Sub(false);
                    unsubscribe.Unsubscribe();
                    outMessage.setText("Уважаемый " + inMessage.getFrom().getFirstName()
                            + "! Вы отменили ежедневную рассылку погоды! " + unsubscribe.Unsubscribe());
                    try {
                        DB.Conn();
                        DB.DelDB("DELETE FROM Users WHERE Chat_id ="+inMessage.getChatId());
                        DB.CloseDB();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default: {
                    try {
                        outMessage.setText("Прогноз погоды для " + Weather.getWeather(inMessage.getText(), model));
                        System.out.println(Weather.getWeather(inMessage.getText(), model));
                    } catch (IOException e) {
                        outMessage.setText("Такой город не найден!");
                    }
                }
            }
        }

                    try {
                        outMessage.setChatId(update.getMessage().getChatId());
                        execute(outMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

    }

    @Override
    public String getBotUsername() {
        return "BasicMyBot";
    }

    @Override
    public String getBotToken() {
        return "752078395:AAEINNh6o200I_rPsaNEro3S2EdcAU7F_qE";
    }
}
