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

/**
 * AdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class AdSquad {

  private String id;

  @JsonProperty("campaign_id")
  private String campaignId;

  @JsonProperty("bid_micro")
  private Double bidMicro;

  @JsonProperty("billing_event")
  private BillingEventEnum billingEvent;

  @JsonProperty("daily_budget_micro")
  private Double dailyBudgetMicro;

  @JsonProperty("lifetime_budget_micro")
  private Double lifetimeBudgetMicro;

  private Date endTime;

  private String name;

  @JsonProperty("optimization_goal")
  private OptimizationGoalEnum optimizationGoal;

  private PlacementEnum placement;

  private Date startTime;

  private StatusEnum status;

  private String targeting;

  private AdSquadTypeEnum type;

  @JsonProperty("included_content_types")
  private ContentTypeEnum includedContentTypes;

  @JsonProperty("excluded_content_types")
  private ContentTypeEnum excludedContentTypes;

  @JsonProperty("cap_and_exclusion_config")
  private CapAndExclusionConfig capAndExclusionConfig;

  @JsonProperty("ad_scheduling_config")
  private AdSchedulingConfig adSchedulingConfig;

  @JsonProperty("auto_bid")
  private boolean autoBid;

  @JsonProperty("target_bid")
  private boolean targetBid;

  @JsonProperty("pixel_id")
  private String pixelId;

  @JsonProperty("reach_and_frequency_status")
  private ReachFrequencyStatusEnum reachAndFrequency_status;

  @JsonProperty("delivery_constraint")
  private DeliveryConstraintEnum deliveryConstraint;

  @JsonProperty("reach_goal")
  private Double reachGoal;

  @JsonProperty("impression_goal")
  private Double impressionGoal;

  @JsonProperty("placement_v2")
  private PlacementV2 placementV2;

  @JsonProperty("pacing_type")
  private PacingTypeEnum pacingType;
} // AdSquad
