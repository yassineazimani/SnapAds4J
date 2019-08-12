package snap.api.model.organization;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import snap.api.model.config.ConfigurationSettings;

/**
 * Organization with ad account
 *
 * @author Yassine
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationWithAdAccount extends Organization {

  /** Contact name */
  @JsonProperty("contact_name")
  private String contact_name;

  /** Contact email */
  @JsonProperty("contact_email")
  private String contact_email;

  /** Contact phone */
  @JsonProperty("contact_phone")
  private String contact_phone;

  /** Tax type */
  @JsonProperty("tax_type")
  private String taxType;

  /** Partner organizations */
  @JsonProperty("partner_orgs")
  private Map<String, String> partnerOrgs;

  /** Accepted term version */
  @JsonProperty("accepted_term_version")
  private Double acceptedTermVersion;

  /** Is agency ? */
  @JsonProperty("is_agency")
  private boolean isAgency;

  /** Configuration settings */
  @JsonProperty("configuration_settings")
  private ConfigurationSettings configurationSettings;

  /** State */
  private String state;

  /**
   * Roles
   *
   * @see <a href="https://businesshelp.snapchat.com/en-US/a/roles-permissions">Roles
   *     permissions</a>
   */
  private List<String> roles;

  /** Ad accounts bound to this organization */
  @JsonProperty("ad_accounts")
  private List<AdAccount> adAccounts;

  /** Display name */
  @JsonProperty("my_display_name")
  private String myDisplayName;

  /** Invited email */
  @JsonProperty("my_invited_email")
  private String myInvitedEmail;

  /** Member ID */
  @JsonProperty("my_member_id")
  private String myMemberId;
} // OrganizationWithAdAccount
