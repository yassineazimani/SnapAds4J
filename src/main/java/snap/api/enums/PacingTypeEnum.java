package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PacingTypeEnum
 *
 * @author Yassine
 */
public enum PacingTypeEnum {
  @JsonProperty("STANDARD")
  STANDARD,
  @JsonProperty("ACCELERATED")
  ACCELERATED;
} // PacingTypeEnum
