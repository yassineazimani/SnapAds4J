package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FrequencyCapIntervalEnum
 *
 * <p>Unit for time_interval
 *
 * @author Yassine
 */
public enum FrequencyCapIntervalEnum {
  /** Hours */
  @JsonProperty("HOURS")
  HOURS,
  /** Days */
  @JsonProperty("DAYS")
  DAYS;
} // FrequencyCapIntervalEnum
