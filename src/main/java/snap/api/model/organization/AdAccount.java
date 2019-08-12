package snap.api.model.organization;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.CurrencyEnum;
import snap.api.enums.StatusEnum;
import snap.api.enums.TypeOrganizationEnum;

/**
 * Ad Accounts handle Ads, billing information, and allows you to manage your Campaigns.
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
public class AdAccount {

  /** Ad account ID */
  private String id;

  /** Last date update of Ad account */
  @JsonProperty("updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date updatedAt;

  /** Date creation of Ad account */
  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private Date createdAt;

  /** Ad account name */
  private String name;

  /** Type organization */
  private TypeOrganizationEnum type;

  /** Status */
  private StatusEnum status;

  /** Currency used */
  private CurrencyEnum currency;

  /** Timezone */
  private String timezone;

  /**
   * Ad account roles
   *
   * @see <a href="https://businesshelp.snapchat.com/en-US/a/roles-permissions">Roles
   *     permissions</a>
   */
  private List<String> roles;
} // AdAccount
