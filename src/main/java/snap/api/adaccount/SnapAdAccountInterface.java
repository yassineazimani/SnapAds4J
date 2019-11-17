package snap.api.adaccount;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

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
   * @param organizationID Organization ID
   * @return All ad accounts
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   */
  public List<AdAccount> getAllAdAccounts(String oAuthAccessToken, String organizationID)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

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
   * @see <a
   *     href="https://developers.snapchat.com/api/docs/#update-an-ad-accounts-lifetime-spend-cap">Update
   *     Ad Account</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param adAccount ad account to update
 * @return 
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @throws JsonProcessingException
   * @throws UnsupportedEncodingException 
   * 
   * @return AdAccount updated
   */
  public Optional<AdAccount> updateAdAccount(String oAuthAccessToken, AdAccount adAccount)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
          JsonProcessingException, UnsupportedEncodingException;
} // SnapAdAccountInterface
