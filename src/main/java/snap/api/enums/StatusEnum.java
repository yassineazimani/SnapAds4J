package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Status enum
 *
 * @author Yassine
 */
public enum StatusEnum {
  /** Status actived */
  @JsonProperty("ACTIVE")
  ACTIVE,
  /** Status paused */
  @JsonProperty("PAUSE")
  PAUSE,
  /** Status paused */
  @JsonProperty("PAUSED")
  PAUSED,
  /** Status redemmed */
  @JsonProperty("REDEEMED")
  REDEEMED,
  /** Status spent */
  @JsonProperty("SPENT")
  SPENT,
  /** Status expired */
  @JsonProperty("EXPIRED")
  EXPIRED,
  /** Status deleted */
  @JsonProperty("DELETED")
  DELETED;
} // StatusEnum
