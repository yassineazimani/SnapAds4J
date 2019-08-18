package snap.api.model.adaccount;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.AdAccountTypeEnum;
import snap.api.enums.CurrencyEnum;
import snap.api.enums.StatusEnum;

/**
 * AdAccount
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class AdAccount {

  /** AD Account ID */
  private String id;

  /** Last date update of ad account */
  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  /** Creation date update of ad account */
  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  /** AD Account Status */
  private StatusEnum status;

  /** Name of the Advertiser */
  private String advertiser;

  /** Account currency */
  private CurrencyEnum currency;

  /** Account name */
  private String name;

  /** Organization ID */
  @JsonProperty("organization_id")
  private String organizationId;

  /** Timezone */
  private String timezone;

  private AdAccountTypeEnum type;

  /** Lifetime Spend Limit for Account (micro currency) */
  @JsonProperty("lifetime_spend_cap_micro")
  private Double lifetimeSpendCapMicro;

  /** Organization ID of the Advertiser selected */
  @JsonProperty("advertiser_organization_id")
  private String advertiserOrganizationId;

  /** List of Funding Source IDs */
  @JsonProperty("funding_source_ids")
  private List<String> fundingSourceIds;

  /** Brand name */
  @JsonProperty("brand_name")
  private String brandName;
} // AdAccount
