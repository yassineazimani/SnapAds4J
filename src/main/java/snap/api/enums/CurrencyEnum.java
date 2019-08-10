package snap.api.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CurrencyEnum {
  @JsonProperty("USD")
  USD,

  @JsonProperty("EUR")
  EUR,

  @JsonProperty("GBP")
  GBP,

  @JsonProperty("AUD")
  AUD,

  @JsonProperty("CAD")
  CAD;
} // CurrencyEnum
