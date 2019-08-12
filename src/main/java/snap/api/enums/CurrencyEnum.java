package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CurrencyEnum
 *
 * @author Yassine
 */
public enum CurrencyEnum {
  /** Dollars */
  @JsonProperty("USD")
  USD,

  /** Euros */
  @JsonProperty("EUR")
  EUR,

  /** Great Britain pounds */
  @JsonProperty("GBP")
  GBP,

  /** Australian dollars */
  @JsonProperty("AUD")
  AUD,

  /** Canadian dollars */
  @JsonProperty("CAD")
  CAD;
} // CurrencyEnum
