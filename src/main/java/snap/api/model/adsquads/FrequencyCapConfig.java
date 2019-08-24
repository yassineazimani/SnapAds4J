package snap.api.model.adsquads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.FrequencyCapIntervalEnum;
import snap.api.enums.FrequencyCapTypeEnum;

@Getter
@Setter
@ToString
/**
 * FrequencyCapConfig
 *
 * <p>Frequency cap spec cap_and_exclusion_config allows you to specify the maximum number of times
 * a user is exposed to an ad over a period of time.
 *
 * @author Yassine
 */
public class FrequencyCapConfig {
  /** Number of times an ad is shown to the user in the interval */
  @JsonProperty("frequency_cap_count")
  private Integer frequencyCapCount;

  /** Event to be frequency capped */
  @JsonProperty("frequency_cap_type")
  private FrequencyCapTypeEnum frequencyCapType;

  /**
   * Interval during which the frequency cap rule is applied. frequency_cap_count is reset at the
   * end of the interval. (Max 30 days or 720 hours)
   */
  @JsonProperty("time_interval")
  private Integer timeInterval;

  /** Unit for time_interval */
  @JsonProperty("frequency_cap_interval")
  private FrequencyCapIntervalEnum frequencyCapInterval;
} // FrequencyCapConfig
