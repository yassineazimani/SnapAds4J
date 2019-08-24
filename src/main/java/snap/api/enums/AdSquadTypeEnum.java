package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AdSquadTypeEnum
 *
 * @author Yassine
 */
public enum AdSquadTypeEnum {
  @JsonProperty("SNAP_ADS")
  SNAP_ADS,
  @JsonProperty("LENS")
  LENS;
}
