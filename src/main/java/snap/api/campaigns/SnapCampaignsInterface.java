package snap.api.campaigns;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.campaigns.Campaign;

/**
 * SnapCampaignsInterface
 *
 * @author Yassine
 */
public interface SnapCampaignsInterface {

  /**
   * Create a campaign.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#create-a-campaign">Create campaign</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param campaign Campaign to create {@link #Campaign}
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @throws JsonProcessingException
 * @throws UnsupportedEncodingException 
   */
  public void createCampaign(String oAuthAccessToken, Campaign campaign)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
          JsonProcessingException, UnsupportedEncodingException;

  /**
   * Update a campaign.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#update-a-campaign">Update campaign</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param campaign Campaign to update {@link #Campaign}
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @throws JsonProcessingException
 * @throws UnsupportedEncodingException 
   */
  public void updateCampaign(String oAuthAccessToken, Campaign campaign)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
          JsonProcessingException, UnsupportedEncodingException;

  /**
   * Get all campaigns of an ad account.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-all-campaigns">Get all
   *     campaigns</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param adAccountId AD Account ID
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @return all campaigns {@link #Campaign}
   */
  public List<Campaign> getAllCampaigns(String oAuthAccessToken, String adAccountId)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;;

  /**
   * Get specific campaign.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-a-specific-campaign">Get specific
   *     campaign</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id Campaign ID to get
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @return campaign {@link #Campaign}
   */
  public Optional<Campaign> getSpecificCampaign(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;;

  /**
   * Delete a specific campaign.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#delete-a-specific-campaign">Delete
   *     campaign</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id Campaign ID to delete
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   */
  public void deleteCampaign(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;;
} // SnapCampaignsInterface
