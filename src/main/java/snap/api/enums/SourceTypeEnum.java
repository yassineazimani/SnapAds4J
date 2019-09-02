package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data source type
 *
 * @author Yassine
 */
public enum SourceTypeEnum {
  @JsonProperty("FIRST_PARTY")
  FIRST_PARTY,
  @JsonProperty("ENGAGEMENT")
  ENGAGEMENT,
  @JsonProperty("PIXEL")
  PIXEL,
  @JsonProperty("FOOT_TRAFFIC_INSIGHTS")
  FOOT_TRAFFIC_INSIGHTS;
} // SourceTypeEnum
