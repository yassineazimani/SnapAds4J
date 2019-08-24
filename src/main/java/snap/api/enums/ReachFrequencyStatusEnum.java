package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ReachFrequencyStatusEnum
 *
 * @author Yassine
 */
public enum ReachFrequencyStatusEnum {
  @JsonProperty("PENDING")
  PENDING,
  @JsonProperty("ACTIVE")
  ACTIVE,
  @JsonProperty("FAILED")
  FAILED;
} // ReachFrequencyStatusEnum
