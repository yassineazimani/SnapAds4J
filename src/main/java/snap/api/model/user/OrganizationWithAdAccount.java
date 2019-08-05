package snap.api.model.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snap.api.enums.TypeUserEnum;
import snap.api.model.config.ConfigurationSettings;

@Getter
@Setter
@ToString
public class OrganizationWithAdAccount extends Organization {
	
	private Map<String, String> partnerOrgs;
	
	private Double acceptedTermVersion;
		
	private boolean isAgency;
	
	private ConfigurationSettings configurationSettings;
	
	private String state;
	
	private List<String> roles;
	
	private List<AdAccount> adAccounts;
	
	private String myDisplayName;
	
	private String myInvitedEmail;
	
	private String myMemberId;
	
	
	public OrganizationWithAdAccount(
      String id,
      Date updatedAt,
      Date createdAt,
      String name,
      String addressLine1,
      String locality,
      String administrativeDistrictLevel1,
      String country,
      String postalCode,
      TypeUserEnum type) {
    super(id,updatedAt,createdAt,name,addressLine1,locality,administrativeDistrictLevel1,country,postalCode,type);

  }// OrganizationWithAdAccount()

	
}// OrganizationWithAdAccount
