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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.*;
import snapads4j.model.AbstractSnapModel;
import snapads4j.model.targeting.Targeting;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * AdSquad
 *
 * @see {https://developers.snapchat.com/api/docs/#ad-squads}
 * @author Yassine
 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class AdSquad extends AbstractSnapModel {

    /**
     * Campaign ID
     */
    @JsonProperty("campaign_id")
    @NotEmpty(message = "The Campaign ID is required")
    private String campaignId;

    /**
     * Max Bid (micro-currency)
     */
    @JsonProperty("bid_micro")
    private Double bidMicro;

    /**
     * Min Roas
     */
    @JsonProperty("roas_value_micro")
    private Double roasValueMicro;

    /**
     * Billing Event
     */
    @JsonProperty("billing_event")
    private BillingEventEnum billingEvent;

    /**
     * Daily Budget (micro-currency)
     */
    @JsonProperty("daily_budget_micro")
    @NotNull(message = "The daily budget micro is required")
    @Min(value = 5000000, message = "The daily budget micro minimum value is 5000000")
    private Double dailyBudgetMicro;

    /**
     * Lifetime budget (micro-currency)
     */
    @JsonProperty("lifetime_budget_micro")
    @NotNull(message = "The lifetime budget micro is required")
    private Double lifetimeBudgetMicro;

    /**
     * End time
     */
    @JsonProperty("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date endTime;

    /**
     * Ad Squad name
     */
    @NotEmpty(message = "The Ad Squad name is required")
    private String name;

    /**
     * Optimization Goal
     */
    @JsonProperty("optimization_goal")
    private OptimizationGoalEnum optimizationGoal;

    /**
     * Start time
     */
    @JsonProperty("start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date startTime;

    /**
     * Ad Squad status
     */
    @NotNull(message = "The status is required")
    private StatusEnum status;

    /**
     * Targeting spec
     */
    @NotNull(message = "The targeting is required")
    private Targeting targeting;

    /**
     * Ad Squad Type
     */
    private AdSquadTypeEnum type;

    /**
     * Content Type to be included
     */
    @JsonProperty("included_content_types")
    private ContentTypeEnum includedContentTypes;

    /**
     * Content Type to be excluded
     */
    @JsonProperty("excluded_content_types")
    private ContentTypeEnum excludedContentTypes;

    /**
     * The frequency cap and exclusion spec
     */
    @JsonProperty("cap_and_exclusion_config")
    private CapAndExclusionConfig capAndExclusionConfig;

    /**
     * The schedule for running ads
     */
    @JsonProperty("ad_scheduling_config")
    private AdSchedulingConfig adSchedulingConfig;

    /**
     * Replaces attributes auto_bid and target_bid.
     */
    @JsonProperty("bid_strategy")
    @NotNull(message = "Bid Strategy is required")
    private BidStrategyEnum bidStrategy;

    /**
     * Pixel to be associated with the Ad Squad
     */
    @JsonProperty("pixel_id")
    private String pixelId;

    /**
     * Status of the reach and frequency booking
     */
    @JsonProperty("reach_and_frequency_status")
    private ReachFrequencyStatusEnum reachAndFrequencyStatus;

    /**
     * Type of delivery
     */
    @JsonProperty("delivery_constraint")
    private DeliveryConstraintEnum deliveryConstraint;

    /**
     * Status of the reach and frequency booking
     */
    @JsonProperty("reach_goal")
    private Double reachGoal;

    /**
     * Reach goal as specified in the Forecasting request
     */
    @JsonProperty("impression_goal")
    private Double impressionGoal;

    /**
     * Advanced placement options
     */
    @JsonProperty("placement_v2")
    private PlacementV2 placementV2;

    /**
     * Type of pacing
     */
    @JsonProperty("pacing_type")
    private PacingTypeEnum pacingType;
} // AdSquad
