package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CampaignTrackTypeEnum {
  @JsonProperty("APP_INSTALL")
  APP_INSTALL,
  @JsonProperty("DEEP_LINK")
  DEEP_LINK,
  @JsonProperty("STORY")
  STORY,
  @JsonProperty("LENS_APP_INSTALL")
  LENS_APP_INSTALL,
  @JsonProperty("LENS_DEEP_LINK")
  LENS_DEEP_LINK;
} // MeasurementSpecEnum
