package snap.api.model.adsquads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CapAndExclusionConfig {

  @JsonProperty("frequency_cap_config")
  private FrequencyCapConfig frequencyCapConfig;
} // CapAndExclusionConfig
