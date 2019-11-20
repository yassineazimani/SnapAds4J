/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.model.fundingsource;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.CreditCardTypeEnum;
import snapads4j.enums.CurrencyEnum;
import snapads4j.enums.FundingSourceTypeEnum;
import snapads4j.enums.StatusEnum;

/**
 * FundingSource
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class FundingSource {

  /** Funding Source ID */
  private String id;

  /** Last date update of funding source */
  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  /** Creation date update of funding source */
  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  /** Start date of the COUPON */
  @JsonProperty("start_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date startDate;

  /** End date of the COUPON */
  @JsonProperty("end_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date endDate;

  /** Funding Source type */
  private FundingSourceTypeEnum type;

  /** Funding source status */
  private StatusEnum status;

  /** Total Budget (micro-currency) */
  @JsonProperty("total_budget_micro")
  private Double totalBudgetMicro;

  /** Budget Spent (micro-currency) */
  @JsonProperty("budget_spent_micro")
  private Double budgetSpentMicro;

  /** Total available credit */
  @JsonProperty("available_credit_micro")
  private Double availableCreditMicro;

  /** Credit Card Type */
  @JsonProperty("card_type")
  private CreditCardTypeEnum cardType;

  /** Last 4 digits of the Credit Card */
  @JsonProperty("last_4")
  private Integer last4;

  /** Expiration year of the Credit Card */
  @JsonProperty("expiration_year")
  private Integer expirationYear;

  /** Expiration month of the Credit Card */
  @JsonProperty("expiration_month")
  private Integer expirationMonth;

  /** Name of the Credit Card */
  @JsonProperty("name")
  private String nameCreditCard;

  /** Organization ID */
  @JsonProperty("organization_id")
  private String organizationId;

  /** Account currency */
  private CurrencyEnum currency;

  /** Daily spend limit for Credit Card (micro-currency) */
  @JsonProperty("daily_spend_limit_micro")
  private Double dailySpendLimitMicro;

  /** Currency for the daily_spend_limit_micro */
  @JsonProperty("daily_spend_currency")
  private CurrencyEnum dailySpendCurrency;

  /** Value of the COUPON (micro-currency) */
  @JsonProperty("value_micro")
  private Double valueMicro;

  /** Email associated with Paypal */
  @JsonProperty("email")
  private String emailPaypal;

  /** Credit Account Type */
  @JsonProperty("credit_account_type")
  private String creditAccountType;
} // FundingSource
