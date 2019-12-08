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
package snapads4j.model.adsquads;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.AdSquadTypeEnum;
import snapads4j.enums.BillingEventEnum;
import snapads4j.enums.ContentTypeEnum;
import snapads4j.enums.DeliveryConstraintEnum;
import snapads4j.enums.OptimizationGoalEnum;
import snapads4j.enums.PacingTypeEnum;
import snapads4j.enums.PlacementEnum;
import snapads4j.enums.ReachFrequencyStatusEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.model.targeting.Targeting;

/**
 * AdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class AdSquad {

  /** Ad Squad ID */
  private String id;
  
  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  /** Campaign ID */
  @JsonProperty("campaign_id")
  private String campaignId;

  /** Max Bid (micro-currency) */
  @JsonProperty("bid_micro")
  private Double bidMicro;

  /** Billing Event */
  @JsonProperty("billing_event")
  private BillingEventEnum billingEvent;

  /** Daily Budget (micro-currency) */
  @JsonProperty("daily_budget_micro")
  private Double dailyBudgetMicro;

  /** Lifetime budget (micro-currency) */
  @JsonProperty("lifetime_budget_micro")
  private Double lifetimeBudgetMicro;

  /** End time */
  @JsonProperty("end_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date endTime;

  /** Ad Squad name */
  private String name;

  /** Optimization Goal */
  @JsonProperty("optimization_goal")
  private OptimizationGoalEnum optimizationGoal;

  /** Placement */
  private PlacementEnum placement;

  /** Start time */
  @JsonProperty("start_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date startTime;

  /** Ad Squad status */
  private StatusEnum status;

  /** Targeting spec */
  private Targeting targeting;

  /** Ad Squad Type */
  private AdSquadTypeEnum type;

  /** Content Type to be included */
  @JsonProperty("included_content_types")
  private ContentTypeEnum includedContentTypes;

  /** Content Type to be excluded */
  @JsonProperty("excluded_content_types")
  private ContentTypeEnum excludedContentTypes;

  /** The frequency cap and exclusion spec */
  @JsonProperty("cap_and_exclusion_config")
  private CapAndExclusionConfig capAndExclusionConfig;

  /** The schedule for running ads */
  @JsonProperty("ad_scheduling_config")
  private AdSchedulingConfig adSchedulingConfig;

  /** Allow Snapchat to automatically set the bid to recommended amount */
  @JsonProperty("auto_bid")
  private boolean autoBid;

  /**
   * Allows Snapchat to adjust the bid aiming to keep your average CPA at or below the amount set by
   * the ad set end date
   */
  @JsonProperty("target_bid")
  private boolean targetBid;

  /** Pixel to be associated with the Ad Squad */
  @JsonProperty("pixel_id")
  private String pixelId;

  /** Status of the reach and frequency booking */
  @JsonProperty("reach_and_frequency_status")
  private ReachFrequencyStatusEnum reachAndFrequency_status;

  /** Type of delivery */
  @JsonProperty("delivery_constraint")
  private DeliveryConstraintEnum deliveryConstraint;

  /** Status of the reach and frequency booking */
  @JsonProperty("reach_goal")
  private Double reachGoal;

  /** Reach goal as specified in the Forecasting request */
  @JsonProperty("impression_goal")
  private Double impressionGoal;

  /** Advanced placement options */
  @JsonProperty("placement_v2")
  private PlacementV2 placementV2;

  /** Type of pacing */
  @JsonProperty("pacing_type")
  private PacingTypeEnum pacingType;
} // AdSquad
