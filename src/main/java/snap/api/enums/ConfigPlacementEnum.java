package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ConfigPlacementEnum
 *
 * @author Yassine
 */
public enum ConfigPlacementEnum {
  /** AUTOMATIC */
  @JsonProperty("AUTOMATIC")
  AUTOMATIC,
  /** CUSTOM */
  @JsonProperty("CUSTOM")
  CUSTOM;
} // ConfigPlacementEnum
