package bot.farm.blank.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramBotService {
  void processUpdate(Update update);
}
