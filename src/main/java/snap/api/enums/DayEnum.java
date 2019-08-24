package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DayEnum
 *
 * @author Yassine
 */
public enum DayEnum {
  @JsonProperty("monday")
  MONDAY,
  @JsonProperty("tuesday")
  TUESDAY,
  @JsonProperty("wednesday")
  WEDNESDAY,
  @JsonProperty("thursday")
  THURSDAY,
  @JsonProperty("friday")
  FRIDAY,
  @JsonProperty("saturday")
  SATURDAY,
  @JsonProperty("sunday")
  SUNDAY;
} // DayEnum
