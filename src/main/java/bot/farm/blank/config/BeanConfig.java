package bot.farm.blank.config;

import bot.farm.blank.Bot;
import bot.farm.blank.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    private final BotConfig botConfig;
    private final MessageService messageService;

    @Bean
    Bot bot() {
        Bot bot = new Bot(messageService);
        bot.setBotUsername(botConfig.getBotName());
        bot.setBotToken(botConfig.getBotToken());

        return bot;
    }
}
