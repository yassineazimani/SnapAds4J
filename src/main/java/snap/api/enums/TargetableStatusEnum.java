package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TargetableStatusEnum
 *
 * @author Yassine
 */
public enum TargetableStatusEnum {
  /** This segment won’t work when used in targeting */
  @JsonProperty("NO_UPLOAD")
  NOT_READY,
  /** This segment doesn’t have enough users to target */
  @JsonProperty("FIRST_PARTY")
  TOO_FEW_USERS,
  /** Segment is ready to target */
  @JsonProperty("FIRST_PARTY")
  READY;
} // TargetableStatusEnum
