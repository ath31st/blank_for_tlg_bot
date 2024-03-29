package bot.farm.blank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final ButtonService buttonService;

  public SendMessage createMessage(String chatId, String message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdownV2(true);
    sendMessage.enableHtml(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    return sendMessage;
  }

  public SendMessage createMenuMessage(String chatId, String message) {

    SendMessage sendMessage = createMessage(chatId, message);
    InlineKeyboardMarkup inlineKeyboardMarkup = buttonService.setInlineKeyMarkup(buttonService.createInlineButton());
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }

}