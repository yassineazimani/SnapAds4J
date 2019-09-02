package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OperationEnum
 *
 * @author Yassine
 */
public enum OperationEnum {
  @JsonProperty("INCLUDE")
  INCLUDE,
  @JsonProperty("EXCLUDE")
  EXCLUDE;
} // OperationEnum
