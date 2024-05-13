package bot.farm.blank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeyboardService {
  public InlineKeyboardMarkup createInlineKeyboardMarkup() {
    return InlineKeyboardMarkup
        .builder()
        .keyboardRow(
            new InlineKeyboardRow(InlineKeyboardButton
                .builder()
                .text("Update message text")
                .callbackData("update_msg_text")
                .build()
            )
        )
        .build();
  }
}
