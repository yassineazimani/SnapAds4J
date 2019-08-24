package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Yassine */
public enum SnapChatPositionsEnum {
  /** INTERSTITIAL_USER */
  @JsonProperty("INTERSTITIAL_USER")
  INTERSTITIAL_USER,
  /** INTERSTITIAL_CONTENT */
  @JsonProperty("INTERSTITIAL_CONTENT")
  INTERSTITIAL_CONTENT,
  /** INSTREAM */
  @JsonProperty("INSTREAM")
  INSTREAM,
  /** FEED */
  @JsonProperty("FEED")
  FEED,
  /** CAMERA */
  @JsonProperty("CAMERA")
  CAMERA;
} // SnapChatPositionsEnum
