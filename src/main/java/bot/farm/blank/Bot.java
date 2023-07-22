package bot.farm.blank;

import bot.farm.blank.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
  @Value("${telegram.bot.name}")
  private String botName;
  @Value("${telegram.bot.token}")
  private String botToken;
  private final BotService botService;

  @Override
  public String getBotUsername() {
    return botName;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage sendMsg = null;
    if (update.hasMessage() && update.getMessage().hasText()) {
      sendMsg = botService.handleMessage(update.getMessage());
    }

    if (update.hasCallbackQuery()) {
      sendMsg = botService.handleCallback(update.getCallbackQuery());
    }

    try {
      execute(sendMsg);
    } catch (TelegramApiException e) {
      if (e.getMessage().endsWith("[403] Forbidden: bot was blocked by the user")) {
        log.info("User with chatId {} has received the \"inactive\" status", sendMsg.getChatId());
      } else {
        log.error(e.getMessage());
      }
    }
  }

//  private void messageProcessing(Message message) {
//    String chatId = String.valueOf(message.getChatId());
//    String inputText = message.getText();
//
//    sendTextMessage(chatId, inputText); // change here send text!
//  }

//  private void callBackQueryProcessing(CallbackQuery callbackQuery) {
//    String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
//
//    switch (callbackQuery.getData()) {
//      case "/INSERT LINK HERE 1" -> sendTextMessage(chatId, "INSERT TEXT HERE 1");
//
//      case "/INSERT LINK HERE 2" -> sendTextMessage(chatId, "INSERT TEXT HERE 2");
//      default -> throw new IllegalStateException("Unexpected value: " + callbackQuery.getData());
//    }
//  }


//  public void sendTextMessage(String chatId, String text) {
//    try {
//      execute(messageService.createMessage(chatId, text));
//    } catch (TelegramApiException e) {
//      if (e.getMessage().endsWith("[403] Forbidden: bot was blocked by the user")) {
//        log.info("User with chatId {} has received the \"inactive\" status", chatId);
//      } else {
//        log.error(e.getMessage());
//      }
//    }
//  }
}
