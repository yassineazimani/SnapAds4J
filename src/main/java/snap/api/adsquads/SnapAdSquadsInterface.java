package snap.api.adsquads;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.adsquads.AdSquad;

public interface SnapAdSquadsInterface {

  public void createAdSquad(String oAuthAccessToken, AdSquad adSquad)
      throws SnapOAuthAccessTokenException, JsonProcessingException, SnapResponseErrorException,
          SnapArgumentException;

  public void updateAdSquad(String oAuthAccessToken, AdSquad adSquad)
      throws SnapOAuthAccessTokenException, JsonProcessingException, SnapResponseErrorException,
          SnapArgumentException;

  public List<AdSquad> getAllAdSquadsFromCampaign(String oAuthAccessToken, String campaignId);

  public List<AdSquad> getAllAdSquadsFromAdAccount(String oAuthAccessToken, String adAccountId);

  public Optional<AdSquad> getSpecificAdSquad(String oAuthAccessToken, String id);

  /**
   * Deletes a specific ad squad.
   *
   * @param oAuthAccessToken oAuthAccessToken
   * @param id Ad Squad ID
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   */
  public void deleteAdSquad(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;
} // SnapAdSquadsInterface
