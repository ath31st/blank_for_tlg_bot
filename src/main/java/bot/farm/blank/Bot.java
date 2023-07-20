package bot.farm.blank;

import bot.farm.blank.service.MessageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;
    private final MessageService messageService;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageProcessing(update.getMessage());
        }

        if (update.hasCallbackQuery()) {
            callBackQueryProcessing(update.getCallbackQuery());
        }
    }

    private void messageProcessing(Message message) {
        String chatId = String.valueOf(message.getChatId());
        String inputText = message.getText();

        sendTextMessage(chatId, inputText); // change here send text!
    }

    private void callBackQueryProcessing(CallbackQuery callbackQuery) {
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());

        switch (callbackQuery.getData()) {
            case "/INSERT LINK HERE 1" -> sendTextMessage(chatId,"INSERT TEXT HERE 1");

            case "/INSERT LINK HERE 2" -> sendTextMessage(chatId, "INSERT TEXT HERE 2");
            default ->
                throw new IllegalStateException("Unexpected value: " + callbackQuery.getData());
        }
    }


    public void sendTextMessage(String chatId, String text) {
        try {
            execute(messageService.createMessage(chatId, text));
        } catch (TelegramApiException e) {
            if (e.getMessage().endsWith("[403] Forbidden: bot was blocked by the user")) {
                log.info("User with chatId {} has received the \"inactive\" status", chatId);
            } else {
                log.error(e.getMessage());
            }
        }
    }
}
