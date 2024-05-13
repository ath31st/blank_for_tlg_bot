package bot.farm.blank;

import bot.farm.blank.service.KeyboardService;
import bot.farm.blank.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
public class Bot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
  private final TelegramClient telegramClient;
  private final String botToken;
  private final MessageService messageService;
  private final KeyboardService keyboardService;

  public Bot(@Value("${telegram.bot.token}") String botToken,
             MessageService messageService,
             KeyboardService keyboardService) {
    this.botToken = botToken;
    this.messageService = messageService;
    this.keyboardService = keyboardService;
    this.telegramClient = new OkHttpTelegramClient(this.botToken);
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public LongPollingUpdateConsumer getUpdatesConsumer() {
    return this;
  }

  @Override
  public void consume(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      long chatId = update.getMessage().getChatId();
      String text = update.getMessage().getText();
      try {
        SendMessage sendMessage = messageService.createMessage(chatId,text);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardService.createInlineKeyboardMarkup());
        
        telegramClient.execute(sendMessage);
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    } else if (update.hasCallbackQuery()) {
      // Set variables
      long chatId = update.getCallbackQuery().getMessage().getChatId();
      String callData = update.getCallbackQuery().getData();
      long messageId = update.getCallbackQuery().getMessage().getMessageId();

      if (callData.equals("update_msg_text")) {
        String answer = "Updated message text";
        EditMessageText newMessage = EditMessageText.builder()
            .chatId(chatId)
            .messageId(Math.toIntExact(messageId))
            .text(answer)
            .build();
        try {
          telegramClient.execute(newMessage);
        } catch (TelegramApiException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
