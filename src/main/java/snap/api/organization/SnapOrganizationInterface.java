package snap.api.organization;

import java.util.List;
import java.util.Optional;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.organization.Organization;
import snap.api.model.organization.OrganizationWithAdAccount;

public interface SnapOrganizationInterface {

  /**
   * Get all organizations
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#organizations">All organizations</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @return All organizations
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   */
  public List<Organization> getAllOrganizations(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException;

  /**
   * Get specific organization
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#organizations">Specific
   *     organization</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id Organization ID
   * @return All organizations
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   */
  public Optional<Organization> getSpecificOrganization(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

  /**
   * Get all organizations with ad accounts
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#organizations">All organizations with
   *     ad accounts</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @return All organizations
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   */
  public List<OrganizationWithAdAccount> getAllOrganizationsWithAdAccounts(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException;
} // SnapOrganizationInterface
