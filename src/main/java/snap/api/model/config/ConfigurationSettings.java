package snap.api.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ConfigurationSettings (Organization with Ad Accounts)
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ConfigurationSettings {

  /** Expresses if the notifications are enabled */
  @JsonProperty("notifications_enabled")
  private boolean notificationsEnabled;
} // ConfigurationSettings
