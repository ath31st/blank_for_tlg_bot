package bot.farm.blank;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
public class Bot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
  private final TelegramClient telegramClient;
  private final String botToken;

  public Bot(@Value("${telegram.bot.token}") String botToken) {
    this.botToken = botToken;
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
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      // Set variables
      String message_text = update.getMessage().getText();
      long chat_id = update.getMessage().getChatId();

      SendMessage message = SendMessage // Create a message object
          .builder()
          .chatId(chat_id)
          .text(message_text)
          .build();
      try {
        telegramClient.execute(message); // Sending our message object to user
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
