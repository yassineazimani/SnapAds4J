package snap.api.fundingsource;

import java.util.List;
import java.util.Optional;

import snap.api.exceptions.SnapArgumentException;
import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.fundingsource.FundingSource;

/**
 * SnapFundingSourceInterface
 *
 * @author Yassine
 */
public interface SnapFundingSourceInterface {

  /**
   * Get all funding sources for the specified Organization.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-all-funding-sources">All funding
   *     source</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param organizationID Organization ID
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @return funding sources
   */
  public List<FundingSource> getAllFundingSource(String oAuthAccessToken, String organizationID)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;

  /**
   * Get a specific funding source.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#get-a-specific-funding-source">Specific
   *     funding source</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @param id FundingSource ID
   * @throws SnapResponseErrorException
   * @throws SnapOAuthAccessTokenException
   * @throws SnapArgumentException
   * @return funding source
   */
  public Optional<FundingSource> getSpecificFundingSource(String oAuthAccessToken, String id)
      throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException;
} // SnapFundingSourceInterface
