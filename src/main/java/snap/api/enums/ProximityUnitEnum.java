package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ProximityUnitEnum <br>
 * Unit to be used for radius.
 *
 * @author Yassine
 */
public enum ProximityUnitEnum {
  @JsonProperty("METERS")
  METERS,
  @JsonProperty("MILES")
  MILES,
  @JsonProperty("FEET")
  FEET,
  @JsonProperty("KILOMETERS")
  KILOMETERS;
} // ProximityUnitEnum
