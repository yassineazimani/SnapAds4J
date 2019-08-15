package snap.api.model.fundingsource;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.CreditCardTypeEnum;
import snap.api.enums.CurrencyEnum;
import snap.api.enums.FundingSourceTypeEnum;
import snap.api.enums.StatusEnum;

@Getter
@Setter
@ToString
public class FundingSource {

  private String id;

  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  @JsonProperty("start_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date startDate;

  @JsonProperty("end_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date endDate;

  private FundingSourceTypeEnum type;

  private StatusEnum status;

  @JsonProperty("total_budget_micro")
  private Double totalBudgetMicro;

  @JsonProperty("budget_spent_micro")
  private Double budgetSpentMicro;

  @JsonProperty("available_credit_micro")
  private Double availableCreditMicro;

  @JsonProperty("card_type")
  private CreditCardTypeEnum cardType;

  @JsonProperty("last_4")
  private Integer last4;

  @JsonProperty("expiration_year")
  private Integer expirationYear;

  @JsonProperty("expiration_month")
  private Integer expirationMonth;

  @JsonProperty("name")
  private String nameCreditCard;

  @JsonProperty("organization_id")
  private String organizationId;

  private CurrencyEnum currency;

  @JsonProperty("daily_spend_limit_micro")
  private Double dailySpendLimitMicro;

  @JsonProperty("daily_spend_currency")
  private CurrencyEnum dailySpendCurrency;

  @JsonProperty("value_micro")
  private Double valueMicro;

  @JsonProperty("email")
  private String emailPaypal;

  @JsonProperty("credit_account_type")
  private String creditAccountType;
} // FundingSource
