package bot.farm.blank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
  @Value("${telegram.bot.token}")
  private String botToken;

  @Bean
  public String getBotToken() {
    return botToken;
  }
}
