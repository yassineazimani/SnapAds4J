package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FundingSourceTypeEnum
 *
 * @author Yassine
 */
public enum FundingSourceTypeEnum {
  /** Line of credit */
  @JsonProperty("LINE_OF_CREDIT")
  LINE_OF_CREDIT,
  /** Credit Card */
  @JsonProperty("CREDIT_CARD")
  CREDIT_CARD,
  /** Coupon */
  @JsonProperty("COUPON")
  COUPON,
  /** Paypal */
  @JsonProperty("PAYPAL")
  PAYPAL;
} // FundingSourceTypeEnum
