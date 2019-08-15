package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Status enum
 *
 * @author Yassine
 */
public enum StatusEnum {
  /** Status active */
  @JsonProperty("ACTIVE")
  ACTIVE,
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
