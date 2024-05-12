package bot.farm.blank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
  public SendMessage createMessage(long chatId, String text) {
    SendMessage message = SendMessage
        .builder()
        .chatId(chatId)
        .text(text)
        .build();

    message.enableMarkdownV2(true);
    message.disableNotification(); // if it needed
    message.enableHtml(true);

    return message;
  }
}
