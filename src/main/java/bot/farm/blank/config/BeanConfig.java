package bot.farm.blank.config;

import bot.farm.blank.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    private final BotConfig botConfig;

    @Bean
    Bot bot() {
        Bot bot = new Bot();
        bot.setBotUsername(botConfig.getBotName());
        bot.setBotToken(botConfig.getBotToken());

        return bot;
    }
}
