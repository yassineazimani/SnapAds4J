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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationWithAdAccount extends Organization {

  @JsonProperty("contact_name")
  private String contact_name;

  @JsonProperty("contact_email")
  private String contact_email;

  @JsonProperty("contact_phone")
  private String contact_phone;

  @JsonProperty("tax_type")
  private String taxType;

  @JsonProperty("partner_orgs")
  private Map<String, String> partnerOrgs;

  @JsonProperty("accepted_term_version")
  private Double acceptedTermVersion;

  @JsonProperty("is_agency")
  private boolean isAgency;

  @JsonProperty("configuration_settings")
  private ConfigurationSettings configurationSettings;

  private String state;

  private List<String> roles;

  @JsonProperty("ad_accounts")
  private List<AdAccount> adAccounts;

  @JsonProperty("my_display_name")
  private String myDisplayName;

  @JsonProperty("my_invited_email")
  private String myInvitedEmail;

  @JsonProperty("my_member_id")
  private String myMemberId;
} // OrganizationWithAdAccount
