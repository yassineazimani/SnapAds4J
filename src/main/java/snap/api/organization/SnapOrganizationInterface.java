package snap.api.organization;

import java.util.List;
import java.util.Optional;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.organization.Organization;
import snap.api.model.organization.OrganizationWithAdAccount;

public interface SnapOrganizationInterface {

  public List<Organization> getAllOrganizations(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException;

  public Optional<Organization> getSpecificOrganization(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

  List<OrganizationWithAdAccount> getAllOrganizationsWithAdAccounts(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException;
} // SnapOrganizationInterface
