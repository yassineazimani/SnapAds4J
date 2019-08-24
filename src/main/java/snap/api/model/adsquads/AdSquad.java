package snap.api.model.adsquads;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.AdSquadTypeEnum;
import snap.api.enums.BillingEventEnum;
import snap.api.enums.ContentTypeEnum;
import snap.api.enums.DeliveryConstraintEnum;
import snap.api.enums.OptimizationGoalEnum;
import snap.api.enums.PacingTypeEnum;
import snap.api.enums.PlacementEnum;
import snap.api.enums.ReachFrequencyStatusEnum;
import snap.api.enums.StatusEnum;
import snap.api.model.targeting.Targeting;

/**
 * AdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class AdSquad {

  /** Ad Squad ID */
  private String id;

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
  private Date endTime;

  /** Ad Squad name */
  private String name;

  /** Optimization Goal */
  @JsonProperty("optimization_goal")
  private OptimizationGoalEnum optimizationGoal;

  /** Placement */
  private PlacementEnum placement;

  /** Start time */
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
