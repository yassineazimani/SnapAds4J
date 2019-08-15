package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CreditCardTypeEnum
 *
 * @author Yassine
 */
public enum CreditCardTypeEnum {
  /** Amex */
  @JsonProperty("AMEX")
  AMEX,

  /** Diners Club */
  @JsonProperty("DINERS_CLUB")
  DINERS_CLUB,

  /** Discover */
  @JsonProperty("DISCOVER")
  DISCOVER,

  /** Jcb */
  @JsonProperty("JCB")
  JCB,

  /** Maestro */
  @JsonProperty("MAESTRO")
  MAESTRO,

  /** MasterCard */
  @JsonProperty("MASTERCARD")
  MASTERCARD,

  /** Visa */
  @JsonProperty("VISA")
  VISA,

  /** Unknown */
  @JsonProperty("UNKNOWN")
  UNKNOWN;
} // CreditCardTypeEnum
