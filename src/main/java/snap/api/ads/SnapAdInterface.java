package snap.api.ads;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.ads.Ad;

/**
 * 
 * @author yassine
 *
 */
public interface SnapAdInterface {

	/**
	 * Create an Ad
	 * 
	 * @param oAuthAccessToken oAuthAccessToken
	 * @param ad               Ad model
	 * @throws SnapOAuthAccessTokenException
	 * @throws JsonProcessingException
	 * @throws SnapResponseErrorException
	 * @throws SnapArgumentException
	 * @throws UnsupportedEncodingException 
	 */
	public void createAd(String oAuthAccessToken, Ad ad) throws SnapOAuthAccessTokenException, JsonProcessingException,
			SnapResponseErrorException, SnapArgumentException, UnsupportedEncodingException;

	/**
	 * Update an Ad
	 * 
	 * @param oAuthAccessToken oAuthAccessToken
	 * @param ad               Ad model
	 * @throws SnapOAuthAccessTokenException
	 * @throws JsonProcessingException
	 * @throws SnapResponseErrorException
	 * @throws SnapArgumentException
	 * @throws UnsupportedEncodingException 
	 */
	public void updateAd(String oAuthAccessToken, Ad ad) throws SnapOAuthAccessTokenException, JsonProcessingException,
			SnapResponseErrorException, SnapArgumentException, UnsupportedEncodingException;

	/**
	 * 
	 * @param oAuthAccessToken oAuthAccessToken
	 * @param adSquadId        AdSquadID
	 * @return List of {@link Ad}
	 * @throws SnapArgumentException
	 * @throws SnapOAuthAccessTokenException
	 * @throws SnapResponseErrorException
	 */
	public List<Ad> getAllAdsFromAdSquad(String oAuthAccessToken, String adSquadId)
			throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException;

	/**
	 * 
	 * @param oAuthAccessToken oAuthAccessToken
	 * @param adAccountId      AdAccountID
	 * @return List of {@link Ad}
	 * @throws SnapArgumentException
	 * @throws SnapOAuthAccessTokenException
	 * @throws SnapResponseErrorException
	 */
	public List<Ad> getAllAdsFromAdAccount(String oAuthAccessToken, String adAccountId)
			throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException;

	/**
	 * 
	 * @param oAuthAccessToken oAuthAccessToken
	 * @param id               Ad ID
	 * @return Optional of {@link Ad}
	 * @throws SnapArgumentException
	 * @throws SnapOAuthAccessTokenException
	 * @throws SnapResponseErrorException
	 */
	public Optional<Ad> getSpecificAd(String oAuthAccessToken, String id)
			throws SnapArgumentException, SnapOAuthAccessTokenException, SnapResponseErrorException;

	/**
	 * Deletes a specific ad.
	 *
	 * @param oAuthAccessToken oAuthAccessToken
	 * @param id               Ad ID
	 * @throws SnapResponseErrorException
	 * @throws SnapOAuthAccessTokenException
	 * @throws SnapArgumentException
	 */
	public void deleteAd(String oAuthAccessToken, String id)
			throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;
}// SnapAdInterface
