package snap.api.user;

import java.util.List;

import snap.api.model.user.Organization;

public interface SnapUserInterface {
	
	public List<Organization> getAllOrganizations(boolean withAdAccounts);
	
	public Organization getSpecificOrganization();
	
}// SnapUserInterface
