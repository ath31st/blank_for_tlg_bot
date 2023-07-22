package bot.farm.blank.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BotConfig {
  @Value("${telegram.bot.name}")
  private String botName;
  @Value("${telegram.bot.token}")
  private String botToken;
}
