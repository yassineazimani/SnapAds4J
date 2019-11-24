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
package snapads4j.model.campaigns;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.BuyModelEnum;
import snapads4j.enums.ObjectiveCampaignEnum;
import snapads4j.enums.StatusEnum;

/**
 * Campaign
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class Campaign {

  /** Campaign ID */
  private String id;

  /** Last date update of campaign */
  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  /** Creation date of campaign */
  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  private StatusEnum status;

  /** Ad Account ID */
  @JsonProperty("ad_account_id")
  private String adAccountId;

  /** Daily Spend Cap (micro-currency) */
  @JsonProperty("daily_budget_micro")
  private Double dailyBudgetMicro;

  /** Start time */
  @JsonProperty("start_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date startTime;

  /** End time */
  @JsonProperty("end_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date endTime;

  /** Campaign name */
  private String name;

  /** Lifetime spend cap for the campaign (microcurrency) */
  @JsonProperty("lifetime_spend_cap_micro")
  private Double lifetimeSpendCapMicro;

  /** The apps to be tracked for this campaign */
  @JsonProperty("measurement_spec")
  private MeasurementSpec measurementSpec;

  /** Objective of the Campaign */
  private ObjectiveCampaignEnum objective;

  /** Buy model */
  @JsonProperty("buy_model")
  private BuyModelEnum buyModel;
} // Campaign