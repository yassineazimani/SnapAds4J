package snap.api.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ConfigurationSettings {

  @JsonProperty("notifications_enabled")
  private boolean notificationsEnabled;
} // ConfigurationSettings
