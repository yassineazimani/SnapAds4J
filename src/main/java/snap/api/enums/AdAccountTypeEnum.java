package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AdAccountTypeEnum {
  /** Direct */
  @JsonProperty("DIRECT")
  DIRECT,

  /** Partner */
  @JsonProperty("PARTNER")
  PARTNER,
} // AdAccountTypeEnum
