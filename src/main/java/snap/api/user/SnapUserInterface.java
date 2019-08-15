package snap.api.user;

import java.util.Optional;

import snap.api.exceptions.SnapOAuthAccessTokenException;
import snap.api.exceptions.SnapResponseErrorException;
import snap.api.model.user.AuthenticatedUser;

/**
 * SnapUserInterface
 *
 * @author Yassine
 */
public interface SnapUserInterface {

  /**
   * Get informations about the authenticated user.
   *
   * @see <a href="https://developers.snapchat.com/api/docs/#user">User</a>
   * @param oAuthAccessToken oAuthAccessToken
   * @return AuthenticatedUser {@link #AuthenticatedUser}
   * @throws SnapOAuthAccessTokenException
   * @throws SnapResponseErrorException
   */
  public Optional<AuthenticatedUser> aboutMe(String oAuthAccessToken)
      throws SnapOAuthAccessTokenException, SnapResponseErrorException;
} // SnapUserInterface
