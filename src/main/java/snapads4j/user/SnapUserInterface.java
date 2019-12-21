/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.user;

import java.util.Optional;

import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.user.AuthenticatedUser;

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
   * @return AuthenticatedUser {@link AuthenticatedUser}
   * @throws SnapOAuthAccessTokenException
   * @throws SnapResponseErrorException
 * @throws SnapExecutionException 
   */
  Optional<AuthenticatedUser> aboutMe(String oAuthAccessToken)
      throws SnapOAuthAccessTokenException, SnapResponseErrorException, SnapExecutionException;
} // SnapUserInterface
