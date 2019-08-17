package snap.api.adaccount;

import java.util.List;
import java.util.Optional;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.adaccount.AdAccount;

public interface SnapAdAccountInterface {

  /**
   * Get all ad accounts
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-all-ad-accounts">All Ad
   *     Accounts</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @return All ad accounts
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   */
  public List<AdAccount> getAllAdAccounts(String oAuthAccessToken)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException;

  /**
   * Get specific ad account
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-a-specific-ad-account">Specific Ad
   *     Account</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id Organization ID
   * @return specific ad account
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   */
  public Optional<AdAccount> getSpecificAdAccount(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

  /**
   * Update a specific ad account
   *
   * @param adAccount ad account to update
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   */
  public void updateAdAccount(AdAccount adAccount)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;
} // SnapAdAccountInterface
