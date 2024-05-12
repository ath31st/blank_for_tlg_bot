package bot.farm.blank;

import bot.farm.blank.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
public class Bot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
  private final TelegramClient telegramClient;
  private final String botToken;
  private final MessageService messageService;

  public Bot(@Value("${telegram.bot.token}") String botToken, MessageService messageService) {
    this.botToken = botToken;
    this.messageService = messageService;
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
        telegramClient.execute(messageService.createMessage(chatId, text));
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
