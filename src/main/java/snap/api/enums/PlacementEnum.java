package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PlacementEnum {
  @JsonProperty("SNAP_ADS")
  SNAP_ADS,
  @JsonProperty("CONTENT")
  CONTENT,
  @JsonProperty("USER_STORIES")
  USER_STORIES,
  @JsonProperty("DISCOVER_FEED")
  DISCOVER_FEED;
} // PlacementEnum
